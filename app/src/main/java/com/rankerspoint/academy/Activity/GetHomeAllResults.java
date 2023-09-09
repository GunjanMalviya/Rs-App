package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.Adapter.GetAllResultsHomeAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetHomeAllResultModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetHomeAllResults extends AppCompatActivity {
    String categoryid="";
    GetAllResultsHomeAdapter getHomeNotesPdfAdapter;
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    String userId;
    List<GetHomeAllResultModel> getAllSyllabusCatModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        setContentView(R.layout.activity_get_home_all_results);
        recyler_notes=findViewById(R.id.recy_exam_series);
        lnr_layout=findViewById(R.id.lnr_layout);
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title.setText("All Results");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyler_notes.setLayoutManager(new GridLayoutManager(GetHomeAllResults.this,1));
  try {


        getAllSyllabusData();}
  catch(Exception ex)
  {
      Toast.makeText(this, "error:--"+ex.getMessage(), Toast.LENGTH_SHORT).show();
  }
    }
    private void getAllSyllabusData() {
        getAllSyllabusCatModels.clear();
        //String SUBCATALL=GETALLNOTESPDF+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETHOMERESLUTSALL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("results", response);
                try {
                    getAllSyllabusCatModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("rfesultsall",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String MockExamResultId = jsonObject1.getString("MockExamResultId");
                            String MExamId = jsonObject1.getString("MExamId");
                            String UserId = jsonObject1.getString("UserId");
                            String Correct = jsonObject1.getString("Correct");
                            String Wrong = jsonObject1.getString("Wrong");
                            String Score = jsonObject1.getString("Score");
                            String Skipped = jsonObject1.getString("Skipped");
                            String TotalQuestion = jsonObject1.getString("TotalQuestion");
                            String PassedStatus = jsonObject1.getString("PassedStatus");
                            String SeriesId = jsonObject1.getString("SeriesId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String Title = jsonObject1.getString("Title");
                            String Accuracy = jsonObject1.getString("Accuracy");
                            String MAddDate = jsonObject1.getString("MAddDate");
                            int TotalMarks = jsonObject1.getInt("TotalMarks");
                            String date=MAddDate.substring(0,10);

                            GetHomeAllResultModel getAllSyllabusCatModel = new GetHomeAllResultModel(MockExamResultId,MExamId,UserId,Correct,Wrong,Score,Skipped,TotalQuestion,PassedStatus,SeriesId,CategoryId,CourseId,Title,date,TotalMarks+"",Accuracy);

                            getAllSyllabusCatModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeNotesPdfAdapter = new GetAllResultsHomeAdapter(getApplicationContext(),getAllSyllabusCatModels);
                        recyler_notes.setAdapter(getHomeNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(HomeNotesPdf.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("UserId",userId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}