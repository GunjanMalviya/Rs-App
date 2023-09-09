package com.rankerspoint.academy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.SingleChapterCatFragAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllSyllabusChapModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectActivity extends AppCompatActivity {
    private RecyclerView rvSelectedCourse;
    private CardView cardAllContent;
    TextView tv_toolbar_title;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        cardAllContent = findViewById(R.id.all_content);
        cardAllContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SubjectActivity.this, MultiTabSyllabus.class);
                intent.putExtra("fromContent", true);
                startActivity(intent);
            }
        });
//        rvSelectedCourse.setLayoutManager(new LinearLayoutManager(SubjectActivity.this));
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String sId = preferences.getString("subjectId", "");
        String Name = preferences.getString("Name", "Name");
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText(Name);
        setSupportActionBar(toolbar);
//        getAllChapterData(sId);
    }

    /*private void getAllChapterData(String subjectId) {
        String SUBCATALL = BaseUrl.GETALLCHAPTERWISE + "/" + subjectId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<GetAllSyllabusChapModel> getAllSyllabusChapModels = new ArrayList<>();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("allchapt", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            String ChapterId = jsonObject1.getString("ChapterId");
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            GetAllSyllabusChapModel getAllSyllabusCatModel = new GetAllSyllabusChapModel(SubjectId, ChapterId, CourseId, CategoryId, SubCategoryId, Name, Details);
                            getAllSyllabusChapModels.add(getAllSyllabusCatModel);
                            SingleChapterCatFragAdapter singleChapterCatAdapter = new SingleChapterCatFragAdapter(getAllSyllabusChapModels, context);
                            rvSelectedCourse.setAdapter(singleChapterCatAdapter);
                            singleChapterCatAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(SubjectActivity.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SubjectActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }*/
}
