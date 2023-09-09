package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.rankerspoint.academy.Adapter.GetDoubtsCourseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetCourseAllDoubts;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllDoubtsCourse extends AppCompatActivity {
    RecyclerView recy_preexam;
    EditText edt_search;
    ProgressDialog progressDialog;
    LinearLayout lnr_layout;

    ImageView imgBack;
    Toolbar toolbar;
    Context context;
    TextView tv_toolbar_title;
    String courseid="";
    GetDoubtsCourseAdapter getExamCategoryAdapter;
    private List<GetCourseAllDoubts> getPreEaxmModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doubts_course);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        recy_preexam=findViewById(R.id.recy_preexam);
        context=this;
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Course Doubts");
        setSupportActionBar(toolbar);
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        courseid = preferences.getString("CourseId", "CourseId");

        Log.d("CourseIdall",courseid);

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lnr_layout=findViewById(R.id.lnr_layout);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recy_preexam.setLayoutManager(new GridLayoutManager(AllDoubtsCourse.this,1));
        getPreEaxmModels(courseid);
    }
    private void getPreEaxmModels( String courseid) {
        getPreEaxmModels.clear();
        String doubtsCourse= BaseUrl.GETALLDOUBTSCOURSE+"/"+courseid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, doubtsCourse, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("doubtsCourseall", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("doubtsCoursealz",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_preexam.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            GetCourseAllDoubts getPreEaxmModel = new GetCourseAllDoubts();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("doubtsCourseakkk", jsonObject1.toString());
                            getPreEaxmModel.setDoubtId(jsonObject1.getString("DoubtId"));
                            getPreEaxmModel.setCourseId(jsonObject1.getString("CourseId"));
                            getPreEaxmModel.setCategoryId(jsonObject1.getString("CategoryId"));
                            getPreEaxmModel.setSubCategoryId(jsonObject1.getString("SubCategoryId"));
                            getPreEaxmModel.setUserId(jsonObject1.getString("UserId"));
                            getPreEaxmModel.setUserName(jsonObject1.getString("UserName"));
                            getPreEaxmModel.setUserPic(jsonObject1.getString("UserPic"));
                            getPreEaxmModel.setPic(jsonObject1.getString("Pic"));
                            getPreEaxmModel.setTitle(jsonObject1.getString("Title"));
                            getPreEaxmModel.setDescription(jsonObject1.getString("Description"));
                            getPreEaxmModel.setType(jsonObject1.getString("Type"));
                            getPreEaxmModel.setAnswerStatus(jsonObject1.getString("AnswerStatus"));
String date=jsonObject1.getString("AddDate");

                          String dattdata=date.substring(0,10);
                            getPreEaxmModel.setAddDate(dattdata);

                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamCategoryAdapter = new GetDoubtsCourseAdapter(context, getPreEaxmModels);
                            recy_preexam.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        recy_preexam.setVisibility(View.GONE);
                        lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}