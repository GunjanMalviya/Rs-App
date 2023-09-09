package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.IndividualTopicCatFragAdapter;
import com.rankerspoint.academy.Model.GetAllTopicIndividual;

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

public class Indiviual_Topic extends AppCompatActivity {
RecyclerView recyclerviewTopic;
ProgressDialog progressDialog;
String TopicId="",Position="",SNameTop="";
    ImageView imgBack;
    LinearLayout lout_notes,lout_quiz;

    Toolbar toolbar;
    TextView tv_toolbar_title,ind_txt_title,ind_txt_subtitle;
    private List<GetAllTopicIndividual> getAllTopicIndividuals = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indiviual__topic);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        ind_txt_title=findViewById(R.id.ind_txt_title);
        ind_txt_subtitle=findViewById(R.id.ind_txt_subtitle);
        lout_notes=findViewById(R.id.lout_notes);
        lout_quiz=findViewById(R.id.lout_quiz);


        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerviewTopic=findViewById(R.id.recyclerviewTopic);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        TopicId = preferences.getString("TopicId", "TopicId");
        Position = preferences.getString("Position", "Position");
        SNameTop = preferences.getString("SNameTop", "SNameTop");

        tv_toolbar_title.setText(Position+" "+SNameTop);
        Log.d("TopicIdNAME",TopicId);
        getAllTopicWiseVideo();
        lout_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Indiviual_Topic.this,TopicWisePdf.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("TopicID",TopicId);
                startActivity(intent);

            }
        });
        lout_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Indiviual_Topic.this,TopicQuizIndividual.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("TopicID",TopicId);
                startActivity(intent);

            }
        });
    }

    private void getAllTopicWiseVideo() {
        getAllTopicIndividuals.clear();
        String SUBCATALL= BaseUrl.GETALLTOPICWISEVIDEO+"/"+TopicId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getAllTopicWiseVideo", response);
                try {

                    getAllTopicIndividuals.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllTopicWiseVideoaa",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getAllTopicWiseVideoasd", jsonObject1.toString());
                            String VideoId = jsonObject1.getString("VideoId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String ChapterId = jsonObject1.getString("ChapterId");
                            String TopicId = jsonObject1.getString("TopicId");
                            String Name = jsonObject1.getString("Name");
                            String DCTopicName = jsonObject1.getString("DCTopicName");
                            String Details = jsonObject1.getString("Details");
                            String Type = jsonObject1.getString("Type");
                            String Pic = jsonObject1.getString("Pic");
                            String VideoLink1 = jsonObject1.getString("VideoLink1");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String startDate = jsonObject1.getString("StartDate");
                            String datae=startDate.substring(0,10);
                            Log.d("datae",datae);

                            ind_txt_title.setText(DCTopicName);
                            ind_txt_subtitle.setText(Details);
                            GetAllTopicIndividual getAllTopicIndividual = new GetAllTopicIndividual(VideoId,CategoryId,SubCategoryId,CourseId,SubjectId,ChapterId,TopicId,Name,Details,Type,Pic,VideoLink1,FeeStatus,datae);

                            getAllTopicIndividuals.add(getAllTopicIndividual);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        }

                        IndividualTopicCatFragAdapter individualTopicCatFragAdapter = new IndividualTopicCatFragAdapter( getAllTopicIndividuals,getApplicationContext());
                        recyclerviewTopic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerviewTopic.setAdapter(individualTopicCatFragAdapter);
                        individualTopicCatFragAdapter.notifyDataSetChanged();
                    }else
                    {

                       // Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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