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

import com.rankerspoint.academy.Activity.ExoplayerOffline;
import com.rankerspoint.academy.Activity.OfflineVideoList;
import com.rankerspoint.academy.Model.OfflineVideoModel;
import com.rankerspoint.academy.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetAllOfflineAdapter extends RecyclerView.Adapter<GetAllOfflineAdapter.BannerImage> {

    List<OfflineVideoModel> getPreEaxmModels;

    Context context;

    public GetAllOfflineAdapter(Context context, List<OfflineVideoModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }

    @NonNull
    @Override
    public GetAllOfflineAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offline_video_play_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllOfflineAdapter.BannerImage holder, int position) {
        final OfflineVideoModel getPreEaxmModel = getPreEaxmModels.get(position);
        holder.txt_score.setText(getPreEaxmModel.getVideoName());
         long size=getPreEaxmModel.getVideoSize();
         long videosize=size/(1024*1024);
         String sizevideo=String.valueOf(videosize);
         holder.txt_size.setText(sizevideo+" MB");

        holder.video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ExoplayerOffline.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("VIDEONAME",getPreEaxmModel.getVideoName());
                context.startActivity(intent);
            }
        });
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof OfflineVideoList) {
                    ((OfflineVideoList)context).showBottomSheetDialog(getPreEaxmModels.get(position).getVideoName());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
      return   getPreEaxmModels.size();
    }

    public static class BannerImage extends RecyclerView.ViewHolder {
        public CircleImageView img;
        public TextView txt_score,txt_size;
        CardView card_layout;
        ImageView video_play,delete_item;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_score=itemView.findViewById(R.id.txt_score);
            txt_size=itemView.findViewById(R.id.txt_size);
            video_play=itemView.findViewById(R.id.video_play);
            delete_item=itemView.findViewById(R.id.delete_item);
         }
        // each data item is just a string in this case

    }
//
//
//    }
}
