package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.rankerspoint.academy.Adapter.SingleTopicCatFragAdapter;
import com.rankerspoint.academy.Model.GetAllTopicSyllabusChapModel;
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

public class SyllabusSingle extends AppCompatActivity {
String chapterid="",name="",sno="";
    RecyclerView recyclerviewTopic;
    ProgressDialog progressDialog;
    TextView Sno,Name;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    private List<GetAllTopicSyllabusChapModel> getAllTopicSyllabusChapModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_single);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("");

        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Sno=findViewById(R.id.sno);
        Name=findViewById(R.id.name);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        chapterid = preferences.getString("ChapterId", "ChapterId");
        name = preferences.getString("SName", "SName");
        sno = preferences.getString("Sno", "Sno");
        Sno.setText(sno+".");
        Name.setText(name);
        Log.d("chapteridNAME",chapterid);
        recyclerviewTopic=findViewById(R.id.recyclerviewTopic);
        getAllTopicWiseChap();

    }
    private void getAllTopicWiseChap() {
getAllTopicSyllabusChapModels.clear();
        String SUBCATALL= BaseUrl.GETALLTOPICWISE+"/"+chapterid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getAllTopicWiseChapaaa", response);
                try {

                    getAllTopicSyllabusChapModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllTopicWiseChapsa",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getAllTopicWiseChaps", jsonObject1.toString());
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String TopicId = jsonObject1.getString("TopicId");
                            String ChapterId = jsonObject1.getString("ChapterId");
                            String CourseId = jsonObject1.getString("CourseId");



                            GetAllTopicSyllabusChapModel getAllTopicSyllabusChapModel = new GetAllTopicSyllabusChapModel(SubjectId,TopicId,ChapterId,CourseId,CategoryId,SubCategoryId,Name);

                            getAllTopicSyllabusChapModels.add(getAllTopicSyllabusChapModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);


                        }

                        SingleTopicCatFragAdapter singleChapterCatAdapter = new SingleTopicCatFragAdapter( getAllTopicSyllabusChapModels,getApplicationContext());
                        recyclerviewTopic.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerviewTopic.setAdapter(singleChapterCatAdapter);
                        singleChapterCatAdapter.notifyDataSetChanged();
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