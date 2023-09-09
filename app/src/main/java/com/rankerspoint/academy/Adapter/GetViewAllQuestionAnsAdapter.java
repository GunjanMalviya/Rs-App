package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.GetViewAllQuestionAnsModel;
import com.rankerspoint.academy.Model.MyAnsResponse;
import com.google.gson.Gson;
import com.rankerspoint.academy.R;

import java.util.ArrayList;
import java.util.List;

public class GetViewAllQuestionAnsAdapter extends RecyclerView.Adapter<GetViewAllQuestionAnsAdapter.BannerImage> {
    List<GetViewAllQuestionAnsModel> getPreEaxmModels;
    Context context;
    public static boolean LangFlag=true;
    List<GetViewAllQuestionAnsModel> FinalList;
    String QuestionId="";
    String score1="";
    int UCts=0,CCts=0,WCts=0,score=0;
    String timeSet="",examid="",examName="";
    List<MyAnsResponse> MyAns=new ArrayList<>();
    public GetViewAllQuestionAnsAdapter(Context context, List<GetViewAllQuestionAnsModel> getPreEaxmModels){
        this.getPreEaxmModels = getPreEaxmModels;
        this.FinalList=new ArrayList<>();
        this.QuestionId=QuestionId;
        this.context=context;

    }

    @NonNull
    @Override
    public GetViewAllQuestionAnsAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_view_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetViewAllQuestionAnsAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        final GetViewAllQuestionAnsModel getPreEaxmModel = FinalList.get(position);

        //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        //  Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
        // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());
        final int count = position + 1;
        holder.txtQuesNo.setText("Q."+(position+1));
        holder.txtQuestion.setText(getPreEaxmModel.getQuestion());
        holder.txt_bg_A.setText(getPreEaxmModel.getAnswer1());
        holder.txt_bg_B.setText(getPreEaxmModel.getAnswer2());
        holder.txt_bg_C.setText(getPreEaxmModel.getAnswer3());
        holder.txt_bg_D.setText(getPreEaxmModel.getAnswer4());
        holder.txt_solu.setText(getPreEaxmModel.getExplanation());
        holder.txt_bg_A.setEnabled(false);
        holder.txt_bg_B.setEnabled(false);
        holder.txt_bg_C.setEnabled(false);
        holder.txt_bg_D.setEnabled(false);

        holder.txt_solu_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.txt_solu.getVisibility() == View.GONE) {
                    holder.txt_solu.setVisibility(View.VISIBLE);

                }
                else {

                    holder.txt_solu.setVisibility(View.GONE);

                }
            }
        });


        holder.txt_bg_A.setBackgroundColor(context.getResources().getColor(R.color.white_color));
        holder.txt_bg_A.setChecked(false);
        holder.txt_bg_B.setBackgroundColor(context.getResources().getColor(R.color.white_color));
        holder.txt_bg_B.setChecked(false);
        holder.txt_bg_C.setBackgroundColor(context.getResources().getColor(R.color.white_color));
        holder.txt_bg_C.setChecked(false);
        holder.txt_bg_D.setBackgroundColor(context.getResources().getColor(R.color.white_color));
        holder.txt_bg_D.setChecked(false);


        if(getPreEaxmModel.getCorrect_answer().equals("1")){
            holder.txt_bg_A.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_right));
            holder.txt_bg_A.setChecked(true);

        }
        else if(getPreEaxmModel.getCorrect_answer().equals("2")){
            holder.txt_bg_B.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_right));
            holder.txt_bg_B.setChecked(true);

        }
        else if(getPreEaxmModel.getCorrect_answer().equals("3")){
            holder.txt_bg_C.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_right));
            holder.txt_bg_C.setChecked(true);

        }
        else if(getPreEaxmModel.getCorrect_answer().equals("4")){
            holder.txt_bg_D.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_right));
            holder.txt_bg_D.setChecked(true);

        }
        if(getPreEaxmModel.getCheckedAnswerStatus().equals("W")){
            if(getPreEaxmModel.getCheckedAnswer().equals("1")){
                holder.txt_bg_A.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_wrong));

            }
            else if(getPreEaxmModel.getCheckedAnswer().equals("2")){
                holder.txt_bg_B.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_wrong));

            }
            else if(getPreEaxmModel.getCheckedAnswer().equals("3")){
                holder.txt_bg_C.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_wrong));

            }
            else if(getPreEaxmModel.getCheckedAnswer().equals("4")){
                holder.txt_bg_D.setBackground(context.getResources().getDrawable(R.drawable.radio_flat_wrong));

            }
        }






        if (getPreEaxmModels.get(position).getFlgRating()) {

            holder.img_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star));
        } else {
            holder.img_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
        }

        holder.img_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPreEaxmModels.get(position).setFlgRating(!getPreEaxmModels.get(position).getFlgRating());
                Toast.makeText(context, "FlagData : "+getPreEaxmModels.get(position).getFlgRating(), Toast.LENGTH_SHORT).show();
                if (getPreEaxmModels.get(position).getFlgRating()) {
                    holder.img_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star));
                } else {
                    holder.img_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
                }
            }
        });
        holder.rbg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {



                switch (checkedId)
               {
                   case R.id.txt_bg_A:
                       //strOption.add(getPreEaxmModel.getAnswer1().toString());
                       getPreEaxmModels.get(position).setSelindex(1);

                       break;
                   case R.id.txt_bg_B:
                       //strOption.add(getPreEaxmModel.getAnswer2().toString());
                       getPreEaxmModels.get(position).setSelindex(2);

                       break;
                   case R.id.txt_bg_C:
                       //strOption.add(getPreEaxmModel.getAnswer3().toString());
                       getPreEaxmModels.get(position).setSelindex(3);

                       break;
                   case R.id.txt_bg_D:
                       //strOption.add(getPreEaxmModel.getAnswer4().toString());
                       getPreEaxmModels.get(position).setSelindex(4);


                       break;
               }
            }
        });
    }

    @Override
    public int getItemCount() {
       // return getPreEaxmModels.size();
        if (FinalList.size() > 0) {
            return FinalList.size();
        } else {
            return 0;
        }

    }


    public static class BannerImage extends RecyclerView.ViewHolder {

        public TextView txtQuestion,txt_A,txt_B,txt_C,txt_D,txtQuesNo,txt_solu_head,txt_solu;
        LinearLayout lll_out_A,lll_out_B,lll_out_C,lll_out_D;
            RadioButton txt_bg_A,txt_bg_B,txt_bg_C,txt_bg_D;
            RadioGroup rbg_group;
            ImageView img_rating;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtQuestion=itemView.findViewById(R.id.txtQuestion);
            txtQuesNo=itemView.findViewById(R.id.txtQuesNo);
            txt_bg_A=itemView.findViewById(R.id.txt_bg_A);
            txt_bg_B=itemView.findViewById(R.id.txt_bg_B);
            txt_bg_C=itemView.findViewById(R.id.txt_bg_C);
            txt_bg_D=itemView.findViewById(R.id.txt_bg_D);
            rbg_group=itemView.findViewById(R.id.rbg_group);
            img_rating=itemView.findViewById(R.id.img_rating);
            txt_solu=itemView.findViewById(R.id.txt_solu);
            txt_solu_head=itemView.findViewById(R.id.txt_solu_head);
        }
    }
    public  void SetFilter()
    {
        FinalList.clear();
        GetViewAllQuestionAnsModel FnModel=new GetViewAllQuestionAnsModel();
            if (LangFlag) {
                for (GetViewAllQuestionAnsModel model : getPreEaxmModels) {
                    FnModel=new GetViewAllQuestionAnsModel();
                    FnModel.setQuestion(model.getQuestion());
                    FnModel.setAnswer1(model.getAnswer1());
                    FnModel.setAnswer2(model.getAnswer2());
                    FnModel.setAnswer3(model.getAnswer3());
                    FnModel.setAnswer4(model.getAnswer4());
                    FnModel.setExplanation(model.getExplanation());
                    FnModel.setCorrect_answer(model.getCorrect_answer());
                    FnModel.setCheckedAnswer(model.getCheckedAnswer());
                    FnModel.setCheckedAnswerStatus(model.getCheckedAnswerStatus());
                    FinalList.add(FnModel);

                    Log.d("Finallist1",new Gson().toJson(FnModel));
                }
            }
            else
            {
                for (GetViewAllQuestionAnsModel model : getPreEaxmModels) {
                    FnModel=new GetViewAllQuestionAnsModel();
                    FnModel.setQuestion(model.getQuestion_l2());
                    FnModel.setAnswer1(model.getAnswer1_l2());
                    FnModel.setAnswer2(model.getAnswer2_l2());
                    FnModel.setAnswer3(model.getAnswer3_l2());
                    FnModel.setAnswer4(model.getAnswer4_l2());
                    FnModel.setExplanation(model.getExplanation_l2());
                    FnModel.setCorrect_answer(model.getCorrect_answer());
                    FnModel.setCheckedAnswer(model.getCheckedAnswer());
                    FnModel.setCheckedAnswerStatus(model.getCheckedAnswerStatus());
                    FinalList.add(FnModel);
                    Log.d("Finallist2",new Gson().toJson(FnModel));
                }
            }
            notifyDataSetChanged();
    }
//   public void SubmitTestInformation(){
//          MyAns.clear();
//                CCts=0;    WCts=0;UCts=0;
//       for (GetViewAllQuestionAnsModel model : getPreEaxmModels) {
//           MyAnsResponse MAns=new MyAnsResponse();
//           MAns.setQuestionID(model.getQuestionId());
//           MAns.setFlagStar(String.valueOf(model.getFlgRating()));
//           String Status="U";
//           if(model.getSelindex()>0)
//           {
//               Status="W";
//                if(model.getCorrect_answer().equals(String.valueOf(model.getSelindex()))){
//                    Status="C";
//                }
//           }
//           if (Status.equals("U")) {
//               UCts++;
//           }
//           else if (Status.equals("W")) {
//                   WCts++;
//           }
//           else {
//                   CCts++;
//           }
//           int totalscroe=UCts+WCts+CCts;
//           score1=CCts+"/"+totalscroe;
//           MAns.setQuesStatus(Status);
//           MAns.setSelAnsWer(model.getSelindex()+"");
//           MyAns.add(MAns);
//
//       }
//
//       Log.d("Questions Data",new Gson().toJson(getPreEaxmModels));
//       Log.d("MyResponses",new Gson().toJson(MyAns)+" Counters : UCts => "+UCts+" ,WCts => "+WCts+" ,CCts => "+CCts);
//    }


}
