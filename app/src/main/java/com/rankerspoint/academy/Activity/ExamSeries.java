package com.rankerspoint.academy.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Adapter.GetMockQuizSeriesAdapter;
import com.rankerspoint.academy.Model.GetExamSeriesMockModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamSeries extends AppCompatActivity {
String series_id="",feestatus="";
RecyclerView recy_exam_series;
    private List<GetExamSeriesMockModel> getExamSeriesMockModels = new ArrayList<>();
    private GetMockQuizSeriesAdapter getMockQuizSeriesAdapter;
    ProgressDialog progressDialog;
    ImageView imgBack;
    Toolbar toolbar;

    TextView tv_toolbar_title;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_series);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("");
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);

        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recy_exam_series=findViewById(R.id.recy_exam_series);
        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recy_exam_series.setLayoutManager(linearLayoutManager1);

        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recy_exam_series);

        SharedPreferences preferences1 = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String Status = preferences1.getString("Status", "");
        Log.d("successded:--",Status);

        series_id = preferences1.getString("cate_id_pay", "");
        feestatus = preferences1.getString("FeeStatusMentor", "");
        Log.d("successded:--",Status);

       // Toast.makeText(this, "Status:----"+Status, Toast.LENGTH_SHORT).show();


        if (feestatus.equals("Free"))
        {
            getFreeLiveClasses();
        }else {
            if (feestatus.equals("Paid"))
            {
                if (Status.equals("True"))
                {
                    getFreeLiveClasses();
                }
                else {
                    Intent intent=new Intent(ExamSeries.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }



    }
    private void getFreeLiveClasses() {
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
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("mockckcelive_eabc", jsonObject1.toString());
                            String ExamId = jsonObject1.getString("ExamId");
                            String SeriesId = jsonObject1.getString("SeriesId");
                            String Title = jsonObject1.getString("Title");
                           int time = jsonObject1.getInt("Duration");

                            String Duration=String.valueOf(time);
                           int marks = jsonObject1.getInt("TotalMarks");
                            String TotalMarks=String.valueOf(marks);
                            String EndDate = jsonObject1.getString("EndDate");
                            String FeeStatus = jsonObject1.getString("FeeStatus");


                            GetExamSeriesMockModel freeVideoModel = new GetExamSeriesMockModel(ExamId,SeriesId,Title,Duration,TotalMarks,EndDate,FeeStatus);
                            getExamSeriesMockModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getMockQuizSeriesAdapter=new GetMockQuizSeriesAdapter(getApplicationContext(),getExamSeriesMockModels);
                        recy_exam_series.setAdapter(getMockQuizSeriesAdapter);


                        // displaying selected image first

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
    public void reverseTimer(int Seconds, final TextView tv) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                tv.setText("TIME : " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("Completed");
            }
        }.start();
    }

}