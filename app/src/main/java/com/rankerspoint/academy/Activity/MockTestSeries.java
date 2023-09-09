package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.rankerspoint.academy.Adapter.GetMockTestBySeriesByCourseAdapter;
import com.rankerspoint.academy.Model.GetMockTestSeriesByCourseModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.MockSeriesAdapter;
import com.rankerspoint.academy.Model.GetMockSeriesModel;

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

public class MockTestSeries extends AppCompatActivity {
    MockSeriesAdapter mockSeriesAdapter;
    RecyclerView recy_mock_test,recy_moreclasses;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack,img_live_location;
    TextView tv_toolbar_title;
    String categoryid="";
    private List<GetMockSeriesModel> getPreEaxmModels = new ArrayList<>();
    GetMockTestBySeriesByCourseAdapter getMockTestBySeriesByCourseAdapter;
    private List<GetMockTestSeriesByCourseModel> getMockTestSeriesByCourseModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_series);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   recy_mock_test=findViewById(R.id.recy_mock_test);
        recy_moreclasses=findViewById(R.id.recy_moreclasses);
        lnr_layout=findViewById(R.id.lnr_layout);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("All Mock Text Series");
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "");
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MockTestSeries.this, LinearLayoutManager.HORIZONTAL, false);
//linearLayoutManager.setReverseLayout(true);
        recy_moreclasses.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recy_mock_test.setLayoutManager(new GridLayoutManager(MockTestSeries.this,3));
        getSubMoreClassesModels(categoryid);
    }
    private void getSubMoreClassesModels(String categoryid) {
        getMockTestSeriesByCourseModels.clear();
        String SUBCATALL= BaseUrl.GETALLCOURSEBYCATEGORY+"/"+categoryid.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSubMoreClassesModels", response);
                try {
                    getMockTestSeriesByCourseModels.clear();
//
                    Log.d("Myresponse",response);
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getSubMoreClassesModels",jsonObject.toString());

                    // Toast.makeText(getContext(), "response:--"+response, Toast.LENGTH_SHORT).show();

                    if (!response.isEmpty()) {

                        for (int i = 0; i < jsonObject.length(); i++) {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getSubMoreClassesModels", jsonObject1.toString());
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Name = jsonObject1.getString("Name");

                            GetMockTestSeriesByCourseModel getPreEaxmModel = new GetMockTestSeriesByCourseModel(CourseId,CategoryId,Name);

                            getMockTestSeriesByCourseModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getMockTestBySeriesByCourseAdapter = new GetMockTestBySeriesByCourseAdapter(MockTestSeries.this, getMockTestSeriesByCourseModels, recy_moreclasses, new GetMockTestBySeriesByCourseAdapter.OnItemClicked() {
                            @Override
                            public void onItemClick(int position) {

                                try {
                                    getPreEaxmModels(getMockTestSeriesByCourseModels.get(position).getCourseId());
                                }catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }

                            }
                        });


                        recy_moreclasses.setAdapter(getMockTestBySeriesByCourseAdapter);
                        // getExamSubCategoryAdapter.notifyDataSetChanged();
                        if (getMockTestSeriesByCourseModels.size() > 0) {
                            getPreEaxmModels(getMockTestSeriesByCourseModels.get(0).getCourseId());

                        }
                    }else
                    {
                        //lnr_layout.setVisibility(View.VISIBLE);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MockTestSeries.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void getPreEaxmModels(String courseid) {
        getPreEaxmModels.clear();
String stringUrl= BaseUrl.GETALLSERIESBYCOURSEANDTYPE+"/"+courseid+"/"+"MockTest".trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, stringUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mock_detail", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("mock_detail",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_mock_test.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String SeriesId = jsonObject1.getString("SeriesId");
                            String Title = jsonObject1.getString("Title");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Status = jsonObject1.getString("Status");
                            String FeeAmount = jsonObject1.getString("FeeAmount");
                            String Pic = jsonObject1.getString("Pic");
                            String TotalExam = jsonObject1.getString("TotalExam");

                            GetMockSeriesModel getPreEaxmModel = new GetMockSeriesModel(SeriesId,Title,FeeStatus,Status,FeeAmount, Pic,TotalExam);

                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            mockSeriesAdapter = new MockSeriesAdapter(MockTestSeries.this, getPreEaxmModels);
                            recy_mock_test.setAdapter(mockSeriesAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(MockTestSeries.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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