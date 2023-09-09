package com.rankerspoint.academy.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.rankerspoint.academy.Adapter.GetAllQuestionAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllQuestionModel;
import com.google.gson.Gson;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllQuestionAttempt extends AppCompatActivity {
String exam_id="";
ProgressDialog progressDialog;
ImageView img_trans,imgBack;
String strcheck="",duration="";
TextView tv_txt_Time;
RecyclerView recy_question_series;
RelativeLayout rbl_out;
private List<GetAllQuestionModel> getAllQuestionModels = new ArrayList<>();
 public static GetAllQuestionAdapter getAllQuestionAdapter;
    String question_l2="";
    String answer1_l2="";
    String answer2_l2="";
    String answer3_l2="";
    String answer4_l2="",examName="",FeeStatusMentor="",status="";
    int count=0;
    List<String> selectedRadioValue;
    String userId="";
    HashMap<String, List<String>> ansList = new HashMap<String, List<String>>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_question_attempt);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        img_trans=findViewById(R.id.img_trans);
        imgBack=findViewById(R.id.imgBack);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        userId = preferences.getString("user_id", "user_id");
        FeeStatusMentor = preferences.getString("FeeStatusMentor", "");
        status = preferences.getString("Status", "");
        Log.d("userId:--",userId+"status:--"+status);

        tv_txt_Time=findViewById(R.id.tv_txt_Time);
        exam_id=getIntent().getStringExtra("EXAM_ID");
        duration=getIntent().getStringExtra("DURATION");
        examName=getIntent().getStringExtra("EXAMNAME");

      Log.d("examname:--",examName+"exaid:--"+exam_id+"duration"+duration);
        GetAllQuestionModel getAllQuestionModel=new GetAllQuestionModel();
        getAllQuestionAdapter=new GetAllQuestionAdapter(getApplicationContext(),getAllQuestionModels,getAllQuestionModel.getQuestionId(),tv_txt_Time.getText().toString(),exam_id,examName,userId);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext() ,R.color.white_color));
        int timeA=Integer.parseInt(duration);

        count=timeA*(60*60)/60;

        Log.d("EMAX_ID",exam_id+" stringdata:--"+GetAllQuestionAdapter.stringButtonradio.toString());
        recy_question_series=findViewById(R.id.recy_question_series);
        recy_question_series.setHasFixedSize(true);
        recy_question_series.setNestedScrollingEnabled(false);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        rbl_out=findViewById(R.id.rbl_out);
       if (FeeStatusMentor.equals("Free"))
       {
           init();
       }else {
           if (FeeStatusMentor.equals("Paid"))
           {
               if (status.equals("True"))
               {
                   init();
               }else
               {
                   Intent intent=new Intent(AllQuestionAttempt.this, PaymentCheckOut.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                   startActivity(intent);
               }
           }
       }


    }
    public void init()
    {
        rbl_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getAllQuestionAdapter.SubmitTestInformation();
                if (GetAllQuestionAdapter.stringButtonradio.toString().length()>0)
                {
                    submitResultsDialog(AllQuestionAttempt.this);
                }
                else
                {
                    Toast.makeText(AllQuestionAttempt.this, "Plz Attempt Question !!", Toast.LENGTH_SHORT).show();
                }
               /* Intent intent=new Intent(AllQuestionAttempt.this,Results.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);*/
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitResultsDialog(AllQuestionAttempt.this);
            }
        });
        recy_question_series.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getAllQuestionMock();
        img_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllQuestionAdapter.LangFlag= !GetAllQuestionAdapter.LangFlag;

                Log.d("Ques_Flag",String.valueOf(GetAllQuestionAdapter.LangFlag));
                getAllQuestionAdapter.SetFilter();

            }
        });
    }
    private void getAllQuestionMock() {
        getAllQuestionModels.clear();
        String SUBCATALL= BaseUrl.GETALLQUESTIONBYEXAM+"/"+exam_id.trim();
        Log.d("urlquestion:",SUBCATALL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("all_question", response);
                try {
                    getAllQuestionModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    //Log.d("all_question2",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                            reverseTimer(count,tv_txt_Time);
                            Log.d("all_question3", jsonObject1.toString());
                            String RecordId = jsonObject1.getString("RecordId");
                            String ExamId = jsonObject1.getString("ExamId");
                            String ExamName = jsonObject1.getString("ExamName");

                            String QuestionId = jsonObject1.getString("QuestionId");
                            String question_id = jsonObject1.getString("question_id");
                            String subject_id = jsonObject1.getString("subject_id");

//                            String topic_id1 = jsonObject1.getString("topic_id");
//                            StringTokenizer stringTokenizer=new StringTokenizer(topic_id1,",");
//                            String topic_id=stringTokenizer.nextToken();
//                            String topic_Nm=stringTokenizer.nextToken();

                            String question = jsonObject1.getString("question");
                           // String marks = String.valueOf( jsonObject1.getDouble("marks"));
                            String time_to_spend = jsonObject1.getString("time_to_spend");
                            String hint = jsonObject1.getString("hint");
                            String correct_answer = jsonObject1.getString("correct_answer");
                            String answer1 = jsonObject1.getString("answer1");
                            String answer2 = jsonObject1.getString("answer2");
                            String answer3 = jsonObject1.getString("answer3");
                            String answer4 = jsonObject1.getString("answer4");
                             question_l2 = jsonObject1.getString("question_l2");
                             answer1_l2 = jsonObject1.getString("answer1_l2");
                             answer2_l2 = jsonObject1.getString("answer2_l2");
                             answer3_l2 = jsonObject1.getString("answer3_l2");
                             answer4_l2 = jsonObject1.getString("answer4_l2");

                             //Gson G=new Gson();
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("subject_id","");
                            editor.apply();
                            GetAllQuestionModel freeVideoModel = new GetAllQuestionModel(RecordId,ExamId,ExamName,QuestionId,question_id,subject_id,question,"",time_to_spend,hint,correct_answer,answer1,answer2,answer3,answer4,question_l2,answer1_l2,answer2_l2,answer3_l2,answer4_l2,"","");
                            freeVideoModel.setSelindex(0);
                            freeVideoModel.setFlgRating(false);
                            Log.d("New_Val", new Gson().toJson(freeVideoModel));
                            getAllQuestionModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        //recy_question_series.setItemViewCacheSize(getAllQuestionModels.size());
                        recy_question_series.setAdapter(getAllQuestionAdapter);
                        GetAllQuestionAdapter.LangFlag=true;
                        getAllQuestionAdapter.SetFilter();
                            getAllQuestionAdapter.notifyDataSetChanged();

                        // displaying selected image first

                    }else
                    {
                       // Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    public void reverseTimer(int Seconds, final TextView tv) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                tv.setText(String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("Completed");
                tv.setVisibility(View.INVISIBLE);
                imgBack.setVisibility(View.INVISIBLE);

            }
        }.start();
    }

    public static void submitResultsDialog(Activity activity) {
        final AlertDialog alert_dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_submit_exam, null);
        builder.setView(view);

        TextView btn_submit = view.findViewById(R.id.btn_submit);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);

//        PushDownAnim.setPushDownAnimTo(btn_submit).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);

        alert_dialog = builder.create();
        alert_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alert_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
        alert_dialog.show();

        btn_submit.setOnClickListener(v -> {
            getAllQuestionAdapter.submitResults();
        });
        btn_cancel.setOnClickListener(v -> alert_dialog.dismiss());
    }
//    public void showAlertTimeFinish() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(AllQuestionAttempt.this);
//        builder.setMessage(getResources().getString(R.string.exceed_time_to_finish_exam)).setCancelable(false)
//                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//                        rbl_out.performClick();
//
//
//                    }
//                });
//
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(AllQuestionAttempt.this, getResources().getString(R.string.back_press_disabled), Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}