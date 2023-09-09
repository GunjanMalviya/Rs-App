package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.GetExamCategoryListAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetPreEaxmModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YourExamsList extends AppCompatActivity {
    RecyclerView recy_preexam;
    EditText edt_search;
    ProgressDialog progressDialog;
    LinearLayout lnr_layout;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    GetExamCategoryListAdapter getExamCategoryAdapter;
    private List<GetPreEaxmModel> getPreEaxmModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_exams_list);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        recy_preexam=findViewById(R.id.recy_preexam);
        recy_preexam.setNestedScrollingEnabled(false);
        edt_search=findViewById(R.id.edt_search);
        lnr_layout=findViewById(R.id.lnr_layout);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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

        toolbar=findViewById(R.id.toolbar);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Your Exam");

        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recy_preexam.setLayoutManager(new GridLayoutManager(YourExamsList.this,1));
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
                    Log.d("preexam",jsonObject.toString());

                    if (response!=null) {

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
                            getExamCategoryAdapter = new GetExamCategoryListAdapter(YourExamsList.this, getPreEaxmModels);
                            recy_preexam.setAdapter(getExamCategoryAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                      //  Toast.makeText(YourExamsList.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
}