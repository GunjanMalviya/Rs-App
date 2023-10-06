package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rankerspoint.academy.Activity.HomeNotesPdf;
import com.rankerspoint.academy.Activity.MockTestSeries;
import com.rankerspoint.academy.Adapter.AdapterImageSlider;
import com.rankerspoint.academy.Adapter.FreeLiveClassAdapter;
import com.rankerspoint.academy.Adapter.FreeLiveClassVideoAdapter;
import com.rankerspoint.academy.Adapter.MentorPostHomeAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSliderImagebyCateModel;
import com.rankerspoint.academy.Model.HomeVideoFreeTopModel;
import com.rankerspoint.academy.Model.HomeVideoSeriesModel;
import com.rankerspoint.academy.Model.MentorPostHomeModel;
import com.rankerspoint.academy.Activity.GetHomeAllResults;
import com.rankerspoint.academy.Activity.LiveClassesVideo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.rankerspoint.academy.Activity.DailyGK;
import com.rankerspoint.academy.Activity.MentorPost;
import com.rankerspoint.academy.Activity.VideoSeriesList;
import com.rankerspoint.academy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class HomeeeFragment extends Fragment {
    private Runnable runnable = null;
    private Handler handler = new Handler();
    CardView card_doubts,card_live_classes,card_mentorPost,card_notesPdf,card_examInfo,card_less,card_more,mock_text_card,card_video_series,card_current_affair;
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    private LinearLayout layout_dots,llaout_three;
    View view,view_badge;
    LinearLayout lnr_layout;
    List<MentorPostHomeModel> getHomeCategorymodels=new ArrayList<>();
    MentorPostHomeAdapter getHomeCategoryNotesPdfAdapter;
    RecyclerView recycler_free_video_home,recycler_free_video_homem,recy_exam_series;
    Context thiscontext;
   private AdapterImageSlider adapterImageSlider;
    private List<GetSliderImagebyCateModel> getSliderImagebyCateModels = new ArrayList<>();
    private List<HomeVideoFreeTopModel> getHomeSliderimageTopVideo = new ArrayList<>();
    List<HomeVideoSeriesModel> getAllSyllabusCatModels=new ArrayList<>();
    String categoryid="";
    FreeLiveClassAdapter freeLiveClassAdapter;
    FreeLiveClassVideoAdapter freeLiveClassVideoAdapter;
    int currentPage = 0;
    int NUM_PAGES = 0;
    Timer timer;
    TextView txt_mentorpost;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_homeee, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.class_spinner);

        // Spinner click listener
//        spinner.setOnItemSelectedListener(getActivity().getApplicationContext());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Scheduled Class");
        categories.add("Today's Class");
        categories.add("Scheduled Class");
        categories.add("Today's Class");
        categories.add("Scheduled Class");
        categories.add("Today's Class");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        /*thiscontext=container.getContext();
        llaout_three=view.findViewById(R.id.llaout_three);
        card_doubts=view.findViewById(R.id.card_doubts);
        card_mentorPost=view.findViewById(R.id.card_mentorPost);
        card_notesPdf=view.findViewById(R.id.card_notesPdf);
        card_current_affair=view.findViewById(R.id.card_current_affair);
        card_video_series=view.findViewById(R.id.card_video_series);
        card_examInfo=view.findViewById(R.id.card_examInfo);
        card_live_classes=view.findViewById(R.id.card_live_classes);
        txt_mentorpost=view.findViewById(R.id.txt_mentorpost);
        mock_text_card=view.findViewById(R.id.mock_text_card);
        recy_exam_series=view.findViewById(R.id.recy_exam_series);
        recycler_free_video_homem=view.findViewById(R.id.recycler_free_video_homem);
        card_examInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, GetHomeAllResults.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        card_mentorPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, MentorPost.class);
                intent.putExtra("CAT_ID",categoryid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        card_live_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, LiveClassesVideo.class);
                intent.putExtra("CAT_ID",categoryid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        mock_text_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, MockTestSeries.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        card_notesPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, HomeNotesPdf.class);
                intent.putExtra("CAT_ID",categoryid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        card_video_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, VideoSeriesList.class);
                intent.putExtra("CAT_ID",categoryid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        card_current_affair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, DailyGK.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        txt_mentorpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(thiscontext, MentorPost.class);
                intent.putExtra("CAT_ID",categoryid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                thiscontext.startActivity(intent);
            }
        });
        recycler_free_video_home=view.findViewById(R.id.recycler_free_video_home);
//        view_badge=view.findViewById(R.id.view_badge);
//        BadgeView homeimageviewbadge = new BadgeView(thiscontext,view_badge);
//        homeimageviewbadge.setTextSize(6.8f);
//        homeimageviewbadge.setTextColor(Color.parseColor("#ffffff"));
//        homeimageviewbadge.setBadgeBackgroundColor(getResources().getDrawable(R.drawable.badge));
//        homeimageviewbadge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        homeimageviewbadge.setText("10");
//        homeimageviewbadge.setHeight(34);
//        homeimageviewbadge.setTypeface(homeimageviewbadge.getTypeface(), Typeface.BOLD);
//        homeimageviewbadge.setWidth(34);
//        homeimageviewbadge.setGravity(Gravity.CENTER);
//        homeimageviewbadge.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);


        //homeimageviewbadge.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //homeimageviewbadge.setGravity(View.TEXT_ALIGNMENT_CENTER);
       // homeimageviewbadge.show();

        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(thiscontext, LinearLayoutManager.HORIZONTAL, false);
        recycler_free_video_home.setLayoutManager(linearLayoutManager1);

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(thiscontext, LinearLayoutManager.HORIZONTAL, false);

        recycler_free_video_homem.setLayoutManager(linearLayoutManager2);
        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recycler_free_video_home);

        recy_exam_series.setLayoutManager(new GridLayoutManager(thiscontext,1));
        recy_exam_series.setNestedScrollingEnabled(false);
        card_less=view.findViewById(R.id.card_less);
        card_more=view.findViewById(R.id.card_more);
        card_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llaout_three.setVisibility(View.VISIBLE);
                card_more.setVisibility(View.GONE);
                card_doubts.setVisibility(View.VISIBLE);
            }
        });
        card_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llaout_three.setVisibility(View.GONE);
                card_more.setVisibility(View.VISIBLE);
                card_doubts.setVisibility(View.GONE);
            }
        });
        progressDialog = new ProgressDialog(thiscontext,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        initComponent();*/
        return view;
    }
    private void getFreeLiveClasses(String categoryid) {
        getSliderImagebyCateModels.clear();
       // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL= BaseUrl.GETALLLIVETOPCATEGROY+"/"+categoryid.trim();;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getfreelivevieo", response);
                try {
                    getAllSyllabusCatModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getfreelive",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getfreelive_abc", jsonObject1.toString());
                            String LivClassId = jsonObject1.getString("LivClassId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String Message = jsonObject1.getString("Message");

                            String Pic1 = jsonObject1.getString("Pic1");
                            String Type = jsonObject1.getString("Type");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String LiVideoLink1 = jsonObject1.getString("LiVideoLink1");


                            HomeVideoSeriesModel freeVideoModel = new HomeVideoSeriesModel(LivClassId,Pic1,Message,CourseId,Type,LiVideoLink1,FeeStatus);
                                getAllSyllabusCatModels.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        freeLiveClassAdapter=new FreeLiveClassAdapter(thiscontext,getAllSyllabusCatModels);
                        recycler_free_video_home.setAdapter(freeLiveClassAdapter);


                        // displaying selected image first

                    }else
                    {
                        //Toast.makeText(thiscontext, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void initComponent() {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "");
        Log.d("categoryidd",categoryid);
       layout_dots = (LinearLayout) view.findViewById(R.id.layout_dots);
       viewPager = (ViewPager) view.findViewById(R.id.pager);

          getSliderImage(categoryid);
          getFreeLiveClasses(categoryid);
        getFreeLiveVideo(categoryid);
        getAllSyllabusData(categoryid);
    }
//    public void addBottomDots(LinearLayout layout_dots, int size, int current) {
//        ImageView[] dots = new ImageView[size];
//
//        layout_dots.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new ImageView(getActivity());
//            int width_height = 15;
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
//            params.setMargins(10, 10, 10, 10);
//            dots[i].setLayoutParams(params);
//            dots[i].setImageResource(R.drawable.shape_circle);
//            dots[i].setColorFilter(ContextCompat.getColor(thiscontext, R.color.overlay_dark_20), PorterDuff.Mode.SRC_ATOP);
//            layout_dots.addView(dots[i]);
//        }
//
//        if (dots.length > 0) {
//            dots[current].setColorFilter(ContextCompat.getColor(thiscontext, R.color.theme_purple), PorterDuff.Mode.SRC_ATOP);
//        }
//    }

    private void getSliderImage(String categoryid) {
        getSliderImagebyCateModels.clear();
        String SUBCATALL= BaseUrl.GETIMAGESLIDER+"/"+categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSliderImage", response);
                try {
                    getSliderImagebyCateModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getSliderImage_2",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getSliderImage_abc", jsonObject1.toString());
                            String ImageId = jsonObject1.getString("ImageId");
                            String ImagePath = jsonObject1.getString("ImagePath");
                            String ImageCategory = jsonObject1.getString("ImageCategory");
                            String ImageSubCategory = jsonObject1.getString("ImageSubCategory");
                            String Heading = jsonObject1.getString("Heading");
                            String Details = jsonObject1.getString("Details");
                            String ImageType = jsonObject1.getString("ImageType");
                            String ImagePriority = jsonObject1.getString("ImagePriority");
                            String LinkType = jsonObject1.getString("LinkType");
                            String LinkId = jsonObject1.getString("LinkId");


                            if(ImageType.equals("CatSlider")) {

                                GetSliderImagebyCateModel getPreEaxmModel = new GetSliderImagebyCateModel(ImageId, ImagePath, ImageCategory, ImageSubCategory, Heading, Details, ImageType, ImagePriority, LinkType, LinkId);

                                getSliderImagebyCateModels.add(getPreEaxmModel);
                            }


                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        adapterImageSlider = new AdapterImageSlider(getActivity(),getSliderImagebyCateModels);

                        viewPager.setAdapter(adapterImageSlider);



                        // displaying selected image first
                        viewPager.setCurrentItem(0);
                        bottomProgressDots( 0);
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int pos) {
                                bottomProgressDots(pos);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
                        startAutoSlider(adapterImageSlider.getCount());
                    }else
                    {

                        //Toast.makeText(thiscontext, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) view.findViewById(R.id.layout_dots);
        ImageView[] dots = new ImageView[getSliderImagebyCateModels.size()];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(thiscontext);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            //dots[current_index].setColorFilter(getResources().getColor(R.color.theme_purple), PorterDuff.Mode.SRC_IN);
            dots[current_index].setColorFilter(ContextCompat.getColor(thiscontext, R.color.theme_purple), PorterDuff.Mode.SRC_ATOP);


        }
    }

    private void getFreeLiveVideo(String categoryid) {
        getHomeSliderimageTopVideo.clear();
     // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL= BaseUrl.GETALLTOPICVIDEOSERIES+"/"+"RecentVideo"+"/"+categoryid.trim();
        Log.d("urlimageslider:-",SUBCATALL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getfreelivevieo", response);
                try {
                    getHomeSliderimageTopVideo.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getfreelive",jsonObject.toString());
                    if (jsonObject.length()>0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getfreelive_abc", jsonObject1.toString());
//                            String LivClassId = jsonObject1.getString("LivClassId");
                            String ImageId = jsonObject1.getString("ImageId");
                            String ImagePath = jsonObject1.getString("ImagePath");

                            String Heading = jsonObject1.getString("Heading");
                            String Details = jsonObject1.getString("Details");
                            String Extra1 = jsonObject1.getString("Extra1");
                            String LinkId = jsonObject1.getString("LinkId");
                            String Extra5 = jsonObject1.getString("Extra5");


                            HomeVideoFreeTopModel freeVideoModel = new HomeVideoFreeTopModel(ImageId,ImagePath,Heading,Details,Extra1,LinkId,Extra5);
                            getHomeSliderimageTopVideo.add(freeVideoModel);

                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        freeLiveClassVideoAdapter=new FreeLiveClassVideoAdapter(thiscontext,getHomeSliderimageTopVideo);
                        recycler_free_video_homem.setAdapter(freeLiveClassVideoAdapter);


                        // displaying selected image first

                    }else
                    {
                        //Toast.makeText(thiscontext, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }
    private void getAllSyllabusData( String categoryid) {
        getHomeCategorymodels.clear();
        String strUrl= BaseUrl.GETTOP10MENTORPOST+"/"+categoryid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("allliveClass", response);
                try {
                    getHomeCategorymodels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("detailspdf",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);

                            String PostId = jsonObject1.getString("PostId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String UserId = jsonObject1.getString("UserId");
                            String UserName = jsonObject1.getString("UserName");
                            String UserPic = jsonObject1.getString("UserPic");

                            String Title = jsonObject1.getString("Title");
                            String PostType = jsonObject1.getString("PostType");
                            String Pic1 = jsonObject1.getString("Pic1");
                            String Link1 = jsonObject1.getString("Link1");
                            String Link2 = jsonObject1.getString("Link2");
                            String AddDate = jsonObject1.getString("AddDate");

                            String date=AddDate.substring(0,10);
                            String UpVotes = jsonObject1.getString("UpVotes");
                            String DownVotes = jsonObject1.getString("DownVotes");
                            String Comments = jsonObject1.getString("Comments");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Href1 = jsonObject1.getString("Href1");
                            String Href2 = jsonObject1.getString("Href2");

                            MentorPostHomeModel getAllSyllabusCatModel = new MentorPostHomeModel(PostId,CategoryId,SubCategoryId,CourseId,UserId,UserName,UserPic,Title,PostType,Pic1,Link1,Link2,date,UpVotes,DownVotes,Comments,FeeStatus,Href1,Href2);

                            getHomeCategorymodels.add(getAllSyllabusCatModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getHomeCategoryNotesPdfAdapter = new MentorPostHomeAdapter(thiscontext,getHomeCategorymodels);
                        recy_exam_series.setAdapter(getHomeCategoryNotesPdfAdapter);
                    }else
                    {
                       // lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(thiscontext, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(thiscontext);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}