package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.ExoYoutPlayer;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.EmbadedPlayer;
import com.rankerspoint.academy.Activity.PaymentCheckOut;

import com.rankerspoint.academy.Model.GetAllTopicIndividual;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class IndividualTopicCatFragAdapter extends RecyclerView.Adapter<IndividualTopicCatFragAdapter.MyViewHolder> {


    private Context context;
    private List<GetAllTopicIndividual> getAllTopicIndividuals = new ArrayList<>();

    public IndividualTopicCatFragAdapter(List<GetAllTopicIndividual> getAllTopicIndividuals, Context context) {
        this.getAllTopicIndividuals = getAllTopicIndividuals;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.individual_topic_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetAllTopicIndividual cc = getAllTopicIndividuals.get(position);
        SharedPreferences preferences =context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String Status = preferences.getString("Status", "");
        Log.d("Statusfindindiv:--",Status);


        holder.txt_date_time.setText(cc.getStartDate());
        holder.classe_txt.setText(cc.getName());
        if (cc.getFeeStatus().equals("Free")) {
            holder.txt_status.setText("Free");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
        }else {
            if (cc.getFeeStatus().equals("Paid")) {
                if (Status.equals("True")) {
                    holder.txt_status.setText("Paid");
                    holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
                } else {
                    holder.txt_status.setText("Paid");
                    holder.txt_status.setTextColor(context.getResources().getColor(R.color.theme_purple));
                }
            }
        }
        //Toast.makeText(context, "ind_txt_subtitle:--"+cc.getDetails(), Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + cc.getPic()).into(holder.image_video);
        holder.card_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", cc.getFeeStatus());
                editor.apply();
                if (cc.getFeeStatus().equals("Free")) {
                    if (cc.getType().equalsIgnoreCase("Youtube")) {
                        Intent intent = new Intent(context, ExoYoutPlayer.class);
                        intent.putExtra("VIDEOURL", cc.getVideoLink1().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    } else if (cc.getType().equalsIgnoreCase("Normal")) {
                        Intent intent = new Intent(context, EmbadedPlayer.class);
                        intent.putExtra("TITLE", cc.getName());
                        intent.putExtra("CONTENTID", cc.getVideoId());
                        intent.putExtra("PIC", cc.getPic());

                        intent.putExtra("VIDEOURLNORMAL", cc.getVideoLink1().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                        context.startActivity(intent);
                    }
                }else {
                    if (cc.getFeeStatus().equals("Paid"))
                    {
                        if (Status.equals("True"))
                        {
                            if (cc.getType().equalsIgnoreCase("Youtube")) {
                                Intent intent = new Intent(context, ExoYoutPlayer.class);
                                intent.putExtra("VIDEOURL", cc.getVideoLink1().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                context.startActivity(intent);
                            } else if (cc.getType().equalsIgnoreCase("Normal")) {
                                Intent intent = new Intent(context, EmbadedPlayer.class);
                                intent.putExtra("TITLE", cc.getName());
                                intent.putExtra("CONTENTID", cc.getVideoId());
                                intent.putExtra("PIC", cc.getPic());
                                intent.putExtra("VIDEOURLNORMAL", cc.getVideoLink1().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                                context.startActivity(intent);
                            }
                        }
                        else {
                            Intent intent = new Intent(context, PaymentCheckOut.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllTopicIndividuals.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_status, classe_txt,txt_date_time;
        ImageView image_video;

        LinearLayout lout_quiz,lout_notes;
CardView card_click;
        public MyViewHolder(View view) {
            super(view);

            image_video = view.findViewById(R.id.image_video);
            txt_date_time = view.findViewById(R.id.txt_date_time);
            txt_status = view.findViewById(R.id.txt_status);
            classe_txt = view.findViewById(R.id.classe_txt);
            card_click = view.findViewById(R.id.card_click);

        }
    }

}


