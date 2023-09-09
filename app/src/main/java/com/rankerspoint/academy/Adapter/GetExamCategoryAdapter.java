package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.rankerspoint.academy.Activity.SubCatListener;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetPreEaxmModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GetExamCategoryAdapter extends RecyclerView.Adapter<GetExamCategoryAdapter.BannerImage> {


    List<GetPreEaxmModel> getPreEaxmModels;
    List<GetPreEaxmModel> searchList;
    Context context;
    SubCatListener subCatListener;

    public GetExamCategoryAdapter(Context context, List<GetPreEaxmModel> getPreEaxmModels, SubCatListener listener) {
        this.subCatListener = listener;
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.searchList=new ArrayList<>();
        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public GetExamCategoryAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.prepare_exam_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetExamCategoryAdapter.BannerImage holder, int position) {
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
                subCatListener.onCatSelected(getPreEaxmModel.getName(),getPreEaxmModel.getCategoryId() );

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
