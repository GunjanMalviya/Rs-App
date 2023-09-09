package com.rankerspoint.academy.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rankerspoint.academy.Adapter.AdapterImageSlider;
import com.rankerspoint.academy.Adapter.GetExamMockTestSeriesAdapter;
import com.rankerspoint.academy.Adapter.GetMockTestBySeriesByCourseAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetEaxmSeriesModel;
import com.rankerspoint.academy.Model.GetMockTestSeriesByCourseModel;
import com.rankerspoint.academy.Model.GetSliderImagebyCateModel;
import com.rankerspoint.academy.Activity.ViewAll_QuizTestSeries;
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

public class TestSeriesFragment extends Fragment {
    private Runnable runnable = null;
    private Handler handler = new Handler();
    ProgressDialog progressDialog;
    private ViewPager viewPager;
    private LinearLayout layout_dots,llaout_three,lnr_layout;
    View view,view_badge;
    RecyclerView recycler_mock_series,recy_moreclasses;
    private AdapterImageSlider adapterImageSlider;
    private GetExamMockTestSeriesAdapter getExamMockTestSeriesAdapter;
    private List<GetSliderImagebyCateModel> getSliderImagebyCateModels = new ArrayList<>();
    private List<GetEaxmSeriesModel> getEaxmSeriesModels = new ArrayList<>();
    GetMockTestBySeriesByCourseAdapter getMockTestBySeriesByCourseAdapter;
    private List<GetMockTestSeriesByCourseModel> getMockTestSeriesByCourseModels = new ArrayList<>();
    String categoryid="";
    int selectedPosition=0;
    Context context;
    TextView txt_viewAll,txt_mock,txt_quiz;
    CardView card_all,card_mock_test,card_course_click;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_test_series, container, false);
//        llaout_three=view.findViewById(R.id.llaout_three);
        context=container.getContext();

        recycler_mock_series=view.findViewById(R.id.recycler_mock_series);
        txt_viewAll=view.findViewById(R.id.txt_viewAll);
        card_course_click=view.findViewById(R.id.card_course_click);
        card_mock_test=view.findViewById(R.id.card_mock_test);
        txt_quiz=view.findViewById(R.id.txt_quiz);
        txt_mock=view.findViewById(R.id.txt_mock);
        lnr_layout=view.findViewById(R.id.lnr_layout);

        recycler_mock_series.setLayoutManager(new GridLayoutManager(context,2));

        recy_moreclasses=view.findViewById(R.id.recy_moreclasses);
        recycler_mock_series.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//linearLayoutManager.setReverseLayout(true);
        recy_moreclasses.setLayoutManager(linearLayoutManager);

        progressDialog = new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        initComponent();

        return view;
    }

    private void initComponent() {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "CAT_ID");
        layout_dots = (LinearLayout) view.findViewById(R.id.layout_dots);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        getSliderImage(categoryid);
        getSubMoreClassesModels(categoryid);
        txt_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewAll_QuizTestSeries.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
               startActivity(intent);
            }
        });

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
                        startAutoSlider(adapterImageSlider.getCount());
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void getSubMoreClassesModels(String categoryid) {
        getMockTestSeriesByCourseModels.clear();
        String SUBCATALL= BaseUrl.GETALLCOURSEBYCATEGORY+"/"+categoryid.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSubMoreClassesModels", response);
                try {
                    getMockTestSeriesByCourseModels.clear();
//
                    Log.d("Myresponse",response);
                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getSubMoreClassesModels",jsonObject.toString());

                    // Toast.makeText(getContext(), "response:--"+response, Toast.LENGTH_SHORT).show();

                    if (!response.isEmpty()) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getSubMoreClassesModels", jsonObject1.toString());
                            String CourseId = jsonObject1.getString("CourseId");
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Name = jsonObject1.getString("Name");

                            GetMockTestSeriesByCourseModel getPreEaxmModel = new GetMockTestSeriesByCourseModel(CourseId,CategoryId,Name);
                            SharedPreferences sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("COR_Id", CourseId);
                               editor.apply();
                            getMockTestSeriesByCourseModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                        getMockTestBySeriesByCourseAdapter = new GetMockTestBySeriesByCourseAdapter(context, getMockTestSeriesByCourseModels, recy_moreclasses, new GetMockTestBySeriesByCourseAdapter.OnItemClicked() {
                            @Override
                            public void onItemClick(int position) {

                                try {

                                    card_course_click.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (selectedPosition == position) {
                                                //onClick.onItemClick(position);
                                              //  card_course_click.setSelected(true); //using selector drawable
                                                txt_quiz.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
                                                txt_quiz.setTextColor(context.getResources().getColor(R.color.white_color));
                                            }
                                                //card_mock_test.setSelected(false);
                                                //onClick.onItemClick(position);
                                                txt_mock.setBackgroundColor(context.getResources().getColor(R.color.white_color));
                                                txt_mock.setTextColor(context.getResources().getColor(R.color.black_color));

                                            getExamwiseSeries(getMockTestSeriesByCourseModels.get(position).getCourseId(),"Quiz");

                                        }
                                    });
                                    card_mock_test.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (selectedPosition == 0) {
                                                //onClick.onItemClick(position);
                                                //card_mock_test.setSelected(true); //using selector drawable
                                                txt_mock.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
                                                txt_mock.setTextColor(context.getResources().getColor(R.color.white_color));
                                            }
                                                //ard_course_click.setSelected(false);
                                                //onClick.onItemClick(position);
                                                txt_quiz.setBackgroundColor(context.getResources().getColor(R.color.white_color));
                                                txt_quiz.setTextColor(context.getResources().getColor(R.color.black_color));

                                            getExamwiseSeries(getMockTestSeriesByCourseModels.get(position).getCourseId(),"MockTest");

                                        }
                                    });
                                }catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }

                            }
                        });


                        recy_moreclasses.setAdapter(getMockTestBySeriesByCourseAdapter);
                        // getExamSubCategoryAdapter.notifyDataSetChanged();
                        if (getMockTestSeriesByCourseModels.size() > 0) {
                            getExamwiseSeries(getMockTestSeriesByCourseModels.get(0).getCourseId(),"MockTest");
                            if (selectedPosition == 0) {
                                //onClick.onItemClick(position);
                                //  card_mock_test.setSelected(true); //using selector drawable
                                txt_mock.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
                                txt_mock.setTextColor(context.getResources().getColor(R.color.white_color));
                            }
                            // card_course_click.setSelected(false);
                            //onClick.onItemClick(position);
                            txt_quiz.setBackgroundColor(context.getResources().getColor(R.color.white_color));
                            txt_quiz.setTextColor(context.getResources().getColor(R.color.black_color));
                            card_course_click.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (selectedPosition == 0) {
                                        //onClick.onItemClick(position);
                                       // card_course_click.setSelected(true); //using selector drawable

                                        txt_quiz.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
                                        txt_quiz.setTextColor(context.getResources().getColor(R.color.white_color));
                                    }
                                       // card_mock_test.setSelected(false);
                                        //onClick.onItemClick(position);
                                        txt_mock.setBackgroundColor(context.getResources().getColor(R.color.white_color));
                                        txt_mock.setTextColor(context.getResources().getColor(R.color.black_color));

                                   // Toast.makeText(context, "quiz", Toast.LENGTH_SHORT).show();
                                    getExamwiseSeries(getMockTestSeriesByCourseModels.get(0).getCourseId(),"Quiz");

                                }


                            });
                            card_mock_test.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (selectedPosition == 0) {
                                        //onClick.onItemClick(position);
                                      //  card_mock_test.setSelected(true); //using selector drawable
                                        txt_mock.setBackgroundColor(context.getResources().getColor(R.color.theme_purple));
                                        txt_mock.setTextColor(context.getResources().getColor(R.color.white_color));
                                    }
                                       // card_course_click.setSelected(false);
                                        //onClick.onItemClick(position);
                                        txt_quiz.setBackgroundColor(context.getResources().getColor(R.color.white_color));
                                        txt_quiz.setTextColor(context.getResources().getColor(R.color.black_color));

                                   // Toast.makeText(context, "mocktest", Toast.LENGTH_SHORT).show();

                                    getExamwiseSeries(getMockTestSeriesByCourseModels.get(0).getCourseId(),"MockTest");

                                }
                            });
                        }
                    }else
                    {
                        //lnr_layout.setVisibility(View.VISIBLE);
                        // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

    private void getExamwiseSeries(String courseid,String type) {
        getEaxmSeriesModels.clear();
String strurl= BaseUrl.GETALLSERIESBYCOURSEANDTYPE+"/"+courseid+"/"+type;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Mock_siries", response);
                try {
                    getEaxmSeriesModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("mockseries",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            lnr_layout.setVisibility(View.GONE);
                            recycler_mock_series.setVisibility(View.VISIBLE);
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj12", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String SeriesId = jsonObject1.getString("SeriesId");
                            String CourseId = jsonObject1.getString("CourseId");
                            String Title = jsonObject1.getString("Title");
                            String FeeStatus = jsonObject1.getString("FeeStatus");
                            String Pic = jsonObject1.getString("Pic");
                            String Status = jsonObject1.getString("Status");


                            GetEaxmSeriesModel getPreEaxmModel = new GetEaxmSeriesModel(CategoryId,SeriesId,CourseId,Title,FeeStatus,Pic,Status);

                            getEaxmSeriesModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            getExamMockTestSeriesAdapter = new GetExamMockTestSeriesAdapter(context, getEaxmSeriesModels);
                            recycler_mock_series.setAdapter(getExamMockTestSeriesAdapter);
                            getExamMockTestSeriesAdapter.notifyDataSetChanged();
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        recycler_mock_series.setVisibility(View.GONE);
                        lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(context, "No Data Found !!", Toast.LENGTH_SHORT).show();
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