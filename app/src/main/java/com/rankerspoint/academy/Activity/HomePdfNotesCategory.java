package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.rankerspoint.academy.Adapter.GetHomeCategoryNotesPdfAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.HomeCategoryNotesPdfModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePdfNotesCategory extends AppCompatActivity {
List<HomeCategoryNotesPdfModel> getHomeCategorymodels=new ArrayList<>();
    GetHomeCategoryNotesPdfAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid="",name="",feestatus="";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    TextView tv_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pdf_notes_category);
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


        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        recyler_notes.setLayoutManager(new GridLayoutManager(HomePdfNotesCategory.this,1));


        SharedPreferences preferences1 = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String Status = preferences1.getString("Status", "");
        name = preferences1.getString("NamePdf", "");
        categoryid = preferences1.getString("cate_id_pay", "");
        feestatus = preferences1.getString("FeeStatus", "");
        Log.d("successded:--",Status+"feesstatus:--"+feestatus);
        tv_toolbar_title.setText(name);

        if (feestatus.equals("Free"))
        {
            getAllSyllabusData();
        }else {
            if (feestatus.equals("Paid"))
            {
                if (Status.equals("True"))
                {
                    getAllSyllabusData();
                }
                else {
                    Intent intent=new Intent(HomePdfNotesCategory.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }

    }
    private void getAllSyllabusData() {
        getHomeCategorymodels.clear();
        String SUBCATALL= BaseUrl.GETALLNOTESPDFCATEGORY+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("pdf_details", response);
                try {
                    getHomeCategorymodels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String NotesId = jsonObject1.getString("NotesId");
                            String NotesCategoryId = jsonObject1.getString("NotesCategoryId");
                            String CourseCategoryId = jsonObject1.getString("CourseCategoryId");
                            String NotesCategoryName = jsonObject1.getString("NotesCategoryName");
                            String Title = jsonObject1.getString("Title");
                            String PdfLang1 = jsonObject1.getString("PdfLang1");
                            String PdfLag2 = jsonObject1.getString("PdfLag2");
                            String UpVotes = jsonObject1.getString("UpVotes");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String Extra1 = jsonObject1.getString("Extra1");
                            String Extra2 = jsonObject1.getString("Extra2");


                            HomeCategoryNotesPdfModel getAllSyllabusCatModel = new HomeCategoryNotesPdfModel(NotesId,NotesCategoryId,CourseCategoryId,NotesCategoryName,Title,PdfLang1,PdfLag2,UpVotes,Pic1,Extra1,Extra2);

                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new GetHomeCategoryNotesPdfAdapter(getApplicationContext(),getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(HomePdfNotesCategory.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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