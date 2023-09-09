package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.rankerspoint.academy.Model.ImageDemo;
import com.rankerspoint.academy.Model.LoginModel;
import com.rankerspoint.academy.Utils.API;
import com.rankerspoint.academy.Utils.ApiClient;
import com.rankerspoint.academy.Utils.Tools;
import com.rankerspoint.academy.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class Login  extends AppCompatActivity {
    private Button btnGo;
    private EditText edtNumber;
    private ProgressDialog progressDialog;
    String mobileNo;
    String password;
    private TextView tvHeader, tvForgotPassword;
    String deviceid = "", DeviceId1 = "";
    private EditText et_pro_email;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        deviceid = android.provider.Settings.Secure.getString(Login.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        btnGo = findViewById(R.id.btn_go);
//        tvHeader = findViewById(R.id.tv_header);
        tvForgotPassword = findViewById(R.id.txt_ForgetPass);
        edtNumber = findViewById(R.id.edt_txt_emailmob);
        otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedbackDialog(Login.this);
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                password = edtNumber.getText().toString();
                if (btnGo.getText().equals("SIGIN")) {
                    try {
                        Map<String, String> hashmap = new HashMap<>();
                        hashmap.put("username", mobileNo);
                        hashmap.put("password", password);
                        hashmap.put("grant_type", "password");
                        loginCalled(hashmap);
                    } catch (Exception ex) {
                        Toast.makeText(Login.this, "ex:--" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (edtNumber.getText() != null && !edtNumber.getText().toString().equals("") && edtNumber.getText().length() >= 9) {
                        mobileNo = edtNumber.getText().toString();
                        getregisterNo(false);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Please enter valid mobile number...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void showFeedbackDialog(Activity activity) {
        final androidx.appcompat.app.AlertDialog alertDialog;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_forget_pass, null);
        builder.setView(view);
        et_pro_email = view.findViewById(R.id.et_pro_mobile);
        AppCompatButton btn_submit = (AppCompatButton) view.findViewById(R.id.bt_submit_forgt);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        btn_submit.setOnClickListener(v -> {
            if (et_pro_email.getText().toString().equals("")) {
                Toast.makeText(activity, "Please Enter Register Mobile Number", Toast.LENGTH_SHORT).show();
            } else if (et_pro_email.getText().length() < 9) {
                Toast.makeText(activity, "Enter Valid Number", Toast.LENGTH_SHORT).show();
            } else {
                {
                    progressDialog.show();
                    getregisterNo(true);
                }
            }
        });
    }

    private void loginCalled(Map<String, String> hashmap) {
        progressDialog.show();
        API apiService = ApiClient.getClient(this)
                .create(API.class);
        retrofit2.Call<LoginModel> loginModelCall = apiService.DoLogin(hashmap);
        loginModelCall.enqueue(new retrofit2.Callback<LoginModel>() {
            @Override
            public void onResponse(retrofit2.Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                progressDialog.dismiss();
                Log.d("result", new Gson().toJson(response.body()));
                try {
                    if (response.isSuccessful()) {
                        getUserDetails();
                    } else {
                        Toast.makeText(Login.this, "PLease check your UserId or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoginModel> call, Throwable t) {
                Toast.makeText(Login.this, "Something went wrong!" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getregisterNo(boolean b) {
        //  getPreEaxmModels.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETSINGLEUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    if (jsonObject.length() > 0) {
                        progressDialog.dismiss();
                        if (b) {
                            forgetUser();
                        } else {
//                            tvHeader.setText("Enter Password");
                            btnGo.setText("SIGIN");
                            edtNumber.setText("");
                            edtNumber.setHint("Enter Password");
                        }
                    } else {
                        otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
                        registerUser();
//                        Intent intent = new Intent(Login.this, Mobile_Registration.class);
//                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
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
                if (b) {
                    params.put("UserId", et_pro_email.getText().toString());
                } else params.put("UserId", edtNumber.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void registerUser() {
//        String OTPMOBILE = "http://smsw.co.in/API/WebSMS/Http/v1.0a/index.php?username=abhiaw&password=686l2u-lAoa&sender=OTPSPM&to=" + mobileNo + "&message=%20Dear%20Student%20Your%20OTP%20is%20" + otp + ".%20spm infotech service &reqid=1&format={json|text}&pe_id= 1201161589219376932 &template_id= 1207164224346095185";
        String OTPMOBILE = "http://bhashsms.com/api/sendmsg.php?user=MCORP&pass=123456&sender=RANKRZ&phone="+mobileNo+"&text="+otp+" is your otp for Pathan Paathan. Do not share it with anyone.&priority=ndnd&stype=normal";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPMOBILE.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    Intent intent = new Intent(getApplicationContext(), Otp_Verification.class);
                    intent.putExtra("MobNo", mobileNo);
                    intent.putExtra("Otp", otp);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Login.this, "Successfully otp sent:- " + mobileNo, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Login.this, "OTP not sent:- " + mobileNo, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Otp Send error !!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
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
                    Log.d("getuserdetailss", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("GETUSERDetails", jsonObject1.toString());
                            String UserId = jsonObject1.getString("UserId");
                            String Firstname = jsonObject1.getString("Firstname");
                            String Lastname = jsonObject1.getString("Lastname");
                            String Pic = jsonObject1.getString("Pic");
                            DeviceId1 = jsonObject1.getString("DeviceId1");
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("user_id", mobileNo);
                            editor.putString("Firstname", Firstname);
                            editor.putString("UserPic", BaseUrl.BANN_IMG_URL + "/" + Pic);
                            editor.apply();
                        }
                        if (deviceid.equals(DeviceId1)) {
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("user_id", mobileNo);
                            editor.apply();
                            Intent intent = new Intent(Login.this, PrePareExam.class);
                            startActivity(intent);
                            finish();
                        } else if (DeviceId1.equals("DeviceId1")) {
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("user_id", mobileNo);
                            editor.apply();
                            setDevicesIdReset();
                        } else {
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("user_id", "");
                            editor.apply();
                            Toast.makeText(Login.this, "Your User Id Already Login Other Device", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(Login.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("UserId", mobileNo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void setDevicesIdReset() {
        //  getPreEaxmModels.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, BaseUrl.UPDATEDEVICEID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("updatedevices", response);
                try {
                    // getPreEaxmModels.clear();
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    startActivity(intent);
                    finish();
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
                params.put("UserId", mobileNo);
                params.put("DeviceId1", deviceid);
                Log.d("param_setdevi:-", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void forgetUser() {
        String OTPMOBILE = "http://smsw.co.in/API/WebSMS/Http/v1.0a/index.php?username=vacademy&password=0VcF8a-puPU&sender=OTPSPM&to=" + et_pro_email.getText().toString() + "&message=Dear Student your otp is " + otp + " .spm infotech service&reqid=1&format={json|text}&pe_id=1201161589219376932&template_id=1207164224346095185";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPMOBILE.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StringTokenizer stringTokenizer = new StringTokenizer(response, "|");
                String status = stringTokenizer.nextToken();
                try {
                    if (response.contains("status")) {
                        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                        intent.putExtra("MobNo", et_pro_email.getText().toString());
                        intent.putExtra("Otp", otp);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Login.this, "Successfully otp sent:-" + et_pro_email.getText().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Otp Send error !!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Login.this, "error:-"+error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Login.super.onBackPressed();
                        finishAffinity();
                        quit();
                    }
                }).create().show();
    }

    public void quit() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
    }
}