package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.AdapterImageSlider;
import com.rankerspoint.academy.Adapter.FreeLiveClassAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSliderImagebyCateModel;
import com.rankerspoint.academy.Model.HomeVideoSeriesModel;
import com.rankerspoint.academy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrePareFragment extends Fragment {

    CardView card_notesPdf,card_examInfo,card_lbl_daily;
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    private LinearLayout layout_dots,llaout_three;
    View view,view_badge;
    LinearLayout lnr_layout;

    LinearLayout lllout_daily;
    RecyclerView recycler_free_video_home;


    private AdapterImageSlider adapterImageSlider;
    private List<GetSliderImagebyCateModel> getSliderImagebyCateModels = new ArrayList<>();
    private List<HomeVideoSeriesModel> freeVideoModels = new ArrayList<>();
    String categoryid="";
    FreeLiveClassAdapter freeLiveClassAdapter;
    TextView textCartItemCount;
    int mCartItemCount = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pre_pare, container, false);
//        llaout_three=view.findViewById(R.id.llaout_three);
        card_lbl_daily=view.findViewById(R.id.card_lbl_daily);
        lllout_daily=view.findViewById(R.id.lllout_daily);
       // view_badge=view.findViewById(R.id.view_badge);
//        BadgeView homeimageviewbadge = new BadgeView(getContext(),view_badge);
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
        card_notesPdf=view.findViewById(R.id.card_notesPdf);
        card_examInfo=view.findViewById(R.id.card_examInfo);


        recycler_free_video_home=view.findViewById(R.id.recycler_free_video_home);


        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_free_video_home.setLayoutManager(linearLayoutManager1);

        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recycler_free_video_home);


        progressDialog = new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        initComponent();
        return view;
    }
//    private void getFreeLiveClasses() {
//        freeVideoModels.clear();
//        String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("getfreelivevieo", response);
//                try {
//                    freeVideoModels.clear();
//                    JSONArray jsonObject = new JSONArray(response);
//                    Log.d("getfreelive",jsonObject.toString());
//                    if (jsonObject.length()>0) {
//                        for (int i = 0; i < jsonObject.length(); i++) {
//                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
//                            Log.d("getfreelive_abc", jsonObject1.toString());
//                            String VideoId = jsonObject1.getString("VideoId");
//                            String CategoryId = jsonObject1.getString("CategoryId");
//                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
//                            String Name = jsonObject1.getString("Name");
//                            String Details = jsonObject1.getString("Details");
//                            String Pic = jsonObject1.getString("Pic");
//                            String VideoLink1 = jsonObject1.getString("VideoLink1");
//                            String FeeStatus = jsonObject1.getString("FeeStatus");
//                            String Status = jsonObject1.getString("Status");
//                            String Type = jsonObject1.getString("Type");
//                            String CourseId = jsonObject1.getString("CourseId");
//
//                            FreeVideoModel freeVideoModel = new FreeVideoModel(VideoId,CategoryId,SubCategoryId,CourseId,Name,Details,Type,Pic,VideoLink1,FeeStatus,Status);
//                            freeVideoModels.add(freeVideoModel);
//
//                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
//                            // getExamCategoryAdapter.notifyDataSetChanged();
//                        }
//                        freeLiveClassAdapter=new FreeLiveClassAdapter(getContext(),freeVideoModels);
//                        recycler_free_video_home.setAdapter(freeLiveClassAdapter);
//
//
//                        // displaying selected image first
//
//                    }else
//                    {
//                        Toast.makeText(getContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.getCache().clear();
//        requestQueue.add(stringRequest);
//    }

    private void initComponent() {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "CAT_ID");
        layout_dots = (LinearLayout) view.findViewById(R.id.layout_dots);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        getSliderImage(categoryid);
//        getFreeLiveClasses();
    }
    public void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(getContext(), R.color.overlay_dark_20), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(getContext(), R.color.theme_purple), PorterDuff.Mode.SRC_ATOP);
        }
    }

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
                        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int pos) {
                                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });

                    }else
                    {

                       // Toast.makeText(getContext(), "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


}