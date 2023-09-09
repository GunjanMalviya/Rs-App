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

import com.rankerspoint.academy.Adapter.DashboardFreeVideoAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.DashBoardFreeVideoModel;
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

public class Free_View_AllVideo extends AppCompatActivity {
    String categoryid="",feestatus="";

    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    DashboardFreeVideoAdapter freeLiveClassAdapter;
    List<DashBoardFreeVideoModel> getAllSyllabusCatModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free__view__all_video);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
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
        tv_toolbar_title.setText("Free Video Series");

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
      String  courseid = preferences.getString("CourseId", "CourseId");

        Log.d("CourseId",courseid);


        // Toast.makeText(this, "Status:----"+Status, Toast.LENGTH_SHORT).show();

        recyler_notes.setLayoutManager(new GridLayoutManager(Free_View_AllVideo.this,1));

        getFreeLiveClasses(courseid);


        //  Log.d("cateidid",categoryid);

    }
    private void getFreeLiveClasses(String categoryid) {
        getAllSyllabusCatModels.clear();
        // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL= BaseUrl.GETALLFREEVIDEODETAILSBYCOURSE+"/"+categoryid.trim();;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getfreeVideo", response);
                try {
                    getAllSyllabusCatModels.clear();
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

                            String DCContentType = jsonObject1.getString("DCContentType");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String VideoLink1 = jsonObject1.getString("VideoLink1");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Type = jsonObject1.getString("Type");




                            DashBoardFreeVideoModel freeVideoModel = new DashBoardFreeVideoModel(DCRecordId,DCCourseId,DCCourseName,DCContentType,Name,Details,Type,Pic,VideoLink1,FeeStatus);
                            getAllSyllabusCatModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        freeLiveClassAdapter=new DashboardFreeVideoAdapter(Free_View_AllVideo.this,getAllSyllabusCatModels);
                        recyler_notes.setAdapter(freeLiveClassAdapter);
                        freeLiveClassAdapter.notifyDataSetChanged();


                        // displaying selected image first

                    }else
                    {
                        recyler_notes.setVisibility(View.GONE);
                        lnr_layout.setVisibility(View.VISIBLE);
                        //  Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(Free_View_AllVideo.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}