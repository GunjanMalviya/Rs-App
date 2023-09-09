package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.PaymentCheckOut;
import com.rankerspoint.academy.Activity.PdfReaderFile;
import com.rankerspoint.academy.Model.GetAllTopicPdfModel;
import com.rankerspoint.academy.R;

import java.util.ArrayList;
import java.util.List;

public class TopicPdfAdapter extends RecyclerView.Adapter<TopicPdfAdapter.MyViewHolder> {


    private Context context;
    private List<GetAllTopicPdfModel> getAllTopicIndividuals = new ArrayList<>();

    public TopicPdfAdapter(List<GetAllTopicPdfModel> getAllTopicIndividuals, Context context) {
        this.getAllTopicIndividuals = getAllTopicIndividuals;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_series_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetAllTopicPdfModel cc = getAllTopicIndividuals.get(position);
        SharedPreferences preferences =context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String Status = preferences.getString("Status", "");
        Log.d("Statusfindindivpdf:--",Status);


        holder.txtTitleExam.setText(cc.getTitle());
        if (cc.getStatus().equals("Free")) {
            holder.txt_status.setText("Free");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
        }else {
            if (cc.getStatus().equals("Paid")) {
                if (Status.equals("True")) {
                    holder.txt_status.setText("Paid");
                    holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
                } else {
                    holder.txt_status.setText("Paid");
                    holder.txt_status.setTextColor(context.getResources().getColor(R.color.theme_purple));
                }
            }
        }
//
        holder.pdf_download.setOnClickListener(new View.OnClickListener() {
            @Override
//        //Toast.makeText(context, "ind_txt_subtitle:--"+cc.getDetails(), Toast.LENGTH_SHORT).show();
//        Picasso.with(context).load(BANN_IMG_URL + cc.getPic()).into(holder.image_video);
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", cc.getStatus());
                editor.putString("SHARE", cc.getExtra2());
                editor.apply();
                if (cc.getStatus().equals("Free")) {

                        Intent intent = new Intent(context, PdfReaderFile.class);
                        intent.putExtra("PDFURL",cc.getPdfLang1());
                        intent.putExtra("PDFURL2",cc.getPdfLag2());
                        intent.putExtra("PDFTITLE",cc.getTitle());
                        intent.putExtra("CONTENTID",cc.getDCContentId());
                        intent.putExtra("PIC",cc.getPic1());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                        context.startActivity(intent);
                    }
               else {
                    if (cc.getStatus().equals("Paid"))
                    {
                        if (Status.equals("True"))
                        {
                            Intent intent = new Intent(context, PdfReaderFile.class);
                            intent.putExtra("PDFURL",cc.getPdfLang1());
                            intent.putExtra("PDFURL2",cc.getPdfLag2());
                            intent.putExtra("PDFTITLE",cc.getTitle());
                            intent.putExtra("CONTENTID",cc.getDCContentId());
                            intent.putExtra("PIC",cc.getPic1());

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                            context.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(context, PaymentCheckOut.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllTopicIndividuals.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleExam, txt_status,txt_date_time;
        ImageView image_video;

        LinearLayout pdf_download,lout_notes;

        public MyViewHolder(View view) {
            super(view);

            txtTitleExam = view.findViewById(R.id.txtTitleExam);
            txt_status = view.findViewById(R.id.txt_status);
            pdf_download = view.findViewById(R.id.pdf_download);

        }
    }

}


