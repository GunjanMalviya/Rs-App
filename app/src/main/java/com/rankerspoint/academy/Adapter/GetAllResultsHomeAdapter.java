package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.ResultWebView;
import com.rankerspoint.academy.Model.GetHomeAllResultModel;
import com.rankerspoint.academy.R;

import java.util.List;

public class GetAllResultsHomeAdapter extends RecyclerView.Adapter<GetAllResultsHomeAdapter.BannerImage> {
    List<GetHomeAllResultModel> getPreEaxmModels;
    Context context;
String purchaseid;
    public GetAllResultsHomeAdapter(Context context, List<GetHomeAllResultModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public GetAllResultsHomeAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.result_analysis, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllResultsHomeAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetHomeAllResultModel getPreEaxmModel = getPreEaxmModels.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
//        Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.imageLogo);

        holder.row_analysis_title.setText(getPreEaxmModel.getTitle());
        holder.row_analysis_duration.setText(getPreEaxmModel.getMAddDate());
        holder.row_analysis_marks.setText(getPreEaxmModel.getScore()+"/"+getPreEaxmModel.getTotalMarks());
        holder.row_analysis_rank.setText("NA");
        holder.row_analysis_toQues.setText(getPreEaxmModel.getTotalQuestion());
      // int value=Integer.parseInt(getPreEaxmModel.getCorrect())+Integer.parseInt(getPreEaxmModel.getWrong());

       holder.row_analysis_attempts.setText(getPreEaxmModel.getTotalQuestion());
       if (getPreEaxmModel.getPassedStatus().equalsIgnoreCase("Passed")) {
           holder.row_analysis_type.setText("Pass");
           holder.row_analysis_type.setTextColor(context.getResources().getColor(R.color.red_600));
       }else {
           holder.row_analysis_type.setText("Fail");
           holder.row_analysis_type.setTextColor(context.getResources().getColor(R.color.theme_purple));
       }
holder.txtProgress.setText(getPreEaxmModel.getAccuracy());
       // holder.img.setImageResource(images.get(position));
holder.card_all_Course.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context, ResultWebView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("EXAMID",getPreEaxmModel.getMExamId());
        context.startActivity(intent);
    }
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


        private ImageView imageLogo;
        private TextView row_analysis_toQues,txtProgress, row_analysis_title,row_analysis_marks,row_analysis_attempts,row_analysis_type,row_analysis_duration,row_analysis_rank;
        CardView card_all_Course;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            row_analysis_title=itemView.findViewById(R.id.row_analysis_title);
            row_analysis_marks=itemView.findViewById(R.id.row_analysis_marks);
            row_analysis_attempts=itemView.findViewById(R.id.row_analysis_attempts);
            row_analysis_type=itemView.findViewById(R.id.row_analysis_type);
            row_analysis_duration=itemView.findViewById(R.id.row_analysis_duration);
            row_analysis_rank=itemView.findViewById(R.id.row_analysis_rank);
            card_all_Course=itemView.findViewById(R.id.card_all_Course);
            row_analysis_toQues=itemView.findViewById(R.id.row_analysis_toQues);
            txtProgress=itemView.findViewById(R.id.txtProgress);


        }


    }
}
