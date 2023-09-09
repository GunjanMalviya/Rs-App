package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.Results;
import com.rankerspoint.academy.Model.GetAllQuestionModel;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rankerspoint.academy.R;

public class GetAllQuestionAdapter  extends RecyclerView.Adapter<GetAllQuestionAdapter .BannerImage> {
    List<GetAllQuestionModel> getPreEaxmModels;
    Context context;
    public static boolean LangFlag=true;
    List<GetAllQuestionModel> FinalList;
    String QuestionId="";

    public static String stringButtonradio="";
    String score1="";
    int UCts=0,CCts=0,WCts=0,score=0;
    String timeSet="",examid="",examName="",userId="";
    List<MyAnsResponse> MyAns=new ArrayList<>();

    public GetAllQuestionAdapter (Context context, List<GetAllQuestionModel> getPreEaxmModels,String QuestionId,String timeSet,String examid,String examName,String userId) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
        this.FinalList=new ArrayList<>();
        this.QuestionId=QuestionId;
        this.timeSet=timeSet;
        this.examid=examid;
        this.examName=examName;
        this.userId=userId;

    }

    @NonNull
    @Override
    public GetAllQuestionAdapter .BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_start_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllQuestionAdapter .BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        final GetAllQuestionModel getPreEaxmModel = FinalList.get(position);
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
        if(getPreEaxmModel.getSelindex()!=0)
        {
            if(getPreEaxmModel.getSelindex()==1)
            {

                holder.txt_bg_A.setChecked(true);
            }
            else if(getPreEaxmModel.getSelindex()==2)
            {
                holder.txt_bg_B.setChecked(true);
            }
            else if(getPreEaxmModel.getSelindex()==3)
            {
                holder.txt_bg_C.setChecked(true);
            }
            else if(getPreEaxmModel.getSelindex()==4)
            {
                holder.txt_bg_D.setChecked(true);
            }
        }

holder.txt_bg_A.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

SetSelection(holder.txt_bg_A,holder.txt_bg_B,holder.txt_bg_C,holder.txt_bg_D,holder.txt_bg_A,holder.txt_bg_A.isChecked(),position);
    }
});
        holder.txt_bg_B.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SetSelection(holder.txt_bg_A,holder.txt_bg_B,holder.txt_bg_C,holder.txt_bg_D,holder.txt_bg_B,holder.txt_bg_B.isChecked(),position);
            }
        });

        holder.txt_bg_C.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SetSelection(holder.txt_bg_A,holder.txt_bg_B,holder.txt_bg_C,holder.txt_bg_D,holder.txt_bg_C,holder.txt_bg_C.isChecked(),position);
            }
        });

        holder.txt_bg_D.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SetSelection(holder.txt_bg_A,holder.txt_bg_B,holder.txt_bg_C,holder.txt_bg_D,holder.txt_bg_D,holder.txt_bg_D.isChecked(),position);
            }
        });

//        holder.rbg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                Toast.makeText(context, "Data 11 "+holder.txt_bg_A.isChecked(), Toast.LENGTH_SHORT).show();
//                switch (checkedId)
//               {
//                   case R.id.txt_bg_A:
//                    holder.txt_bg_A.setChecked(!(holder.txt_bg_A.isChecked()));
//                  stringButtonradio = String.valueOf(holder.rbg_group.getCheckedRadioButtonId());
//                  //strOption.add(getPreEaxmModel.getAnswer1().toString());
//                  getPreEaxmModels.get(position).setSelindex(1);
//
//
//
//                       break;
//                   case R.id.txt_bg_B:
//                       //strOption.add(getPreEaxmModel.getAnswer2().toString());
//                       getPreEaxmModels.get(position).setSelindex(2);
//                       stringButtonradio=String.valueOf(holder.rbg_group.getCheckedRadioButtonId());
//                       break;
//                   case R.id.txt_bg_C:
//                       //strOption.add(getPreEaxmModel.getAnswer3().toString());
//                       getPreEaxmModels.get(position).setSelindex(3);
//                       stringButtonradio=String.valueOf(holder.rbg_group.getCheckedRadioButtonId());
//                       break;
//                   case R.id.txt_bg_D:
//                       //strOption.add(getPreEaxmModel.getAnswer4().toString());
//                       getPreEaxmModels.get(position).setSelindex(4);
//                       stringButtonradio=String.valueOf(holder.rbg_group.getCheckedRadioButtonId());
//
//                       break;
//               }
//            }
//        });
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

        public TextView txtQuestion,txt_A,txt_B,txt_C,txt_D,txtQuesNo;
        LinearLayout lll_out_A,lll_out_B,lll_out_C,lll_out_D;
            CheckBox txt_bg_A,txt_bg_B,txt_bg_C,txt_bg_D;

            ImageView img_rating;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtQuestion=itemView.findViewById(R.id.txtQuestion);
            txtQuesNo=itemView.findViewById(R.id.txtQuesNo);
            txt_bg_A=itemView.findViewById(R.id.txt_bg_A);
            txt_bg_B=itemView.findViewById(R.id.txt_bg_B);
            txt_bg_C=itemView.findViewById(R.id.txt_bg_C);
            txt_bg_D=itemView.findViewById(R.id.txt_bg_D);
           // rbg_group=itemView.findViewById(R.id.rbg_group);
            img_rating=itemView.findViewById(R.id.img_rating);
            this.setIsRecyclable(false);
        }
    }
    public  void SetFilter()
    {
        FinalList.clear();
        GetAllQuestionModel FnModel=new GetAllQuestionModel();
            if (LangFlag) {
                for (GetAllQuestionModel model : getPreEaxmModels) {
                    FnModel=new GetAllQuestionModel();
                    FnModel.setQuestion(model.getQuestion());
                    FnModel.setAnswer1(model.getAnswer1());
                    FnModel.setAnswer2(model.getAnswer2());
                    FnModel.setAnswer3(model.getAnswer3());
                    FnModel.setAnswer4(model.getAnswer4());
                    FnModel.setSelindex(model.getSelindex());
                    FinalList.add(FnModel);
                }
            }
            else
            {
                for (GetAllQuestionModel model : getPreEaxmModels) {
                    FnModel=new GetAllQuestionModel();
                    FnModel.setQuestion(model.getQuestion_l2());
                    FnModel.setAnswer1(model.getAnswer1_l2());
                    FnModel.setAnswer2(model.getAnswer2_l2());
                    FnModel.setAnswer3(model.getAnswer3_l2());
                    FnModel.setAnswer4(model.getAnswer4_l2());
                    FnModel.setSelindex(model.getSelindex());
                    FinalList.add(FnModel);
                }
            }
            notifyDataSetChanged();
    }
   public void SubmitTestInformation(){
          MyAns.clear();
                CCts=0;    WCts=0;UCts=0;
       for (GetAllQuestionModel model : getPreEaxmModels) {
           MyAnsResponse MAns=new MyAnsResponse();
           MAns.setQuestionID(model.getQuestionId());
           MAns.setTopicId(model.getTopic_id());
           MAns.setFlagStar(String.valueOf(model.getFlgRating()));
           String Status="U";
           if(model.getSelindex()>0)
           {
               Status="W";
                if(model.getCorrect_answer().equals(String.valueOf(model.getSelindex()))){
                    Status="C";
                }
           }
           if (Status.equals("U")) {
               UCts++;
           }
           else if (Status.equals("W")) {
                   WCts++;
           }
           else {
                   CCts++;
           }
           int totalscroe=UCts+WCts+CCts;
           score1=CCts+"/"+totalscroe;
           MAns.setQuesStatus(Status);
           MAns.setSelAnsWer(model.getSelindex()+"");
           MyAns.add(MAns);

       }

       Log.d("Questions_atempt",new Gson().toJson(getPreEaxmModels));
       Log.d("MyResponses_atempt",new Gson().toJson(MyAns)+" Counters : UCts => "+UCts+" ,WCts => "+WCts+" ,CCts => "+CCts);
    }

    void SetSelection(CheckBox C1,CheckBox C2,CheckBox C3,CheckBox C4,CheckBox C5,boolean res,int pos){

        C1.setChecked(false);
        C2.setChecked(false);
        C3.setChecked(false);
        C4.setChecked(false);
        if(C1.getId()==C5.getId())
        {
            C1.setChecked(res);
            getPreEaxmModels.get(pos).setSelindex(res?1:0);
        }
        else if(C2.getId()==C5.getId())
        {
            C2.setChecked(res);
            getPreEaxmModels.get(pos).setSelindex(res?2:0);
        }
        else if(C3.getId()==C5.getId())
        {
            C3.setChecked(res);
            getPreEaxmModels.get(pos).setSelindex(res?3:0);
        }
        else if(C4.getId()==C5.getId())
        {
            C4.setChecked(res);
            getPreEaxmModels.get(pos).setSelindex(res?4:0);
        }
        validateSubmission();
    }
    void validateSubmission(){
        stringButtonradio="";
        for (GetAllQuestionModel model : getPreEaxmModels) {
            if(model.getSelindex()!=0){
                stringButtonradio="True";
                break;
            }
        }
    }



    public void submitResults(){

        //getSubCourseModels.clear();
SubmitTestInformation();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.SUBMITEXAMRESULTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("submit_result", response);

                try {
                    // getSubCourseModels.clear();

                    //JSONArray jsonObject = new JSONArray(response);
                    Log.d("submit_result",response.toString());

                    if (response.equals(null))
                    {
                        Toast.makeText(context, "Wrong Submission !!!", Toast.LENGTH_SHORT).show();

                    }else {

//
                        Intent intent=new Intent(context, Results.class);
                        intent.putExtra("examId",examid);
                        intent.putExtra("userId",userId);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                        Toast.makeText(context, "Successfully submit !!", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("ExamId",examid);
                params.put("UserId",userId);
                params.put("ExamName",examName);
                params.put("Score",score1);
                params.put("CorrectAnsList",new Gson().toJson(MyAns));

                params.put("Correct",CCts+"");
                params.put("Wrong",WCts+"");
                params.put("Unattemped",UCts+"");
                params.put("Extra1",timeSet);
              //  params.put("Extra2",new Gson().toJson(MyAns));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    public class MyAnsResponse{
        public String getQuestionID() {
            return QuestionID;
        }

        public void setQuestionID(String questionID) {
            QuestionID = questionID;
        }

        public String getQuesStatus() {
            return QuesStatus;
        }

        public void setQuesStatus(String quesStatus) {
            QuesStatus = quesStatus;
        }

        public String QuestionID;
        public String QuesStatus;

        public String getFlagStar() {
            return FlagStar;
        }

        public void setFlagStar(String flagStar) {
            FlagStar = flagStar;
        }

        public String FlagStar;

        public String getSelAnsWer() {
            return SelAnsWer;
        }

        public void setSelAnsWer(String selAnsWer) {
            SelAnsWer = selAnsWer;
        }
        public String SelAnsWer;

        public String getTopicId() {
            return TopicId;
        }

        public void setTopicId(String topicId) {
            TopicId = topicId;
        }
        public String TopicId;

    }

}
