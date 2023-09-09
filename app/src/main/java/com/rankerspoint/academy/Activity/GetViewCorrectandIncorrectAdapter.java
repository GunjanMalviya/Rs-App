package com.rankerspoint.academy.Activity;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.MyAnsResponse1;
import com.rankerspoint.academy.R;

import java.util.ArrayList;
import java.util.List;

public class GetViewCorrectandIncorrectAdapter extends RecyclerView.Adapter<GetViewCorrectandIncorrectAdapter.BannerImage> {
   Context context;
    List<MyAnsResponse1> MyAns=new ArrayList<>();
    public GetViewCorrectandIncorrectAdapter(Context context, List<MyAnsResponse1> MyAns){
        this.MyAns = MyAns;

        this.context=context;
    }

    @NonNull
    @Override
    public GetViewCorrectandIncorrectAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.linear_circle_layout, parent, false);
        return new BannerImage(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull GetViewCorrectandIncorrectAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        final MyAnsResponse1 getPreEaxmModel = MyAns.get(position);
        holder.txt_ans_correct.setText(""+(position+1));
        if (getPreEaxmModel.getQuesStatus().equalsIgnoreCase("C"))
        {
            holder.lll_out_ans.setBackground(context.getResources().getDrawable(R.drawable.primary_oval));
        }else if (getPreEaxmModel.getQuesStatus().equalsIgnoreCase("W"))
        {
            holder.lll_out_ans.setBackground(context.getResources().getDrawable(R.drawable.primary_oval_wong));

        }else if (getPreEaxmModel.getQuesStatus().equalsIgnoreCase("U"))
        {
            holder.lll_out_ans.setBackground(context.getResources().getDrawable(R.drawable.primary_oval_un));

        }
        else {
            Toast.makeText(context, "wrong ans!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
       return MyAns.size();
    }
    public static class BannerImage extends RecyclerView.ViewHolder {

        public TextView txt_ans_correct;
        LinearLayout lll_out_ans;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txt_ans_correct=itemView.findViewById(R.id.txt_ans_correct);

            lll_out_ans=itemView.findViewById(R.id.lll_out_ans);
        }
    }

}
