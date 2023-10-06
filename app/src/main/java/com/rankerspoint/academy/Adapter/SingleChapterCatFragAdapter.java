package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.MultiTabSyllabus;
import com.rankerspoint.academy.Activity.SubjectActivity;
import com.rankerspoint.academy.Activity.SyllabusSingle;
import com.rankerspoint.academy.Model.GetAllSyllabusChapModel;
import com.rankerspoint.academy.R;

import java.util.ArrayList;
import java.util.List;

public class SingleChapterCatFragAdapter extends RecyclerView.Adapter<SingleChapterCatFragAdapter.MyViewHolder> {


    private Context context;
    private List<GetAllSyllabusChapModel> getAllSyllabusChapModels = new ArrayList<>();

    public SingleChapterCatFragAdapter(List<GetAllSyllabusChapModel> getAllSyllabusCatModels, Context context) {
        this.getAllSyllabusChapModels = getAllSyllabusCatModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sub_cat_item_chap, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetAllSyllabusChapModel cc = getAllSyllabusChapModels.get(position);
//        holder.sno.setText(getAllSyllabusChapModels.get(position).+".");

        holder.tvCourse.setText(cc.getName());
        holder.tvSubCourse.setText(cc.getDetails());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("CAT_ID", cc.getCategoryId());
                editor.putString("COURSE_ID", cc.getCourseId());
                editor.putString("ChapterId", cc.getChapterId());
                editor.putString("SName", cc.getName());
                editor.putString("Sno", String.valueOf(position+1));
                editor.apply();
                Intent intent=new Intent(context, MultiTabSyllabus.class);
                intent.putExtra("fromContent", true);
//                Intent intent=new Intent(context, SyllabusSingle.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllSyllabusChapModels.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCourse, tvSubCourse;
        CardView linearLayout;


        public MyViewHolder(View view) {
            super(view);
            tvCourse = view.findViewById(R.id.tv_course);
            tvSubCourse = view.findViewById(R.id.tv_sub_course);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

}


