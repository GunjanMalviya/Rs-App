package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.CommentMentorPostShowReplyAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.CommentMetorPostShowReplyModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorCommentReply extends AppCompatActivity {
    List<CommentMetorPostShowReplyModel> getHomeCategorymodels=new ArrayList<>();
    CommentMentorPostShowReplyAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid="",user_name="",user_pic="",PostId="";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout,llout_comments,llout_upvotes;
    ProgressDialog progressDialog;
    String user_id="";
    ImageButton btn_sent1;
    EditText edt_sent1;
    ImageView userImage_imageView;
    TextView txt_userNm,txt_time,txt_title;
    String NAME="", TIME="", USERPIC="", TITLE="",COMMENTID="";
    ImageView imgBack;
    TextView tv_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_comment_reply);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);      recyler_notes=findViewById(R.id.recy_exam_series);
        lnr_layout=findViewById(R.id.lnr_layout);
        edt_sent1=findViewById(R.id.edt_sent1);
        btn_sent1=findViewById(R.id.btn_sent1);
        userImage_imageView=findViewById(R.id.userImage_imageView);
        txt_userNm=findViewById(R.id.txt_userNm);
        txt_time=findViewById(R.id.txt_time);
        txt_title=findViewById(R.id.txt_title);
        llout_comments=findViewById(R.id.llout_comments);
        llout_upvotes=findViewById(R.id.llout_upvotes);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
tv_toolbar_title.setText("All Replies..");
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
       progressDialog.show();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        user_id = preferences.getString("user_id", "");

        user_name = preferences.getString("Firstname", "");
        user_pic = preferences.getString("UserPic", "");
        PostId=getIntent().getStringExtra("PostId");
        NAME=getIntent().getStringExtra("NAME");
        TIME=getIntent().getStringExtra("TIME");
        TITLE=getIntent().getStringExtra("TITLE");
        USERPIC=getIntent().getStringExtra("USERPIC");
        COMMENTID=getIntent().getStringExtra("COMMENTID");

        Picasso.with(MentorCommentReply.this).load(BaseUrl.BANN_IMG_URL + USERPIC).into(userImage_imageView);

        txt_title.setText(TITLE);
        txt_time.setText(TIME);
        txt_userNm.setText(NAME);
        recyler_notes.setLayoutManager(new GridLayoutManager(MentorCommentReply.this,1));
        recyler_notes.setNestedScrollingEnabled(false);
        btn_sent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_sent1.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter your text", Toast.LENGTH_SHORT).show();
                } else {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("ReplyCmntId", COMMENTID);
                        jsonObject.put("UserId", user_id);
                        jsonObject.put("UserName", user_name);
                        jsonObject.put("UserPic", user_pic);
                        jsonObject.put("CommentType", "Reply");
                        jsonObject.put("PostId", PostId);
                        jsonObject.put("Text", edt_sent1.getText().toString());
                        jsonObject.put("Pic1","");


                        if (isNetworkConnected()) {
                            // edt_Sent.setText("");
                            setMessageMentor(jsonObject);

                            //firebase

                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        getAllSyllabusData("0",recyler_notes);

    }
    private void getAllSyllabusData(String pos,RecyclerView recyler_notes) {
        getHomeCategorymodels.clear();
        String strUrl= BaseUrl.GETALLPOSTCOMNNETREPLY+"/"+COMMENTID;
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

                            String CommentId = jsonObject1.getString("CommentId");
                            String ReplyCmntId = jsonObject1.getString("ReplyCmntId");
                            String UserId = jsonObject1.getString("UserId");
                            String UserName = jsonObject1.getString("UserName");
                            String UserPic = jsonObject1.getString("UserPic");
                            String CommentType = jsonObject1.getString("CommentType");
                            String PostId = jsonObject1.getString("PostId");

                            String Text = jsonObject1.getString("Text");
                            String AddDate = jsonObject1.getString("AddDate");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String date=AddDate.substring(0,10);

                            CommentMetorPostShowReplyModel getAllSyllabusCatModel = new CommentMetorPostShowReplyModel(CommentId,ReplyCmntId,UserId,UserName,UserPic,CommentType,PostId,Text,date,Pic1);

                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new CommentMentorPostShowReplyAdapter(getApplicationContext(),getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(MentorCommentReply.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    public void setMessageMentor(JSONObject jsonObject) {


        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json123", jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.MENTORPOSTCOMMENT, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("chatsetdata:-", response.toString());
                        if (response != null) {

                            edt_sent1.setText("");

                            try {

                                Toast.makeText(getApplicationContext(), "Message added!!", Toast.LENGTH_SHORT).show();
                                getAllSyllabusData("0",recyler_notes);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjReq);

    }

    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}