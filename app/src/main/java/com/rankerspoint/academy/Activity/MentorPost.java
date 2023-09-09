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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.MentorPostHomeAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.MentorPostHomeModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorPost extends AppCompatActivity {
    List<MentorPostHomeModel> getHomeCategorymodels=new ArrayList<>();
    MentorPostHomeAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid="",name="";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    String user_id="";
    TextView tv_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_post);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   recyler_notes=findViewById(R.id.recy_exam_series);
        lnr_layout=findViewById(R.id.lnr_layout);
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        user_id = preferences.getString("user_id", "");
        categoryid=getIntent().getStringExtra("CAT_ID");
        Log.d("userid_pay",user_id+"categoryid"+categoryid);
        tv_toolbar_title.setText("Mentor Post");
        recyler_notes.setLayoutManager(new GridLayoutManager(MentorPost.this,1));
        recyler_notes.setNestedScrollingEnabled(false);
        getAllSyllabusData();
    }
    private void getAllSyllabusData() {
        getHomeCategorymodels.clear();
        String strUrl= BaseUrl.GETALLPOSTBYCATEGORYMENTOR+"/"+categoryid;
        Log.d("mentorposturl",strUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allliveClass", response);
                try {
                    getHomeCategorymodels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                            String PostId = jsonObject1.getString("PostId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String UserId = jsonObject1.getString("UserId");
                            String UserName = jsonObject1.getString("UserName");
                            String UserPic = jsonObject1.getString("UserPic");

                            String Title = jsonObject1.getString("Title");
                            String PostType = jsonObject1.getString("PostType");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String Link1 = jsonObject1.getString("Link1");
                            String Link2 = jsonObject1.getString("Link2");
                            String AddDate = jsonObject1.getString("AddDate");

                            String date=AddDate.substring(0,10);
                            String UpVotes = jsonObject1.getString("UpVotes");
                            String DownVotes = jsonObject1.getString("DownVotes");
                            String Comments = jsonObject1.getString("Comments");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Href1 = jsonObject1.getString("Href1");
                            String Href2 = jsonObject1.getString("Href2");

                            MentorPostHomeModel getAllSyllabusCatModel = new MentorPostHomeModel(PostId,CategoryId,SubCategoryId,CourseId,UserId,UserName,UserPic,Title,PostType,Pic1,Link1,Link2,date,UpVotes,DownVotes,Comments,FeeStatus,Href1,Href2);

                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new MentorPostHomeAdapter(getApplicationContext(),getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                      //  Toast.makeText(MentorPost.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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