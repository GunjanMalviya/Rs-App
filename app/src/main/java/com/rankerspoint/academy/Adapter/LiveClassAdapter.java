package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.SingleCourseDetails;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSubCourseModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveClassAdapter extends RecyclerView.Adapter<LiveClassAdapter.BannerImage> {
    List<GetSubCourseModel> getPreEaxmModels;
    Context context;

    public LiveClassAdapter(Context context, List<GetSubCourseModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }

    @NonNull
    @Override
    public BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.live_class_adapter, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerImage holder, int position) {
        final GetSubCourseModel getPreEaxmModel = getPreEaxmModels.get(position);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.imageLogo);
        holder.tvCourceName.setText(getPreEaxmModel.getName());
        holder.tvName.setText(getPreEaxmModel.getTeachers());
        holder.card_all_Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleCourseDetails.class);
                intent.putExtra("COURSE_ID", getPreEaxmModel.getCourseId());
                intent.putExtra("CAT_ID", getPreEaxmModel.getCategoryId());
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
        CircleImageView imageLogo;
        CardView card_all_Course;
        TextView tvCourceName, tvName;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            imageLogo = itemView.findViewById(R.id.iv_profile);
            card_all_Course = itemView.findViewById(R.id.card_main);
            tvCourceName = itemView.findViewById(R.id.tv_cource_name);
            tvName = itemView.findViewById(R.id.tv_name);
        }
        // each data item is just a string in this case
    }
}
