package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.AllQuestionAttempt;

import com.rankerspoint.academy.Model.GetExamSeriesMockModel;
import com.rankerspoint.academy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetMockQuizSeriesAdapter extends RecyclerView.Adapter<GetMockQuizSeriesAdapter.BannerImage> {


    List<GetExamSeriesMockModel> getPreEaxmModels;

    Context context;

    public GetMockQuizSeriesAdapter(Context context, List<GetExamSeriesMockModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public GetMockQuizSeriesAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.exam_descrip_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetMockQuizSeriesAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetExamSeriesMockModel getPreEaxmModel = getPreEaxmModels.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
    //  Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
       // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());

        holder.txtTitle.setText(getPreEaxmModel.getTitle());

        try {
            //dateformat
            String datef = getPreEaxmModel.getEndDate();

            String datae=datef.substring(0,10);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(datae);
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);



            holder.expre_date.setText("Expires on "+dt1.format(date));



        }catch (Exception ex)
        {
            Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
        }
        holder.txtTotalQuest.setText(getPreEaxmModel.getTotalMarks());
        holder.txttotalMark.setText(getPreEaxmModel.getTotalMarks());
        holder.txt_duration.setText(getPreEaxmModel.getDuration()+" Mins");


        holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", getPreEaxmModel.getFeeStatus());
                editor.apply();
                Intent intent = new Intent(context, AllQuestionAttempt.class);
                intent.putExtra("EXAM_ID", getPreEaxmModel.getExamId().toString());
                intent.putExtra("DURATION", getPreEaxmModel.getDuration().toString());
                intent.putExtra("EXAMNAME", getPreEaxmModel.getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
        holder.btn_attempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", getPreEaxmModel.getFeeStatus());
                editor.apply();
                Intent intent = new Intent(context, AllQuestionAttempt.class);
                intent.putExtra("EXAM_ID", getPreEaxmModel.getExamId().toString());
                intent.putExtra("DURATION", getPreEaxmModel.getDuration().toString());
                intent.putExtra("EXAMNAME", getPreEaxmModel.getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
      return   getPreEaxmModels.size();

    }

    public static class BannerImage extends RecyclerView.ViewHolder {


        public CircleImageView img;
        public TextView txtTitle,expre_date,txtTotalQuest,txttotalMark,txt_duration;
        public CardView card_preexam_subcat;
        Button btn_attempt;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitleexam);
            expre_date=itemView.findViewById(R.id.txtTitleexpireDate);
            txtTotalQuest=itemView.findViewById(R.id.txtTotalQuest);
            txttotalMark=itemView.findViewById(R.id.txttotalMark);
            txt_duration=itemView.findViewById(R.id.txt_duration);
            btn_attempt=itemView.findViewById(R.id.btn_attempt);
            //img=itemView.findViewById(R.id.imageLogo);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);
        }
        // each data item is just a string in this case

    }
//
//
//    }
}
