package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.GetAllResultsAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllResultsModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllResults extends AppCompatActivity {
ProgressDialog progressDialog;
    private List<GetAllResultsModel> getAllResultsModels = new ArrayList<>();
    private GetAllResultsAdapter getAllResultsAdapter;
    RecyclerView recy_result_all;
    ImageView imgBack;
    private long exitTime = 0;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    String UserId="",EXAMID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_results);
        recy_result_all=findViewById(R.id.recy_result_all);
        SharedPreferences preferences =getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        UserId = preferences.getString("user_id", "user_id");

        EXAMID=getIntent().getStringExtra("EXAMID");
        Log.d("userid:-",UserId+"examid:--"+EXAMID);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Your Results");
        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(GetAllResults.this,Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }
        });



        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recy_result_all.setLayoutManager(new GridLayoutManager(GetAllResults.this,1));
    resultShow();
    }


    private void resultShow() {
        getAllResultsModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETEXAMRESULTSBYUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rsult", response);
                try {
getAllResultsModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllResults",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("results1", jsonObject1.toString());
                            String ExamResultId=jsonObject1.getString("ExamResultId");
                            String ExamId=jsonObject1.getString("ExamId");
                            String UserId=jsonObject1.getString("UserId");
                            String ExamName=jsonObject1.getString("ExamName");
                            String AddDate=jsonObject1.getString("AddDate");
                            String Score=jsonObject1.getString("Score");
                            String Rank=jsonObject1.getString("Rank");
                            String Correct=jsonObject1.getString("Correct");
                            String Wrong=jsonObject1.getString("Wrong");

                            String Unattemped=jsonObject1.getString("Unattemped");
                            String Status=jsonObject1.getString("Status");
                            String Extra1=jsonObject1.getString("Extra1");
                            String CorrectAnsList=jsonObject1.getString("CorrectAnsList");
                            JSONArray jsonObjectobj = new JSONArray(CorrectAnsList);
                            if (jsonObject.length()>0) {
                                for (int j = 0; j < jsonObjectobj.length(); j++) {
                                    JSONObject jsonObj = jsonObjectobj.getJSONObject(j);
                                    Log.d("results2", jsonObj.toString());
                                    String FlagStar = jsonObj.getString("FlagStar");
                                    String QuesStatus = jsonObj.getString("QuesStatus");
                                    String QuestionID = jsonObj.getString("QuestionID");
                                    String SelAnsWer = jsonObj.getString("SelAnsWer");
                                    Log.d("strResults", "flagstate:--" + FlagStar + "QuesStatus:--" + QuesStatus + "QuestionID:--" + QuestionID);
                                }
                            }else {
                                Toast.makeText(GetAllResults.this, "No data fount !!", Toast.LENGTH_SHORT).show();
                            }
                            GetAllResultsModel getPreEaxmModel=new GetAllResultsModel(ExamResultId,ExamId,UserId,ExamName,AddDate,Score,Rank,CorrectAnsList,Correct,Wrong,Unattemped,Status,Extra1);
                            getAllResultsModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        }
                        getAllResultsAdapter = new GetAllResultsAdapter(GetAllResults.this, getAllResultsModels);
                        recy_result_all.setAdapter(getAllResultsAdapter);
                      //  getExamCategoryAdapter.notifyDataSetChanged();


                                            }else
                    {
                        // lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(GetAllResults.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("ExamId",EXAMID);
                params.put("UserId",UserId);
//                params.put("ExamId","ExamvAWRoa8ruUmOpgAVF404aw");
//                params.put("UserId","7309338957");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


}