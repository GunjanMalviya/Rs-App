package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.GetHomeVideoSeriesAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.HomeVideoSeriesModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoSeries extends AppCompatActivity {
    String categoryid="",feestatus="";
    GetHomeVideoSeriesAdapter getHomeNotesPdfAdapter;
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    List<HomeVideoSeriesModel> getAllSyllabusCatModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_series);
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
        tv_toolbar_title.setText("Video Series");

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        SharedPreferences preferences1 = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String Status = preferences1.getString("Status", "");
       categoryid = preferences1.getString("cate_id_pay", "");
         feestatus = preferences1.getString("FeeStatusMentor", "");
        Log.d("successded:--",Status);

       // Toast.makeText(this, "Status:----"+Status, Toast.LENGTH_SHORT).show();

        recyler_notes.setLayoutManager(new GridLayoutManager(VideoSeries.this,1));
    if (feestatus.equals("Free"))
    {
        getAllSyllabusData();
    }else {
        if (feestatus.equals("Paid"))
        {
            if (Status.equals("True"))
            {
                getAllSyllabusData();
            }
            else {
                Intent intent=new Intent(VideoSeries.this, PaymentCheckOut.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        }
    }


      //  Log.d("cateidid",categoryid);

    }
    private void getAllSyllabusData() {
        getAllSyllabusCatModels.clear();
        String SUBCATALL= BaseUrl.GETVIDEOSERIESBYCATEGORYALL+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("videoSeries", response);
                try {
                    getAllSyllabusCatModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailsereis",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String ImageId = jsonObject1.getString("ImageId");
                            String Heading = jsonObject1.getString("Heading");
                            String ImagePath = jsonObject1.getString("ImagePath");
                            String Details = jsonObject1.getString("Details");
                            String LinkType = jsonObject1.getString("LinkType");
                            String LinkId = jsonObject1.getString("LinkId");
                            String Extra1 = jsonObject1.getString("Extra1");

                            HomeVideoSeriesModel getAllSyllabusCatModel = new HomeVideoSeriesModel(ImageId,ImagePath,Heading,Details,LinkType,LinkId,Extra1);
                            getAllSyllabusCatModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeNotesPdfAdapter = new GetHomeVideoSeriesAdapter(getApplicationContext(),getAllSyllabusCatModels);
                        recyler_notes.setAdapter(getHomeNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(VideoSeries.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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