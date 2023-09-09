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

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSubPreEaxmModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GetExamSubCategoryMoreClassesAdapter extends RecyclerView.Adapter<GetExamSubCategoryMoreClassesAdapter.BannerImage> {
    RecyclerView recy_subpreexam;
    List<GetSubPreEaxmModel> getPreEaxmModels;
    Context context;
    //declare interface
    private OnItemClicked onClick;
    private int selectedPosition = -1;
    private String selectedItem = "";

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public GetExamSubCategoryMoreClassesAdapter(String trim, Context context, List<GetSubPreEaxmModel> getPreEaxmModels, RecyclerView recy_subpreexam, OnItemClicked onItemClicked) {
        this.selectedItem = trim;
        this.getPreEaxmModels = getPreEaxmModels;
        this.recy_subpreexam = recy_subpreexam;
        this.onClick = onItemClicked;
        this.context = context;
    }

    public GetExamSubCategoryMoreClassesAdapter(Context context, List<GetSubPreEaxmModel> getPreEaxmModels, RecyclerView recy_subpreexam, OnItemClicked onClick) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.recy_subpreexam = recy_subpreexam;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public GetExamSubCategoryMoreClassesAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prepare_exam_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetExamSubCategoryMoreClassesAdapter.BannerImage holder, int position) {
        final GetSubPreEaxmModel getPreEaxmModel = getPreEaxmModels.get(position);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
        holder.txtTitle.setText(getPreEaxmModel.getName());
  /*      if (selectedPosition == position) {
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
        if (selectedItem.trim().equalsIgnoreCase(getPreEaxmModel.getName().trim())) {
            holder.itemView.setSelected(true); //using selector drawable
            holder.txtTitleCourse.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
            holder.txtTitleCourse.setTextColor(context.getResources().getColor(R.color.white_color));
        } else {
            holder.itemView.setSelected(false);
            holder.txtTitleCourse.setBackgroundColor(context.getResources().getColor(R.color.white_color));
            holder.txtTitleCourse.setTextColor(context.getResources().getColor(R.color.black_color));
        }*/
        holder.itemView.setOnClickListener(v -> {
            onClick.onItemClick(position);
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedItem = getPreEaxmModel.getName();
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
        private TextView txtTitle,txtSubTitle;
        private CardView card_preexam_subcat;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitleExam);
            txtSubTitle=itemView.findViewById(R.id.txtSubTitleExam);
            img=itemView.findViewById(R.id.imageLogo);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);
        }
        // each data item is just a string in this case
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
