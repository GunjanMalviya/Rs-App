package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.rankerspoint.academy.Model.MyAnsResponse1;
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
import java.util.StringTokenizer;

import static android.content.Intent.EXTRA_STREAM;

public class Results extends AppCompatActivity {
    TextView txt_share,txt_viewSolution;
    Bitmap bitmap=null;
    TextView txt_userName,txt_score_2,txt_outscore_2,txt_rank_2,txt_rank_out_2;
    LinearLayout lll_screen;
    RecyclerView recy_view_answer_key;
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
String userId="",examId="";
    private long exitTime=0;
    String CorrectAnsList="",subject_id="",username="";
    GetViewCorrectandIncorrectAdapter getViewCorrectandIncorrectAdapter;
    List<MyAnsResponse1> MyAns=new ArrayList<>();
    TextView txt_score,txt_courseNm,txt_outscore,txt_rank,txt_out_rank,txtCorrect,txt_Wrong,txt_unattemp,txt_examName,txt_score_scr;
    String ExResultID="",userid="";
    PackageManager packageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
         packageManager = getPackageManager();

        setContentView(R.layout.activity_results);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        recy_view_answer_key=findViewById(R.id.recy_view_answer_key);
        txt_share = findViewById(R.id.txt_share);
        lll_screen=findViewById(R.id.lll_screen);
        txt_score_2=findViewById(R.id.txt_score_2);
        txt_outscore_2=findViewById(R.id.txt_outscore_2);
        txt_viewSolution=findViewById(R.id.txt_viewSolution);
        txt_score=findViewById(R.id.txt_score);
        txt_outscore=findViewById(R.id.txt_outscore);
        txt_rank=findViewById(R.id.txt_rank);
        txt_out_rank=findViewById(R.id.txt_out_rank);
        txtCorrect=findViewById(R.id.txtCorrect);
        txt_Wrong=findViewById(R.id.txt_Wrong);
        txt_unattemp=findViewById(R.id.txt_unattemp);
        txt_examName=findViewById(R.id.txt_examName);
        txt_score_scr=findViewById(R.id.txt_score_scr);
        txt_userName=findViewById(R.id.txt_userName);
        txt_courseNm=findViewById(R.id.txt_courseNm);

        getViewCorrectandIncorrectAdapter=new GetViewCorrectandIncorrectAdapter(getApplicationContext(),MyAns);
//        examid=getIntent().getStringExtra("EXAMID");
        recy_view_answer_key.setLayoutManager(new GridLayoutManager(Results.this,4));
//        userid=getIntent().getStringExtra("USERID");
//       Log.d("examid_results",examid);
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        subject_id = preferences.getString("subject_id", "");
        username = preferences.getString("Firstname", "");
        Log.d("subject_id",subject_id);

        examId=getIntent().getStringExtra("examId");
        userId=getIntent().getStringExtra("userId");
        txt_userName.setText(username);
        Log.d("EXAM_RESULT_ID",examId+"user:-"+userId);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        resultShow();
        txt_viewSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subject_id.equals("")||subject_id==null) {
                    Intent intent = new Intent(getApplicationContext(), ViewAnswerKey.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("EXAM_ID", examId);
                    intent.putExtra("CORRECT_LIST", CorrectAnsList);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), ViewMockTestSeries.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("EXAM_ID", examId);
                    intent.putExtra("CORRECT_LIST", CorrectAnsList);
                    startActivity(intent);
                }
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

                shareBitmapFacebook(screenShot(lll_screen),"myimage");
//                showBottomSheetDialog();
             }
        });

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
                Toast.makeText(Results.this, "Whatsapp require..!!", Toast.LENGTH_SHORT).show();
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
    private void shareBitmapFacebook(Bitmap bitmap, String fileName) {
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName + System.currentTimeMillis() + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Uri bmpUri = FileProvider.getUriForFile(Results.this, getApplicationContext().getPackageName()+".fileprovider", file);
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
                Toast.makeText(Results.this, "Instagram require..!!", Toast.LENGTH_SHORT).show();

            }
            // lll_screen.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resultShow() {
        //getSubCourseModels.clear();
        MyAns.clear();
       // String APIURL=GETSINGLEALLRESULTS+"/"+ExResultID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETEXAMRESULTSBYUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Doubt_topic", response);
                try {

                     MyAns.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("GETALLUSERDOUBTS",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("results1", jsonObject1.toString());
                          String ExamResultId=jsonObject1.getString("ExamResultId");
                          ExamId=jsonObject1.getString("ExamId");
                          String ExamName=jsonObject1.getString("ExamName");
                            txt_courseNm.setText(ExamName);
                          String AddDate=jsonObject1.getString("AddDate");
                          String Score=jsonObject1.getString("Score");
                          String Rank=jsonObject1.getString("Rank");
                          String Correct=jsonObject1.getString("Correct");
                          String Wrong=jsonObject1.getString("Wrong");

                            String Unattemped=jsonObject1.getString("Unattemped");
                            String Status=jsonObject1.getString("Status");
                            String Extra1=jsonObject1.getString("Extra1");
                            CorrectAnsList=jsonObject1.getString("CorrectAnsList");
                            JSONArray jsonObjectobj = new JSONArray(CorrectAnsList);
                            if (jsonObject.length()>0) {
                                for (int j = 0; j < jsonObjectobj.length(); j++) {
                                    JSONObject jsonObj = jsonObjectobj.getJSONObject(j);
                                    MyAnsResponse1 Ans=new MyAnsResponse1();
                                    Log.d("results2", jsonObj.toString());
                                    String FlagStar = jsonObj.getString("FlagStar");
                                    String QuesStatus = jsonObj.getString("QuesStatus");
                                    String QuestionID = jsonObj.getString("QuestionID");
                                    String SelAnsWer = jsonObj.getString("SelAnsWer");
                                    Log.d("strResults", "flagstate:--" + FlagStar + "QuesStatus:--" + QuesStatus + "QuestionID:--" + QuestionID);
                                    Ans.setFlagStar(FlagStar);
                                    Ans.setQuesStatus(QuesStatus);
                                    Ans.setQuestionID(QuestionID);
                                    Ans.setSelAnsWer(SelAnsWer);
                                    MyAns.add(Ans);



                                }

                                recy_view_answer_key.setAdapter(getViewCorrectandIncorrectAdapter);

                            }else {
                                Toast.makeText(Results.this, "No data fount !!", Toast.LENGTH_SHORT).show();
                            }


                            StringTokenizer st = new StringTokenizer(Score, "/");
                            String marks=st.nextToken();
                            String toQuestion=st.nextToken();
                            txt_score.setText(marks);
                            txt_score_2.setText(marks);
                            txt_outscore.setText("Out of "+toQuestion);
                            txt_outscore_2.setText("Out of "+toQuestion);


                            txtCorrect.setText("Correct ("+Correct+")");
                            txt_Wrong.setText("Wrong ("+Wrong+")");
                            txt_examName.setText(ExamName);
                            txt_unattemp.setText("Unattemped ("+Unattemped+")");
                            txt_score_scr.setText(Score);


//                            getPreEaxmModels.add(getPreEaxmModel);
//                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
//                            getExamCategoryAdapter = new GetDoubtsMyUserAdapter(MyDoubts.this, getPreEaxmModels);
//                            recy_preexam.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                       // lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(Results.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("ExamId",examId);
                params.put("UserId",userId);

                Log.d("paramter_exam_res:-",params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
//    private void resultShow() {
//        // getAllResultsModels.clear();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETEXAMRESULTSBYUSER, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("rsult", response);
//                try {
//                    // getAllResultsModels.clear();
//
//                    JSONArray jsonObject = new JSONArray(response);
//                    Log.d("getAllResults",jsonObject.toString());
//
//                    if (jsonObject.length()>0) {
//
//                        for (int i = 0; i < jsonObject.length(); i++) {
//
//
//                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
//                            Log.d("results1", jsonObject1.toString());
//                            ExamResultId=jsonObject1.getString("ExamResultId");
//                            String ExamId=jsonObject1.getString("ExamId");
//                            String UserId=jsonObject1.getString("UserId");
//                            String ExamName=jsonObject1.getString("ExamName");
//                            String AddDate=jsonObject1.getString("AddDate");
//                            String Score=jsonObject1.getString("Score");
//                            String Rank=jsonObject1.getString("Rank");
//                            String Correct=jsonObject1.getString("Correct");
//                            String Wrong=jsonObject1.getString("Wrong");
//
//                            String Unattemped=jsonObject1.getString("Unattemped");
//                            String Status=jsonObject1.getString("Status");
//                            String Extra1=jsonObject1.getString("Extra1");
//                            String CorrectAnsList=jsonObject1.getString("CorrectAnsList");
//                            JSONArray jsonObjectobj = new JSONArray(CorrectAnsList);
////
//                        }
////
//
//
//                    }else
//                    {
//                        // lnr_layout.setVisibility(View.VISIBLE);
//                        Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                //progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("ExamId",examid);
//                params.put("UserId",userId);
////                params.put("ExamId","ExamvAWRoa8ruUmOpgAVF404aw");
////                params.put("UserId","7309338957");
//
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.getCache().clear();
//        requestQueue.add(stringRequest);
//    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Results.this,Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}