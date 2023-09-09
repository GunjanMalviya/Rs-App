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
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import com.rankerspoint.academy.Adapter.GetAllMockQuestionAdapter;

import com.rankerspoint.academy.Adapter.SubjectShowAdapter;
import com.rankerspoint.academy.Model.GetAllMockQuestionModel;

import com.rankerspoint.academy.Model.SubjectShowModel;
import com.google.gson.Gson;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
//import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
//
//import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_PUSH_DURATION;
//import static com.thekhaeng.pushdownanim.PushDownAnim.DEFAULT_RELEASE_DURATION;
//import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class MockQuestionAttempts extends AppCompatActivity {
    String exam_id="";
    ProgressDialog progressDialog;
    ImageView img_trans,imgBack;
    String strcheck="",duration;
    TextView tv_txt_Time;
    LinearLayout ll_take_exam_previous,ll_take_exam_next;
    RecyclerView recy_question_series,recy_subject;
    RelativeLayout rbl_out;

    private List<GetAllMockQuestionModel> getAllQuestionModels = new ArrayList<>();
    private List<SubjectShowModel> subjectShowModels = new ArrayList<>();
    public static GetAllMockQuestionAdapter getAllQuestionAdapter;
    public static SubjectShowAdapter subjectShowAdapter;
    String question_l2="";
    String answer1_l2="";
    String answer2_l2="";
    String answer3_l2="";
    String answer4_l2="",examName="";
    int count=0;

    List<String> selectedRadioValue;
    String userId="";
    HashMap<String, List<String>> ansList = new HashMap<String, List<String>>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_question_attempts);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        img_trans=findViewById(R.id.img_trans);
        imgBack=findViewById(R.id.imgBack);
        ll_take_exam_next=findViewById(R.id.ll_take_exam_next);
        ll_take_exam_previous=findViewById(R.id.ll_take_exam_previous);

        rbl_out=findViewById(R.id.rbl_out);

        recy_subject=findViewById(R.id.recy_subject);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        userId = preferences.getString("user_id", "user_id");
        Log.d("userId:--",userId);

        tv_txt_Time=findViewById(R.id.tv_txt_Time);
        exam_id=getIntent().getStringExtra("EXAM_ID");
        duration=getIntent().getStringExtra("DURATION");


        Log.d("examname:--",examName+"exaid:--"+exam_id);
        GetAllMockQuestionModel getAllQuestionModel=new GetAllMockQuestionModel();


        getAllQuestionAdapter=new GetAllMockQuestionAdapter(getApplicationContext(),getAllQuestionModels,getAllQuestionModel.getQuestionId(),tv_txt_Time.getText().toString(),exam_id,examName,userId);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext() ,R.color.white_color));
        int timeA=Integer.parseInt(duration);

        count=timeA*(60*60)/60;

        Log.d("EMAX_ID",exam_id+" stringdata:--"+GetAllMockQuestionAdapter.stringButtonradio.toString());
        recy_question_series=findViewById(R.id.recy_question_series);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getSubjectList();
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                submitResultsDialog(MockQuestionAttempts.this);
//            }
//        });

        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(MockQuestionAttempts.this, LinearLayoutManager.HORIZONTAL, false);
            recy_subject.setLayoutManager(linearLayoutManager1);
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(MockQuestionAttempts.this, LinearLayoutManager.HORIZONTAL, false);

        recy_question_series.setLayoutManager(linearLayoutManager2);




        ll_take_exam_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int CurrenePosition = ((LinearLayoutManager)recy_question_series.getLayoutManager()).findFirstVisibleItemPosition();
                recy_question_series.getLayoutManager().scrollToPosition(CurrenePosition-1);
//                if (linearLayoutManager2.findLastCompletelyVisibleItemPosition() < (getAllQuestionAdapter.getItemCount() - 1)) {
//                    linearLayoutManager2.scrollToPosition(linearLayoutManager2.findLastCompletelyVisibleItemPosition() - 1);
//                }
            }
        });
        ll_take_exam_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int CurrenePosition = ((LinearLayoutManager)recy_question_series.getLayoutManager()).findFirstVisibleItemPosition();
                recy_question_series.getLayoutManager().scrollToPosition(CurrenePosition+1);
                ll_take_exam_previous.setVisibility(View.VISIBLE);
//                if (linearLayoutManager2.findLastCompletelyVisibleItemPosition() < (getAllQuestionAdapter.getItemCount() - 1)) {
//                    linearLayoutManager2.scrollToPosition(linearLayoutManager2.findLastCompletelyVisibleItemPosition() + 1);
//                }

            }
        });
        img_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllMockQuestionAdapter.LangFlag= !GetAllMockQuestionAdapter.LangFlag;

                Log.d("Ques_Flag",String.valueOf(GetAllMockQuestionAdapter.LangFlag));
                getAllQuestionAdapter.SetFilter();

            }
        });


        //submit
        rbl_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getAllQuestionAdapter.SubmitTestInformation();
                if (GetAllMockQuestionAdapter.stringButtonradio.toString().length()>0)
                {
                    submitResultsDialog(MockQuestionAttempts.this);
                }
                else
                {
                    Toast.makeText(MockQuestionAttempts.this, "Plz Attempt Question !!", Toast.LENGTH_SHORT).show();
                }
               /* Intent intent=new Intent(AllQuestionAttempt.this,Results.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);*/
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GetAllMockQuestionAdapter.stringButtonradio.toString().length()>0)
                {
                    submitResultsDialog(MockQuestionAttempts.this);
                }
                else
                {
                    Toast.makeText(MockQuestionAttempts.this, "Plz Attempt Question !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void getAllQuestionMock(String subject_id) {
        getAllQuestionModels.clear();
        String SUBCATALL= BaseUrl.GETALLQUESTIONDETAILSBYEXAMANDSUBJECT+"/"+exam_id.trim()+"/"+subject_id.trim();
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


                            Log.d("all_mock/question3", jsonObject1.toString());
                            String RecordId = jsonObject1.getString("RecordId");
                            String ExamId = jsonObject1.getString("ExamId");
                            String ExamName = jsonObject1.getString("ExamName");

                            String QuestionId = jsonObject1.getString("QuestionId");
                            String question_id = jsonObject1.getString("question_id");
                            String subject_id = jsonObject1.getString("subject_id");

                            String topic_id1 = jsonObject1.getString("topic_id");
                            StringTokenizer stringTokenizer=new StringTokenizer(topic_id1,",");
                            String topic_id=stringTokenizer.nextToken();
                            String topic_Nm=stringTokenizer.nextToken();

                            String question = jsonObject1.getString("question");
                            String marks = String.valueOf( jsonObject1.getDouble("marks"));
                            String time_to_spend = jsonObject1.getString("time_to_spend");
                            String hint = jsonObject1.getString("hint");
                            String explanation = jsonObject1.getString("explanation");
                            String explanation_l2 = jsonObject1.getString("explanation_l2");
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
                            editor.putString("subject_id",subject_id);
                            editor.apply();

                            GetAllMockQuestionModel freeVideoModel = new GetAllMockQuestionModel(RecordId,ExamId,ExamName,QuestionId,question_id,subject_id,question,marks,time_to_spend,hint,correct_answer,answer1,answer2,answer3,answer4,question_l2,answer1_l2,answer2_l2,answer3_l2,answer4_l2,topic_id,topic_Nm);
                            freeVideoModel.setSelindex(0);
                            freeVideoModel.setFlgRating(false);
                            Log.d("New_Val", new Gson().toJson(freeVideoModel));
                            getAllQuestionModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }

                        recy_question_series.setAdapter(getAllQuestionAdapter);
                        GetAllMockQuestionAdapter.LangFlag=true;
                        getAllQuestionAdapter.SetFilter();


                        // displaying selected image first

                    }else
                    {
                     //   Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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
            }
        }.start();
    }

//    public static void submitResultsDialog(Activity activity) {
//        final AlertDialog alert_dialog;
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_submit_exam, null);
//        builder.setView(view);
//
//        TextView btn_submit = view.findViewById(R.id.btn_submit);
//        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
//
//        PushDownAnim.setPushDownAnimTo(btn_submit).setScale(MODE_SCALE, 0.89f).setDurationPush(DEFAULT_PUSH_DURATION).setDurationRelease(DEFAULT_RELEASE_DURATION);
//
//        alert_dialog = builder.create();
//        alert_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        alert_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
//        alert_dialog.show();
//
//        btn_submit.setOnClickListener(v -> {
//            getAllQuestionAdapter.submitResults();
//        });
//        btn_cancel.setOnClickListener(v -> alert_dialog.dismiss());
//    }
private void getSubjectList() {
    subjectShowModels.clear();
    String SUBCATALL= BaseUrl.GETALLSUBJECTOFEXAM+"/"+exam_id.trim();
    StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("subjectShowModels", response);
            try {
                subjectShowModels.clear();

                JSONArray jsonObject = new JSONArray(response);
                Log.d("course_frag",jsonObject.toString());

                if (jsonObject.length()>0) {
                    reverseTimer(count,tv_txt_Time);
                    for (int i = 0; i < jsonObject.length(); i++)
                    {
                        SubjectShowModel getPreEaxmModel = new SubjectShowModel();
                        JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                        Log.d("submoderl", jsonObject1.toString());
                            getPreEaxmModel.setExamId(jsonObject1.getString("ExamId"));
                            String SubjectId=jsonObject1.getString("SubjectId");
                            getPreEaxmModel.setSubjectId(jsonObject1.getString("SubjectId"));
                            getPreEaxmModel.setSubjectName(jsonObject1.getString("SubjectName"));
                           subjectShowModels.add(getPreEaxmModel);
                        // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        // getExamCategoryAdapter.notifyDataSetChanged();
                    }

                }else
                {

                   // Toast.makeText(MockQuestionAttempts.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                }
                subjectShowAdapter = new SubjectShowAdapter(MockQuestionAttempts.this, subjectShowModels);
                recy_subject.setAdapter(subjectShowAdapter);
                //getExamSubCourseAdapter.notifyDataSetChanged();

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
    RequestQueue requestQueue = Volley.newRequestQueue(MockQuestionAttempts.this);
    requestQueue.getCache().clear();
    requestQueue.add(stringRequest);
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

    @Override
    public void onBackPressed() {


        if (GetAllMockQuestionAdapter.stringButtonradio.toString().length()>0)
        {
            submitResultsDialog(MockQuestionAttempts.this);
        }
        else
        {
            Toast.makeText(MockQuestionAttempts.this, "Plz Attempt Question !!", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(MockQuestionAttempts.this, getResources().getString(R.string.back_press_disabled), Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}