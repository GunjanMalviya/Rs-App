package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.MockQuestionAttempts;
import com.rankerspoint.academy.Model.SubjectShowModel;
import com.rankerspoint.academy.R;

import java.util.List;

public class SubjectShowAdapter extends RecyclerView.Adapter<SubjectShowAdapter.BannerImage> {
    private Context context;
    List<SubjectShowModel> freeVideoModels;
    private int selectedPosition = 0;
    public SubjectShowAdapter(Context context, List<SubjectShowModel>  freeVideoModels) {
        this.context = context;
        this.freeVideoModels = freeVideoModels;
    }

    @NonNull
    @Override
    public SubjectShowAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.more_subject_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectShowAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        final SubjectShowModel freeVideoModel = freeVideoModels.get(position);

      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

       // Picasso.with(context).load(BANN_IMG_URL + freeVideoModel.getPic()).into(holder.img);
        holder.txtTitleCourse.setText(freeVideoModel.getSubjectName());
        if (selectedPosition == position) {
            //onClick.onItemClick(position);
            holder.itemView.setSelected(true); //using selector drawable
            holder.card_course_click.setCardBackgroundColor(context.getResources().getColor(R.color.theme_purple));
            holder.txtTitleCourse.setTextColor(context.getResources().getColor(R.color.white_color));
        } else {
            holder.itemView.setSelected(false);
            //onClick.onItemClick(position);
            holder.card_course_click.setCardBackgroundColor(context.getResources().getColor(R.color.white_color));
            holder.txtTitleCourse.setTextColor(context.getResources().getColor(R.color.black_color));
        }

        holder.card_course_click.setOnClickListener(v -> {
            if (context instanceof MockQuestionAttempts) {
                ((MockQuestionAttempts)context).getAllQuestionMock(freeVideoModel.getSubjectId());
            }
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
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

    public static class BannerImage extends RecyclerView.ViewHolder {


       TextView txtTitleCourse;
       CardView card_course_click;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitleCourse=itemView.findViewById(R.id.txtTitleCourse);
            card_course_click=itemView.findViewById(R.id.card_course_click);
        }
        // each data item is just a string in this case

    }
}
