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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.VideoSeries;
import com.rankerspoint.academy.Model.HomeVideoSeriesListModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetHomeVideoSeriesListAdapter extends RecyclerView.Adapter<GetHomeVideoSeriesListAdapter.BannerImage> {
    List<HomeVideoSeriesListModel> getPreEaxmModels;
    Context context;

    public GetHomeVideoSeriesListAdapter(Context context, List<HomeVideoSeriesListModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }

    @NonNull
    @Override
    public GetHomeVideoSeriesListAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_series_list_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetHomeVideoSeriesListAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        final HomeVideoSeriesListModel getPreEaxmModel = getPreEaxmModels.get(position);
        //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
         Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.image_video);
        // Log.d("imageurl:--",BANN_IMG_URL + getPreEaxmModel.getPic());
        holder.classe_txt.setText(getPreEaxmModel.getName());
        if (getPreEaxmModel.getFeeStatus().equals("Free"))
        {
            holder.txt_status.setText("Free");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
        }else
        {
            holder.txt_status.setText("***");
            holder.txt_status.setLetterSpacing(0.2f);

        }

        SharedPreferences preferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

     String userId = preferences.getString("user_id", "");

holder.llout_video_series.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try {


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",userId);
            // jsonObject.put("UserId","8853616625");
            jsonObject.put("PurchasedServiceId",getPreEaxmModel.getCourseId());

            getAllPaid(jsonObject,getPreEaxmModel.getCourseId());
            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("cate_id_pay", getPreEaxmModel.getCategoryId());
            editor.putString("FeeStatusMentor", getPreEaxmModel.getFeeStatus());
            editor.apply();





        }catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }
});

    }

    @Override
    public int getItemCount() {
         return getPreEaxmModels.size();

    }

    public static class BannerImage extends RecyclerView.ViewHolder {

        public TextView classe_txt,txt_status;
         LinearLayout llout_video_series;
         ImageView image_video;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            classe_txt=itemView.findViewById(R.id.classe_txt);
            txt_status=itemView.findViewById(R.id.txt_status);
            llout_video_series=itemView.findViewById(R.id.llout_video_series);
            image_video=itemView.findViewById(R.id.image_video);
        }
    }
    public void getAllPaid(JSONObject jsonObject,String courseid){
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("json",jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.GETALLPAYMENTBYUSERANDTYPE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HomeVideoSeriesListModel model=new HomeVideoSeriesListModel();
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
                            Intent intent = new Intent(context, VideoSeries.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                            context.startActivity(intent);

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

}
