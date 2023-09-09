package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.SingleCourseDetails;
import com.rankerspoint.academy.Model.PaymentDetailsModel;
import com.rankerspoint.academy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.BannerImage> {


    List<PaymentDetailsModel> getPreEaxmModels;

    Context context;

    public PaymentDetailsAdapter(Context context, List<PaymentDetailsModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;


    }

    @NonNull
    @Override
    public PaymentDetailsAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.payment_details_course_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentDetailsAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final PaymentDetailsModel getPreEaxmModel = getPreEaxmModels.get(position);
        try {
            //dateformat
            String datef = getPreEaxmModel.getTransDate();

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(datef);
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            holder.txt_date.setText(dt1.format(date));
        }catch (Exception ex)
        {
            Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
        }
       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
       // Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.img);

        holder.txt_course.setText(getPreEaxmModel.getPurchasedServiceName());
        holder.txt_type.setText(getPreEaxmModel.getPurchaseType());
        //int str=Integer.getInteger(getPreEaxmModel.getAmount());

        holder.txt_amount.setText(getPreEaxmModel.getAmount());
        holder.txt_status.setText(getPreEaxmModel.getStatus());
        if (holder.txt_status.getText().toString().equalsIgnoreCase("Success"))
        {
            holder.img_success.setImageDrawable(context.getDrawable(R.drawable.ic_success));
        }
        else {
            holder.img_success.setImageDrawable(context.getDrawable(R.drawable.ic_cross));
        }
       // holder.img.setImageResource(images.get(position));
holder.card_pay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (holder.txt_status.getText().equals("success")) {
            Intent intent = new Intent(context, SingleCourseDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("COURSE_ID", getPreEaxmModel.getPurchasedServiceId());
            context.startActivity(intent);
        }else {
            Toast.makeText(context, "Payment Failed Retry Payment !!", Toast.LENGTH_SHORT).show();
        }
    }
});

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


        private ImageView img_success;
        private TextView txt_course,txt_type,txt_date,txt_amount,txt_status;
CardView card_pay;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_course=itemView.findViewById(R.id.txt_course);
            txt_type=itemView.findViewById(R.id.txt_type);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_amount=itemView.findViewById(R.id.txt_amount);
            txt_status=itemView.findViewById(R.id.txt_status);
            img_success=itemView.findViewById(R.id.img_success);
            card_pay=itemView.findViewById(R.id.card_pay);


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
