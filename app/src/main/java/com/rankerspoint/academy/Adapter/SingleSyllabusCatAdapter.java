package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetAllSyllabusCatModel;
import com.rankerspoint.academy.Model.GetAllSyllabusChapModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rankerspoint.academy.R;

public class SingleSyllabusCatAdapter extends RecyclerView.Adapter<SingleSyllabusCatAdapter.MyViewHolder> {

    private List<GetAllSyllabusCatModel> getAllSyllabusCatModels;
    private Context context;
    private List<GetAllSyllabusChapModel> getAllSyllabusChapModels = new ArrayList<>();

    public SingleSyllabusCatAdapter(List<GetAllSyllabusCatModel> getAllSyllabusCatModels, Context context) {
        this.getAllSyllabusCatModels = getAllSyllabusCatModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sub_cat_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetAllSyllabusCatModel cc = getAllSyllabusCatModels.get(position);


        holder.prodNAme.setText(cc.getName());
        holder.recyclerSubCate.setNestedScrollingEnabled(false);
//        if (cc.getSub_array() == null || cc.getSub_array().length() == 0) {
//
//
//            holder.pimage.setVisibility(View.GONE);
//        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        getAllChapterData(holder.recyclerSubCate, cc.getSubjectId());
                    }

        });
    }

    @Override
    public int getItemCount() {
        return getAllSyllabusCatModels.size();
    }

    private void getAllChapterData(RecyclerView recyclerView, String subjectId) {
        getAllSyllabusChapModels.clear();
        String SUBCATALL= BaseUrl.GETALLCHAPTERWISE+"/"+subjectId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allchapterwise", response);
                try {
                    getAllSyllabusChapModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("allchapt",jsonObject.toString());

                    if (jsonObject.length()>0) {

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



                            GetAllSyllabusChapModel getAllSyllabusCatModel = new GetAllSyllabusChapModel(ChapterId,SubjectId,CourseId,CategoryId,SubCategoryId,Name,Details);

                            getAllSyllabusChapModels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            SingleChapterCatAdapter singleChapterCatAdapter = new SingleChapterCatAdapter( getAllSyllabusChapModels,context);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setAdapter(singleChapterCatAdapter);
                            singleChapterCatAdapter.notifyDataSetChanged();
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {

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
        ImageView image, pimage, image1;
        boolean mines = true;
        RecyclerView recyclerSubCate;

        LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            prodNAme = view.findViewById(R.id.pNAme);

            pimage = view.findViewById(R.id.image);
            image1 = view.findViewById(R.id.image1);

            recyclerSubCate = view.findViewById(R.id.recyclerSubCate);

            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

}


