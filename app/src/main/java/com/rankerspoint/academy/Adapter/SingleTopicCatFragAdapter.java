package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.Indiviual_Topic;
import com.rankerspoint.academy.Model.GetAllTopicSyllabusChapModel;
import com.rankerspoint.academy.R;

import java.util.ArrayList;
import java.util.List;

public class SingleTopicCatFragAdapter extends RecyclerView.Adapter<SingleTopicCatFragAdapter.MyViewHolder> {


    private Context context;
    private List<GetAllTopicSyllabusChapModel> getAllTopicSyllabusChapModels = new ArrayList<>();

    public SingleTopicCatFragAdapter(List<GetAllTopicSyllabusChapModel> getAllTopicSyllabusChapModels, Context context) {
        this.getAllTopicSyllabusChapModels = getAllTopicSyllabusChapModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_topic_wise, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetAllTopicSyllabusChapModel cc = getAllTopicSyllabusChapModels.get(position);
        holder.sno.setText("1."+(position+1));

        holder.prodNAme.setText(cc.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("TopicId", cc.getTopicId());
                editor.putString("Position", "1."+(position+1));
                editor.putString("SNameTop", cc.getName());
                editor.apply();
                Intent intent=new Intent(context, Indiviual_Topic.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllTopicSyllabusChapModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView prodNAme, sno;
        ImageView image, pimage, image1;
        boolean mines = true;


        CardView linearLayout;


        public MyViewHolder(View view) {
            super(view);
            prodNAme = view.findViewById(R.id.pNAme);
            sno = view.findViewById(R.id.sno);


            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

}


