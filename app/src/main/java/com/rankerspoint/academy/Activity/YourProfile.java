package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetUserAllDoubts;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class YourProfile extends AppCompatActivity {
    ImageView imgBack,settings;
    Toolbar toolbar;
    CardView card_editProfile;
    TextView tv_toolbar_title,txt_post,txt_user,txt_followers,txt_following,txt_loc,txt_doutans,txt_thanksreceived,txt_questanscorrectly;
    ProgressDialog progressDialog;
    String userId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        txt_post=findViewById(R.id.txt_post);
        txt_user=findViewById(R.id.txt_user);
        txt_followers=findViewById(R.id.txt_followers);
        txt_following=findViewById(R.id.txt_following);
        txt_loc=findViewById(R.id.txt_loc);
        txt_doutans=findViewById(R.id.txt_doutans);
        txt_thanksreceived=findViewById(R.id.txt_thanksreceived);
        txt_questanscorrectly=findViewById(R.id.txt_questanscorrectly);
        settings=findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);
            }
        });
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        userId = preferences.getString("user_id", "");
        Log.d("userId:--",userId);
        //progress diaolog
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        card_editProfile=findViewById(R.id.card_editProfile);
        card_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MyProfile.class);
                startActivity(intent);
            }
        });
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title.setText("Your Profile");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getUserDetails();
    }
    private void getUserDetails() {
      //  getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETSINGLEUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getuserdetails", response);
                try {
                   // getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getuserdetailss12",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            GetUserAllDoubts getPreEaxmModel = new GetUserAllDoubts();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("GETUSERDetails123456", jsonObject1.toString());

                            String UserId = jsonObject1.getString("UserId");
                            String Firstname = jsonObject1.getString("Firstname");
                            String Lastname = jsonObject1.getString("Lastname");
                            String Gender = jsonObject1.getString("Gender");
                            String Email = jsonObject1.getString("Email");
                            String Mobile = jsonObject1.getString("Mobile");
                            String HouseNo = jsonObject1.getString("HouseNo");
                            String Street = jsonObject1.getString("Street");
                            String Landmark = jsonObject1.getString("Landmark");
                            String State = jsonObject1.getString("State");
                            String Country = jsonObject1.getString("Country");
                            String ZipCode = jsonObject1.getString("ZipCode");
                            String Longitude = jsonObject1.getString("Longitude");
                            String Latitude = jsonObject1.getString("Latitude");
                            String DoubtsAnswered = jsonObject1.getString("DoubtsAnswered");
                            String ThanksRecieved = jsonObject1.getString("ThanksRecieved");
                            String AnsweredCorrectly = jsonObject1.getString("AnsweredCorrectly");

                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Firstname",Firstname);
                            editor.apply();

                            String Posts = jsonObject1.getString("Posts");
                            String Followers = jsonObject1.getString("Followers");
                            String Following = jsonObject1.getString("Following");
                            txt_loc.setText("In "+Country);
                            txt_user.setText(Firstname);
                            txt_post.setText(Posts);
                            txt_followers.setText(Followers);
                            txt_following.setText(Following);
                        }
                    }else
                    {

                        //Toast.makeText(YourProfile.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

                params.put("UserId",userId);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}