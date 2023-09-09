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

import com.rankerspoint.academy.Activity.SingleDoubtDetails;
import com.rankerspoint.academy.Model.GetCourseAllDoubts;
import com.rankerspoint.academy.R;

import java.util.List;

public class GetDoubtsCourseAdapter extends RecyclerView.Adapter<GetDoubtsCourseAdapter.BannerImage> {


    List<GetCourseAllDoubts> getPreEaxmModels;
   // List<GetPreEaxmModel> searchList;
    Context context;

    public GetDoubtsCourseAdapter(Context context, List<GetCourseAllDoubts> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
//        this.searchList=new ArrayList<>();
//        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public GetDoubtsCourseAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.doubt_cate_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetDoubtsCourseAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetCourseAllDoubts getPreEaxmModel = getPreEaxmModels.get(position);
        holder.txt_title_doubt.setText(getPreEaxmModel.getTitle());
        holder.txt_descrip.setText(getPreEaxmModel.getDescription());
        holder.tv_toolbar_title.setText(getPreEaxmModel.getUserName());
//        Date c = Calendar.getInstance().getTime();
//        System.out.println("Current time => " + c);
//
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);
        holder.tv_toolbar_date.setText(getPreEaxmModel.getAddDate());
        holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleDoubtDetails.class);
                intent.putExtra("DOUBTS_ID", getPreEaxmModel.getDoubtId().toString());

                context.startActivity(intent);
            }
        });

       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        //Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);

       // holder.img.setImageResource(images.get(position));
//        holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubCategoryClass.class);
//                intent.putExtra("CAT_ID", getPreEaxmModel.getCategoryId().toString());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
      return getPreEaxmModels.size();

    }

    public static class BannerImage extends RecyclerView.ViewHolder {



        private TextView txt_title_doubt,txt_descrip,tv_toolbar_title,tv_toolbar_date;
        private CardView card_preexam_subcat;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_title_doubt=itemView.findViewById(R.id.txt_title_doubt);
            txt_descrip=itemView.findViewById(R.id.txt_descrip);
            tv_toolbar_title=itemView.findViewById(R.id.tv_toolbar_title);
            tv_toolbar_date=itemView.findViewById(R.id.tv_toolbar_date);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);

        }
        // each data item is just a string in this case

    }
//    public  void filter(String search)
//    {
//        searchList.clear();
//        search=search.toLowerCase();
//        for (GetPreEaxmModel model:getPreEaxmModels)
//        {
//            if (model.getName().toLowerCase().contains(search))
//                searchList.add(model);
//
//            //Toast.makeText(context, "list"+getPreEaxmModels.size(), Toast.LENGTH_SHORT).show();
//            notifyDataSetChanged();
//        }
//
//    }
}
