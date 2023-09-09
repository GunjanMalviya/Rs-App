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
import com.rankerspoint.academy.Model.UpcomingClassesModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingCourseAdapter extends RecyclerView.Adapter<UpcomingCourseAdapter.BannerImage> {
    List<UpcomingClassesModel> getPreEaxmModels;
    Context context;
String purchaseid;
    public UpcomingCourseAdapter(Context context, List<UpcomingClassesModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public UpcomingCourseAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_course_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingCourseAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final UpcomingClassesModel getPreEaxmModel = getPreEaxmModels.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.imageLogo);
        holder.txtTitleExam.setText(getPreEaxmModel.getName());
        holder.txtSubTitle.setText(getPreEaxmModel.getDetails());
        holder.txtlanguage.setText(getPreEaxmModel.getLangauge());
        holder.txtTeacherNm.setText("Teachers: "+getPreEaxmModel.getTeachers());
//        holder.card_all_Course.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context, SingleCourseDetails.class);
//                intent.putExtra("COURSE_ID", getPreEaxmModel.getCourseId().toString());
//
//                context.startActivity(intent);
//            }
//        });
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
        private TextView txtTitleExam,txtSubTitle,txtlanguage,txtTeacherNm;
        CardView card_all_Course;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitleExam=itemView.findViewById(R.id.txtTitleExam);
            txtSubTitle=itemView.findViewById(R.id.txtSubTitle);
            imageLogo=itemView.findViewById(R.id.imageLogo);
            txtlanguage=itemView.findViewById(R.id.txtlanguage);
            card_all_Course=itemView.findViewById(R.id.card_all_Course);
            txtTeacherNm=itemView.findViewById(R.id.txtTeacherNm);
        }
        // each data item is just a string in this case

    }
}
