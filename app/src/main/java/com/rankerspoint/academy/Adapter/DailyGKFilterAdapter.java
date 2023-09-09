package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.GKFilterModel;
import com.rankerspoint.academy.R;

import java.util.List;

public class DailyGKFilterAdapter extends RecyclerView.Adapter<DailyGKFilterAdapter.BannerImage> {


    List<GKFilterModel> getPreEaxmModels;
//    List<DailyGkModel> searchList;
    Context context;
    private onItemClickListener mItemClickListener;

    public void setOnItemClickListener(onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClickListener(View view, int position);


    }
    public DailyGKFilterAdapter(Context context, List<GKFilterModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
//        this.searchList=new ArrayList<>();
//        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public DailyGKFilterAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.gk_fill_filter_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyGKFilterAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GKFilterModel getPreEaxmModel = getPreEaxmModels.get(position);

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
       // Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.img);
        holder.classe_txt.setText(getPreEaxmModel.getGkname());
        holder.classe_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClickListener(view,position);
                }
            }
        });

//        holder.classe_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

       // holder.img.setImageResource(images.get(position));


    }

    @Override
    public int getItemCount() {
        return getPreEaxmModels.size();
//        if (searchList.size() > 0) {
//            return searchList.size();
//        } else {
//            return 0;
//        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {



        private TextView classe_txt;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            classe_txt=itemView.findViewById(R.id.txt_title);




        }
        // each data item is just a string in this case

    }
//    public  void filter(String search)
//    {
//        searchList.clear();
//        search=search.toLowerCase();
//        for (DailyGkModel model:getPreEaxmModels)
//        {
//            if (model.getAddDate().contains(search))
//                searchList.add(model);
//
//            //Toast.makeText(context, "list"+getPreEaxmModels.size(), Toast.LENGTH_SHORT).show();
//            notifyDataSetChanged();
//        }
//
//    }


}
