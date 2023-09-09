package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.Results;
import com.rankerspoint.academy.Model.GetAllResultsModel;
import com.rankerspoint.academy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetAllResultsAdapter extends RecyclerView.Adapter<GetAllResultsAdapter.BannerImage> {

    List<GetAllResultsModel> getPreEaxmModels;

    Context context;

    public GetAllResultsAdapter(Context context, List<GetAllResultsModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;


    }

    @NonNull
    @Override
    public GetAllResultsAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.result_all_show_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllResultsAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetAllResultsModel getPreEaxmModel = getPreEaxmModels.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
    //  Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
       // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());

        holder.txt_score.setText(getPreEaxmModel.getScore());

        try {
            //dateformat
            String datef = getPreEaxmModel.getAddDate();



            String datae=datef.substring(0,10);


            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(datae);
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);



            holder.txt_date.setText("Exam on "+dt1.format(date));



        }catch (Exception ex)
        {
            Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
        }
        holder.txt_CrA.setText(getPreEaxmModel.getCorrect());
        holder.txt_WrA.setText(getPreEaxmModel.getWrong());
        holder.txt_UrA.setText(getPreEaxmModel.getUnattemped());
        holder.lll_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Results.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("EXAM_RESULT_ID",getPreEaxmModel.getExamResultId());

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
        public TextView txt_score,txt_rank,txt_date,txt_CrA,txt_WrA,txt_UrA;

        Button btn_attempt;
        LinearLayout lll_results;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_score=itemView.findViewById(R.id.txt_score);
            txt_rank=itemView.findViewById(R.id.txt_rank);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_CrA=itemView.findViewById(R.id.txt_CrA);
            txt_WrA=itemView.findViewById(R.id.txt_WrA);
            txt_UrA=itemView.findViewById(R.id.txt_UrA);
            lll_results=itemView.findViewById(R.id.lll_results);
            //img=itemView.findViewById(R.id.imageLogo);

        }
        // each data item is just a string in this case

    }
//
//
//    }
}
