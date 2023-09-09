package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.YoutubePlayerLiveClasses;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.rankerspoint.academy.Model.LiveClassesVideoModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LiveClassesVideoAdapter extends RecyclerView.Adapter<LiveClassesVideoAdapter.BannerImage> {


    List<LiveClassesVideoModel> getPreEaxmModels;

    Context context;

    public LiveClassesVideoAdapter(Context context, List<LiveClassesVideoModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;

    }

    @NonNull
    @Override
    public LiveClassesVideoAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.live_layout_class_list, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveClassesVideoAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));

       /* final LiveClassesVideoModel getPreEaxmModel = getPreEaxmModels.get(position);
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String user_id = preferences.getString("user_id", "");
        try {
            //dateformat
            String datef = getPreEaxmModel.getClassLiveDate();

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(datef);
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            holder.txt_date.setText(dt1.format(date));
        }catch (Exception ex)
        {
            Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
        }
        //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.banner_image);

        holder.txt_start.setText(getPreEaxmModel.getName());
        holder.txt_liveclass.setText(getPreEaxmModel.getMessage());
        if (getPreEaxmModel.getFeeStatus().equals("Free"))
        {
            holder.txt_status.setText("Free");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red_500));
        }
        else {
            holder.txt_status.setText("***");
            holder.txt_status.setLetterSpacing(0.2f);
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.theme_purple));
        }

        // holder.img.setImageResource(images.get(position));
        holder.card_liveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UserId", user_id);
                    // jsonObject.put("UserId","8853616625");
                    jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());

                    getAllPaid(jsonObject,getPreEaxmModel.getCourseId());
                    getSingleClass(getPreEaxmModel.getLivClassId());
                }catch ( JSONException ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
//        Intent intent=new Intent(context, YoutubePlayerLiveClasses.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//
//        intent.putExtra("LIVE_CLASS_ID",getPreEaxmModel.getLivClassId());
//        intent.putExtra("LICLASS_NAME",getPreEaxmModel.getName());
//        intent.putExtra("VIDEO_URL",getPreEaxmModel.getLiVideoLink1());
//        intent.putExtra("PDF_URL",getPreEaxmModel.getPdf());
//        intent.putExtra("MESSAGELIV",getPreEaxmModel.getMessage());
//        intent.putExtra("COURSE_ID",getPreEaxmModel.getCourseId());
//        intent.putExtra("FEESTATUS",getPreEaxmModel.getFeeStatus());
//        context.startActivity(intent);
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return getPreEaxmModels.size();
//        if (searchList.size() > 0) {
//            return searchList.size();
//        } else {
//            return 0;
//        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {

        private ImageView banner_image;
        private TextView txt_start,txt_liveclass,txt_date,txt_status;
        CardView card_liveVideo;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            /*txt_start=itemView.findViewById(R.id.txt_start);
            txt_liveclass=itemView.findViewById(R.id.txt_liveclass);
            txt_date=itemView.findViewById(R.id.txt_date);
            banner_image=itemView.findViewById(R.id.banner_image);
            card_liveVideo=itemView.findViewById(R.id.card_liveVideo);
            txt_status=itemView.findViewById(R.id.txt_status);*/


        }
        // each data item is just a string in this case

    }
    public void getAllPaid(JSONObject jsonObject,String courseid){
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

//    public  void filter(String search)
//    {
//        searchList.clear();
//        search=search.toLowerCase();
//        for (GetPreEaxmModel model:getPreEaxmModels)
//        {
//            if (model.getName().toLowerCase().contains(search))
//                searchList.add(model);
//
//            //Toast.makeText(context, "list"+getPreEaxmModels.size(), Toast.LENGTH_SHORT).show();
//            notifyDataSetChanged();
//        }
//
//    }


    private void getSingleClass(String livId) {
        String strUrl= BaseUrl.GETALLSINGLECLASS+"/"+livId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allliveClass", response);
                try {


                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                            String LivClassId = jsonObject1.getString("LivClassId");
                            String CourseId = jsonObject1.getString("CourseId");

                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Message = jsonObject1.getString("Message");
                            String Pic1 = jsonObject1.getString("Pic1");

                            String LiVideoLink1 = jsonObject1.getString("LiVideoLink1");
                            String LiVideoLink2 = jsonObject1.getString("LiVideoLink2");
                            String Pdf = jsonObject1.getString("Pdf");
                            String ClassLiveDate = jsonObject1.getString("ClassLiveDate");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Type = jsonObject1.getString("Type");
                            String Status = jsonObject1.getString("Status");

                            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("LivClassId", LivClassId);
                            editor.putString("CourseId", CourseId);

                            editor.putString("NameLiv", Name);
                            editor.putString("LiVideoLink1", LiVideoLink1);
                            editor.putString("StatusLive", Status);

                            editor.apply();
                            Intent intent=new Intent(context, YoutubePlayerLiveClasses.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                            intent.putExtra("LIVE_CLASS_ID",LivClassId);
                            intent.putExtra("LICLASS_NAME",Name);
                            intent.putExtra("VIDEO_URL",LiVideoLink1);
                            intent.putExtra("PDF_URL",Pdf);
                            intent.putExtra("MESSAGELIV",Message);
                            intent.putExtra("COURSE_ID",CourseId);
                            intent.putExtra("FEESTATUS",FeeStatus);
                            context.startActivity(intent);


                        }

                    }else
                    {
                       // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //  progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
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
