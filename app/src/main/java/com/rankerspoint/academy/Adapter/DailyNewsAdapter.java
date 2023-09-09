package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.DailyNewsModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyNewsAdapter extends RecyclerView.Adapter<DailyNewsAdapter.BannerImage> {


    List<DailyNewsModel> getPreEaxmModels;

    Context context;

    public DailyNewsAdapter(Context context, List<DailyNewsModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;


    }

    @NonNull
    @Override
    public DailyNewsAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.daily_news_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyNewsAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final DailyNewsModel getPreEaxmModel = getPreEaxmModels.get(position);
        try {
            //dateformat
            String datef = getPreEaxmModel.getAddDate();
            String datae=datef.substring(0,10);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(datae);
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            holder.txt_date.setText(dt1.format(date));
        }catch (Exception ex)
        {
            Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
        }
       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.img);
        holder.classe_txt.setText(getPreEaxmModel.getTitle());
        holder.txt_gkname.setText(getPreEaxmModel.getGKName());
        holder.details_txt.setText(getPreEaxmModel.getDetailsLag1());
        holder.txt_readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String Url=   getPreEaxmModel.getLink();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                i.setData(Uri.parse(Url));
                context.startActivity(i);
            }
        });

       // holder.img.setImageResource(images.get(position));


    }

    @Override
    public int getItemCount() {
        return getPreEaxmModels.size();
//        if (searchList.size() > 0) {
//            return searchList.size();
//        } else {
//            return 0;
//        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView classe_txt,txt_date,txt_gkname,details_txt,txt_readMore;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            classe_txt=itemView.findViewById(R.id.txt_title);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_gkname=itemView.findViewById(R.id.txt_gkname);
            details_txt=itemView.findViewById(R.id.details_txt);
            txt_readMore=itemView.findViewById(R.id.txt_readMore);

            img=itemView.findViewById(R.id.image_video);

        }
        // each data item is just a string in this case

    }
//    public  void filter(String search)
//    {
//        searchList.clear();
//        search=search.toLowerCase();
//        for (GetPreEaxmModel model:getPreEaxmModels)
//        {
//            if (model.getName().toLowerCase().contains(search))
//                searchList.add(model);
//
//            //Toast.makeText(context, "list"+getPreEaxmModels.size(), Toast.LENGTH_SHORT).show();
//            notifyDataSetChanged();
//        }
//
//    }
}
