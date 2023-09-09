package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.rankerspoint.academy.Adapter.PaymentDetailsAdapter;
import com.rankerspoint.academy.Model.PaymentDetailsModel;
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

public class PaymentSuccessfully extends AppCompatActivity {
    List<PaymentDetailsModel> getHomeCategorymodels=new ArrayList<>();
    PaymentDetailsAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid="",name="";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    ImageView imgBack;
    String user_id="";
    TextView tv_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_successfully);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);    recyler_notes=findViewById(R.id.recy_exam_series);
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
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        user_id = preferences.getString("user_id", "");
        Log.d("userid_pay",user_id);
        tv_toolbar_title.setText("Payment History");
        recyler_notes.setLayoutManager(new GridLayoutManager(PaymentSuccessfully.this,1));
        recyler_notes.setNestedScrollingEnabled(false);
        getAllSyllabusData();
    }
    private void getAllSyllabusData() {
        getHomeCategorymodels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETALLPAYMENTUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allpayemtUser", response);
                try {
                    getHomeCategorymodels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                            String PaymentId = jsonObject1.getString("PaymentId");
                            String UserId = jsonObject1.getString("UserId");
                            String PurchaseType = jsonObject1.getString("PurchaseType");
                            String PurchasedServiceId = jsonObject1.getString("PurchasedServiceId");
                            String PurchasedServiceName = jsonObject1.getString("PurchasedServiceName");
                            String Amount = jsonObject1.getString("Amount");
                            String TransDate = jsonObject1.getString("TransDate");
                            String date=TransDate.substring(0,10);
                            String Status = jsonObject1.getString("Status");



                            PaymentDetailsModel getAllSyllabusCatModel = new PaymentDetailsModel(PaymentId,UserId,PurchaseType,PurchasedServiceId,PurchasedServiceName,Amount,date,Status);

                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new PaymentDetailsAdapter(getApplicationContext(),getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(PaymentSuccessfully.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("UserId",user_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}