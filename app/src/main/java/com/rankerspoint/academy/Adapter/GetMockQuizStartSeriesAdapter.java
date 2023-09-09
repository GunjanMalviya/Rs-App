package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.MockTestWebView;
import com.rankerspoint.academy.Model.GetMockExamStartModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rankerspoint.academy.R;

public class GetMockQuizStartSeriesAdapter extends RecyclerView.Adapter<GetMockQuizStartSeriesAdapter.BannerImage> {
    List<GetMockExamStartModel> getPreEaxmModels;
    Context context;
    public GetMockQuizStartSeriesAdapter(Context context, List<GetMockExamStartModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }
    @NonNull
    @Override
    public GetMockQuizStartSeriesAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mock_test_list, parent, false);
        return new BannerImage(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GetMockQuizStartSeriesAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetMockExamStartModel getPreEaxmModel = getPreEaxmModels.get(position);
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
       String userid  = preferences.getString("user_id", "");
       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
    //  Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);
       // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());

        holder.txtTitle.setText(getPreEaxmModel.getTitle());
        if (getPreEaxmModel.getFeeStatus().equals("Free"))
        {
            holder.txt_status.setText("Free");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
        }else
        {
            holder.txt_status.setText("***");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.theme_purple));

            holder.txt_status.setLetterSpacing(0.2f);
        }

        holder.startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserId", userid);
                // jsonObject.put("UserId","8853616625");
                jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());

                getAllPaid(jsonObject, getPreEaxmModel.getCourseId(),getPreEaxmModel.getExamId(),userid);
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("FeeStatusMentor", getPreEaxmModel.getFeeStatus());
                editor.apply();
            }catch (JSONException ex)
            {
                ex.printStackTrace();
            }



            }
        });

    }

    @Override
    public int getItemCount() {
      return   getPreEaxmModels.size();

    }

    public static class BannerImage extends RecyclerView.ViewHolder {

        public TextView txtTitle,startExam,txt_status;
        public CardView card_preexam_subcat;

        public BannerImage(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_Nm_Title);

            startExam=itemView.findViewById(R.id.startExam);
            txt_status=itemView.findViewById(R.id.txt_status);
            //img=itemView.findViewById(R.id.imageLogo);
            card_preexam_subcat=itemView.findViewById(R.id.card_preexam_subcat);
        }
        // each data item is just a string in this case

    }
    public void getAllPaid(JSONObject jsonObject, String courseid,String examId,String userid){
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.GETALLPAYMENTBYUSERANDTYPE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("getAllPayemnt:--",response.toString());

                        String Succeeded;
                        try {
                            Succeeded = response.getString("Status");
                            Log.d("SucceededMsg:--",Succeeded.toString());
                            getSingleCourse(courseid);

                            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Status", Succeeded);
                            editor.apply();
                          try {
                              JSONObject jsonObject1 = new JSONObject();
                              jsonObject1.put("UserId", userid);
                              jsonObject1.put("ExamId", examId);
                              getAllAttemptStatus(jsonObject1,userid,examId);
                          }catch (JSONException ex)
                          {
                              ex.printStackTrace();
                          }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

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
    private void getSingleCourse(String courseid) {

        String SUBCATALL= BaseUrl.GETSINGLECOURSE+"/"+courseid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sub_Prod_detail", response);
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam_sub",jsonObject.toString());

                    if (jsonObject.length()>0) {

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
                            String datae=RegLastDate.substring(0,10);
                            Log.d("datae",datae);

                            String ExpiryDate = jsonObject1.getString("ExpiryDate");
                            String FreeTrail = jsonObject1.getString("FreeTrail");
                            String Price = jsonObject1.getString("Price");
                            String Langauge = jsonObject1.getString("Langauge");
                            String Teachers = jsonObject1.getString("Teachers");
                            Log.d("FreeTrail",FreeTrail);
                            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("CourseId", courseid);
                            editor.putString("CategoryId", CategoryId);
                            editor.putString("SubCategoryId", SubCategoryId);
                            editor.putString("Name", Name);
                            editor.putString("PriceC", Price);

                            editor.apply();

                        }
                    }else
                    {

                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    public void getAllAttemptStatus(JSONObject jsonObject,String userid,String examId){
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.GETEXAMSTATUSOFUSER, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllStatus:--",response.toString());
                        String Succeeded,AttemptNo;
                        try {
                            Succeeded = response.getString("Status");
                            AttemptNo = response.getString("AttemptNo");
                            Log.d("SucceededMsgSts:--",Succeeded.toString());
                            if (Succeeded.equals("True")) {
                                Intent intent = new Intent(context, MockTestWebView.class);
                                intent.putExtra("EXAM_ID", examId);
//                intent.putExtra("SUBJECT_ID", getPreEaxmModel.getSubjectId().toString());
                                intent.putExtra("USERID", userid);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                context.startActivity(intent);
                            }else {
                                Toast.makeText(context, "Exam attempt out of limit!! ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

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


}
