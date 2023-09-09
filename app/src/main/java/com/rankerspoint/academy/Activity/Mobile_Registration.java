package com.rankerspoint.academy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Mobile_Registration extends AppCompatActivity {
    EditText ed_register_mobile;
    TextView tv_register_now;
    String otp="";
    LinearLayout layout_allready;
    RadioGroup radioGroup1;
    RadioButton radio_mobile, radio_email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__registration);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);     radioGroup1 = findViewById(R.id.radioGroup1);
        radio_mobile = findViewById(R.id.radio_mobile);
        radio_email = findViewById(R.id.radio_email);
        layout_allready = findViewById(R.id.layout_allready);
        tv_register_now = findViewById(R.id.tv_register_now);
        ed_register_mobile = findViewById(R.id.ed_register_mobile);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        layout_allready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mobile_Registration.this, Login.class);
                startActivity(intent);
            }
        });
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_mobile:
                        break;
                    case R.id.radio_email:
                        Intent intent = new Intent(Mobile_Registration.this, EmailRegister.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        tv_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // http://173.45.76.226:81/send.aspx?username=jaihoinstitute & pass=india6352412010 &  &  &  & message=123456 is your OTP for verification.
                otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
//                Log.d("otpmobile", otp);
//                String username="jaihoinstitute";
//String pass="india6352412010";
//String route="trans1";
//String senderid="JAIHOO";
//String message=otp+" is your OTP for verification";
                if (ed_register_mobile.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_enter_user_name), Toast.LENGTH_SHORT).show();
                } else if (ed_register_mobile.getText().length() < 9) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_enter_user_name_valid), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (isNetworkConnected()) {
                            getregisterNo();
                            progressDialog.show();
                        } else {
                            Toast.makeText(Mobile_Registration.this, getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void registerUser() {
        // String OTPMOBILE = "http://173.45.76.226:81/send.aspx?username=jaihoinstitute&pass=india6352412010&route=trans1&senderid=JAIHOO&numbers="+ed_register_mobile.getText().toString().trim()+"&message="+otp+"%20is%20your%20OTP%20for%20verification.";
        String mobNo=ed_register_mobile.getText().toString().trim();
        String OTPMOBILE = "http://bhashsms.com/api/sendmsg.php?user=MCORP&pass=123456&sender=RANKRZ&phone="+mobNo+"&text="+otp+" is your otp for Pathan Paathan. Do not share it with anyone.&priority=ndnd&stype=normal";
//        String OTPMOBILE = "http://173.45.76.226:81/send.aspx?username=allexamtrick&pass=allexamtrick2021&route=trans1&senderid=RNKRPT&numbers=91"+mobNo+"&message=Dear%20Students%20Your%20OTP%20is%20"+otp+"%20Rankers%20Point.";
//                "http://www.bulksms.webcure.in/api/otp.php?authkey=377473Asvt4vtg628d321eP1&mobile="+ed_register_mobile.getText().toString().trim()+"&message=Your%20"+otp+"%20is%202786&sender=&otp="+otp;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPMOBILE.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response_otp", response.toString());
                try {
//                    JSONObject jsonObject = new JSONObject(response.toString());
//                    String type = jsonObject.optString("type");
//                    Log.d("status_res:-", type);
                    progressDialog.dismiss();
//                    if (type.equalsIgnoreCase("success")) {
                        Intent intent = new Intent(getApplicationContext(), Otp_Verification.class);
                        intent.putExtra("MobNo", ed_register_mobile.getText().toString());
                        intent.putExtra("Otp", otp);
                        startActivity(intent);
                        Toast.makeText(Mobile_Registration.this, "Successfully otp sent:-" + ed_register_mobile.getText().toString(), Toast.LENGTH_SHORT).show();
                        finish();
//                    } else {
//                        Toast.makeText(Mobile_Registration.this, "Otp not sent:-" + ed_register_mobile.getText().toString(), Toast.LENGTH_SHORT).show();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mobile_Registration.this, "error:-" + error.toString(), Toast.LENGTH_SHORT).show();
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void getregisterNo() {
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
                        Toast.makeText(Mobile_Registration.this, "This number all ready register", Toast.LENGTH_SHORT).show();
                    } else {
                        registerUser();
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
                params.put("UserId", ed_register_mobile.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}