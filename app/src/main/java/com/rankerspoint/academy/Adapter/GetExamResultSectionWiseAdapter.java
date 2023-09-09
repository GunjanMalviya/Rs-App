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

import com.rankerspoint.academy.Model.SectionWiseResultModel;
import com.rankerspoint.academy.R;

import java.util.List;

public class GetExamResultSectionWiseAdapter extends RecyclerView.Adapter<GetExamResultSectionWiseAdapter.BannerImage> {
    List<SectionWiseResultModel> getPreEaxmModels;
    Context context;
String purchaseid;
    public GetExamResultSectionWiseAdapter(Context context, List<SectionWiseResultModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public GetExamResultSectionWiseAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.sectional_analysis_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetExamResultSectionWiseAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final SectionWiseResultModel getPreEaxmModel = getPreEaxmModels.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
//        Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.imageLogo);
        holder.txt_correct.setText(getPreEaxmModel.getCorrect());
        holder.txt_wrong.setText(getPreEaxmModel.getWrong());
        holder.txt_skip.setText(getPreEaxmModel.getSkipped());
        holder.txt_accuracy.setText(getPreEaxmModel.getAccuracy());
        holder.txt_percentage.setText(getPreEaxmModel.getPercentile());
        holder.txt_timetaken.setText(getPreEaxmModel.getTimeTake());
        holder.txt_texttitle.setText(getPreEaxmModel.getSubjectName());
        holder.txt_score.setText(getPreEaxmModel.getScore());
        holder.txt_marks.setText("/"+getPreEaxmModel.getTotalMark());

       // holder.img.setImageResource(images.get(position));

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


        private ImageView imageLogo;
        private TextView txt_correct,txt_wrong,txt_skip,txt_percentage,txt_accuracy,txt_timetaken,txt_score,txt_marks,txt_texttitle;
        CardView card_all_Course;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_correct=itemView.findViewById(R.id.txt_correct);
            txt_wrong=itemView.findViewById(R.id.txt_wrong);
            txt_skip=itemView.findViewById(R.id.txt_skip);
            txt_percentage=itemView.findViewById(R.id.txt_percentage);
            txt_accuracy=itemView.findViewById(R.id.txt_accuracy);
            txt_timetaken=itemView.findViewById(R.id.txt_timetaken);
            txt_score=itemView.findViewById(R.id.txt_score);
            txt_marks=itemView.findViewById(R.id.txt_marks);
            txt_texttitle=itemView.findViewById(R.id.txt_texttitle);

        }
        // each data item is just a string in this case

    }
}
