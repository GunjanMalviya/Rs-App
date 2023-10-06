package com.rankerspoint.academy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rankerspoint.academy.Adapter.GetHomeNotesPdfAdapter;
import com.rankerspoint.academy.Fragment.RecentLiveVideo;
import com.rankerspoint.academy.Model.HomeNotesPdfModel;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeNotesPdf extends Fragment {
    String categoryid = "";
    GetHomeNotesPdfAdapter getHomeNotesPdfAdapter;
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    List<HomeNotesPdfModel> getAllSyllabusCatModels = new ArrayList<>();
    private View view;

    public HomeNotesPdf() {
    }

    public static HomeNotesPdf newInstance() {
        HomeNotesPdf fragment = new HomeNotesPdf();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_notes_pdf, container, false);
        recyler_notes=view.findViewById(R.id.recy_exam_series);
        lnr_layout=view.findViewById(R.id.lnr_layout);
        recyler_notes.setLayoutManager(new GridLayoutManager(getContext(),1));
        SharedPreferences preferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "");
        getAllSyllabusData();
        return view;
    }

    /*@Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home_notes_pdf);
            Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
            Tools.setSystemBarLight(this);
            recyler_notes=findViewById(R.id.recy_exam_series);
            lnr_layout=findViewById(R.id.lnr_layout);
            imgBack=findViewById(R.id.imgBack);
            tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            tv_toolbar_title.setText("Notes & PDF");

            progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            categoryid=getIntent().getStringExtra("CAT_ID");
            recyler_notes.setLayoutManager(new GridLayoutManager(HomeNotesPdf.this,1));
            Log.d("cateidid",categoryid);
            getAllSyllabusData();
        }*/
    private void getAllSyllabusData() {
        getAllSyllabusCatModels.clear();
        String SUBCATALL = BaseUrl.GETALLNOTESPDF + "/" + categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("pdf_details", response);
                try {
                    getAllSyllabusCatModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf", jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String CourseCategoryId = jsonObject1.getString("CourseCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String CourseId = jsonObject1.getString("CourseId");
                            String Pic1 = jsonObject1.getString("Pic1");

                            HomeNotesPdfModel getAllSyllabusCatModel = new HomeNotesPdfModel(CategoryId,CourseCategoryId,Name,FeeStatus,CourseId,Pic1);

                            getAllSyllabusCatModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeNotesPdfAdapter = new GetHomeNotesPdfAdapter(getContext(),getAllSyllabusCatModels);
                        recyler_notes.setAdapter(getHomeNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}