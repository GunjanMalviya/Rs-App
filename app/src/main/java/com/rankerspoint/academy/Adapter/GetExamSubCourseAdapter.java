package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.SingleCourseDetails;
import com.rankerspoint.academy.Model.GetSubCourseModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetExamSubCourseAdapter extends RecyclerView.Adapter<GetExamSubCourseAdapter.BannerImage> {
    List<GetSubCourseModel> getPreEaxmModels;
    Context context;

    public GetExamSubCourseAdapter(Context context, List<GetSubCourseModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }

    @NonNull
    @Override
    public GetExamSubCourseAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_course_exam_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetExamSubCourseAdapter.BannerImage holder, int position) {
        final GetSubCourseModel getPreEaxmModel = getPreEaxmModels.get(position);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.imageLogo);
        holder.txtTitleExam.setText(getPreEaxmModel.getName());
        holder.txtSubTitle.setText(getPreEaxmModel.getDetails());
        holder.txtlanguage.setText(getPreEaxmModel.getLangauge());
        holder.tvPricing.setText("\u20B9 "+getPreEaxmModel.getPrice());
        holder.tvDiscount.setText(getPreEaxmModel.getPrice());
        holder.tvDiscount.setPaintFlags(holder.tvDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvStartDate.setText(getDate(getPreEaxmModel.getExpiryDate()));
        holder.tvEndDate.setText(getDate(getPreEaxmModel.getExpiryDate()));
        holder.card_all_Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleCourseDetails.class);
                intent.putExtra("CAT_ID", getPreEaxmModel.getCategoryId());
                intent.putExtra("COURSE_ID", getPreEaxmModel.getCourseId().toString());
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

    public String getDate(String d){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(d);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String formattedDate = outputFormat.format(date);
        return formattedDate;
    }
    public static class BannerImage extends RecyclerView.ViewHolder {
        private TextView tvPricing, tvStartDate, tvEndDate;
        private TextView tvDiscount;
        private ImageView imageLogo;
        private TextView txtTitleExam, txtSubTitle, txtlanguage;
        CardView card_all_Course;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            tvStartDate = itemView.findViewById(R.id.tv_start_date);
            tvEndDate = itemView.findViewById(R.id.tv_end_date);
            tvPricing = itemView.findViewById(R.id.tv_pricing);
            tvDiscount = itemView.findViewById(R.id.tv_dis_pricing);
            txtTitleExam = itemView.findViewById(R.id.txtTitleExam);
            txtSubTitle = itemView.findViewById(R.id.txtSubTitle);
            imageLogo = itemView.findViewById(R.id.imageLogo);
            txtlanguage = itemView.findViewById(R.id.txtlanguage);
            card_all_Course = itemView.findViewById(R.id.card_all_Course);
        }
        // each data item is just a string in this case
    }
}
