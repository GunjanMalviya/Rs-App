package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.rankerspoint.academy.Adapter.GetHomeVideoSeriesListAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.HomeVideoSeriesListModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoSeriesList extends AppCompatActivity {
    String categoryid="";
    GetHomeVideoSeriesListAdapter getHomeNotesPdfAdapter;
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    List<HomeVideoSeriesListModel> getAllSyllabusCatModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_series_list);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   recyler_notes=findViewById(R.id.recy_exam_series);
        lnr_layout=findViewById(R.id.lnr_layout);
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title.setText("Video Category List");

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        categoryid=getIntent().getStringExtra("CAT_ID");
        Log.d("categoryid:---",categoryid);
        recyler_notes.setLayoutManager(new GridLayoutManager(VideoSeriesList.this,1));
        //  Log.d("cateidid",categoryid);
        getAllSyllabusData();
    }
    private void getAllSyllabusData() {
        getAllSyllabusCatModels.clear();
        String SUBCATALL= BaseUrl.GETVIDEOSERIESBYCATEGORY+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("videoSeries", response);
                try {
                    getAllSyllabusCatModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailsereis",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String DImageCategoryId = jsonObject1.getString("DImageCategoryId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String Name = jsonObject1.getString("Name");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String FeeStatus = jsonObject1.getString("FeeStatus");

                            HomeVideoSeriesListModel getAllSyllabusCatModel = new HomeVideoSeriesListModel(CategoryId,DImageCategoryId,CourseId,Name,Pic1,FeeStatus);
                            getAllSyllabusCatModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeNotesPdfAdapter = new GetHomeVideoSeriesListAdapter(getApplicationContext(),getAllSyllabusCatModels);
                        recyler_notes.setAdapter(getHomeNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(VideoSeriesList.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
