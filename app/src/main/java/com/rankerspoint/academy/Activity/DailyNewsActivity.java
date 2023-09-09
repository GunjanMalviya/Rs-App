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
import com.rankerspoint.academy.Adapter.DailyNewsAdapter;
import com.rankerspoint.academy.Model.DailyNewsModel;
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

public class DailyNewsActivity extends AppCompatActivity {
    List<DailyNewsModel> getHomeCategorymodels = new ArrayList<>();
    DailyNewsAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid = "", name = "";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_news);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        recyler_notes = findViewById(R.id.recy_exam_series);
        lnr_layout = findViewById(R.id.lnr_layout);
        imgBack = findViewById(R.id.imgBack);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        categoryid = getIntent().getStringExtra("DailyGKID");
        tv_toolbar_title.setText("Daily News");
        recyler_notes.setLayoutManager(new GridLayoutManager(DailyNewsActivity.this, 1));
        recyler_notes.setNestedScrollingEnabled(false);
        getAllSyllabusData();
    }

    private void getAllSyllabusData() {
        getHomeCategorymodels.clear();
        String SUBCATALL = BaseUrl.GETSINGLEDAILYGKDETAILS + "/" + categoryid.trim();
        Log.d("url_daily", SUBCATALL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("gkstatus", response);
                try {
                    getHomeCategorymodels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String DailyGKId = jsonObject1.getString("DailyGKId");
                            String GKCategory = jsonObject1.getString("GKCategory");
                            String GKName = jsonObject1.getString("GKName");
                            String Title = jsonObject1.getString("Title");
                            String DetailsLag1 = jsonObject1.getString("DetailsLag1");
                            String DetailsLag2 = jsonObject1.getString("DetailsLag2");
                            String Link = jsonObject1.getString("Link");
                            String AddDate = jsonObject1.getString("AddDate");
                            String Pic1 = jsonObject1.getString("Pic1");
                            DailyNewsModel getAllSyllabusCatModel = new DailyNewsModel(DailyGKId, GKCategory, GKName, Title, DetailsLag1, DetailsLag2, Link, AddDate, Pic1);
                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new DailyNewsAdapter(getApplicationContext(), getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    } else {
                        lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(DailyNewsActivity.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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