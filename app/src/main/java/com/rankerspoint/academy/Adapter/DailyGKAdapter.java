package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.DailyNewsActivity;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.DailyGkModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DailyGKAdapter extends RecyclerView.Adapter<DailyGKAdapter.BannerImage> {


    List<DailyGkModel> getPreEaxmModels;
    List<DailyGkModel> searchList;
    Context context;

    public DailyGKAdapter(Context context, List<DailyGkModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.searchList=new ArrayList<>();
        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public DailyGKAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.daily_gk, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyGKAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final DailyGkModel getPreEaxmModel = searchList.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.img);
        holder.classe_txt.setText(getPreEaxmModel.getTitle());

       // holder.img.setImageResource(images.get(position));
holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(context, DailyNewsActivity.class);
        intent.putExtra("DailyGKID",getPreEaxmModel.getDailyGKId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
    }
});

    }

    @Override
    public int getItemCount() {
//        return getPreEaxmModels.size();
        if (searchList.size() > 0) {
            return searchList.size();
        } else {
            return 0;
        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView classe_txt;
        private CardView card_preexam_subcat;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            classe_txt=itemView.findViewById(R.id.classe_txt);

            img=itemView.findViewById(R.id.image_video);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);
        }
        // each data item is just a string in this case

    }
    public  void filter(String search)
    {
        searchList.clear();
        search=search.toLowerCase();
        for (DailyGkModel model:getPreEaxmModels)
        {
            if (model.getAddDate().contains(search))
                searchList.add(model);

            //Toast.makeText(context, "list"+getPreEaxmModels.size(), Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }

    }


}
