package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.rankerspoint.academy.Adapter.LiveClassesListHomeCateAdapter;
import com.rankerspoint.academy.Adapter.LiveClassesVideoAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.LiveClassesHomeCateModel;
import com.rankerspoint.academy.Model.LiveClassesVideoModel;
import com.rankerspoint.academy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiveClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiveClassFragment extends Fragment {
    private static String catId;
    private static String courseid;
    private LiveClassesListHomeCateAdapter getHomeCategoryNotesPdfAdapter;
    private LiveClassesVideoAdapter upComingAdapter;
    private ProgressDialog progressDialog;

    public LiveClassFragment() {
    }

    public static LiveClassFragment newInstance(String cat, String course) {
        LiveClassFragment fragment = new LiveClassFragment();
        catId = cat;
        courseid = course;
        return fragment;
    }

    Context context;
    View view;
    private ViewPager view_pager;
    private TabLayout tab_layout;
    String Name = "";
    RecyclerView rvVideos;
    LinearLayout lnrNoData;
    Button btnRecent, btnUpcoming;
    List<LiveClassesHomeCateModel> recentClasses = new ArrayList<>();
    List<LiveClassesVideoModel> upcomingClasses = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_live_class, container, false);
        context = container.getContext();
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        categoryid = preferences.getString("CategoryId", "");
//        courceId = preferences.getString("CourseId", "");
        Name = preferences.getString("Name", "Name");
        btnRecent = view.findViewById(R.id.btn_recent);
        btnUpcoming = view.findViewById(R.id.btn_upcoming);
        lnrNoData = view.findViewById(R.id.lnr_layout);
        rvVideos = view.findViewById(R.id.rv_videos);
        rvVideos.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        getRecentClass(catId);
        btnRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                btnRecent.setBackground(getResources().getDrawable(R.color.green));
                btnUpcoming.setBackground(getResources().getDrawable(R.color.white_color));
                btnRecent.setTextColor(getResources().getColor(R.color.white_color));
                btnUpcoming.setTextColor(getResources().getColor(R.color.black_color));
                getRecentClass(catId);
            }
        });
        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                btnUpcoming.setBackground(getResources().getDrawable(R.color.green));
                btnRecent.setBackground(getResources().getDrawable(R.color.white_color));
                btnUpcoming.setTextColor(getResources().getColor(R.color.white_color));
                btnRecent.setTextColor(getResources().getColor(R.color.black_color));
                getUpcomingClass(catId);
            }
        });
        return view;
    }

    private void getUpcomingClass(String catId) {
        upcomingClasses.clear();
        String strUrl = BaseUrl.GETALLUPCOMINGLIVECLASSBYCAT + "/" + catId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allliveClass", response);
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            rvVideos.setVisibility(View.VISIBLE);
                            lnrNoData.setVisibility(View.GONE);
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
                            LiveClassesVideoModel getAllSyllabusCatModel = new LiveClassesVideoModel(LivClassId, SubjectId, CourseId, CategoryId, Name, Message, Pic1, LiVideoLink1, LiVideoLink2, Pdf, ClassLiveDate, FeeStatus, Type);
                            upcomingClasses.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        upComingAdapter = new LiveClassesVideoAdapter(context, upcomingClasses);
                        rvVideos.setAdapter(getHomeCategoryNotesPdfAdapter);
                    } else {
                        lnrNoData.setVisibility(View.VISIBLE);
                        rvVideos.setVisibility(View.GONE);
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

    private void getRecentClass(String catId) {
        recentClasses.clear();
        String strUrl = BaseUrl.GETALLLIVECLASSESCATE + "/" + catId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allliveClass", response);
                try {
                    recentClasses.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        rvVideos.setVisibility(View.VISIBLE);
                        lnrNoData.setVisibility(View.GONE);
                        for (int i = 0; i < jsonObject.length(); i++) {
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
                            LiveClassesHomeCateModel getAllSyllabusCatModel = new LiveClassesHomeCateModel(LivClassId, SubjectId, CourseId, CategoryId, Name, Message, Pic1, LiVideoLink1, LiVideoLink2, Pdf, ClassLiveDate, FeeStatus, Type);
                            if (courseid.equalsIgnoreCase(CourseId)) {
                                recentClasses.add(getAllSyllabusCatModel);
                            }
                        }
                        getHomeCategoryNotesPdfAdapter = new LiveClassesListHomeCateAdapter(context, recentClasses);
                        rvVideos.setAdapter(getHomeCategoryNotesPdfAdapter);
                    } else {
                        lnrNoData.setVisibility(View.VISIBLE);
                        rvVideos.setVisibility(View.GONE);
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
    }
}