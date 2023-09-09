package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.GetSectionWiseModel;
import com.rankerspoint.academy.R;

import java.util.List;

public class GetSectionWiseAdapter extends RecyclerView.Adapter<GetSectionWiseAdapter.BannerImage> {

RecyclerView recy_subpreexam;
    List<GetSectionWiseModel> getPreEaxmModels;
    Context context;
    //declare interface
    private OnItemClicked onClick;
    private int selectedPosition = 0;
    //make interface like this
    public interface OnItemClicked {

        void onItemClick(int position);

    }

    public GetSectionWiseAdapter(Context context, List<GetSectionWiseModel> getPreEaxmModels, RecyclerView recy_subpreexam, OnItemClicked onClick ) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.recy_subpreexam=recy_subpreexam;
        this.onClick=onClick;

    }

    @NonNull
    @Override
    public GetSectionWiseAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.more_classes_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetSectionWiseAdapter.BannerImage holder, int position) {





        final GetSectionWiseModel getPreEaxmModel = getPreEaxmModels.get(position);


        holder.txtTitleCourse.setText(getPreEaxmModel.getSubjectName());


        if (selectedPosition == position) {
            //onClick.onItemClick(position);
            holder.itemView.setSelected(true); //using selector drawable
            holder.txtTitleCourse.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
            holder.txtTitleCourse.setTextColor(context.getResources().getColor(R.color.white_color));
        } else {
            holder.itemView.setSelected(false);
            //onClick.onItemClick(position);
            holder.txtTitleCourse.setBackgroundColor(context.getResources().getColor(R.color.white_color));
            holder.txtTitleCourse.setTextColor(context.getResources().getColor(R.color.black_color));
        }

        holder.itemView.setOnClickListener(v -> {
            onClick.onItemClick(position);
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
        });

    }

    @Override
    public int getItemCount() {
        if (getPreEaxmModels.size() > 0) {
            return getPreEaxmModels.size();
        } else {
            return 0;
        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView txtTitleCourse, txtSubTitle;
        private CardView card_course_click;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitleCourse = itemView.findViewById(R.id.txtTitleCourse);
            card_course_click = itemView.findViewById(R.id.card_course_click);
        }
        // each data item is just a string in this case

    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
