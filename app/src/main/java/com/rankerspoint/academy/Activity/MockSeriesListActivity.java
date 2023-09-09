package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.rankerspoint.academy.Adapter.GetMockQuizStartSeriesAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetMockExamStartModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockSeriesListActivity extends AppCompatActivity {
    String series_id="",Title="";
    RecyclerView recy_exam_series;
    LinearLayout lnr_layout;
    private List<GetMockExamStartModel> getExamSeriesMockModels = new ArrayList<>();
    private GetMockQuizStartSeriesAdapter getMockQuizSeriesAdapter;
    ProgressDialog progressDialog;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_series_list);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);

        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        series_id=getIntent().getStringExtra("SERIES_ID");
        Title=getIntent().getStringExtra("Title");
        Log.d("series_id:--",series_id);
        tv_toolbar_title.setText(Title);
        recy_exam_series=findViewById(R.id.recy_exam_series);
        lnr_layout=findViewById(R.id.lnr_layout);

        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recy_exam_series.setLayoutManager(linearLayoutManager1);

        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recy_exam_series);
        getMockExamSeriesList();
    }
    private void getMockExamSeriesList() {
        getExamSeriesMockModels.clear();
        String SUBCATALL= BaseUrl.GETALLEXAMBYSERIES+"/"+series_id.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mockfree", response);
                try {
                    getExamSeriesMockModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("mockfree2",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_exam_series.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("mockckcelive_eabc", jsonObject1.toString());
                            String ExamId = jsonObject1.getString("ExamId");
                            String SeriesId = jsonObject1.getString("SeriesId");
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String Title = jsonObject1.getString("Title");
                            int time = jsonObject1.getInt("Duration");
                            String Duration=String.valueOf(time);
                            int marks = jsonObject1.getInt("TotalMarks");
                            String TotalMarks=String.valueOf(marks);
                            String EndDate = jsonObject1.getString("EndDate");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String FeeAmount = jsonObject1.getString("FeeAmount");
                            String CourseId = jsonObject1.getString("CourseId");

                            GetMockExamStartModel freeVideoModel = new GetMockExamStartModel(ExamId,SubjectId,SeriesId,Title,Duration,TotalMarks,EndDate,FeeStatus,FeeAmount,CourseId);
                            getExamSeriesMockModels.add(freeVideoModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getMockQuizSeriesAdapter=new GetMockQuizStartSeriesAdapter(getApplicationContext(),getExamSeriesMockModels);
                        recy_exam_series.setAdapter(getMockQuizSeriesAdapter);
                        // displaying selected image first
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
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