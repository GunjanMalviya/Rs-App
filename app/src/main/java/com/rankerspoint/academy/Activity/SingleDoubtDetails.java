package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.rankerspoint.academy.Adapter.GetDoubtsAllAnswerAdapter;
import com.rankerspoint.academy.Model.GetAllDoubtsAnswerModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SingleDoubtDetails extends AppCompatActivity {
TextView txt_title_doubt,txt_descrip;
Button type_cmd;
    TextView tv_toolbar_title,tv_toolbar_date,txt_ans_count;
    ImageView imgBack,close_image;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    String doubtsid="";
    String doubtsId="",UserId="",UserName="";
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    EditText edt_type_ans;
    RelativeLayout bottom_rltive;
    RecyclerView recy_answer;
    GetDoubtsAllAnswerAdapter getExamCategoryAdapter;
    private List<GetAllDoubtsAnswerModel> getPreEaxmModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_doubt_details);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_date = findViewById(R.id.tv_toolbar_date);
        txt_title_doubt = findViewById(R.id.txt_title_doubt);
        txt_descrip = findViewById(R.id.txt_descrip);
        setSupportActionBar(toolbar);
        txt_ans_count=findViewById(R.id.txt_ans_count);
        recy_answer=findViewById(R.id.recy_answer);
        SharedPreferences preferences =getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        UserId = preferences.getString("user_id", "user_id");
        UserName = preferences.getString("Firstname", "");
        Intent intent = getIntent();
        doubtsid = intent.getStringExtra("DOUBTS_ID");
        Log.d("DOUBTS_ID",doubtsid);
        type_cmd=findViewById(R.id.type_cmd);
        imgBack=findViewById(R.id.imgBack);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

        bottom_rltive=findViewById(R.id.bottom_rltive);
        bottom_rltive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getPreEaxmModels(doubtsid);
        getAllDoubtAnswer(doubtsid);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        tv_toolbar_date.setText(formattedDate);

    }
    private void getPreEaxmModels(String doubtsid) {
       // getPreEaxmModels.clear();
        String DOUBTSID= BaseUrl.GETSINGLEDOUBTSCOURSE+"/"+doubtsid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DOUBTSID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GETSINGLEDOUBTSCOURSE", response);
                try {
                   // getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("GETSINGLEDOUBSEasa",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("GETSINGLEDOUBTSCfs", jsonObject1.toString());
                            doubtsId=jsonObject1.getString("DoubtId");
                            String courseId=jsonObject1.getString("CourseId");
                            String categoryId=jsonObject1.getString("CategoryId");

                            String userId=jsonObject1.getString("UserId");
                            String userName=jsonObject1.getString("UserName");
                            String userPic=jsonObject1.getString("UserPic");
                            String pic=jsonObject1.getString("Pic");
                            String title=jsonObject1.getString("Title");
                            String description=jsonObject1.getString("Description");
                            String type=jsonObject1.getString("Type");
                            String answerStatus=jsonObject1.getString("AnswerStatus");

                            txt_title_doubt.setText(title);
                            txt_descrip.setText(description);
                            tv_toolbar_title.setText(userName);
                            type_cmd.setText(type);
//                            getPreEaxmModels.add(getPreEaxmModel);
//                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
//                            getExamCategoryAdapter = new GetDoubtsMyUserAdapter(MyDoubts.this, getPreEaxmModels);
//                            recy_preexam.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {

                        //Toast.makeText(SingleDoubtDetails.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("UserId",UserId);
                params.put("Email",UserId);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        Button bt_details=view.findViewById(R.id.bt_details);
        LinearLayout linearlayout_camera=view.findViewById(R.id.linearlayout_camera);
        edt_type_ans=view.findViewById(R.id.edt_type_ans);
        bt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_type_ans.getText().toString().equals("")) {
                    Toast.makeText(SingleDoubtDetails.this, "Plz enter your answer !!", Toast.LENGTH_SHORT).show();
                } else {
                    addDoubt_Ans_Details();
                    mBottomSheetDialog.dismiss();
                }
            }
        });
//        ((TextView) view.findViewById(R.id.name)).setText(people.);
//        ((TextView) view.findViewById(R.id.address)).setText(R.string.middle_lorem_ipsum);
//        (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mBottomSheetDialog.dismiss();
//            }
//        });


        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    private void addDoubt_Ans_Details() {
        //getSubCourseModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.ADDANSWERDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Ans_Doubt_topic", response);
                try {
                    // getSubCourseModels.clear();

                    //JSONArray jsonObject = new JSONArray(response);
                    Log.d("Ans_Doubt_topic_xs",response.toString());



                    if (response.equals(null))
                    {
                        Toast.makeText(getApplicationContext(), "Wrong Submission !!!", Toast.LENGTH_SHORT).show();

                    }else {
                        getAllDoubtAnswer(doubtsid);
                        Toast.makeText(getApplicationContext(), "Successfully submit !!", Toast.LENGTH_SHORT).show();

                    }

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
                params.put("DoubtId",doubtsId);

                params.put("UserId",UserId);
                params.put("UserName",UserName);
                params.put("UserPic","");
                params.put("Pic","");
                params.put("Answer",edt_type_ans.getText().toString());

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void getAllDoubtAnswer( String courseid) {
        getPreEaxmModels.clear();
        String doubtsCourse= BaseUrl.GETALLANSWERDETAILS+"/"+courseid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, doubtsCourse, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getAllDoubtAnswera", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllDoubtAnswerb",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            txt_ans_count.setText("("+jsonObject.length()+")");

                            GetAllDoubtsAnswerModel getPreEaxmModel = new GetAllDoubtsAnswerModel();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getAllDoubtAnswerd", jsonObject1.toString());
                            getPreEaxmModel.setDoubtId(jsonObject1.getString("DoubtId"));
                            getPreEaxmModel.setAnswerId(jsonObject1.getString("AnswerId"));

                            getPreEaxmModel.setUserId(jsonObject1.getString("UserId"));
                            getPreEaxmModel.setUserName(jsonObject1.getString("UserName"));
                            getPreEaxmModel.setUserPic(jsonObject1.getString("UserPic"));
                            getPreEaxmModel.setPic(jsonObject1.getString("Pic"));
                            getPreEaxmModel.setAnswer(jsonObject1.getString("Answer"));
                            getPreEaxmModel.setAddDate(jsonObject1.getString("AddDate"));
                            getPreEaxmModel.setAnswerLike(jsonObject1.getString("AnswerLike"));
                            getPreEaxmModel.setAnswerDislike(jsonObject1.getString("AnswerDislike"));

                                    getPreEaxmModels.add(getPreEaxmModel);

                        }

                        recy_answer.setLayoutManager(new GridLayoutManager(SingleDoubtDetails.this,1));

                        // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                        getExamCategoryAdapter = new GetDoubtsAllAnswerAdapter(getApplicationContext(), getPreEaxmModels);
                        recy_answer.setAdapter(getExamCategoryAdapter);
                        // getExamCategoryAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}