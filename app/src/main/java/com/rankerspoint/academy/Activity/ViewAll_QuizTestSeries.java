package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import com.rankerspoint.academy.Adapter.GetExamMockTestSeriesAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetEaxmSeriesModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAll_QuizTestSeries extends AppCompatActivity {
RecyclerView recycler_mock_series;
ProgressDialog progressDialog;
    private GetExamMockTestSeriesAdapter getExamMockTestSeriesAdapter;
    ImageView imgBack;
    TextView tv_toolbar_title,txt_mock,txt_quiz;
    int selectedPosition=0;
    LinearLayout lnr_layout;
    private List<GetEaxmSeriesModel> getEaxmSeriesModels = new ArrayList<>();
    CardView card_all,card_mock_test,card_course_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all__quiz_test_series);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  progressDialog = new ProgressDialog(ViewAll_QuizTestSeries.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        card_course_click=findViewById(R.id.card_course_click);
        card_mock_test=findViewById(R.id.card_mock_test);
        txt_quiz=findViewById(R.id.txt_quiz);
        lnr_layout=findViewById(R.id.lnr_layout);
        txt_mock=findViewById(R.id.txt_mock);
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title.setText("All Quiz");

        recycler_mock_series=findViewById(R.id.recycler_mock_series);
        recycler_mock_series.setLayoutManager(new GridLayoutManager(ViewAll_QuizTestSeries.this,2));
        SharedPreferences preferences1 = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String coursrid = preferences1.getString("COR_Id", "");
        getExamwiseSeries(coursrid,"MockTest");
        if (selectedPosition == 0) {
            //onClick.onItemClick(position);
            //  card_mock_test.setSelected(true); //using selector drawable
            txt_mock.setBackgroundColor(getResources().getColor(R.color.theme_purple));
            txt_mock.setTextColor(getResources().getColor(R.color.white_color));
        }
        // card_course_click.setSelected(false);
        //onClick.onItemClick(position);
        txt_quiz.setBackgroundColor(getResources().getColor(R.color.white_color));
        txt_quiz.setTextColor(getResources().getColor(R.color.black_color));
        card_course_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getExamwiseSeries(coursrid,"Quiz");
                if (selectedPosition == 0) {
                    //onClick.onItemClick(position);
                    //  card_mock_test.setSelected(true); //using selector drawable
                    txt_quiz.setBackgroundColor(getResources().getColor(R.color.theme_purple));
                    txt_quiz.setTextColor(getResources().getColor(R.color.white_color));
                }
                // card_course_click.setSelected(false);
                //onClick.onItemClick(position);
                txt_mock.setBackgroundColor(getResources().getColor(R.color.white_color));
                txt_mock.setTextColor(getResources().getColor(R.color.black_color));
            }
        });
        card_mock_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getExamwiseSeries(coursrid,"MockTest");
                if (selectedPosition == 0) {
                    //onClick.onItemClick(position);
                    //  card_mock_test.setSelected(true); //using selector drawable
                    txt_mock.setBackgroundColor(getResources().getColor(R.color.theme_purple));
                    txt_mock.setTextColor(getResources().getColor(R.color.white_color));
                }
                // card_course_click.setSelected(false);
                //onClick.onItemClick(position);
                txt_quiz.setBackgroundColor(getResources().getColor(R.color.white_color));
                txt_quiz.setTextColor(getResources().getColor(R.color.black_color));
            }
        });
    }
    private void getExamwiseSeries(String courseid,String type) {
        getEaxmSeriesModels.clear();
        String strurl= BaseUrl.GETALLSERIESBYCOURSEANDTYPE+"/"+courseid+"/"+type;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Mock_siries", response);
                try {
                    getEaxmSeriesModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("mockseries",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            lnr_layout.setVisibility(View.GONE);
                            recycler_mock_series.setVisibility(View.VISIBLE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj12", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SeriesId = jsonObject1.getString("SeriesId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String Title = jsonObject1.getString("Title");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Pic = jsonObject1.getString("Pic");
                            String Status = jsonObject1.getString("Status");
                            GetEaxmSeriesModel getPreEaxmModel = new GetEaxmSeriesModel(CategoryId,SeriesId,CourseId,Title,FeeStatus,Pic,Status);
                            getEaxmSeriesModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamMockTestSeriesAdapter = new GetExamMockTestSeriesAdapter(ViewAll_QuizTestSeries.this, getEaxmSeriesModels);
                            recycler_mock_series.setAdapter(getExamMockTestSeriesAdapter);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                        recycler_mock_series.setVisibility(View.GONE);
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
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAll_QuizTestSeries.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}