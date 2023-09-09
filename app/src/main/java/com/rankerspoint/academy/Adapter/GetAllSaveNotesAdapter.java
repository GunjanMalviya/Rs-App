package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.ExoYoutPlayer;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.EmbadedPlayer;
import com.rankerspoint.academy.Activity.PdfReaderFile;
import com.rankerspoint.academy.Activity.SaveNotesAllPdfandNotes;
import com.rankerspoint.academy.Model.SaveNotesModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetAllSaveNotesAdapter extends RecyclerView.Adapter<GetAllSaveNotesAdapter.BannerImage> {

    List<SaveNotesModel> getPreEaxmModels;

    Context context;

    public GetAllSaveNotesAdapter(Context context, List<SaveNotesModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }
    @NonNull
    @Override
    public GetAllSaveNotesAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.save_notes_pdf_layout, parent, false);
        return new BannerImage(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GetAllSaveNotesAdapter.BannerImage holder, int position) {
        final SaveNotesModel getPreEaxmModel = getPreEaxmModels.get(position);
        holder.txt_score.setText(getPreEaxmModel.getTitle());
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.video_play);
if (getPreEaxmModel.getContentType().equals("Pdf"))
{
    holder.img_video_play.setImageDrawable(context.getDrawable(R.drawable.ic_outline_picture_as_pdf_24));
}else if (getPreEaxmModel.getContentType().equals("Video"))
{
    holder.img_video_play.setImageDrawable(context.getDrawable(R.drawable.video));
}
        holder.video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", getPreEaxmModel.getExtra3());
                editor.putString("Status", getPreEaxmModel.getExtra2());
                editor.apply();
                if (getPreEaxmModel.getContentType().equals("Video")) {
                    if (getPreEaxmModel.getVideoType().trim().equals("Youtube")) {
                        Intent intent = new Intent(context, ExoYoutPlayer.class);
                        intent.putExtra("VIDEOURL", getPreEaxmModel.getExtra1().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    } else if (getPreEaxmModel.getVideoType().trim().equals("Normal")) {
                        Intent intent = new Intent(context, EmbadedPlayer.class);
                        intent.putExtra("TITLE", getPreEaxmModel.getTitle());
                        intent.putExtra("CONTENTID", getPreEaxmModel.getContentId());
                        intent.putExtra("PIC", getPreEaxmModel.getPic1());
                        intent.putExtra("VIDEOURLNORMAL", getPreEaxmModel.getExtra1());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Video Type Error", Toast.LENGTH_SHORT).show();
                    }
                }else if (getPreEaxmModel.getContentType().equals("Pdf")){
                    Intent intent = new Intent(context, PdfReaderFile.class);
                    intent.putExtra("PDFURL",getPreEaxmModel.getExtra1());
                    intent.putExtra("PDFURL2",getPreEaxmModel.getExtra1());
                    intent.putExtra("PDFTITLE",getPreEaxmModel.getTitle());
                    intent.putExtra("CONTENTID",getPreEaxmModel.getContentId());
                    intent.putExtra("PIC",getPreEaxmModel.getPic1());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    context.startActivity(intent);
                }
            }
        });
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof SaveNotesAllPdfandNotes) {
                    ((SaveNotesAllPdfandNotes)context).showBottomSheetDialog(getPreEaxmModels.get(position).getSaveNoteId());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
      return   getPreEaxmModels.size();
    }

    public static class BannerImage extends RecyclerView.ViewHolder {
        public CircleImageView img;
        public TextView txt_score,txt_size;
        CardView card_layout;
        ImageView video_play,delete_item,img_video_play;

        public BannerImage(@NonNull View itemView) {
            super(itemView);

            txt_score=itemView.findViewById(R.id.txt_score);
            video_play=itemView.findViewById(R.id.video_play);
            delete_item=itemView.findViewById(R.id.delete_item);
            img_video_play=itemView.findViewById(R.id.img_video_play);
         }
        // each data item is just a string in this case

    }

}
