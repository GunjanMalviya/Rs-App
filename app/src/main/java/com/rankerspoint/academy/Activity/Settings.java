package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.rankerspoint.academy.BuildConfig;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Settings extends AppCompatActivity {
CardView  btn_rate,btn_feedback,btn_share;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    CardView btn_logout;
    ProgressDialog progressDialog;
  static   String date="",userId="",email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   btn_rate = findViewById(R.id.btn_rate);
        btn_logout=findViewById(R.id.btn_logout);
        btn_share=findViewById(R.id.btn_share);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        userId = preferences.getString("user_id", "user_id");
        email = preferences.getString("Email", "");
        progressDialog=new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        //datetime
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());


        btn_logout.setOnClickListener(v -> {
            showLogout(this);
        });
        btn_feedback=findViewById(R.id.btn_feedback);
        btn_rate.setOnClickListener(v -> {
            showRatingDialog(this);
        });
        toolbar=findViewById(R.id.toolbar);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Settings");
        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_feedback.setOnClickListener(v -> {
            showFeedbackDialog(this);
        });

btn_share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Install Shikha Mam GS Android App at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
});
    }
    public static void showRatingDialog(Activity activity) {
        final AlertDialog alert_dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_rating, null);
        builder.setView(view);
        final RatingBar rating_bar = view.findViewById(R.id.rating_bar);
        TextView btn_submit = view.findViewById(R.id.btn_submit);
        TextView tv_no = view.findViewById(R.id.tv_no);
//        PushDownAnim.setPushDownAnimTo(btn_submit).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);

        alert_dialog = builder.create();
        alert_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alert_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
        alert_dialog.show();

        btn_submit.setOnClickListener(v -> {
            if (rating_bar.getRating() >= 3) {
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));

                } catch (ActivityNotFoundException anfe) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
                }
                alert_dialog.dismiss();
            } else if (rating_bar.getRating() <= 0) {
                Toast.makeText(activity, "" + activity.getString(R.string.rating_error), Toast.LENGTH_SHORT).show();
            }

        });
        tv_no.setOnClickListener(v -> alert_dialog.dismiss());
    }
    public static void showFeedbackDialog(Activity activity) {
        final androidx.appcompat.app.AlertDialog alertDialog;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_feedback, null);
        builder.setView(view);
        final EditText edt_feedback = view.findViewById(R.id.edt_feedback);
        TextView btn_submit = view.findViewById(R.id.btn_submit);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimationTheme;
//        PushDownAnim.setPushDownAnimTo(btn_submit).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.show();
        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());
        btn_submit.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(edt_feedback.getText().toString())) {
                alertDialog.dismiss();
                sendFeedback(activity, edt_feedback.getText().toString());
            } else {
                Toast.makeText(activity, "" + activity.getString(R.string.empty_feedback), Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed

        super.onBackPressed();
    }
    public static void sendFeedback(Activity activity, String s) {

        String mailto = "mailto:" + email +
                "?cc=" + "" +
                "&subject=" + Uri.encode(activity.getString(R.string.app_name)) +
                "&body=" + Uri.encode(s);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));

        try {
            activity.startActivity(emailIntent);
        } catch (ActivityNotFoundException ignored) {
        }

    }

public  void updateUserLastLogin()
{
    StringRequest stringRequest=new StringRequest(Request.Method.PUT, BaseUrl.UPDATELASTLOGINBYEMAIL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("userLastLogin",response);

            try {
                if (response.equals("1"))
                {
                    SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("user_id", "");
                    editor.apply();
                        Intent intent=new Intent(Settings.this,Login.class);

                        startActivity(intent);

                }else
                {
                    Toast.makeText(Settings.this, "Error Logout !!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }, new Response.ErrorListener()
    {
        @Override
        public  void onErrorResponse(VolleyError error)
        {
progressDialog.dismiss();
        }
    })
    {
        @Override
        protected Map<String, String>getParams() throws AuthFailureError
        {
            HashMap<String,String> params=new HashMap<>();
            params.put("Email",userId);
            params.put("LastLogin",date);
            return params;
        }
    };
    RequestQueue requestQueue= Volley.newRequestQueue(this);
    requestQueue.getCache().clear();
    requestQueue.add(stringRequest);
}


    public  void showLogout(Activity activity) {
        final androidx.appcompat.app.AlertDialog alertDialog;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.custom_dialogbox_otp, null);
        builder.setView(view);



        Button btn_submit = view.findViewById(R.id.btn_yes);
        Button btn_cancel = view.findViewById(R.id.btn_no);
        alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimationTheme;
//        PushDownAnim.setPushDownAnimTo(btn_submit).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.show();
        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());
        btn_submit.setOnClickListener(v -> {
            progressDialog.show();
            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("user_id", "");

            editor.putString("Status","");

            editor.apply();
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);

        });

    }


}