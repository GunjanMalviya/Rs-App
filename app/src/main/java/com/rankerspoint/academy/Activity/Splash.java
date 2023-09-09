package com.rankerspoint.academy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
 import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.BuildConfig;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Splash extends AppCompatActivity {
    Animation anim;
    ImageView imageView;
    TextView ver_name;
    String userId="";
    String deviceid,DeviceId1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);     SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        deviceid=getDeviceId();
        Log.d("deviceid:--",deviceid);
        userId = preferences.getString("user_id", "");
        Log.d("userId:--",userId);
        imageView=(ImageView)findViewById(R.id.imageView2);                            // Declare an imageView to show the animation.
        ver_name=findViewById(R.id.ver_name);
        String versionCode = String.valueOf(BuildConfig.VERSION_NAME);

//        ver_name.setText("ver "+versionCode);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (isNetworkConnected()) {
                    if (userId.equals("")||userId==null) {

                        Intent intent = new Intent(Splash.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        getUserDetails(userId);
//                        Intent intent=new Intent(getApplicationContext(), Dashboard.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                }
                }else
                {
                    Intent intent=new Intent(Splash.this,No_Internet_Conn.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
    }
    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getUserDetails(String userid ) {
        //  getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETSINGLEUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getuserdetails", response);
                try {
                    // getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getuserdetailss",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("GETUSERDetails", jsonObject1.toString());

                            String UserId = jsonObject1.getString("UserId");
                            String Firstname = jsonObject1.getString("Firstname");
                            String Lastname = jsonObject1.getString("Lastname");
                            String Pic = jsonObject1.getString("Pic");
                            DeviceId1 = jsonObject1.getString("DeviceId1");

                            if (deviceid.equals(DeviceId1))
                            {
                                Intent intent=new Intent(Splash.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (DeviceId1.equals("DeviceId1"))
                            {
                               // Toast.makeText(Splash.this, "Your User Id Already Login Other Device", Toast.LENGTH_SHORT).show();
                               // setDevicesIdReset();
                                Intent intent=new Intent(Splash.this,Login.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent=new Intent(Splash.this,Login.class);
                                startActivity(intent);
                                finish();
                               // Toast.makeText(Splash.this, "Your User Id Already Login Other Device", Toast.LENGTH_SHORT).show();
                            }
                        }
                        }
                       else
                    {

                       // Toast.makeText(Splash.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("UserId",userid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    public String getDeviceId() {
        String deviceId = android.provider.Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (deviceId != null && !deviceId.equals("")) {
            return deviceId;
        } else {
            return null;
        }
    }

}
