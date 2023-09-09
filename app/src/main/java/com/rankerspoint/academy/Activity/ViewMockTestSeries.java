package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.GetViewMockAnswerAdapter;
import com.rankerspoint.academy.Model.GetViewMockAnswerModel;
import com.rankerspoint.academy.Model.MyAnsResponse;
import com.google.gson.Gson;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewMockTestSeries extends AppCompatActivity {
    String exam_id="",subject_id="";
    ProgressDialog progressDialog;
    ImageView img_trans,imgBack;
    String strcheck="",duration,ExamId="";
    TextView tv_txt_Time;
    RecyclerView recy_question_series;
    private long exitTime = 0;
    List<MyAnsResponse> MyAns=new ArrayList<>();

    private List<GetViewMockAnswerModel> getViewAllQuestionAnsModels = new ArrayList<>();
    private GetViewMockAnswerAdapter getViewAllQuestionAnsAdapter;
    String question_l2="";
    String answer1_l2="";
    String answer2_l2="";
    String answer3_l2="";
    String answer4_l2="",examName="",CORRECT_LIST="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mock_test_series);

        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);

        img_trans=findViewById(R.id.img_trans);

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        exam_id=getIntent().getStringExtra("EXAM_ID");
        CORRECT_LIST=getIntent().getStringExtra("CORRECT_LIST");

        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        subject_id = preferences.getString("subject_id", "subject_id");
        Log.d("subject_id",subject_id);
        MyAns.clear();
        JSONArray jsonObjectobj = null;
        try {
            jsonObjectobj = new JSONArray(CORRECT_LIST);
            if (jsonObjectobj.length()>0) {
                for (int j = 0; j < jsonObjectobj.length(); j++) {
                    MyAnsResponse Ans=new MyAnsResponse();
                    JSONObject jsonObj = jsonObjectobj.getJSONObject(j);
                    Log.d("results2", jsonObj.toString());
                    String FlagStar = jsonObj.getString("FlagStar");
                    String QuesStatus = jsonObj.getString("QuesStatus");
                    String QuestionID = jsonObj.getString("QuestionID");
                    String SelAnsWer = jsonObj.getString("SelAnsWer");
                    //Log.d("strResults", "flagstate:--" + FlagStar + "QuesStatus:--" + QuesStatus + "QuestionID:--" + QuestionID);
                    Ans.setFlagStar(FlagStar);
                    Ans.setQuesStatus(QuesStatus);
                    Ans.setQuestionID(QuestionID);
                    Ans.setSelAnsWer(SelAnsWer);
                    MyAns.add(Ans);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





        Log.d("examname:--",examName+"exaid:--"+exam_id);

        getViewAllQuestionAnsAdapter=new GetViewMockAnswerAdapter(getApplicationContext(),getViewAllQuestionAnsModels);

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext() ,R.color.white_color));



        Log.d("EMAX_ID",exam_id);
        recy_question_series=findViewById(R.id.recy_question_series);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        recy_question_series.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getAllQuestionMock();
        img_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetViewMockAnswerAdapter.LangFlag= !GetViewMockAnswerAdapter.LangFlag;

                Log.d("Ques_Flag",String.valueOf(GetViewMockAnswerAdapter.LangFlag));
                getViewAllQuestionAnsAdapter.SetFilter();

            }
        });
    }
    private void getAllQuestionMock() {
        getViewAllQuestionAnsModels.clear();
        String SUBCATALL= BaseUrl.GETALLQUESTIONDETAILSBYEXAMANDSUBJECT+"/"+exam_id.trim()+"/"+subject_id.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("all_question", response);
                try {
                    getViewAllQuestionAnsModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("viewMocketetSeries_data",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            strcheck="test";
                            Log.d("all_question3", jsonObject1.toString());
                            String RecordId = jsonObject1.getString("RecordId");
                            String ExamId = jsonObject1.getString("ExamId");
                            String ExamName = jsonObject1.getString("ExamName");

                            String QuestionId = jsonObject1.getString("QuestionId");
                            String question_id = jsonObject1.getString("question_id");
                            String subject_id = jsonObject1.getString("subject_id");
                            String question = jsonObject1.getString("question");
                            String marks = String.valueOf( jsonObject1.getDouble("marks"));
                            String time_to_spend = jsonObject1.getString("time_to_spend");
                            String hint = jsonObject1.getString("hint");
                            String explanation = jsonObject1.getString("explanation");
                            String explanation_l2 = jsonObject1.getString("explanation_l2");
                            String correct_answer = jsonObject1.getString("correct_answer");
                            String answer1 = jsonObject1.getString("answer1");
                            String answer2 = jsonObject1.getString("answer2");
                            String answer3 = jsonObject1.getString("answer3");
                            String answer4 = jsonObject1.getString("answer4");
                            question_l2 = jsonObject1.getString("question_l2");
                            answer1_l2 = jsonObject1.getString("answer1_l2");
                            answer2_l2 = jsonObject1.getString("answer2_l2");
                            answer3_l2 = jsonObject1.getString("answer3_l2");
                            answer4_l2 = jsonObject1.getString("answer4_l2");

                            //Gson G=new Gson();

                            GetViewMockAnswerModel freeVideoModel = new GetViewMockAnswerModel(RecordId,ExamId,ExamName,QuestionId,question_id,subject_id,question,marks,time_to_spend,hint,correct_answer,answer1,answer2,answer3,answer4,question_l2,answer1_l2,answer2_l2,answer3_l2,answer4_l2,explanation,explanation_l2);
                            for (MyAnsResponse MAn:MyAns
                            ) {
                                if(QuestionId.equals(MAn.getQuestionID()))
                                {
                                    freeVideoModel.setCheckedAnswerStatus(MAn.getQuesStatus());
                                    freeVideoModel.setCheckedAnswer(MAn.getSelAnsWer());
                                }
                            }
                           /* freeVideoModel.setSelindex(0);
                            freeVideoModel.setFlgRating(false);*/
                            Log.d("New_Val", new Gson().toJson(freeVideoModel));
                            getViewAllQuestionAnsModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }




                        recy_question_series.setAdapter(getViewAllQuestionAnsAdapter);
                        GetViewMockAnswerAdapter.LangFlag=true;
                        getViewAllQuestionAnsAdapter.SetFilter();
                        getViewAllQuestionAnsAdapter.notifyDataSetChanged();


                        // displaying selected image first

                    }else
                    {
                      //  Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }





}