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

import com.rankerspoint.academy.Adapter.DashboardFreeQuizAdapter;
import com.rankerspoint.academy.Model.DashBoardFreeQuizModel;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllFeeQuizListDashboard extends AppCompatActivity {
    String categoryid="",feestatus="";

    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    DashboardFreeQuizAdapter dashboardFreeQuizAdapter;
    List<DashBoardFreeQuizModel> getDashboardFreeModels=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_fee_quiz_list_dashboard);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);    recyler_notes=findViewById(R.id.recy_exam_series);
        lnr_layout=findViewById(R.id.lnr_layout);
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title.setText("Free All Quiz");

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String  courseid = preferences.getString("CourseId", "CourseId");

        Log.d("CourseId",courseid);


        // Toast.makeText(this, "Status:----"+Status, Toast.LENGTH_SHORT).show();

        recyler_notes.setLayoutManager(new GridLayoutManager(ViewAllFeeQuizListDashboard.this,1));

        getFreeQuizDashboard(courseid);


        //  Log.d("cateidid",categoryid);

    }
    private void getFreeQuizDashboard(String categoryid) {
        getDashboardFreeModels.clear();
        // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL= BaseUrl.GETALLFREEQUIZDETAILSBYCOURSE+"/"+categoryid.trim();;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getfreeQuiz", response);
                try {
                    getDashboardFreeModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("videofree",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        recyler_notes.setVisibility(View.VISIBLE);
                        lnr_layout.setVisibility(View.GONE);
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getfreelive_abc", jsonObject1.toString());
                            String DCRecordId = jsonObject1.getString("DCRecordId");
                            String DCCourseId = jsonObject1.getString("DCCourseId");
                            String DCCourseName = jsonObject1.getString("DCCourseName");

                            String DCTopicId = jsonObject1.getString("DCTopicId");
                            String ExamId = jsonObject1.getString("ExamId");
                            String DCContentType = jsonObject1.getString("DCContentType");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Title = jsonObject1.getString("Title");
                            int Duration = jsonObject1.getInt("Duration");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Pic = jsonObject1.getString("Pic");
                            String Description = jsonObject1.getString("Description");

                            DashBoardFreeQuizModel freeVideoModel = new DashBoardFreeQuizModel(DCRecordId,DCCourseId,DCCourseName,DCTopicId,ExamId,DCContentType,CategoryId,Title,Duration+"",FeeStatus,Pic,Description);
                            getDashboardFreeModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        dashboardFreeQuizAdapter=new DashboardFreeQuizAdapter(ViewAllFeeQuizListDashboard.this,getDashboardFreeModels);
                        recyler_notes.setAdapter(dashboardFreeQuizAdapter);
                        dashboardFreeQuizAdapter.notifyDataSetChanged();

                        // displaying selected image first

                    }else
                    {
                        recyler_notes.setVisibility(View.GONE);
                        lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAllFeeQuizListDashboard.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}