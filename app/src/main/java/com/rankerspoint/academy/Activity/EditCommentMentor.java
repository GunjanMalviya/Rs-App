package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditCommentMentor extends AppCompatActivity {

    String categoryid="",user_name="",user_pic="",PostId="";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout,llout_comments,llout_upvotes;
    ProgressDialog progressDialog;
    String user_id="";
    ImageButton btn_sent1;
    EditText edt_sent1;
    ImageView userImage_imageView;
    TextView txt_userNm,txt_time,txt_title;
    String COMMENTID="";
    ImageView imgBack;
    TextView tv_toolbar_title;
    RelativeLayout bottomrd1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment_mentor);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        recyler_notes=findViewById(R.id.recy_exam_series);
        bottomrd1=findViewById(R.id.bottomrd1);
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
        tv_toolbar_title.setText("Update comment");
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        user_id = preferences.getString("user_id", "");

        user_name = preferences.getString("Firstname", "");
        user_pic = preferences.getString("UserPic", "");
        PostId=getIntent().getStringExtra("PostId");

        COMMENTID=getIntent().getStringExtra("COMMENTID");



        btn_sent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_sent1.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter your text", Toast.LENGTH_SHORT).show();
                } else {


                    try {

                        if (isNetworkConnected()) {
                            // edt_Sent.setText("");
                            setDataUpdate(COMMENTID,edt_sent1.getText().toString());

                            //firebase

                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        getAllSyllabusData("0",recyler_notes);

    }

    private void getAllSyllabusData(String pos,RecyclerView recyler_notes) {

        String strUrl= BaseUrl.GETSINGLEPOSTCOMMENTS+"/"+COMMENTID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("single_post", response);
                try {

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailpostf",jsonObject.toString());

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
                            txt_time.setText(date);
                            txt_title.setText(Text);
                            txt_userNm.setText(UserName);
                            edt_sent1.setText(Text);
                            Picasso.with(EditCommentMentor.this).load(BaseUrl.BANN_IMG_URL + "/"+"UserIcon.png").into(userImage_imageView);


                        }

                    }else
                    {

                        //Toast.makeText(EditCommentMentor.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    private void setDataUpdate(String commentId,String text) {

//        String strUrl=GETSINGLEPOSTCOMMENTS+"/"+COMMENTID;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, BaseUrl.UPDATEPOSTCOMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("single_post", response);
                try {



                    if (response.length()>0) {

                        edt_sent1.setText("");

                        try {

                            Toast.makeText(getApplicationContext(), "Message added!!", Toast.LENGTH_SHORT).show();
                            getAllSyllabusData("0",recyler_notes);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } catch (Exception e) {
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
                params.put("CommentId",commentId);
                params.put("Text",text);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}

