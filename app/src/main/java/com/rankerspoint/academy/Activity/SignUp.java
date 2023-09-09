package com.rankerspoint.academy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    TextView Login_sign;
    Button btn_regis;
    ProgressDialog progressDialog;
    String email = "", pass = "", confirmpass = "", MobNo = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String deviceId = "";
    TextInputEditText edt_txt_emailmob, edt_txt_pass, edt_txt_confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);    Login_sign = findViewById(R.id.Login_sign);
        edt_txt_emailmob = findViewById(R.id.edt_txt_emailmob);
        edt_txt_pass = findViewById(R.id.edt_txt_pass);
        btn_regis = findViewById(R.id.btn_regis);
        edt_txt_confirmpass = findViewById(R.id.edt_txt_confirmpass);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        MobNo = getIntent().getStringExtra("MobNo");
        edt_txt_emailmob.setText(MobNo);
        edt_txt_emailmob.setEnabled(false);
        deviceId = getDeviceId();
        Log.d("deviceid:-", deviceId);
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_txt_emailmob.getText().toString();
                pass = edt_txt_pass.getText().toString();
                confirmpass = edt_txt_confirmpass.getText().toString();
                if (edt_txt_emailmob.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_enter_email1), Toast.LENGTH_SHORT).show();
                }
//                else if(!edt_txt_emailmob.getText().toString().matches(emailPattern)){
//                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.invalid_email_pattern), Toast.LENGTH_SHORT).show();
//                }
                else if (edt_txt_pass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_enter_pwd), Toast.LENGTH_SHORT).show();
                } else if (edt_txt_confirmpass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_enter_confirm_pwd), Toast.LENGTH_SHORT).show();
                } else if (!edt_txt_pass.getText().toString().equals(edt_txt_confirmpass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.both_pwds_doent_match), Toast.LENGTH_SHORT).show();
                } else {
                    if (isNetworkConnected()) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("Email", edt_txt_emailmob.getText().toString().trim());
                            jsonObject.put("Password", edt_txt_pass.getText().toString().trim());
                            jsonObject.put("ConfirmPassword", edt_txt_confirmpass.getText().toString().trim());
                            jsonObject.put("Role", "User");
                            jsonObject.put("RegType", "Email");
                            jsonObject.put("DeviceId", deviceId);
                            Register(jsonObject);
                            edt_txt_pass.setText("");
                            edt_txt_confirmpass.setText("");
                            progressDialog.show();
                        } catch (Exception ex) {
                            Toast.makeText(SignUp.this, "ex" + ex, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.show();
                    } else {
                        Toast.makeText(SignUp.this, "No internet!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Login_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void Register(JSONObject jsonObject) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.SIGNUP, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("register:--", response.toString());
                        String Succeeded, message, error;
                        try {
                            Succeeded = response.getString("Succeeded");
                            if (Succeeded.equals("True")) {
                                message = response.getString("Message");
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                                SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("user_id", edt_txt_emailmob.getText().toString());
                                editor.apply();
                                Toast.makeText(SignUp.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                error = response.getString("Errors");
                                Toast.makeText(SignUp.this, error, Toast.LENGTH_SHORT).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(jsonObjReq);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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