package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.rankerspoint.academy.Activity.PaymentCheckOut;
import com.rankerspoint.academy.Activity.SubjectActivity;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Fragment.Syllabus_Single;
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

public class SingleSyllabusCatFragAdapter extends RecyclerView.Adapter<SingleSyllabusCatFragAdapter.MyViewHolder> {
    private List<GetAllSyllabusCatModel> getAllSyllabusCatModels;
    private Context context;
    private List<GetAllSyllabusChapModel> getAllSyllabusChapModels = new ArrayList<>();
    private SelectedCourse selectedCourse;

    public SingleSyllabusCatFragAdapter(List<GetAllSyllabusCatModel> getAllSyllabusCatModels, Context context, SelectedCourse selectedCourse) {
        this.getAllSyllabusCatModels = getAllSyllabusCatModels;
        this.context = context;
        this.selectedCourse = selectedCourse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sub_frag_cat_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetAllSyllabusCatModel cc = getAllSyllabusCatModels.get(position);
        holder.prodNAme.setText(cc.getName());
       /* if (cc.getName().equalsIgnoreCase("chemistry")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.orange_100));
            holder.ivIcon.setImageResource(R.drawable.chemistry);
        } else if (cc.getName().equalsIgnoreCase("physics")) {
            holder.ivIcon.setImageResource(R.drawable.physics);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
        } else if (cc.getName().equalsIgnoreCase("mathematics")) {
            holder.ivIcon.setImageResource(R.drawable.mth);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.orange_100));
        } else {
            holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.orange_100));
        }*/
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (selectedCourse!=null){
                    selectedCourse.selectedCour(cc.getSubjectId());
                }*/
                SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("subjectId", cc.getSubjectId());
                editor.apply();
                Intent intent = new Intent(context, SubjectActivity.class);
                context.startActivity(intent);
//                getAllChapterData(holder.recyclerSubCate, cc.getSubjectId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return getAllSyllabusCatModels.size();
    }

    private void getAllChapterData(RecyclerView recyclerView, String subjectId) {
        getAllSyllabusChapModels.clear();
        String SUBCATALL = BaseUrl.GETALLCHAPTERWISE + "/" + subjectId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allchapterwise", response);
                try {
                    getAllSyllabusChapModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("allchapt", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("chapterwise", jsonObject1.toString());
                            String ChapterId = jsonObject1.getString("ChapterId");
                            String SubjectId = jsonObject1.getString("SubjectId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            GetAllSyllabusChapModel getAllSyllabusCatModel = new GetAllSyllabusChapModel(SubjectId, ChapterId, CourseId, CategoryId, SubCategoryId, Name, Details);
                            getAllSyllabusChapModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            SingleChapterCatFragAdapter singleChapterCatAdapter = new SingleChapterCatFragAdapter(getAllSyllabusChapModels, context);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(singleChapterCatAdapter);
                            singleChapterCatAdapter.notifyDataSetChanged();
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView prodNAme, pdetails;
        ImageView ivIcon;
        public CardView cardView;
        boolean mines = true;
        RecyclerView recyclerSubCate;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            prodNAme = view.findViewById(R.id.pNAme);
            ivIcon = view.findViewById(R.id.iv_icon);
//            cardView = view.findViewById(R.id.view2);
//            image1 = view.findViewById(R.id.image1);
//            recyclerSubCate = view.findViewById(R.id.recyclerSubCate);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }
}


