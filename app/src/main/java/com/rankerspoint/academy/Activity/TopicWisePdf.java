package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.TopicPdfAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllTopicPdfModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicWisePdf extends AppCompatActivity {
private List<GetAllTopicPdfModel> getAllTopicIndividuals= new ArrayList<>();
TopicPdfAdapter topicPdfAdapter;
RecyclerView recy_pdf;
ProgressDialog progressDialog;
String TopicID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_wise_pdf);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  recy_pdf=findViewById(R.id.recy_pdf);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
TopicID=getIntent().getStringExtra("TopicID");
        recy_pdf.setLayoutManager(new GridLayoutManager(TopicWisePdf.this,2));
        getAllTopicPdf();
    }


    private void getAllTopicPdf() {
        getAllTopicIndividuals.clear();
        String SUBCATALL= BaseUrl.GETALLPDFNOTES+"/"+TopicID.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getAllTopicWisePdf", response);
                try {

                    getAllTopicIndividuals.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllTopicWiseVidpdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getAllTopicWiseVideoasd", jsonObject1.toString());
                            String DCRecordId = jsonObject1.getString("DCRecordId");
                            String DCTopicId = jsonObject1.getString("DCTopicId");
                            String DCTopicName = jsonObject1.getString("DCTopicName");
                            String DCContentId = jsonObject1.getString("DCContentId");
                            String Title = jsonObject1.getString("Title");
                            String NotesCategoryName = jsonObject1.getString("NotesCategoryName");
                            String PdfLang1 = jsonObject1.getString("PdfLang1");
                            String PdfLag2 = jsonObject1.getString("PdfLag2");
                            String AddDate = jsonObject1.getString("AddDate");
                            String Status = jsonObject1.getString("Extra1");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String Extra2 = jsonObject1.getString("Extra2");

                            String datae=AddDate.substring(0,10);
                            Log.d("datae",datae);


                            GetAllTopicPdfModel getAllTopicIndividual = new GetAllTopicPdfModel(DCRecordId,DCTopicId,DCTopicName,DCContentId,Title,NotesCategoryName,PdfLang1,PdfLag2,AddDate,Status,Pic1,Extra2);

                            getAllTopicIndividuals.add(getAllTopicIndividual);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        }

                         topicPdfAdapter = new TopicPdfAdapter( getAllTopicIndividuals,getApplicationContext());
                        recy_pdf.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recy_pdf.setAdapter(topicPdfAdapter);
                        topicPdfAdapter.notifyDataSetChanged();
                    }else
                    {

                       // Toast.makeText(getApplicationContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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