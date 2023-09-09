package com.rankerspoint.academy.Activity;

import static com.rankerspoint.academy.BaseUrl.BaseUrl.BANN_IMG_URL;
import static com.rankerspoint.academy.BaseUrl.BaseUrl.GETALLPAYMENTBYUSERANDTYPE;
import static com.rankerspoint.academy.BaseUrl.BaseUrl.GETALLSYLLABUSCAT;
import static com.rankerspoint.academy.BaseUrl.BaseUrl.GETSINGLECOURSE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.SingleSyllabusCatAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllSyllabusCatModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleCourseDetails extends AppCompatActivity {
    ImageView imageLogo;
    ProgressDialog progressDialog;
    String courseid;
    LinearLayout lbl_skip, syllbus_llt, lbl_skip_new, lll_out;
    RecyclerView recyclerSyllabus;
    NestedScrollView scroll_view;
    String userId = "";
    TextView txtRegEndDate, txtLAnguage, txtTeacherNm, txtPrice, txtlanguageetx, skip_free, txt_syllabus;
    SingleSyllabusCatAdapter singleSyllabusCatAdapter;
    private String catId;
    private List<GetAllSyllabusCatModel> getAllSyllabusCatModels = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_course_details);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageLogo = findViewById(R.id.imageLogo);
        txtRegEndDate = findViewById(R.id.txtRegEndDate);
        txtLAnguage = findViewById(R.id.txtLAnguage);
        txtTeacherNm = findViewById(R.id.txtTeacherNm);
        scroll_view = findViewById(R.id.scroll_view);
        syllbus_llt = findViewById(R.id.syllbus_llt);
        lbl_skip_new = findViewById(R.id.lbl_skip_new);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
        Log.d("userId:--", userId);
//        PurchasedServiceId=getIntent().getStringExtra("PURCHASE_ID");
//        Log.d("PurchasedServiceId:--",PurchasedServiceId);
        txt_syllabus = findViewById(R.id.txt_syllabus);
        txt_syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_view.scrollTo(0, (int) syllbus_llt.getY());
                syllbus_llt.setBackgroundColor(getResources().getColor(R.color.light_blue_100));
            }
        });
        txtPrice = findViewById(R.id.txtPrice);
        skip_free = findViewById(R.id.skip_free);
        txtlanguageetx = findViewById(R.id.txtlanguageetx);
        lbl_skip = findViewById(R.id.lbl_skip);
        recyclerSyllabus = findViewById(R.id.recyclerSyllabus);
        recyclerSyllabus.setNestedScrollingEnabled(false);
        recyclerSyllabus.setLayoutManager(new GridLayoutManager(SingleCourseDetails.this, 1));

        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        courseid = getIntent().getStringExtra("COURSE_ID");
        catId = getIntent().getStringExtra("CAT_ID");
        Log.d("courseididd", courseid);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId", userId);
            // jsonObject.put("UserId","8853616625");
            jsonObject.put("PurchasedServiceId", courseid);
            getAllPaid(jsonObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getAllSyllabusData();
    }

    private void getSingleCourse(String status) {
        String SUBCATALL = GETSINGLECOURSE + "/" + courseid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sub_Prod_detail", response);
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam_sub", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            String RegLastDate = jsonObject1.getString("RegLastDate");
                            String datae = RegLastDate.substring(0, 10);
                            Log.d("datae", datae);
                            String ExpiryDate = jsonObject1.getString("ExpiryDate");
                            String FreeTrail = jsonObject1.getString("FreeTrail");
                            int Price = jsonObject1.getInt("Price");
                            String Langauge = jsonObject1.getString("Langauge");
                            String Teachers = jsonObject1.getString("Teachers");
                            Log.d("FreeTrail", FreeTrail + Price);
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("CourseId", courseid);
                            editor.putString("CategoryId", CategoryId);
                            editor.putString("SubCategoryId", SubCategoryId);
                            editor.putString("Pic", Pic);
                            editor.putString("Name", Name);
                            editor.putString("PriceC", Price + "");
                            editor.putString("Date", datae);
                            editor.putString("Langauge", Langauge);
                            editor.putString("Teachers", Teachers);
                            editor.putString("FreeTrail", FreeTrail);
                            editor.apply();
                            Intent intent=new Intent(getApplicationContext(), MultiTabSyllabus.class);
                            intent.putExtra("fromContent", false);
                            intent.putExtra("CAT_ID", catId);
                            intent.putExtra("COURSE_ID", courseid);
                            startActivity(intent);
                            finish();
                            if (FreeTrail.equals("Yes")) {
                                lbl_skip_new.setVisibility(View.GONE);
                                skip_free.setText("START FREE TRIAL");
                                skip_free.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        /*Intent intent = new Intent(getApplicationContext(), MultiTabSyllabus.class);
                                        intent.putExtra("CAT_ID", catId);
                                        intent.putExtra("COURSE_ID", courseid);
                                        startActivity(intent);*/
                                    }
                                });
                            } else {
                                if (status.equals("True")) {
                                    lbl_skip_new.setVisibility(View.GONE);
                                    skip_free.setText("All Ready Paid - GO");
                                    skip_free.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(), MultiTabSyllabus.class);
                                            intent.putExtra("CAT_ID", catId);
                                            intent.putExtra("COURSE_ID", courseid);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    skip_free.setText("Buy Now");
                                    skip_free.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getApplicationContext(), PaymentCheckOut.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                            txtRegEndDate.setText("Last date:-" + datae);
                            txtLAnguage.setText(Langauge);
                            txtTeacherNm.setText("By:-" + Teachers);
                            txtPrice.setText("Price:-" + Price);
                            txtlanguageetx.setText(Langauge);
                            Picasso.with(getApplicationContext()).load(BANN_IMG_URL + Pic).into(imageLogo);
                        }
                    } else {
                        // Toast.makeText(SingleCourseDetails.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    private void getAllSyllabusData() {
        getAllSyllabusCatModels.clear();
        String SUBCATALL = GETALLSYLLABUSCAT + "/" + courseid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Prod_detail", response);
                try {
                    getAllSyllabusCatModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            GetAllSyllabusCatModel getAllSyllabusCatModel = new GetAllSyllabusCatModel(SubjectId, CourseId, CategoryId, SubCategoryId, Name, Details);
                            getAllSyllabusCatModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            singleSyllabusCatAdapter = new SingleSyllabusCatAdapter(getAllSyllabusCatModels, getApplicationContext());
                            recyclerSyllabus.setAdapter(singleSyllabusCatAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        //Toast.makeText(SingleCourseDetails.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    public void getAllPaid(JSONObject jsonObject) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, GETALLPAYMENTBYUSERANDTYPE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllPayemnt:--", response.toString());
                        String Succeeded;
                        try {
                            Succeeded = response.getString("Status");
                            Log.d("SucceededMsg:--", Succeeded.toString());
                            getSingleCourse(Succeeded);
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Status", Succeeded);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingleCourseDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(jsonObjReq);
    }
//
//    private void getAllPaid(String PurchasedServiceId) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETALLPAYMENTBYUSERANDTYPE, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("All_Paid_Details", response);
//                try {
//
////
////                    JSONArray jsonObject = new JSONArray(response);
////                    Log.d("paidd",jsonObject.toString());
////
////                    if (jsonObject.length()>0) {
////
////                        for (int i = 0; i < jsonObject.length(); i++) {
////
////                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
////                            Log.d("jsoonoobjxyz", jsonObject1.toString());
////                            String PaymentId = jsonObject1.getString("PaymentId");
////                            purchaseid = jsonObject1.getString("PurchasedServiceId");
////                            String CategoryId = jsonObject1.getString("CategoryId");
////                            String ValidTo = jsonObject1.getString("ValidTo");
////                            String ValidFrom = jsonObject1.getString("ValidFrom");
////                            String Amount = jsonObject1.getString("Amount");
////                        }
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("UserId","8853616625");
//                params.put("PurchasedServiceId",PurchasedServiceId);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(SingleCourseDetails.this);
//        requestQueue.getCache().clear();
//        requestQueue.add(stringRequest);
//    }
}