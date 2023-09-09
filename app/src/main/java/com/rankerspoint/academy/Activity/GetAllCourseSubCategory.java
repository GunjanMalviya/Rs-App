package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.rankerspoint.academy.Adapter.GetExamSubCourseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSubCourseModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllCourseSubCategory extends AppCompatActivity {
    RecyclerView recy_subCourse;
    GetExamSubCourseAdapter getExamSubCategoryAdapter;
    String categoryid;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    private List<GetSubCourseModel> getPreEaxmModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_course_sub_category);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        toolbar=findViewById(R.id.toolbar);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Courses");
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recy_subCourse=findViewById(R.id.recy_subCourse);
        lnr_layout=findViewById(R.id.lnr_layout);
        categoryid = getIntent().getStringExtra("SUB_CAT_ID");
        Log.d("categoryidd",categoryid);
        recy_subCourse.setLayoutManager(new GridLayoutManager(GetAllCourseSubCategory.this,1));
        getSubPreEaxmModels();
    }
    private void getSubPreEaxmModels() {
        getPreEaxmModels.clear();
        String SUBCATALL= BaseUrl.GETALLCOURSEBYSUBCATEGORY+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sub_Prod_detail", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam_sub",jsonObject.toString());

                    if (response!=null) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_subCourse.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CourseId = jsonObject1.getString("CourseId");

                            Log.d("CourseIdggg", CourseId);
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            String RegLastDate = jsonObject1.getString("RegLastDate");
                            String ExpiryDate = jsonObject1.getString("ExpiryDate");
                            String FreeTrail = jsonObject1.getString("FreeTrail");
                            String Price = jsonObject1.getString("Price");
                            String Langauge = jsonObject1.getString("Langauge");
                            String Teachers = jsonObject1.getString("Teachers");

                            GetSubCourseModel getPreEaxmModel = new GetSubCourseModel(CourseId,CategoryId,SubCategoryId,Name,Details,Pic,Logo,RegLastDate,ExpiryDate,FreeTrail,Price,Langauge,Teachers);

                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamSubCategoryAdapter = new GetExamSubCourseAdapter(GetAllCourseSubCategory.this, getPreEaxmModels);
                            recy_subCourse.setAdapter(getExamSubCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(GetAllCourseSubCategory.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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