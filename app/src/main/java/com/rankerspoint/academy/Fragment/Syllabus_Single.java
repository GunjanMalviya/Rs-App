package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Activity.MentorPostSingleTestDescription;
import com.rankerspoint.academy.Activity.PaymentCheckOut;
import com.rankerspoint.academy.Activity.SubjectActivity;
import com.rankerspoint.academy.Activity.SyllabusSingle;
import com.rankerspoint.academy.Adapter.SingleChapterCatFragAdapter;
import com.rankerspoint.academy.Adapter.SingleSyllabusCatFragAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllSyllabusCatModel;
import com.rankerspoint.academy.Model.GetAllSyllabusChapModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.SelectedCourse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Syllabus_Single#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Syllabus_Single extends Fragment implements SelectedCourse {
    public Syllabus_Single() {
    }

    public static Syllabus_Single newInstance() {
        Syllabus_Single fragment = new Syllabus_Single();
        return fragment;
    }

    Context context;
    View view;
    String courseid, Name;
    SingleSyllabusCatFragAdapter singleSyllabusCatAdapter;
    private List<GetAllSyllabusCatModel> getAllSyllabusCatModels = new ArrayList<>();
    RecyclerView recyclerSyllabus;
    ProgressDialog progressDialog;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_syllabus__single, container, false);
        context = container.getContext();
//        rvSelectedCourse = view.findViewById(R.id.rv_syl);
        recyclerSyllabus = view.findViewById(R.id.recyclerSyllabus);
        recyclerSyllabus.setLayoutManager(new GridLayoutManager(context, 3));
//        recyclerSyllabus.setNestedScrollingEnabled(false);
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        courseid = preferences.getString("CourseId", "CourseId");
        Log.d("CourseId", courseid);
        getAllSyllabusData();
        return view;
    }

    private void getAllSyllabusData() {
        getAllSyllabusCatModels.clear();
        String SUBCATALL = BaseUrl.GETALLSYLLABUSCAT + "/" + courseid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getAllSyllabusData", response);
                try {
                    getAllSyllabusCatModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllSyllabusDataxs", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            GetAllSyllabusCatModel getAllSyllabusCatModel = new GetAllSyllabusCatModel(SubjectId, CourseId, CategoryId, SubCategoryId, Name, Details);
                            getAllSyllabusCatModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            singleSyllabusCatAdapter = new SingleSyllabusCatFragAdapter(getAllSyllabusCatModels, context, Syllabus_Single.this);
                            recyclerSyllabus.setAdapter(singleSyllabusCatAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                         Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void selectedCour(String subjectId) {

    }
}