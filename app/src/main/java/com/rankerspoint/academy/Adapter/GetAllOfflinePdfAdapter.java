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

import com.rankerspoint.academy.Activity.YourPdfNotesOffline;
import com.rankerspoint.academy.Activity.PdfViewDemo;
import com.rankerspoint.academy.Model.OfflinePdfModel;
import com.rankerspoint.academy.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetAllOfflinePdfAdapter extends RecyclerView.Adapter<GetAllOfflinePdfAdapter.BannerImage> {

    List<OfflinePdfModel> getPreEaxmModels;

    Context context;

    public GetAllOfflinePdfAdapter(Context context, List<OfflinePdfModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }

    @NonNull
    @Override
    public GetAllOfflinePdfAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offline_pdf_view_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllOfflinePdfAdapter.BannerImage holder, int position) {
        final OfflinePdfModel getPreEaxmModel = getPreEaxmModels.get(position);
        holder.txt_score.setText(getPreEaxmModel.getVideoName());
         long size=getPreEaxmModel.getPdfSize();
         long videosize=size/(1024*1024);
         String sizevideo=String.valueOf(videosize);
         holder.txt_size.setText(sizevideo+" MB");

        holder.video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PdfViewDemo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("PDFName",getPreEaxmModel.getVideoName());
                context.startActivity(intent);
            }
        });
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof YourPdfNotesOffline) {
                    ((YourPdfNotesOffline)context).showBottomSheetDialog(getPreEaxmModels.get(position).getVideoName());
                }

            }
        });
//        holder.pdf_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+context.getPackageName()+"/files"+"/"+"Documents",getPreEaxmModel.getVideoName());
//                Uri uri = Uri.fromFile(outputFile);
//
//                Intent share = new Intent();
//                share.setAction(Intent.ACTION_SEND);
//                share.setType("application/pdf");
//                share.putExtra(Intent.EXTRA_STREAM, uri+".pdf");
//                //share.setPackage("com.whatsapp");
//
//                context.startActivity(share);
//            }
//        });
    }

    @Override
    public int getItemCount() {
      return   getPreEaxmModels.size();
    }

    public static class BannerImage extends RecyclerView.ViewHolder {
        public CircleImageView img;
        public TextView txt_score,txt_size;
        CardView card_layout;
        ImageView video_play,delete_item,pdf_share;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_score=itemView.findViewById(R.id.txt_score);
            txt_size=itemView.findViewById(R.id.txt_size);
            video_play=itemView.findViewById(R.id.video_play);
            delete_item=itemView.findViewById(R.id.delete_item);

         }
        // each data item is just a string in this case

    }
//
//
//    }
}
