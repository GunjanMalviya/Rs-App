package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.CommentMetorPostShowReplyModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentMentorPostShowReplyAdapter extends RecyclerView.Adapter<CommentMentorPostShowReplyAdapter.BannerImage> {
    private Context context;
    List<CommentMetorPostShowReplyModel> freeVideoModels;

    public CommentMentorPostShowReplyAdapter(Context context, List<CommentMetorPostShowReplyModel>  freeVideoModels) {
        this.context = context;
        this.freeVideoModels = freeVideoModels;
    }
    @NonNull
    @Override
    public CommentMentorPostShowReplyAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mentor_post_comment_reply_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentMentorPostShowReplyAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        final CommentMetorPostShowReplyModel freeVideoModel = freeVideoModels.get(position);

      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String user_id = preferences.getString("user_id", "");
        String Status = preferences.getString("Status", "");

      //Picasso.with(context).load(freeVideoModel.getUserPic().trim()).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + "/"+"UserIcon.png").into(holder.img);

        Log.d("freeVideoModel.get",freeVideoModel.getUserPic().trim());
        holder.txt_title.setText(freeVideoModel.getText());
        holder.txt_userNm.setText(freeVideoModel.getUserName());
        holder.txt_time.setText(freeVideoModel.getAddDate());
//        holder.llout_comments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context, MentorCommentReply.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                intent.putExtra("NAME",freeVideoModel.getUserName());
//                intent.putExtra("TIME",freeVideoModel.getAddDate());
//                intent.putExtra("USERPIC",freeVideoModel.getUserPic());
//                intent.putExtra("TITLE",freeVideoModel.getText());
//                intent.putExtra("COMMENTID",freeVideoModel.getCommentId());
//               context.startActivity(intent);
//            }
//        });
    }
    @Override
    public int getItemCount() {
        if (freeVideoModels.size() > 0) {
            return freeVideoModels.size();
        } else {
            return 0;
        }
    }
    public static class BannerImage extends RecyclerView.ViewHolder {
        private ImageView img;
        CardView card_liveVideo;
        LinearLayout llout_upvotes,llout_comments;
        TextView txt_userNm,txt_time,txt_title;
        public BannerImage(@NonNull View itemView) {
            super(itemView);

           img=itemView.findViewById(R.id.userImage_imageView);
//            llout_comments=itemView.findViewById(R.id.llout_comments);
//            llout_upvotes=itemView.findViewById(R.id.llout_upvotes);
            card_liveVideo=itemView.findViewById(R.id.card_liveVideo);
            txt_userNm=itemView.findViewById(R.id.txt_userNm);
            txt_time=itemView.findViewById(R.id.txt_time);
            txt_title=itemView.findViewById(R.id.txt_title);
        }
        // each data item is just a string in this case
    }

}
