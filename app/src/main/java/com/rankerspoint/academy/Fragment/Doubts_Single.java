package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Activity.Doubts_Topic;
import com.rankerspoint.academy.Adapter.GetDoubtsCourseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetCourseAllDoubts;
import com.rankerspoint.academy.Activity.AllDoubtsCourse;
import com.rankerspoint.academy.Activity.MyDoubts;
import com.rankerspoint.academy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doubts_Single extends Fragment {
    public Doubts_Single() {
    }

    public static Doubts_Single newInstance() {
        Doubts_Single fragment = new Doubts_Single();
        return fragment;
    }
Context context;
View view;
LinearLayout doubt_llout,linearlayout_viewall;
String courseid="",catId="",subCatId="",status="";
RecyclerView _recy_doubts_chats_list;
CardView card_my_doubts;
    ProgressDialog progressDialog;
    GetDoubtsCourseAdapter getExamCategoryAdapter;
    private List<GetCourseAllDoubts> getPreEaxmModels = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_doubts__single, container, false);
        context=container.getContext();

      

      
        _recy_doubts_chats_list=view.findViewById(R.id._recy_doubts_chats_list);
        linearlayout_viewall=view.findViewById(R.id.linearlayout_viewall);
        linearlayout_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(context, AllDoubtsCourse.class);
                    startActivity(intent);
            }
        });
        _recy_doubts_chats_list.suppressLayout(true);
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        status = preferences.getString("Status", "");
        courseid = preferences.getString("CourseId", "CourseId");
        catId = preferences.getString("CategoryId", "CategoryId");
        subCatId = preferences.getString("SubCategoryId", "SubCategoryId");

        Log.d("CourseId",courseid);

        doubt_llout=view.findViewById(R.id.doubt_llout);
        card_my_doubts=view.findViewById(R.id.card_my_doubts);


        progressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        _recy_doubts_chats_list.setLayoutManager(new GridLayoutManager(context,1));
        getPreEaxmModels(courseid);
        card_my_doubts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(context, MyDoubts.class);
                    startActivity(intent);
            }
        });
        doubt_llout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("True")) {
                    Intent intent = new Intent(context, Doubts_Topic.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(context, "Not Allow !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void getPreEaxmModels( String courseid) {
        getPreEaxmModels.clear();
        String doubtsCourse= BaseUrl.GETALLDOUBTSCOURSE+"/"+courseid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, doubtsCourse, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("doubtsCourse", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("doubtsCourse",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            GetCourseAllDoubts getPreEaxmModel = new GetCourseAllDoubts();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("doubtsCourse", jsonObject1.toString());
                            getPreEaxmModel.setDoubtId(jsonObject1.getString("DoubtId"));
                            getPreEaxmModel.setCourseId(jsonObject1.getString("CourseId"));
                            getPreEaxmModel.setCategoryId(jsonObject1.getString("CategoryId"));
                            getPreEaxmModel.setSubCategoryId(jsonObject1.getString("SubCategoryId"));
                            getPreEaxmModel.setUserId(jsonObject1.getString("UserId"));
                            getPreEaxmModel.setUserName(jsonObject1.getString("UserName"));
                            getPreEaxmModel.setUserPic(jsonObject1.getString("UserPic"));
                            getPreEaxmModel.setPic(jsonObject1.getString("Pic"));
                            getPreEaxmModel.setTitle(jsonObject1.getString("Title"));
                            getPreEaxmModel.setDescription(jsonObject1.getString("Description"));
                            getPreEaxmModel.setType(jsonObject1.getString("Type"));
                            getPreEaxmModel.setAnswerStatus(jsonObject1.getString("AnswerStatus"));
                          String adddate=jsonObject1.getString("AddDate") ;
                          String date=adddate.substring(0,10);
                            getPreEaxmModel.setAddDate(date);


                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamCategoryAdapter = new GetDoubtsCourseAdapter(context, getPreEaxmModels);
                            _recy_doubts_chats_list.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
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
}