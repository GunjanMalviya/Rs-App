package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GoExamMentorPost extends AppCompatActivity {
ImageView img_for;
LinearLayout lll_out;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_exam_mentor_post);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this); img_for=findViewById(R.id.img_for);
        lll_out=findViewById(R.id.lll_out);
        img_for.setColorFilter(getApplicationContext().getResources().getColor(R.color.overlay_dark_50));
String EXAM_ID=getIntent().getStringExtra("EXAM_ID");
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        lll_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSingleExamDetails(EXAM_ID);
                progressDialog.show();
            }
        });
    }

    private void getSingleExamDetails(String examId) {

        String SUBCATALL= BaseUrl.GETSINGLEXAMDETAILS+"/"+examId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mockfree", response);
                try {

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

                            Intent intent=new Intent(GoExamMentorPost.this, AllQuestionAttempt.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            intent.putExtra("EXAM_ID",ExamId);
                            intent.putExtra("DURATION",Duration);
                            intent.putExtra("EXAMNAME",Title);
                            startActivity(intent);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        // displaying selected image first
                    }else
                    {

                         Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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