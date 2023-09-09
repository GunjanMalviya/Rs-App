package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.TopicQiuzAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllTopicQuizModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicQuizIndividual extends AppCompatActivity {
    private List<GetAllTopicQuizModel> getAllTopicIndividuals= new ArrayList<>();
    TopicQiuzAdapter topicQiuzAdapter;
    RecyclerView recy_quiz;
    ProgressDialog progressDialog;
    String TopicID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_quiz_individual);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        recy_quiz=findViewById(R.id.recy_quiz);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        TopicID=getIntent().getStringExtra("TopicID");
        recy_quiz.setLayoutManager(new GridLayoutManager(TopicQuizIndividual.this,2));
        getAllTopicPdf();

    }


    private void getAllTopicPdf() {
        getAllTopicIndividuals.clear();
        String SUBCATALL= BaseUrl.GETALLQUIZTOPIC+"/"+TopicID.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getAllTopicWisePdf", response);
                try {

                    getAllTopicIndividuals.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllTopicWiseVidpdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getAllTopicWiseVideoasd", jsonObject1.toString());
                            String DCRecordId = jsonObject1.getString("DCRecordId");
                            String DCTopicId = jsonObject1.getString("DCTopicId");
                            String DCTopicName = jsonObject1.getString("DCTopicName");
                            String ExamId = jsonObject1.getString("ExamId");
                            String Title = jsonObject1.getString("Title");
                            int time = jsonObject1.getInt("Duration");

                            int marks = jsonObject1.getInt("TotalMarks");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String DCContentId = jsonObject1.getString("DCContentId");



                            String Duration=String.valueOf(time);

                            String TotalMarks=String.valueOf(marks);

                            GetAllTopicQuizModel getAllTopicIndividual = new GetAllTopicQuizModel(DCRecordId,DCTopicId,DCTopicName,DCContentId,ExamId,Title,Duration,TotalMarks,FeeStatus);

                            getAllTopicIndividuals.add(getAllTopicIndividual);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        }

                        topicQiuzAdapter = new TopicQiuzAdapter( getAllTopicIndividuals,getApplicationContext());
                        recy_quiz.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recy_quiz.setAdapter(topicQiuzAdapter);
                        topicQiuzAdapter.notifyDataSetChanged();
                    }else
                    {

                        //Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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