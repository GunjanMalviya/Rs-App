package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
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
import com.rankerspoint.academy.Adapter.DailyGKAdapter;
import com.rankerspoint.academy.Adapter.DailyGKFilterAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.DailyGkModel;
import com.rankerspoint.academy.Model.GKFilterModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DailyGK extends AppCompatActivity {
    List<DailyGkModel> getHomeCategorymodels = new ArrayList<>();
    List<GKFilterModel> getFilters = new ArrayList<>();
    DailyGKFilterAdapter dailyGKFilterAdapter;
    DailyGKAdapter getHomeCategoryNotesPdfAdapter;
    String categoryid = "", name = "";
    RecyclerView recyler_notes;
    LinearLayout lnr_layout;
    ProgressDialog progressDialog;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    ImageView imgBack, imgCalender, imgFilter;
    TextView tv_toolbar_title;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_g_k);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        recyler_notes = findViewById(R.id.recy_exam_series);
        lnr_layout = findViewById(R.id.lnr_layout);
        imgBack = findViewById(R.id.imgBack);
        imgCalender = findViewById(R.id.imgCalender);
        imgFilter = findViewById(R.id.imgFilter);
        imgFilter.setVisibility(View.GONE);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
//        imgFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomSheetDialog();
//            }
//        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DatePickerDialog(DailyGK.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(DailyGK.this, AlertDialog.THEME_HOLO_LIGHT, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tv_toolbar_title.setText("Daily GK");
        recyler_notes.setLayoutManager(new GridLayoutManager(DailyGK.this, 1));
        getAllSyllabusData();
    }

    private void getAllSyllabusData() {
        getHomeCategorymodels.clear();
        // String SUBCATALL=GETALLNOTESPDFCATEGORY+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.GETALLDAILYGK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("gkstatus", response);
                try {
                    getHomeCategorymodels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyler_notes.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String DailyGKId = jsonObject1.getString("DailyGKId");
                            String GKCategory = jsonObject1.getString("GKCategory");
                            String GKName = jsonObject1.getString("GKName");
                            String Title = jsonObject1.getString("Title");
                            String DetailsLag1 = jsonObject1.getString("DetailsLag1");
                            String DetailsLag2 = jsonObject1.getString("DetailsLag2");
                            String Link = jsonObject1.getString("Link");
                            String datef = jsonObject1.getString("AddDate");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String AddDate = datef.substring(0, 10);
                            GKFilterModel gkFilterModel = new GKFilterModel(GKName);
                            getFilters.add(gkFilterModel);
                            DailyGkModel getAllSyllabusCatModel = new DailyGkModel(DailyGKId, GKCategory, GKName, Title, DetailsLag1, DetailsLag2, Link, AddDate, Pic1);
                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new DailyGKAdapter(getApplicationContext(), getHomeCategorymodels);
                        recyler_notes.setAdapter(getHomeCategoryNotesPdfAdapter);
                    } else {
                        lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(DailyGK.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String date = sdf.format(myCalendar.getTime());
        //Toast.makeText(DailyGK.this, "date:-"+sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
        getHomeCategoryNotesPdfAdapter.filter(date);
    }

    private void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        final View view = getLayoutInflater().inflate(R.layout.bottom_sheet_list, null);
        RecyclerView recy_anwer = view.findViewById(R.id.recy_anwer);
        dailyGKFilterAdapter = new DailyGKFilterAdapter(DailyGK.this, getFilters);
        recy_anwer.setLayoutManager(new GridLayoutManager(DailyGK.this, 1));
        recy_anwer.setAdapter(dailyGKFilterAdapter);
        dailyGKFilterAdapter.setOnItemClickListener(new DailyGKFilterAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                getHomeCategoryNotesPdfAdapter.filter(getFilters.get(position).getGkname());
            }
        });
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }
}