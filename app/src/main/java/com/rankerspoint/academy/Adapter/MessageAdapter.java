package com.rankerspoint.academy.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.Chat;
import com.rankerspoint.academy.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RINGHT=1;
    private Context mContext;
    private List<Chat> mChat;

    public MessageAdapter(Context mContext, List<Chat> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
     //   Toast.makeText(mContext, "message adapter", Toast.LENGTH_SHORT).show();

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);


            return new MessageAdapter.ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat=mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        holder.timechat.setText(chat.gettimechat());
        holder.username.setText(chat.getSender());
       // Picasso.with(mContext).load(BANN_IMG_URL + chat.getUserPic()).into(holder.img_circle_user);

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;
        public TextView timechat;
        public TextView username;
    CircleImageView img_circle_user;
        public ViewHolder(View iteView)
        {
            super(iteView);
            show_message=iteView.findViewById(R.id.show_message);
            timechat=iteView.findViewById(R.id.time_chats);
            username=iteView.findViewById(R.id.usernameX);
            img_circle_user=iteView.findViewById(R.id.img_circle_user);



        }
    }

    @Override
    public int getItemViewType(int position) {

      return position;
    }
}
