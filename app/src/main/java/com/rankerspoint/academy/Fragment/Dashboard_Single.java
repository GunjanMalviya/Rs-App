package com.rankerspoint.academy.Fragment;

import static com.rankerspoint.academy.BaseUrl.BaseUrl.BANN_IMG_URL;
import static com.rankerspoint.academy.BaseUrl.BaseUrl.GETALLFREEQUIZDETAILSBYCOURSE;
import static com.rankerspoint.academy.BaseUrl.BaseUrl.GETALLFREEVIDEODETAILSBYCOURSE;
import static com.rankerspoint.academy.BaseUrl.BaseUrl.GETALLPAYMENTBYUSERANDTYPE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.rankerspoint.academy.Activity.PaymentCheckOut;
import com.rankerspoint.academy.Activity.SingleCourseDetails;
import com.rankerspoint.academy.Activity.ViewAllFeeQuizListDashboard;
import com.rankerspoint.academy.Adapter.DashboardFreeQuizAdapter;
import com.rankerspoint.academy.Adapter.DashboardFreeVideoAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.DashBoardFreeQuizModel;
import com.rankerspoint.academy.Model.DashBoardFreeVideoModel;
import com.rankerspoint.academy.Activity.Free_View_AllVideo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard_Single extends Fragment {
    RecyclerView recycler_free_video_home,recycler_quiz_free;
    ProgressDialog progressDialog;
    TextView skip_free,skip,txt_dailyQuiz,txt_free_video,txtRegEndDate,txtLAnguage,txtTeacherNm,txtPrice, txtlanguageetx;
    LinearLayout lbl_skip,syllbus_llt,lbl_skip_new;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    String courseid="",status="",userId="",Pic="",Name="",Price="",datae="",Langauge="",Teachers="",FreeTrail="";
    ImageView imageLogo;
    public Dashboard_Single() {
    }
    public static Dashboard_Single newInstance() {
        Dashboard_Single fragment = new Dashboard_Single();
        return fragment;
    }
    DashboardFreeVideoAdapter freeLiveClassAdapter;
    DashboardFreeQuizAdapter dashboardFreeQuizAdapter;
    List<DashBoardFreeVideoModel> getAllSyllabusCatModels=new ArrayList<>();
    List<DashBoardFreeQuizModel> getDashboardFreeModels=new ArrayList<>();
    Context context;
    View view;
    TextView txt_viewAll_Quiz,txt_vieaAll_Free;
    int time = 3000;
    Timer timer;
    LinearLayoutManager linearLayoutManager1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dashboard__single, container, false);
        context=container.getContext();
        NestedScrollView scrollView = (NestedScrollView) view.findViewById (R.id.nest_scrollview);
        scrollView.setFillViewport (true);
        /*recycler_free_video_home=view.findViewById(R.id.recycler_free_video_home);
        recycler_quiz_free=view.findViewById(R.id.recycler_quiz_free);
        txt_viewAll_Quiz=view.findViewById(R.id.txt_viewAll_Quiz);
        txt_vieaAll_Free=view.findViewById(R.id.txt_vieaAll_Free);
        lbl_skip_new=view.findViewById(R.id.lbl_skip_new);
        lbl_skip=view.findViewById(R.id.lbl_skip);
        skip_free=view.findViewById(R.id.skip_free);
        skip=view.findViewById(R.id.skip);


        txtRegEndDate=view.findViewById(R.id.txtRegEndDate);
        txtLAnguage=view.findViewById(R.id.txtLAnguage);
        txtTeacherNm=view.findViewById(R.id.txtTeacherNm);
        txtPrice=view.findViewById(R.id.txtPrice);
        txtlanguageetx=view.findViewById(R.id.txtlanguageetx);

        txt_dailyQuiz=view.findViewById(R.id.txt_dailyQuiz);
        txt_free_video=view.findViewById(R.id.txt_free_video);
        imageLogo=view.findViewById(R.id.imageLogo);
        linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler_free_video_home.setLayoutManager(linearLayoutManager1);
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recycler_free_video_home);
        *//*recycler_quiz_free.setNestedScrollingEnabled(false);
        recycler_free_video_home.setNestedScrollingEnabled(false);*//*
        //  final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler_quiz_free.setLayoutManager(new GridLayoutManager(context,1));
//        final LinearSnapHelper linearSnapHelper1 = new LinearSnapHelper();
//        linearSnapHelper1.attachToRecyclerView(recycler_quiz_free);
        txt_vieaAll_Free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Free_View_AllVideo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
        txt_viewAll_Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewAllFeeQuizListDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.show();
        //initComponent();
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        userId = preferences.getString("user_id", "");
        Pic= preferences.getString("Pic", "");
        Name= preferences.getString("Name", "");
        Price= preferences.getString("PriceC", "");
        datae= preferences.getString("datae", "");
        Langauge= preferences.getString("Langauge", "");
        Teachers= preferences.getString("Teachers", "");
        FreeTrail= preferences.getString("FreeTrail", "");
        courseid=preferences.getString("CourseId","CourseId");
        status=preferences.getString("Status","");
        // courseid=  getActivity().getIntent().getExtras().getString("COURSE_ID");
        Log.d("couriddddd",courseid+Price);
        getFreeLiveClasses(courseid);
        getFreeQuizDashboard(courseid);
        // it's the delay time for sliding between items in recyclerview
        timer = new Timer();
        if (FreeTrail.equals("Yes")) {
            lbl_skip_new.setVisibility(View.GONE);
            skip_free.setText("START FREE TRIAL");
            lbl_skip.setVisibility(View.GONE);
            skip_free.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, SingleCourseDetails.class);
                    intent.putExtra("COURSE_ID",courseid);
                    startActivity(intent);
                }
            });

        }
        else {

            if (status.equals("True")) {
                lbl_skip_new.setVisibility(View.GONE);
                lbl_skip.setVisibility(View.GONE);
                skip_free.setText("All Ready Paid - GO");
                skip_free.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SingleCourseDetails.class);
                        intent.putExtra("COURSE_ID", courseid);
                        startActivity(intent);
                    }
                });
            } else {
                skip_free.setText("Buy Now");
                skip_free.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PaymentCheckOut.class);


                        startActivity(intent);
                    }
                });
            }
        }
        txtRegEndDate.setText("Last date:-"+datae);

        txtLAnguage.setText(Langauge);

        txtTeacherNm.setText("By:-"+Teachers);

        txtPrice.setText("Price:-"+Price);

        txtlanguageetx.setText(Langauge);
        Picasso.with(context).load(BANN_IMG_URL + Pic).into(imageLogo);

*/
        return view;
    }
//    private void initComponent() {
//        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        courseid = preferences.getString("CourseId", "CourseId");

//        Log.d("CourseId",courseid);
//
////        getFreeLiveClasses(courseid);
//        getFreeQuizDashboard(courseid);
//    }

/*    private void getFreeLiveClasses(String categoryid) {
        getAllSyllabusCatModels.clear();
        // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL=GETALLFREEVIDEODETAILSBYCOURSE+"/"+categoryid.trim();;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getfreeVideo", response);
                try {
                    getAllSyllabusCatModels.clear();
                    JSONArray jsonObject = new JSONArray(response);

                    Log.d("videofree",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        recycler_free_video_home.setVisibility(View.VISIBLE);
                        txt_vieaAll_Free.setVisibility(View.VISIBLE);
                        txt_free_video.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getfreelive_abc", jsonObject1.toString());
                            String DCRecordId = jsonObject1.getString("DCRecordId");
                            String DCCourseId = jsonObject1.getString("DCCourseId");
                            String DCCourseName = jsonObject1.getString("DCCourseName");

                            String DCContentType = jsonObject1.getString("DCContentType");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String VideoLink1 = jsonObject1.getString("VideoLink1");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Type = jsonObject1.getString("Type");




                            DashBoardFreeVideoModel freeVideoModel = new DashBoardFreeVideoModel(DCRecordId,DCCourseId,DCCourseName,DCContentType,Name,Details,Type,Pic,VideoLink1,FeeStatus);
                            getAllSyllabusCatModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        freeLiveClassAdapter=new DashboardFreeVideoAdapter(context,getAllSyllabusCatModels);
                        recycler_free_video_home.setAdapter(freeLiveClassAdapter);
                        freeLiveClassAdapter.notifyDataSetChanged();


                        // displaying selected image first
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {

                                if (linearLayoutManager1.findLastCompletelyVisibleItemPosition() < (freeLiveClassAdapter.getItemCount() - 1)) {

                                    linearLayoutManager1.smoothScrollToPosition(recycler_free_video_home, new RecyclerView.State(), linearLayoutManager1.findLastCompletelyVisibleItemPosition() + 1);
                                }

                                else if (linearLayoutManager1.findLastCompletelyVisibleItemPosition() == (freeLiveClassAdapter.getItemCount() - 1)) {

                                    linearLayoutManager1.smoothScrollToPosition(recycler_free_video_home, new RecyclerView.State(), 0);
                                }
                            }
                        }, 0, time);
                    }else
                    {
                        //  Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void getFreeQuizDashboard(String categoryid) {
        getDashboardFreeModels.clear();
        // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL=GETALLFREEQUIZDETAILSBYCOURSE+"/"+categoryid.trim();;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getfreeQuiz", response);
                try {
                    getDashboardFreeModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("videofree",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        txt_viewAll_Quiz.setVisibility(View.VISIBLE);
                        recycler_quiz_free.setVisibility(View.VISIBLE);
                        txt_dailyQuiz.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getfreelive_abc", jsonObject1.toString());
                            String DCRecordId = jsonObject1.getString("DCRecordId");
                            String DCCourseId = jsonObject1.getString("DCCourseId");
                            String DCCourseName = jsonObject1.getString("DCCourseName");

                            String DCTopicId = jsonObject1.getString("DCTopicId");
                            String ExamId = jsonObject1.getString("ExamId");
                            String DCContentType = jsonObject1.getString("DCContentType");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Title = jsonObject1.getString("Title");
                            int Duration = jsonObject1.getInt("Duration");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Pic = jsonObject1.getString("Pic");
                            String Description = jsonObject1.getString("Description");




                            DashBoardFreeQuizModel freeVideoModel = new DashBoardFreeQuizModel(DCRecordId,DCCourseId,DCCourseName,DCTopicId,ExamId,DCContentType,CategoryId,Title,Duration+"",FeeStatus,Pic,Description);
                            getDashboardFreeModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        dashboardFreeQuizAdapter=new DashboardFreeQuizAdapter(context,getDashboardFreeModels);
                        recycler_quiz_free.setAdapter(dashboardFreeQuizAdapter);
                        dashboardFreeQuizAdapter.notifyDataSetChanged();

                        // displaying selected image first

                    }else
                    {
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }*/
/*    private void getSingleCourse(String status) {

        String SUBCATALL=GETSINGLECOURSE+"/"+courseid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sub_Prod_detail", response);
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam_sub",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            String RegLastDate = jsonObject1.getString("RegLastDate");
                            String datae=RegLastDate.substring(0,10);
                            Log.d("datae",datae);

                            String ExpiryDate = jsonObject1.getString("ExpiryDate");
                            String FreeTrail = jsonObject1.getString("FreeTrail");
                            String Price = jsonObject1.getString("Price");
                            String Langauge = jsonObject1.getString("Langauge");
                            String Teachers = jsonObject1.getString("Teachers");
                            Log.d("FreeTrail",FreeTrail);
                            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("CourseId", courseid);
                            editor.putString("CategoryId", CategoryId);
                            editor.putString("SubCategoryId", SubCategoryId);
                            editor.putString("Name", Name);
                            editor.putString("PriceC", Price);

                            editor.apply();
                            if (FreeTrail.equals("Yes")) {
                                lbl_skip_new.setVisibility(View.GONE);
                                skip_free.setText("START FREE TRIAL");
                                lbl_skip.setVisibility(View.GONE);
                                skip_free.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(context, SingleCourseDetails.class);
                                        intent.putExtra("COURSE_ID",courseid);
                                        startActivity(intent);
                                    }
                                });

                            }
                            else
                            {

                                if (status.equals("True")) {
                                    lbl_skip_new.setVisibility(View.GONE);
                                    lbl_skip.setVisibility(View.GONE);
                                    skip_free.setText("All Ready Paid - GO");
                                    skip_free.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(context,SingleCourseDetails.class);
                                            intent.putExtra("COURSE_ID",courseid);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {
                                    skip_free.setText("Buy Now");
                                    skip_free.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(context, PaymentCheckOut.class);


                                            startActivity(intent);
                                        }
                                    });
                                }

                            }

                            txtRegEndDate.setText("Last date:-"+datae);

                            txtLAnguage.setText(Langauge);

                            txtTeacherNm.setText("By:-"+Teachers);

                            txtPrice.setText("Price:-"+Price);

                            txtlanguageetx.setText(Langauge);
                            Picasso.with(context).load(BANN_IMG_URL + Pic).into(imageLogo);


                        }
                    }else
                    {

                        // Toast.makeText(SingleCourseDetails.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }*/

   /* public void getAllPaid(JSONObject jsonObject){
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, GETALLPAYMENTBYUSERANDTYPE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("getAllPayemnt:--",response.toString());

                        String Succeeded;
                        try {
                            Succeeded = response.getString("Status");
                            Log.d("SucceededMsg:--",Succeeded.toString());
                            // getSingleCourse(Succeeded);

                            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Status", Succeeded);
                            editor.apply();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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


    }*/

}