package com.rankerspoint.academy.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.Adapter.AdapterImageSlider;
import com.rankerspoint.academy.Adapter.BannerAdapter;
import com.rankerspoint.academy.Adapter.GetExamSubCategoryMoreClassesAdapter;
import com.rankerspoint.academy.Adapter.GetExamSubCourseAdapter;
import com.rankerspoint.academy.Adapter.UpcomingCourseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetSliderImagebyCateModel;
import com.rankerspoint.academy.Model.GetSubCourseModel;
import com.rankerspoint.academy.Model.GetSubPreEaxmModel;
import com.rankerspoint.academy.Model.UpcomingClassesModel;
import com.rankerspoint.academy.Activity.ViewAllCouse_SubCategory;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseFragment extends Fragment {
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private RecyclerView recycler_image_slider;
    ArrayList<Integer> array_image = new ArrayList<Integer>();
    private BannerAdapter bannerAdapter;
    RecyclerView recy_subpreexam;
    GetExamSubCategoryMoreClassesAdapter getExamSubCategoryAdapter;
    String categoryid, cityName, Mobile1, Email;
    RecyclerView recy_subCourse;
    LinearLayout talk_lbt_out, llout_viewAll;
    GetExamSubCourseAdapter getExamSubCourseAdapter;
    String purchaseid = "";
    TextView talk_help, faq_txt_help, tvSubCategoryHeader;
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    Context context;
    View view;
    private AdapterImageSlider adapterImageSlider;
    private UpcomingCourseAdapter upcomingCourseAdapter;
    private List<GetSubPreEaxmModel> getPreEaxmModels = new ArrayList<>();
    private List<GetSubCourseModel> getSubCourseModels = new ArrayList<>();
    private List<UpcomingClassesModel> getUpcomingCouseModels = new ArrayList<>();
    private List<GetSliderImagebyCateModel> getSliderImagebyCateModels = new ArrayList<>();
    private String subCateId;
    private String catName;
    private TextView tvCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course, container, false);
        context = container.getContext();
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tvCount = view.findViewById(R.id.tv_count);
        talk_help = view.findViewById(R.id.talk_help);
        recy_subpreexam = view.findViewById(R.id.recy_moreclasses);
        recy_subCourse = view.findViewById(R.id.recy_subCourse);
        recy_subpreexam.setNestedScrollingEnabled(false);
        recy_subCourse.setNestedScrollingEnabled(false);
        llout_viewAll = view.findViewById(R.id.llout_viewAll);
        talk_lbt_out = view.findViewById(R.id.talk_lbt_out);
        faq_txt_help = view.findViewById(R.id.faq_txt_help);
        adapterImageSlider = new AdapterImageSlider(getActivity(), new ArrayList<GetSliderImagebyCateModel>());
        talk_lbt_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsappmessage();
            }
        });
        recycler_image_slider = view.findViewById(R.id.recycler_image_slider);
        // recyclerImages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        recycler_image_slider.setLayoutManager(linearLayoutManager1);
//
//        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
//        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(recycler_image_slider);
        recycler_image_slider.setLayoutManager(new GridLayoutManager(context, 1));
        recycler_image_slider.setNestedScrollingEnabled(false);
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "CAT_ID");
        cityName = preferences.getString("cityName", "cityName");
        Mobile1 = preferences.getString("Mobile1", "Mobile1");
        Email = preferences.getString("Email", "Email");
        subCateId = preferences.getString("SubCatId", "");
        catName = preferences.getString("SubCatName", "");

        talk_help.setText("Need help with " + cityName + " Courses Subscription ?");
        String text_shipment_email = getResources().getString(R.string.fcissue)
                + " <font color='"
                + getResources().getColor(R.color.theme_purple) + "'>" + Email
                + "</font>";
        faq_txt_help.setText(Html.fromHtml(text_shipment_email),
                TextView.BufferType.SPANNABLE);
//        tvSubCategoryHeader.setText(catName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//linearLayoutManager.setReverseLayout(true);
        recy_subpreexam.setLayoutManager(linearLayoutManager);
//        recy_subpreexam.scrollToPosition(getPreEaxmModels.size()-1);
        llout_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllCouse_SubCategory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });
        getSubPreEaxmModels(subCateId.trim());
//        getSubMoreClassesModels();
        initComponent();
//        subcategoryid = preferences.getString("SUB_CAT_ID","SUB_CAT_ID");
//        Log.d("subcategoryid",subcategoryid);
        recy_subCourse.setLayoutManager(new GridLayoutManager(context, 1));
        return view;
    }

    private void getSubMoreClassesModels() {
        getPreEaxmModels.clear();
        String SUBCATALL = BaseUrl.GETSUBCATEGORY + "/" + categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSubMoreClassesModels", response);
                try {
                    getPreEaxmModels.clear();
                    SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("SubCatId", "");
                    editor.apply();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getSubMoreClassesModels", jsonObject.toString());
                    if (!response.isEmpty()) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getSubMoreClassesModels", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            GetSubPreEaxmModel getPreEaxmModel = new GetSubPreEaxmModel(CategoryId, SubCategoryId, Name, Details, Pic, Logo);
                            getPreEaxmModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        List<GetSubPreEaxmModel> tempList = new ArrayList<>();
                        for (int i = 0; i < getPreEaxmModels.size(); i++) {
                            if(catName.trim().equalsIgnoreCase(getPreEaxmModels.get(i).getName().trim())){
                                tempList.add(getPreEaxmModels.get(i));
                                getPreEaxmModels.set(i,getPreEaxmModels.get(0));
                                getPreEaxmModels.set(0,tempList.get(0));
                            }
                        }
                        getExamSubCategoryAdapter = new GetExamSubCategoryMoreClassesAdapter(catName.trim(), context, getPreEaxmModels, recy_subpreexam, new GetExamSubCategoryMoreClassesAdapter.OnItemClicked() {
                            @Override
                            public void onItemClick(int position) {
                                getSubPreEaxmModels(getPreEaxmModels.get(position).getSubCategoryId());
                                SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("SubCatId", getPreEaxmModels.get(position).getSubCategoryId());
                                editor.apply();
                            }
                        });
                        recy_subpreexam.setAdapter(getExamSubCategoryAdapter);
                        // getExamSubCategoryAdapter.notifyDataSetChanged();
                        if (getPreEaxmModels.size() > 0) {
                            getSubPreEaxmModels(subCateId.trim());
                        }
                    } else {
                        Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void getSubPreEaxmModels(String SubCategoryId) {
        getSubCourseModels.clear();
        String SUBCATALL = BaseUrl.GETALLCOURSEBYSUBCATEGORY + "/" + SubCategoryId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    getSubCourseModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("course_frag", jsonObject.toString());
                    tvCount.setText(jsonObject.length()+" Batches available");
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            recy_subCourse.setVisibility(View.VISIBLE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            String RegLastDate = jsonObject1.getString("RegLastDate");
                            String ExpiryDate = jsonObject1.getString("ExpiryDate");
                            String FreeTrail = jsonObject1.getString("FreeTrail");
                            String Price = jsonObject1.getString("Price");
                            String Langauge = jsonObject1.getString("Langauge");
                            String Teachers = jsonObject1.getString("Teachers");
                            GetSubCourseModel getPreEaxmModel = new GetSubCourseModel(CourseId, CategoryId, SubCategoryId, Name, Details, Pic, Logo, RegLastDate, ExpiryDate, FreeTrail, Price, Langauge, Teachers);
                            getSubCourseModels.add(getPreEaxmModel);
                        }
                    } else {
                         Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }
                    getExamSubCourseAdapter = new GetExamSubCourseAdapter(context, getSubCourseModels);
                    recy_subCourse.setAdapter(getExamSubCourseAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void getUpcomingCouse(String CategoryId) {
        getUpcomingCouseModels.clear();
        String SUBCATALL = BaseUrl.GETUPCOMINGCOURSE + "/" + CategoryId.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("courseupcoming", response);
                try {
                    getUpcomingCouseModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("upcoming", jsonObject.toString());
                    if (jsonObject.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SubCategoryId = jsonObject1.getString("SubCategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String AddDate = jsonObject1.getString("AddDate");
                            String FreeTrail = jsonObject1.getString("FreeTrail");
                            String Price = jsonObject1.getString("Price");
                            String Langauge = jsonObject1.getString("Langauge");
                            String Teachers = jsonObject1.getString("Teachers");
                            UpcomingClassesModel getPreEaxmModel = new UpcomingClassesModel(CourseId, CategoryId, SubCategoryId, Name, Details, Pic, AddDate, FreeTrail, Langauge, Teachers, Price);
                            getUpcomingCouseModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }
                    upcomingCourseAdapter = new UpcomingCourseAdapter(context, getUpcomingCouseModels);
                    recycler_image_slider.setAdapter(upcomingCourseAdapter);
                    //getExamSubCourseAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void whatsappmessage() {
        String contact = "+91" + Mobile1; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void initComponent() {
        layout_dots = (LinearLayout) view.findViewById(R.id.layout_dots);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        getSliderImage();
        getUpcomingCouse(categoryid);
    }

    public void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];
        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(context);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(context, R.color.overlay_dark_20), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(context, R.color.theme_purple), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void getSliderImage() {
        getSliderImagebyCateModels.clear();
        String SUBCATALL = BaseUrl.GETIMAGESLIDER + "/" + categoryid.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSliderImage", response);
                try {
                    getSliderImagebyCateModels.clear();
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getSliderImage_2", jsonObject.toString());
                    if (jsonObject.length() > 0) {
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
                            if (ImageType.equals("CatSlider")) {
                                GetSliderImagebyCateModel getPreEaxmModel = new GetSliderImagebyCateModel(ImageId, ImagePath, ImageCategory, ImageSubCategory, Heading, Details, ImageType, ImagePriority, LinkType, LinkId);
                                getSliderImagebyCateModels.add(getPreEaxmModel);
                            }
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        adapterImageSlider = new AdapterImageSlider(getActivity(), getSliderImagebyCateModels);
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
                        startAutoSlider(adapterImageSlider.getCount());
                    } else {
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
}



