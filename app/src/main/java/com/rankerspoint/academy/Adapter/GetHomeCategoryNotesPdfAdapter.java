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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.PdfReaderFile;
import com.rankerspoint.academy.Model.HomeCategoryNotesPdfModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GetHomeCategoryNotesPdfAdapter extends RecyclerView.Adapter<GetHomeCategoryNotesPdfAdapter.BannerImage> {
    List<HomeCategoryNotesPdfModel> getPreEaxmModels;
    Context context;

    public GetHomeCategoryNotesPdfAdapter(Context context, List<HomeCategoryNotesPdfModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public GetHomeCategoryNotesPdfAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_notes_pdf_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetHomeCategoryNotesPdfAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        final HomeCategoryNotesPdfModel getPreEaxmModel = getPreEaxmModels.get(position);
        //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.image_video);
        // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());
        holder.classe_txt.setText(getPreEaxmModel.getNotesCategoryName());
        holder.txt_title.setText(getPreEaxmModel.getTitle());
        holder.txt_votes.setText(getPreEaxmModel.getUpVotes()+" upvotes");

//        if (getPreEaxmModel.getExtra1().equals("Free")) {
//            holder.txt_status.setText("Free");
//            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
//        }else {
//
//                    holder.txt_status.setText("Paid");
//                    holder.txt_status.setTextColor(context.getResources().getColor(R.color.theme_purple));
//
//        }
        holder.lout_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", getPreEaxmModel.getExtra1());
                editor.putString("SHARE", getPreEaxmModel.getExtra2());
                editor.apply();
                //Toast.makeText(context, "share:--"+getPreEaxmModel.getExtra2(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, PdfReaderFile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("PDFURL",getPreEaxmModel.getPdfLang1());
                intent.putExtra("PDFURL2",getPreEaxmModel.getPdfLag2());
                intent.putExtra("CONTENTID",getPreEaxmModel.getNotesCategoryId());
                intent.putExtra("PIC",getPreEaxmModel.getPic1());
                intent.putExtra("PDFTITLE",getPreEaxmModel.getTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
         return getPreEaxmModels.size();


    }

    public static class BannerImage extends RecyclerView.ViewHolder {
LinearLayout lout_pdf;
        public TextView classe_txt,txt_title,txt_votes,txt_status;
        ImageView image_video;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            classe_txt=itemView.findViewById(R.id.classe_txt);
            image_video=itemView.findViewById(R.id.image_video);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_votes=itemView.findViewById(R.id.txt_votes);
          //  txt_status=itemView.findViewById(R.id.txt_status);
            lout_pdf=itemView.findViewById(R.id.lout_pdf);


        }
    }

}
