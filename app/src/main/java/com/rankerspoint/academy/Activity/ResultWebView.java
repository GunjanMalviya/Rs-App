package com.rankerspoint.academy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.Adapter.GetExamResultSectionWiseAdapter;
import com.rankerspoint.academy.Adapter.GetSectionWiseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSectionWiseModel;
import com.rankerspoint.academy.Model.SectionWiseResultModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Intent.EXTRA_STREAM;
import static android.os.Build.VERSION.SDK_INT;

public class ResultWebView extends AppCompatActivity {
    TextView txt_share,txt_viewSolution;
    Bitmap bitmap=null;
    TextView txt_userName,txt_score_2,txt_outscore_2,txt_reattempt,txt_rank_out_2;
    LinearLayout lll_screen;
    RecyclerView recy_view_answer_key,recy_moreclasses,recy_subCourse;
    String widths = "";
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    ProgressDialog progressDialog;
    String ExamId="";
    String Facebook = "com.facebook.katana";
    String Twitter = "com.twitter.android";
    String Instagram = "com.instagram.android";
    String  Pinterest = "com.pinterest";
    String  Whatsapp = "com.whatsapp";
    String userId="",examId="",userName="";
    private long exitTime=0;
    String CorrectAnsList="",subject_id="";
    GetSectionWiseAdapter getSectionWiseAdapter;
    private List<GetSectionWiseModel> getPreEaxmModels = new ArrayList<>();
    TextView txt_score,txt_coursenm,txt_outscore,txt_rank,txt_out_rank,txtCorrect,txt_Wrong,txt_unattemp,txt_examName,txt_score_scr;

    PackageManager packageManager;
    GetExamResultSectionWiseAdapter getExamResultSectionWiseAdapter;
    private List<SectionWiseResultModel> getExamSectionWiseModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   setContentView(R.layout.activity_result_web_view);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        packageManager = getPackageManager();

        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        recy_view_answer_key=findViewById(R.id.recy_view_answer_key);
        recy_moreclasses=findViewById(R.id.recy_moreclasses);
        recy_subCourse=findViewById(R.id.recy_subCourse);
        txt_share = findViewById(R.id.txt_share);
        lll_screen=findViewById(R.id.lll_screen);
        txt_score_2=findViewById(R.id.txt_score_2);
        txt_outscore_2=findViewById(R.id.txt_outscore_2);
        txt_viewSolution=findViewById(R.id.txt_viewSolution);
        txt_score=findViewById(R.id.txt_score);
        txt_outscore=findViewById(R.id.txt_outscore);
        txt_reattempt=findViewById(R.id.txt_reattempt);
        txt_rank=findViewById(R.id.txt_rank);
        txt_out_rank=findViewById(R.id.txt_out_rank);
        txtCorrect=findViewById(R.id.txtCorrect);
        txt_Wrong=findViewById(R.id.txt_Wrong);
        txt_unattemp=findViewById(R.id.txt_unattemp);
        txt_coursenm=findViewById(R.id.txt_coursenm);
       // txt_examName=findViewById(R.id.txt_examName);
       // txt_score_scr=findViewById(R.id.txt_score_scr);
        txt_userName=findViewById(R.id.txt_userName);

//        examid=getIntent().getStringExtra("EXAMID");
        recy_view_answer_key.setLayoutManager(new GridLayoutManager(ResultWebView.this,4));
//        userid=getIntent().getStringExtra("USERID");
//       Log.d("examid_results",examid);
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        subject_id = preferences.getString("subject_id", "");
        userId = preferences.getString("user_id", "");
        userName = preferences.getString("Firstname", "");
        Log.d("subject_id",subject_id);
        txt_userName.setText(userName);
        examId=getIntent().getStringExtra("EXAMID");

        Log.d("EXAM_RESULT_ID",examId+"user:-"+userId);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ResultWebView.this, LinearLayoutManager.HORIZONTAL, false);
//linearLayoutManager.setReverseLayout(true);
        recy_moreclasses.setLayoutManager(linearLayoutManager);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",userId);
            jsonObject.put("ExamId",examId);
            resultShow(jsonObject);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        txt_viewSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), MocktestViewAnswerWebView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("EXAM_ID", examId);
                    intent.putExtra("USERID", userId);
                    startActivity(intent);

            }
        });
        lll_screen.post(new Runnable() {
            @Override
            public void run() {
                Log.d("widdthd",lll_screen.getWidth()+"");
                screenShot(lll_screen);

            }
        });
//lll_screen.setVisibility(View.GONE);
        LinearLayout ll=new LinearLayout(getApplicationContext());
        txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////screenShot(lll_screen);
                // lll_screen.setVisibility(View.VISIBLE);

                    shareBitmapFacebook(screenShot(lll_screen), "myimage");

//                showBottomSheetDialog();
            }
        });
        txt_reattempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("UserId", userId);
                        jsonObject1.put("ExamId", examId);
                        getAllAttemptStatus(jsonObject1,userId,examId);
                    }catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }


            }
        });
        recy_subCourse.setLayoutManager(new GridLayoutManager(ResultWebView.this,1));
        getSubMoreClassesModels(examId);

    }
    public Bitmap screenShot(View view) {
        Log.d("widdthd11",view.getWidth()+"");
        bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        lll_screen.setVisibility(View.GONE);
        return bitmap;
    }

    //////// this method share your image
    private void shareBitmap(Bitmap bitmap, String fileName) {
        try {
            File file = new File(getApplicationContext().getCacheDir(), fileName.trim() + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
//            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//            intent.setType("image/png");
//            startActivity(intent);

            Intent intent = packageManager.getLaunchIntentForPackage(Whatsapp);

            //  Log.d("intentwhatsapp",intent.toString());
            if (intent != null) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("image/png");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                whatsappIntent.putExtra(EXTRA_STREAM, Uri.fromFile(file));
                startActivity(whatsappIntent);
            }
            else
            {
                Toast.makeText(ResultWebView.this, "Whatsapp require..!!", Toast.LENGTH_SHORT).show();
            }

            // lll_screen.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.bottom_sheet_share, null);
        ImageView img_facebook=view.findViewById(R.id.img_facebook);
        ImageView img_instagram=view.findViewById(R.id.img_instagram);
        ImageView img_Whatsapp=view.findViewById(R.id.img_Whatsapp);
        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBitmapFacebook(screenShot(lll_screen),"myimage");
            }
        });
        img_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBitmapInsta(screenShot(lll_screen),"myimage");
            }
        });
        img_Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBitmap(screenShot(lll_screen),"myimage");
            }
        });
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
    private void shareBitmapFacebook(Bitmap bitmap, String fileName) {
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName + System.currentTimeMillis() + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
           Uri bmpUri = FileProvider.getUriForFile(ResultWebView.this, getApplicationContext().getPackageName()+".fileprovider", file);
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, bmpUri);

            Log.d("datauri:--",file.toString());
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share Image"));
//            Intent intent = packageManager.getLaunchIntentForPackage(Facebook);
            // Log.d("intentfacebook",intent.toString());
//            if (intent != null) {
//
//                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//                shareIntent.setType("image/png");
//                shareIntent.putExtra(EXTRA_STREAM, Uri.fromFile(file));
//                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                shareIntent.setPackage("com.facebook.katana");
//                startActivity(shareIntent);
//            }
//            else
//            {
//                Toast.makeText(Results.this, "Facebook require..!!", Toast.LENGTH_SHORT).show();
//            }
            // lll_screen.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void shareBitmapInsta(Bitmap bitmap, String fileName) {
        try {
            File file = new File(getApplicationContext().getCacheDir(), fileName.trim() + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
//            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//            intent.setType("image/png");
//            startActivity(intent);

            Intent intent = packageManager.getLaunchIntentForPackage(Instagram);
            // Log.d("intentinstagram",intent.toString());
            if (intent != null)
            {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(EXTRA_STREAM, Uri.fromFile(file));
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                shareIntent.setPackage("com.instagram.android");
                startActivity(shareIntent);
            }
            else
            {
                Toast.makeText(ResultWebView.this, "Instagram require..!!", Toast.LENGTH_SHORT).show();

            }
            // lll_screen.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resultShow(JSONObject jsonObject){
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.GETRESULTBYUSER, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("getresult:--",response.toString());

                        try {

                            String Correct = response.getString("Correct");
                            String Wrong = response.getString("Wrong");
                            String Skipped = response.getString("Skipped");
                            String TotalQuestion = response.getString("TotalQuestion");
                            txt_outscore_2.setText("Out of "+TotalQuestion);
                            String Score = response.getString("Score");
                            String TotalMark = response.getString("TotalMark");
                            String PassedStatus = response.getString("PassedStatus");
                            String CuttOff = response.getString("CuttOff");

                            String TimeTake = response.getString("TimeTake");
                            String Accuracy = response.getString("Accuracy");
                            String Percentile = response.getString("Percentile");
                            String ExamName = response.getString("ExamName");

                            txt_coursenm.setText(ExamName);
                            txt_score.setText(Score);
                            txt_score_2.setText(Score);
                            txt_outscore.setText("Out of "+TotalQuestion);




                            txtCorrect.setText("Correct ("+Correct+")");
                            txt_Wrong.setText("Wrong ("+Wrong+")");

                            txt_unattemp.setText("Unattemped ("+Skipped+")");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                    }

                },  new Response.ErrorListener() {
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

    @Override
    public void onBackPressed() {
//        Intent intent=new Intent(ResultWebView.this,Dashboard.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        super.onBackPressed();
    }
    private void getSubMoreClassesModels(String examId) {
        getPreEaxmModels.clear();
        String SUBCATALL= BaseUrl.GETALLSUBJECTOFEXAM+"/"+examId.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSubMoreClassesModels", response);
                try {
                    getPreEaxmModels.clear();
//
                    Log.d("Myresponse",response);
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getSubMoreClassesModels",jsonObject.toString());

                    // Toast.makeText(getContext(), "response:--"+response, Toast.LENGTH_SHORT).show();

                    if (!response.isEmpty()) {

                        for (int i = 0; i < jsonObject.length(); i++) {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getSubMoreClassesModels", jsonObject1.toString());
                            String RecordId = jsonObject1.getString("RecordId");
                            String ExamId = jsonObject1.getString("ExamId");
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String SubjectName = jsonObject1.getString("SubjectName");


                            GetSectionWiseModel getPreEaxmModel = new GetSectionWiseModel(RecordId, ExamId, SubjectId, SubjectName);

                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getSectionWiseAdapter = new GetSectionWiseAdapter(ResultWebView.this, getPreEaxmModels, recy_moreclasses, new GetSectionWiseAdapter.OnItemClicked() {
                            @Override
                            public void onItemClick(int position) {

                                try {
                                    JSONObject jsonObject1=new JSONObject();
                                    jsonObject1.put("UserId",userId);
                                    jsonObject1.put("ExamId",examId);
                                    jsonObject1.put("SubjectId",getPreEaxmModels.get(position).getSubjectId());
                                    resultShowSection(jsonObject1,getPreEaxmModels.get(position).getSubjectName());

                                }catch (JSONException ex)
                                {
                                    ex.printStackTrace();
                                }

                            }
                        });


                        recy_moreclasses.setAdapter(getSectionWiseAdapter);
                        // getExamSubCategoryAdapter.notifyDataSetChanged();
                        if (getPreEaxmModels.size() > 0) {
                            try {
                                JSONObject jsonObject1=new JSONObject();
                                jsonObject1.put("UserId",userId);
                                jsonObject1.put("ExamId",examId);
                                jsonObject1.put("SubjectId",getPreEaxmModels.get(0).getSubjectId());
                                resultShowSection(jsonObject1,getPreEaxmModels.get(0).getSubjectName());

                            }catch (JSONException ex)
                            {
                                ex.printStackTrace();
                            }

                        }
                    }else
                    {
                        //lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ResultWebView.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    public void resultShowSection(JSONObject jsonObject,String subjectNm){
        getExamSectionWiseModels.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.GETEXAMRESULTSBYEXAMSUBJECT, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getExamSectionWiseModels.clear();
                        Log.d("getresult:--",response.toString());

                        try {

                            String Correct = response.getString("Correct");
                            String Wrong = response.getString("Wrong");
                            String Skipped = response.getString("Skipped");
                            String TotalQuestion = response.getString("TotalQuestion");
                            String Score = response.getString("Score");
                            String TotalMark = response.getString("TotalMark");
                            String PassedStatus = response.getString("PassedStatus");
                            String CuttOff = response.getString("CuttOff");

                            String TimeTake = response.getString("TimeTake");
                            String Accuracy = response.getString("Accuracy");
                            String Percentile = response.getString("Percentile");
                            SectionWiseResultModel getPreEaxmModel = new SectionWiseResultModel(Correct,Wrong,Skipped,TotalQuestion,Score,TotalMark,PassedStatus,CuttOff,TimeTake,Percentile,Accuracy);
                            getPreEaxmModel.setSubjectName(subjectNm);
                            getExamSectionWiseModels.add(getPreEaxmModel);
                            getExamResultSectionWiseAdapter = new GetExamResultSectionWiseAdapter(ResultWebView.this, getExamSectionWiseModels);
                            recy_subCourse.setAdapter(getExamResultSectionWiseAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                    }

                },  new Response.ErrorListener() {
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
    private void deleteAttemptExam(String userid,String examId) {

     //   String SUBCATALL=GETALLSUBJECTOFEXAM+"/"+examId.trim();
Log.d("userid:--",userid+"examid:--"+examId);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.DELETEALLEXAMREATTEMPT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getResultshowdelte", response);
                try {

                Intent intent=new Intent(ResultWebView.this,MockTestWebView.class);
                intent.putExtra("EXAM_ID",examId);
                intent.putExtra("USERID",userId);
                startActivity(intent);

//

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
                params.put("UserId",userid);
                params.put("ExamId",examId);
                Log.d("param:--",params.toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ResultWebView.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void showPermissionDialog() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                startActivityForResult(intent, 2000);
            } catch (Exception e) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2000);

            }

        } else
            ActivityCompat.requestPermissions(ResultWebView.this,
                    new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 333);
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getApplicationContext(),
                    WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 333) {
            if (grantResults.length > 0) {
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (read && write){

                }else {

                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                } else {

                }
            }
        }
    }
    public void getAllAttemptStatus(JSONObject jsonObject,String userid,String examId){
        RequestQueue queue = Volley.newRequestQueue(ResultWebView.this);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.GETEXAMSTATUSOFUSER, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllStatus:--",response.toString());
                        String Succeeded,AttemptNo;
                        try {

                            Succeeded = response.getString("Status");
                            AttemptNo = response.getString("AttemptNo");
                            Log.d("SucceededMsgSts:--",Succeeded.toString());
                            if (Succeeded.equals("True")) {
                                deleteAttemptExam(userid,examId);
//                                Intent intent = new Intent(ResultWebView.this, MockTestWebView.class);
//                                intent.putExtra("EXAM_ID", examId);
////                intent.putExtra("SUBJECT_ID", getPreEaxmModel.getSubjectId().toString());
//                                intent.putExtra("USERID", userid);
//
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                                startActivity(intent);
                            }else {
                                Toast.makeText(ResultWebView.this, "Exam attempt out of limit!! ", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultWebView.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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

}