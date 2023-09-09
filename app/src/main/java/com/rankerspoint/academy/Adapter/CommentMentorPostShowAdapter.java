package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.Dashboard;
import com.rankerspoint.academy.Activity.EditCommentMentor;
import com.rankerspoint.academy.Activity.MentorCommentReply;
import com.rankerspoint.academy.Model.CommentMetorPostShowModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentMentorPostShowAdapter extends RecyclerView.Adapter<CommentMentorPostShowAdapter.BannerImage> {
    private Context context;
    List<CommentMetorPostShowModel> freeVideoModels;
RecyclerView recyclerView;
    public CommentMentorPostShowAdapter(Context context, List<CommentMetorPostShowModel>  freeVideoModels) {
        this.context = context;
        this.freeVideoModels = freeVideoModels;
    }
    @NonNull
    @Override
    public CommentMentorPostShowAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mentor_post_comment_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentMentorPostShowAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        final CommentMetorPostShowModel freeVideoModel = freeVideoModels.get(position);

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
Log.d("checkdata",freeVideoModel.getUserId()+"Userid:--"+user_id);
        if (freeVideoModel.getUserId().equals(user_id))
        {
            holder.img_delete.setVisibility(View.VISIBLE);
           holder.img_delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   PopupMenu popupMenu=new PopupMenu(context,holder.img_delete);
                   popupMenu.inflate(R.menu.comment_menu);
                 //  popupMenu.show();
                   popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem menuItem) {
                           switch (menuItem.getItemId())
                          {
                             case R.id.item_update:
                             Intent intent=new Intent(context, EditCommentMentor.class);
                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                             intent.putExtra("COMMENTID",freeVideoModel.getCommentId());
                             context.startActivity(intent);
                                         break;
                               case R.id.item_delete:

                                    getSingleCourse(freeVideoModel.getCommentId());

                                           break;
        }
        return false;
                       }
                   });
                   popupMenu.show();
               }
           });


        }
        holder.llout_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, MentorCommentReply.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("NAME",freeVideoModel.getUserName());
                intent.putExtra("TIME",freeVideoModel.getAddDate());
                intent.putExtra("USERPIC",freeVideoModel.getUserPic());
                intent.putExtra("TITLE",freeVideoModel.getText());
                intent.putExtra("COMMENTID",freeVideoModel.getCommentId());
               context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        if (freeVideoModels.size() > 0) {
            return freeVideoModels.size();
        } else {
            return 0;
        }
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//        switch (menuItem.getItemId())
//        {
//            case R.id.item_update:
//                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item_delete:
//                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }

    public static class BannerImage extends RecyclerView.ViewHolder {
        private ImageView img,img_delete;
        CardView card_liveVideo;
        LinearLayout llout_upvotes,llout_comments;
        TextView txt_userNm,txt_time,txt_title;
        public BannerImage(@NonNull View itemView) {
            super(itemView);

           img=itemView.findViewById(R.id.userImage_imageView);
            img_delete=itemView.findViewById(R.id.img_delete);
            llout_comments=itemView.findViewById(R.id.llout_comments);
            llout_upvotes=itemView.findViewById(R.id.llout_upvotes);
            card_liveVideo=itemView.findViewById(R.id.card_liveVideo);
            txt_userNm=itemView.findViewById(R.id.txt_userNm);
            txt_time=itemView.findViewById(R.id.txt_time);
            txt_title=itemView.findViewById(R.id.txt_title);
        }
        // each data item is just a string in this case
    }
    public void getSingleCourse(String courseid) {

        String SUBCATALL= BaseUrl.GETALLPOSTCOMNNETDELETE+"/"+courseid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sub_delete model", response);
                try {
//                    JSONArray jsonObject = new JSONArray(response);
//                    Log.d("preexam_sub",jsonObject.toString());

                    if (response.length()>0) {

                        Toast.makeText(context, "delete data", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    }else
                    {
                       // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


}
