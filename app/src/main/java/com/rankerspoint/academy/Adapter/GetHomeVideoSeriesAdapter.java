package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.ExoYoutPlayer;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.EmbadedPlayer;
import com.rankerspoint.academy.Model.HomeVideoSeriesModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GetHomeVideoSeriesAdapter extends RecyclerView.Adapter<GetHomeVideoSeriesAdapter.BannerImage> {
    List<HomeVideoSeriesModel> getPreEaxmModels;
    Context context;

    public GetHomeVideoSeriesAdapter(Context context, List<HomeVideoSeriesModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public GetHomeVideoSeriesAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_series_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetHomeVideoSeriesAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        final HomeVideoSeriesModel getPreEaxmModel = getPreEaxmModels.get(position);
        //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
         Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getImagePath()).into(holder.image_video);
        // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());
        holder.classe_txt.setText(getPreEaxmModel.getHeading());
        holder.txt_details.setText(getPreEaxmModel.getDetails());
        holder.llout_video_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPreEaxmModel.getStatus().equalsIgnoreCase("Youtube")) {
                    Intent intent = new Intent(context, ExoYoutPlayer.class);
                    intent.putExtra("VIDEOURL", getPreEaxmModel.getLinkId().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);
                } else if (getPreEaxmModel.getStatus().equalsIgnoreCase("Normal")) {
                    Intent intent = new Intent(context, EmbadedPlayer.class);
                    intent.putExtra("TITLE", getPreEaxmModel.getHeading());
                    intent.putExtra("CONTENTID", getPreEaxmModel.getImageId());
                    intent.putExtra("PIC", getPreEaxmModel.getImagePath());
                    intent.putExtra("VIDEOURLNORMAL", getPreEaxmModel.getLinkId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
         return getPreEaxmModels.size();


    }

    public static class BannerImage extends RecyclerView.ViewHolder {

        public TextView classe_txt,txt_details;
LinearLayout llout_video_series;
ImageView image_video;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            classe_txt=itemView.findViewById(R.id.classe_txt);
            txt_details=itemView.findViewById(R.id.txt_details);
            llout_video_series=itemView.findViewById(R.id.llout_video_series);
            image_video=itemView.findViewById(R.id.image_video);


        }
    }

}
