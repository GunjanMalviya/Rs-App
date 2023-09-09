package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rankerspoint.academy.Adapter.LiveClassesVideoAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.LiveClassesVideoModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpcomingLiveClasses extends Fragment {
    public UpcomingLiveClasses() {
    }

    public static UpcomingLiveClasses newInstance() {
        UpcomingLiveClasses fragment = new UpcomingLiveClasses();
        return fragment;
    }
    Context context;
    View view;
    List<LiveClassesVideoModel> getHomeCategorymodels=new ArrayList<>();
    LiveClassesVideoAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid="",name="";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;

    String user_id="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_upcoming_live_classes, container, false);
        context=container.getContext();
        recyler_notes=view.findViewById(R.id.recy_exam_series);
        lnr_layout=view.findViewById(R.id.lnr_layout);

        progressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        user_id = preferences.getString("user_id", "");
        categoryid=preferences.getString("CAT_ID","");
        Log.d("userid_pay",user_id+"categoryid"+categoryid);

        recyler_notes.setLayoutManager(new GridLayoutManager(context,1));
        recyler_notes.setNestedScrollingEnabled(false);
        getAllSyllabusData();
        return view;
    }
    private void getAllSyllabusData() {
        getHomeCategorymodels.clear();
        String strUrl= BaseUrl.GETALLUPCOMINGLIVECLASSBYCAT+"/"+categoryid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allliveClass", response);
                try {
                    getHomeCategorymodels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                            String LivClassId = jsonObject1.getString("LivClassId");
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Message = jsonObject1.getString("Message");
                            String Pic1 = jsonObject1.getString("Pic1");

                            String LiVideoLink1 = jsonObject1.getString("LiVideoLink1");
                            String LiVideoLink2 = jsonObject1.getString("LiVideoLink2");
                            String Pdf = jsonObject1.getString("Pdf");
                            String ClassLiveDate = jsonObject1.getString("ClassLiveDate");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Type = jsonObject1.getString("Type");



                            LiveClassesVideoModel getAllSyllabusCatModel = new LiveClassesVideoModel(LivClassId,SubjectId,CourseId,CategoryId,Name,Message,Pic1,LiVideoLink1,LiVideoLink2,Pdf,ClassLiveDate,FeeStatus,Type);

                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new LiveClassesVideoAdapter(context,getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
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
