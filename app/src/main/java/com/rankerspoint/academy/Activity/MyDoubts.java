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
import com.rankerspoint.academy.Adapter.GetDoubtsMyUserAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetUserAllDoubts;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDoubts extends AppCompatActivity {
    RecyclerView recy_preexam;
    EditText edt_search;
    ProgressDialog progressDialog;
    LinearLayout lnr_layout;
    GetDoubtsMyUserAdapter getExamCategoryAdapter;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    String UserId="";
    private List<GetUserAllDoubts> getPreEaxmModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doubts);
        recy_preexam=findViewById(R.id.recy_preexam);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("My Course Doubts");
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
        lnr_layout=findViewById(R.id.lnr_layout);
        SharedPreferences preferences =getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        UserId = preferences.getString("user_id", "user_id");
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recy_preexam.setLayoutManager(new GridLayoutManager(MyDoubts.this,1));
        getPreEaxmModels();
    }
    private void getPreEaxmModels() {
        getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETALLUSERDOUBTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GETALLUSERDOUBTS", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("GETALLUSERDOUBTS",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_preexam.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            GetUserAllDoubts getPreEaxmModel = new GetUserAllDoubts();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("GETALLUSERDOUBTS", jsonObject1.toString());
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


                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamCategoryAdapter = new GetDoubtsMyUserAdapter(MyDoubts.this, getPreEaxmModels);
                            recy_preexam.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
//                        Toast.makeText(MyDoubts.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("UserId",UserId);
                params.put("Email",UserId);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}