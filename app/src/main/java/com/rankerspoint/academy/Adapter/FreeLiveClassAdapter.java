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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Activity.ExoYoutPlayer;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.EmbadedPlayer;
import com.rankerspoint.academy.Activity.PaymentCheckOut;
import com.rankerspoint.academy.Activity.SingleCourseDetails;

import com.rankerspoint.academy.Model.HomeVideoSeriesModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeLiveClassAdapter extends RecyclerView.Adapter<FreeLiveClassAdapter.BannerImage> {


    private Context context;
    List<HomeVideoSeriesModel> freeVideoModels;

    public FreeLiveClassAdapter(Context context,List<HomeVideoSeriesModel>  freeVideoModels) {
        this.context = context;
        this.freeVideoModels = freeVideoModels;
    }

    @NonNull
    @Override
    public FreeLiveClassAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.image_layout_today_live, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeLiveClassAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));
        final HomeVideoSeriesModel freeVideoModel = freeVideoModels.get(position);

      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String user_id = preferences.getString("user_id", "");
        String Status = preferences.getString("Status", "");

        Picasso.with(context).load(BaseUrl.BANN_IMG_URL + freeVideoModel.getImagePath()).into(holder.img);


        holder.card_liveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FeeStatusMentor", freeVideoModel.getStatus());
                editor.apply();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UserId", user_id);
                    // jsonObject.put("UserId","8853616625");
                    jsonObject.put("PurchasedServiceId", freeVideoModel.getDetails());

                    getAllPaid(jsonObject);
                }catch ( JSONException ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                getSingleCourse(freeVideoModel.getDetails());
                if (freeVideoModel.getStatus().equals("Free")) {
                    if (freeVideoModel.getLinkType().equalsIgnoreCase("Youtube")) {
                        Intent intent = new Intent(context, ExoYoutPlayer.class);
                        intent.putExtra("VIDEOURL", freeVideoModel.getLinkId().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    } else if (freeVideoModel.getLinkType().equalsIgnoreCase("Normal")) {
                        Intent intent = new Intent(context, EmbadedPlayer.class);
                        intent.putExtra("TITLE", freeVideoModel.getHeading());
                        intent.putExtra("CONTENTID", freeVideoModel.getImageId());
                        intent.putExtra("PIC", freeVideoModel.getImagePath());
                        intent.putExtra("VIDEOURLNORMAL", freeVideoModel.getLinkId().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    }
                }else {
                    if (freeVideoModel.getStatus().equals("Paid"))
                    {
                        if (Status.equals("True"))
                        {
                            if (freeVideoModel.getLinkType().equalsIgnoreCase("Youtube")) {
                                Intent intent = new Intent(context, ExoYoutPlayer.class);
                                intent.putExtra("VIDEOURL", freeVideoModel.getLinkId().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                context.startActivity(intent);
                            } else if (freeVideoModel.getLinkType().equalsIgnoreCase("Normal")) {
                                Intent intent = new Intent(context, EmbadedPlayer.class);
                                intent.putExtra("TITLE", freeVideoModel.getHeading());
                                intent.putExtra("CONTENTID", freeVideoModel.getImageId());
                                intent.putExtra("PIC", freeVideoModel.getImagePath());

                                intent.putExtra("VIDEOURLNORMAL", freeVideoModel.getLinkId().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                                context.startActivity(intent);
                            }
                        }else if (Status.equals(""))
                        {
                            Intent intent=new Intent(context, SingleCourseDetails.class);
                            intent.putExtra("COURSE_ID",freeVideoModel.getDetails());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            context.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(context, PaymentCheckOut.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });
     holder.txt_image.setText(freeVideoModel.getHeading());
       // holder.img.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        if (freeVideoModels.size() > 0) {
            return freeVideoModels.size();
        } else {
            return 0;
        }

    }

    public static class BannerImage extends RecyclerView.ViewHolder {
        private ImageView img;
        CardView card_liveVideo;
        TextView txt_image;
        public BannerImage(@NonNull View itemView) {
            super(itemView);

           img=itemView.findViewById(R.id.banner_image);
            txt_image=itemView.findViewById(R.id.txt_image);
            card_liveVideo=itemView.findViewById(R.id.card_liveVideo);
        }
        // each data item is just a string in this case

    }
    public void getAllPaid(JSONObject jsonObject){
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

                      //  Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
