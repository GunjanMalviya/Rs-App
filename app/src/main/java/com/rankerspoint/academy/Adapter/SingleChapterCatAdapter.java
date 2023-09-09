package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.GetAllSyllabusChapModel;
import com.rankerspoint.academy.R;

import java.util.ArrayList;
import java.util.List;

public class SingleChapterCatAdapter extends RecyclerView.Adapter<SingleChapterCatAdapter.MyViewHolder> {


    private Context context;
    private List<GetAllSyllabusChapModel> getAllSyllabusChapModels = new ArrayList<>();

    public SingleChapterCatAdapter(List<GetAllSyllabusChapModel> getAllSyllabusCatModels, Context context) {
        this.getAllSyllabusChapModels = getAllSyllabusCatModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sub_cat_item1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetAllSyllabusChapModel cc = getAllSyllabusChapModels.get(position);

        holder.prodNAme.setText(cc.getName());

    }

    @Override
    public int getItemCount() {
        return getAllSyllabusChapModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView prodNAme, pdetails;
        ImageView image, pimage, image1;
        boolean mines = true;


        LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            prodNAme = view.findViewById(R.id.pNAme);


            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

}


