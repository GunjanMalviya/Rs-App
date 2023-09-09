package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.Dashboard;
import com.rankerspoint.academy.Model.GetPreEaxmModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetExamCategoryListAdapter extends RecyclerView.Adapter<GetExamCategoryListAdapter.BannerImage> {


    List<GetPreEaxmModel> getPreEaxmModels;
    List<GetPreEaxmModel> searchList;
    Context context;

    public GetExamCategoryListAdapter(Context context, List<GetPreEaxmModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.searchList=new ArrayList<>();
        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public GetExamCategoryListAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.your_exam_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetExamCategoryListAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetPreEaxmModel getPreEaxmModel = searchList.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
        holder.txtTitle.setText(getPreEaxmModel.getName());
        holder.txtSubTitle.setText(getPreEaxmModel.getDetails());
       // holder.img.setImageResource(images.get(position));
        holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("cate_nm", getPreEaxmModel.getName());
                editor.putString("CAT_ID", getPreEaxmModel.getCategoryId());
                editor.apply();

                Intent intent = new Intent(context, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (searchList.size() > 0) {
            return searchList.size();
        } else {
            return 0;
        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView txtTitle,txtSubTitle;
        private CardView card_preexam_subcat;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitleExam);
            txtSubTitle=itemView.findViewById(R.id.txtSubTitleExam);
            img=itemView.findViewById(R.id.imageLogo);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);
        }
        // each data item is just a string in this case

    }
    public  void filter(String search)
    {
        searchList.clear();
        search=search.toLowerCase();
        for (GetPreEaxmModel model:getPreEaxmModels)
        {
            if (model.getName().toLowerCase().contains(search))
                searchList.add(model);

            //Toast.makeText(context, "list"+getPreEaxmModels.size(), Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }

    }
}
