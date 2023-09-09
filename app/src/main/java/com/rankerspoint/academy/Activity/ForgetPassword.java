package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class ForgetPassword extends AppCompatActivity {
    String MobNo="",Otp="" ,otp2="";
    CountDownTimer startTimer;
    TextView txt_mobile,txtEdit,BtnResend,txt_timer;
    EditText et_otp;
    EditText edt_new_pass;
    ImageView img_times;
    ProgressDialog progressDialog;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        txt_mobile=findViewById(R.id.txt_mobile);
        txtEdit=findViewById(R.id.txtEdit);
        BtnResend=findViewById(R.id.BtnResend);
        et_otp=findViewById(R.id.et_otp);
        img_times=findViewById(R.id.img_times);
        txt_timer=findViewById(R.id.txt_timer);
        btnVerify=findViewById(R.id.btnVerify);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        MobNo=getIntent().getStringExtra("MobNo");
        Otp=getIntent().getStringExtra("Otp");

        Log.d("mob:---",MobNo.toString()+"Otp:--"+Otp.toString());
        txt_mobile.setText(MobNo);
        otp2= new DecimalFormat("000000").format(new Random().nextInt(999999));

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().equals(""))
                {
                    Toast.makeText(ForgetPassword.this, "Enter Your Otp", Toast.LENGTH_SHORT).show();
                }else if (!et_otp.getText().toString().equals(Otp))
                {
                    Toast.makeText(ForgetPassword.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                }else
                {
                    if (isNetworkConnected())
                    {
                        showFeedbackDialog(ForgetPassword.this);
                    }else {
                        Toast.makeText(ForgetPassword.this,getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        BtnResend.setEnabled(false); // The button is initially disabled //

        startTimer();

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


        BtnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
                progressDialog.show();
            }
        });
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),Mobile_Registration.class);
                startActivity(intent);
                finish();

            }
        });

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public void registerUser() {
//        String OTPMOBILE = "http://173.45.76.226:81/send.aspx?username=allexamtrick&pass=allexamtrick2021&route=trans1&senderid=RNKRPT&numbers=91"+MobNo+"&message=Dear%20Students%20Your%20OTP%20is%20"+otp2+"%20Rankers%20Point.";
        String OTPMOBILE = "http://bhashsms.com/api/sendmsg.php?user=MCORP&pass=123456&sender=RANKRZ&phone="+MobNo+"&text="+otp2+" is your otp for Pathan Paathan. Do not share it with anyone.&priority=ndnd&stype=normal";
        Otp=otp2;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OTPMOBILE.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("OTPRESPONSE",response.toString());
    txt_timer.setVisibility(View.VISIBLE);
                        BtnResend.setEnabled(false);
                        BtnResend.setTextColor(getResources().getColor(R.color.overlay_dark_50));
                        img_times.setImageResource(R.drawable.ic_message);
                        startTimer();

                        Toast.makeText(ForgetPassword.this, "Successfully otp sent:-"+MobNo, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPassword.this, "error:-"+error.toString(), Toast.LENGTH_SHORT).show();
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

                Log.e("TAG", "onTick: "+sec );
                txt_timer.setText(String.format("00:"+"%02d", sec));
                if(sec == 1)
                {
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
                BtnResend.setEnabled(true); // The button is enabled by the timer afte 5 seconds //
                img_times.setImageResource(R.drawable.ic_baseline_message_24);
                BtnResend.setTextColor(getResources().getColor(R.color.theme_purple));

            }
        }.start();
    }
    public  void showFeedbackDialog(Activity activity) {
        final androidx.appcompat.app.AlertDialog alertDialog;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_forget_new_pass, null);
        builder.setView(view);
        edt_new_pass = view.findViewById(R.id.enter_new_pass);
        AppCompatButton btn_submit = (AppCompatButton) view.findViewById(R.id.bt_submit_forgt);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.show();

        btn_submit.setOnClickListener(v -> {

            if (edt_new_pass.getText().toString().equals("")) {
                Toast.makeText(activity, "Insert New Password", Toast.LENGTH_SHORT).show();

            }

            else {
                getNewPassword();
            }

        });

    }
    public void getNewPassword() {
        //  getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.FORGRTPASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getuserdetails", response);
                try {
                    // getPreEaxmModels.clear();



                    if (response.length()==0) {

                        Toast.makeText(ForgetPassword.this, "Forget password Successfully!!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ForgetPassword.this,Login.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ForgetPassword.this, "Forget error", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception
                        e) {
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

                params.put("UserId",MobNo);
                params.put("NewPassword",edt_new_pass.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}