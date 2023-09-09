package com.rankerspoint.academy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class Otp_Verification extends AppCompatActivity {
    String MobNo = "", Otp = "", otp2 = "";
    CountDownTimer startTimer;
    TextView txt_mobile, txtEdit, BtnResend, txt_timer;
    EditText et_otp;
    ImageView img_times;
    ProgressDialog progressDialog;
    Button btnVerify;
    private OtpTextView otpView;
    private LinearLayout llResend;
    private TextView timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__verification);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        timerText = findViewById(R.id.timer_text);
        txt_mobile = findViewById(R.id.tv_number);
        BtnResend = findViewById(R.id.resend);
        txt_timer = findViewById(R.id.timer);
        otpView = findViewById(R.id.otp_view);
        llResend = findViewById(R.id.ll_resend);
        otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
            }

            @Override
            public void onOTPComplete(String otp) {
                if (!otpView.getOTP().equalsIgnoreCase(Otp)) {
                    Toast.makeText(Otp_Verification.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNetworkConnected()) {
                        Intent intent = new Intent(getApplicationContext(), SignUp.class);
                        intent.putExtra("MobNo", MobNo);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Otp_Verification.this, getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    /*    txtEdit = findViewById(R.id.txtEdit);

        et_otp = findViewById(R.id.et_otp);
        img_times = findViewById(R.id.img_times);

        btnVerify = findViewById(R.id.btnVerify);


        otp2 = new DecimalFormat("000000").format(new Random().nextInt(999999));
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().equals("")) {
                    Toast.makeText(Otp_Verification.this, "Enter Your Otp", Toast.LENGTH_SHORT).show();
                } else if (!et_otp.getText().toString().equals(Otp)) {
                    Toast.makeText(Otp_Verification.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNetworkConnected()) {
                        Intent intent = new Intent(getApplicationContext(), SignUp.class);
                        intent.putExtra("MobNo", MobNo);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Otp_Verification.this, getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        BtnResend.setEnabled(false); // The button is initially disabled //
        //Timer buttonTimer = new Timer();
//        buttonTimer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        BtnResend.setEnabled(true); // The button is enabled by the timer afte 5 seconds //
//                        img_times.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_message_24));
//                        BtnResend.setTextColor(getResources().getColor(R.color.theme_purple));
//                    }
//                });
//            }
//        }, 20000); // Set your time period here //

        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mobile_Registration.class);
                startActivity(intent);
                finish();
            }
        });*/
        MobNo = getIntent().getStringExtra("MobNo");
        Otp = getIntent().getStringExtra("Otp");
        txt_mobile.setText("Enter the Code sent on (+91) " + MobNo);
        startTimer();
        txt_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        BtnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                progressDialog.show();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void registerUser() {
//        String OTPMOBILE = "http://173.45.76.226:81/send.aspx?username=allexamtrick&pass=allexamtrick2021&route=trans1&senderid=AETLMS&numbers=" + MobNo + "&message=%20Dear%20Your%20OTP%20is%20" + otp2 + "%20ALL%20EXAM%20TRICKS";
        String OTPMOBILE = "http://bhashsms.com/api/sendmsg.php?user=MCORP&pass=123456&sender=RANKRZ&phone=" + MobNo + "&text=" + otp2 + " is your otp for Pathan Paathan. Do not share it with anyone.&priority=ndnd&stype=normal";
        Otp = otp2;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPMOBILE.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response_otp", response.toString());
                StringTokenizer stringTokenizer = new StringTokenizer(response, "|");
                String status = stringTokenizer.nextToken();
                Log.d("status_res:-", status);
                try {
                    if (status.equals("1")) {
                        timerText.setVisibility(View.VISIBLE);
                        txt_timer.setVisibility(View.VISIBLE);
                        llResend.setVisibility(View.GONE);
                        startTimer();
                        Toast.makeText(Otp_Verification.this, "Successfully otp sent:-" + MobNo, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Otp_Verification.this, "Otp Send error !!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Otp_Verification.this, "error:-" + error.toString(), Toast.LENGTH_SHORT).show();
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

    private void startTimer() {
        startTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long sec = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txt_timer.setText(String.format("00:" + "%02d", sec));
                if (sec == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txt_timer.setText("00:00");
                        }
                    }, 1000);
                }
            }

            public void onFinish() {
                txt_timer.setVisibility(View.GONE);
                timerText.setVisibility(View.GONE);
                llResend.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}