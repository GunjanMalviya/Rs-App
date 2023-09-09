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

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.SubCategoryClass;
import com.rankerspoint.academy.Model.GetPreEaxmModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetHomeeeCateAdapter extends RecyclerView.Adapter<GetHomeeeCateAdapter.BannerImage> {


    List<GetPreEaxmModel> getPreEaxmModels;
    List<GetPreEaxmModel> searchList;
    Context context;

    public GetHomeeeCateAdapter(Context context, List<GetPreEaxmModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.searchList=new ArrayList<>();
        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public GetHomeeeCateAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.homee_cate_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetHomeeeCateAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetPreEaxmModel getPreEaxmModel = searchList.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
        holder.txtTitle.setText(getPreEaxmModel.getName());
        //holder.txtSubTitle.setText(getPreEaxmModel.getDetails());
       // holder.img.setImageResource(images.get(position));
        holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubCategoryClass.class);
                intent.putExtra("CAT_ID", getPreEaxmModel.getCategoryId().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (searchList.size() > 0) {
            return searchList.size();
        } else {
            return 0;
        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView txtTitle,txtSubTitle;
        private CardView card_preexam_subcat;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitleExam);
            //txtSubTitle=itemView.findViewById(R.id.txtSubTitleExam);
            img=itemView.findViewById(R.id.imageLogo);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);
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
