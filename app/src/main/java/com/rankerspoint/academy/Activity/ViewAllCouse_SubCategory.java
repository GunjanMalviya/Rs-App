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
import android.widget.TextView;

import com.rankerspoint.academy.Adapter.GetExamSubCourseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSubCourseModel;
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

public class ViewAllCouse_SubCategory extends AppCompatActivity {
    RecyclerView recy_subCourse;
    GetExamSubCourseAdapter getExamSubCourseAdapter;
    ProgressDialog progressDialog;
    String subcatid = "";
    ImageView imgBack;
    TextView tv_toolbar_title;
    private List<GetSubCourseModel> getSubCourseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_couse__sub_category);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   recy_subCourse = findViewById(R.id.recy_subCourse);
        imgBack = findViewById(R.id.imgBack);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title.setText("All Course");
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        subcatid = preferences.getString("SubCatId", "");
        progressDialog = new ProgressDialog(ViewAllCouse_SubCategory.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recy_subCourse.setLayoutManager(new GridLayoutManager(ViewAllCouse_SubCategory.this, 1));
        getSubPreEaxmModels(subcatid);
    }

    private void getSubPreEaxmModels(String SubCategoryId) {
        getSubCourseModels.clear();
        String SUBCATALL = BaseUrl.GETALLCOURSEBYSUBCATEGORY + "/" + SubCategoryId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sub_Course_frag", response);
                try {
                    getSubCourseModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("course_frag", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_subCourse.setVisibility(View.VISIBLE);
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
                            GetSubCourseModel getPreEaxmModel = new GetSubCourseModel(CourseId, CategoryId, SubCategoryId, Name, Details, Pic, Logo, RegLastDate, ExpiryDate, FreeTrail, Price, Langauge, Teachers);
                            getSubCourseModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }
                    getExamSubCourseAdapter = new GetExamSubCourseAdapter(ViewAllCouse_SubCategory.this, getSubCourseModels);
                    recy_subCourse.setAdapter(getExamSubCourseAdapter);
                    //getExamSubCourseAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAllCouse_SubCategory.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}