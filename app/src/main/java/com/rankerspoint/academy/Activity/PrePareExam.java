package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.rankerspoint.academy.Adapter.GetExamCategoryAdapter;
import com.rankerspoint.academy.Adapter.GetExamSubCategoryMoreClassesAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetPreEaxmModel;
import com.rankerspoint.academy.Model.GetSubPreEaxmModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrePareExam extends AppCompatActivity implements SubCatListener {
    RecyclerView recy_preexam;
    EditText edt_search;
    ProgressDialog progressDialog;
    LinearLayout lnr_layout;
    GetExamCategoryAdapter getExamCategoryAdapter;
    private List<GetPreEaxmModel> getPreEaxmModels = new ArrayList<>();
    private RecyclerView recySubCat;
    private List<GetSubPreEaxmModel> getSubCatList = new ArrayList<>();
    private GetExamSubCategoryMoreClassesAdapter getExamSubCategoryAdapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_pare_exam);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  recy_preexam = findViewById(R.id.recy_preexam);
        recySubCat = findViewById(R.id.recy_subcat);
        /*recySubCat.setNestedScrollingEnabled(false);
        recy_preexam.setNestedScrollingEnabled(false);*/
//        edt_search = findViewById(R.id.edt_search);
        lnr_layout = findViewById(R.id.lnr_layout);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
/*
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getExamCategoryAdapter.filter(edt_search.getText().toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getExamCategoryAdapter.filter(edt_search.getText().toString().toLowerCase());
            }
        });
*/
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        recySubCat.setLayoutManager(new LinearLayoutManager(PrePareExam.this, LinearLayoutManager.VERTICAL, false));
        recy_preexam.setLayoutManager(new LinearLayoutManager(PrePareExam.this, LinearLayoutManager.VERTICAL, false));
        userId = preferences.getString("user_id", "");
       /* if (!userId.equals("")) {
            recy_preexam.setVisibility(View.GONE);
            recySubCat.setVisibility(View.VISIBLE);
            getSubMoreClassesModels(preferences.getString("CAT_ID", ""));
        } else {
            getPreEaxmModels();
        }*/
        getPreEaxmModels();
    }

    private void getPreEaxmModels() {
        getPreEaxmModels.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.GETALLCATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Prod_detail", response);
                try {
                    getPreEaxmModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam", jsonObject.toString());
                    if (response != null) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_preexam.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            GetPreEaxmModel getPreEaxmModel = new GetPreEaxmModel(CategoryId, Name, Details, Pic, Logo);
                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels, PrePareExam.this);
                            recy_preexam.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(PrePareExam.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCatSelected(String name, String categoryId) {
        recy_preexam.setVisibility(View.GONE);
        recySubCat.setVisibility(View.VISIBLE);
        getSubMoreClassesModels(categoryId);
    }

    private void getSubMoreClassesModels(String categoryId) {
        getPreEaxmModels.clear();
        String SUBCATALL = BaseUrl.GETSUBCATEGORY + "/" + categoryId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    getPreEaxmModels.clear();
                    getSubCatList.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    if (!response.isEmpty()) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            GetSubPreEaxmModel getPreEaxmModel = new GetSubPreEaxmModel(CategoryId, SubCategoryId, Name, Details, Pic, Logo);
                            getSubCatList.add(getPreEaxmModel);
                        }
                        getExamSubCategoryAdapter = new GetExamSubCategoryMoreClassesAdapter(PrePareExam.this, getSubCatList, recySubCat, new GetExamSubCategoryMoreClassesAdapter.OnItemClicked() {
                            @Override
                            public void onItemClick(int position) {
                                SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("SubCatId", getSubCatList.get(position).getSubCategoryId());
                                editor.putString("SubCatName", getSubCatList.get(position).getName());
                                editor.apply();
                                Intent intent;
                                if (!userId.equals("")) {
                                    intent = new Intent(PrePareExam.this, Dashboard.class);
                                } else {
                                    intent = new Intent(PrePareExam.this, Login.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                        recySubCat.setAdapter(getExamSubCategoryAdapter);
                    } else {
                        Toast.makeText(PrePareExam.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(PrePareExam.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}

