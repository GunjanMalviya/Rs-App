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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Activity.ExoYoutPlayer;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.GoExamMentorPost;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.rankerspoint.academy.Activity.EmbadedPlayer;
import com.rankerspoint.academy.Activity.MentorCommentHome;
import com.rankerspoint.academy.Activity.MentorPostSingleTestDescription;
import com.rankerspoint.academy.Activity.PdfReaderFile;
import com.rankerspoint.academy.Activity.YoutubePlayerLiveClasses;
import com.rankerspoint.academy.Model.MentorPostHomeModel;
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

public class MentorPostHomeAdapter extends RecyclerView.Adapter<MentorPostHomeAdapter.BannerImage> {
    List<MentorPostHomeModel> getPreEaxmModels;
    Context context;

    public MentorPostHomeAdapter(Context context, List<MentorPostHomeModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
    }

    @NonNull
    @Override
    public MentorPostHomeAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mentor_post_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorPostHomeAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final MentorPostHomeModel getPreEaxmModel = getPreEaxmModels.get(position);
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

       String user_id = preferences.getString("user_id", "");

        try {
            //dateformat
            String datef = getPreEaxmModel.getAddDate();

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(datef);
            // *** same for the format String below
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            holder.txt_time.setText(dt1.format(date));
        }catch (Exception ex)
        {
            Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
        }
       //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
       Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getUserPic()).into(holder.userImage_imageView);
       Picasso.with(context).load(BaseUrl.BANN_IMG_URL + getPreEaxmModel.getPic1()).into(holder.banner_image);

holder.txt_userNm.setText(getPreEaxmModel.getUserId());
holder.txt_title.setText(getPreEaxmModel.getTitle());
holder.txt_comments.setText(" comments");
holder.txt_upvotes.setText(" upvotes");

if (getPreEaxmModel.getPostType().equals("Quiz"))
{
    holder.lllout_quiz.setVisibility(View.VISIBLE);
    holder.text_quiz_title.setText(getPreEaxmModel.getPostType());
    holder.banner_image.setVisibility(View.GONE);
    try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserId", user_id);
        // jsonObject.put("UserId","8853616625");
        jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());

        getAllPaid(jsonObject,getPreEaxmModel.getCourseId());

    }catch ( JSONException ex)
    {
        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }
    holder.quiz_start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("FeeStatusMentor",getPreEaxmModel.getFeeStatus());

            editor.apply();
            Intent intent=new Intent(context, GoExamMentorPost.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("EXAM_ID",getPreEaxmModel.getLink1());
//            intent.putExtra("DURATION","00");
//            intent.putExtra("EXAMNAME",getPreEaxmModel.getTitle());

            context.startActivity(intent);
        }
    });
}
        if (getPreEaxmModel.getPostType().equals("Text"))
        {

            holder.lllout_quiz.setVisibility(View.GONE);

            holder.banner_image.setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserId", user_id);
                // jsonObject.put("UserId","8853616625");
                jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());

                getAllPaid(jsonObject,getPreEaxmModel.getCourseId());

            }catch ( JSONException ex)
            {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            holder.banner_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(context, MentorPostSingleTestDescription.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("DATE",getPreEaxmModel.getAddDate());
                    intent.putExtra("TITLE",getPreEaxmModel.getTitle());
                    intent.putExtra("LINK1",getPreEaxmModel.getLink1());
                    intent.putExtra("LINK2",getPreEaxmModel.getLink2());
                    intent.putExtra("IMAGE",getPreEaxmModel.getPic1());
                    intent.putExtra("FEESTATUS",getPreEaxmModel.getFeeStatus());
                    intent.putExtra("HREF1",getPreEaxmModel.getHref1());
                    context.startActivity(intent);
                }
            });
        }
        if (getPreEaxmModel.getPostType().equals("Pdf"))
        {

            holder.lllout_quiz.setVisibility(View.GONE);

            holder.banner_image.setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserId", user_id);
                // jsonObject.put("UserId","8853616625");
                jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());

                getAllPaid(jsonObject,getPreEaxmModel.getCourseId());

            }catch ( JSONException ex)
            {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            holder.banner_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("FeeStatusMentor",getPreEaxmModel.getFeeStatus());

                    editor.apply();
                    Intent intent=new Intent(context, PdfReaderFile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    intent.putExtra("PDFURL",getPreEaxmModel.getLink1());
                    intent.putExtra("PDFURL2",getPreEaxmModel.getLink2());
                    intent.putExtra("CONTENTID",getPreEaxmModel.getPostId());
                    intent.putExtra("PIC",getPreEaxmModel.getPic1());

                    intent.putExtra("PDFTITLE",getPreEaxmModel.getTitle());

                    context.startActivity(intent);
                }
            });
        }

        if (getPreEaxmModel.getPostType().equals("Video"))
        {

            holder.lllout_quiz.setVisibility(View.GONE);

            holder.banner_image.setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserId", user_id);
                // jsonObject.put("UserId","8853616625");
                jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());

                getAllPaid(jsonObject,getPreEaxmModel.getCourseId());
                getSingleClass(getPreEaxmModel.getCourseId());

            }catch ( JSONException ex)
            {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            holder.banner_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getPreEaxmModel.getLink2().equalsIgnoreCase("Youtube")) {
                        SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("FeeStatusMentor",getPreEaxmModel.getFeeStatus());

                        editor.apply();
                        Intent intent = new Intent(context, ExoYoutPlayer.class);
                        intent.putExtra("VIDEOURL", getPreEaxmModel.getLink1());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);
                    } else if (getPreEaxmModel.getLink2().equalsIgnoreCase("Normal")) {
                        SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("FeeStatusMentor",getPreEaxmModel.getFeeStatus());

                        editor.apply();
                        Intent intent = new Intent(context, EmbadedPlayer.class);
                        intent.putExtra("TITLE", getPreEaxmModel.getTitle());
                        intent.putExtra("CONTENTID", getPreEaxmModel.getPostId());
                        intent.putExtra("PIC", getPreEaxmModel.getPic1());
                        intent.putExtra("VIDEOURLNORMAL", getPreEaxmModel.getLink1());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(intent);

                    }
                }
            });
        }
holder.llout_comments.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context, MentorCommentHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("PostId",getPreEaxmModel.getPostId());
        context.startActivity(intent);
    }
});
        // holder.img.setImageResource(images.get(position));
//holder.card_liveVideo.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("UserId", user_id);
//            // jsonObject.put("UserId","8853616625");
//            jsonObject.put("PurchasedServiceId", getPreEaxmModel.getCourseId());
//
//            getAllPaid(jsonObject,getPreEaxmModel.getCourseId());
//            getSingleClass(getPreEaxmModel.getLivClassId());
//        }catch ( JSONException ex)
//        {
//            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//});

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


        private ImageView userImage_imageView,banner_image;
        private TextView txt_userNm,txt_time,txt_title,txt_upvotes,txt_comments,text_quiz_title,quiz_start;
CardView card_liveVideo;
LinearLayout llout_upvotes,llout_comments,lllout_quiz;
        public BannerImage(@NonNull View itemView) {
            super(itemView);
            userImage_imageView=itemView.findViewById(R.id.userImage_imageView);
            banner_image=itemView.findViewById(R.id.banner_image);
            txt_userNm=itemView.findViewById(R.id.txt_userNm);
            txt_time=itemView.findViewById(R.id.txt_time);

            txt_title=itemView.findViewById(R.id.txt_title);
            txt_upvotes=itemView.findViewById(R.id.txt_upvotes);
            txt_comments=itemView.findViewById(R.id.txt_comments);
            llout_upvotes=itemView.findViewById(R.id.llout_upvotes);
            llout_comments=itemView.findViewById(R.id.llout_comments);
            lllout_quiz=itemView.findViewById(R.id.lllout_quiz);
            text_quiz_title=itemView.findViewById(R.id.text_quiz_title);
            quiz_start=itemView.findViewById(R.id.quiz_start);


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

                        //Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
