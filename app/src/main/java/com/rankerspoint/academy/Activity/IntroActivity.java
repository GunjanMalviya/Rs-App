package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetPopUpImageModel;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class IntroActivity extends AppCompatActivity {
    private static int MAX_STEP = 0;

    private ViewPager viewPager;
    ImageView img_for;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<GetPopUpImageModel> getPopUpImageModels = new ArrayList<>();
//    private String about_title_array[] = {
//            "Live Courses with Study Plan",
//            "Expert Faculty",
//            "Best Practice & Study Material",
//            "Effective exam preparation"
//    };
//    private String about_description_array[] = {
//            "Courses designed with a day-wise study schedule",
//            "Experienced faculty who have mentored toppers",
//            "Unlimited quizzes, mock tests & PDF notes",
//            "we ensure that you get guidance from our well-experienced faculty",
//    };
//    private int about_images_array[] = {
//            R.drawable.intro1,
//            R.drawable.intro2,
//            R.drawable.intro3,
//            R.drawable.intro2
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        MAX_STEP=getPopUpImageModels.size();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        getSubPreEaxmModels();
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        //handleSSLHandshake();
    }
    private void getSubPreEaxmModels() {
        getPopUpImageModels.clear();
        String SUBCATALL= BaseUrl.GETPOPUPIMAGE+"/"+"Popup";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Image_pop", response);
                try {
                    getPopUpImageModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("popup_image",jsonObject.toString());

                    if (response!=null) {

                        for (int i = 0; i < jsonObject.length(); i++) {


                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String ImageId = jsonObject1.getString("ImageId");
                            String ImagePath = jsonObject1.getString("ImagePath");
                            String ImageCategory = jsonObject1.getString("ImageCategory");
                            String ImageSubCategory = jsonObject1.getString("ImageSubCategory");
                            String Heading = jsonObject1.getString("Heading");
                            String Details = jsonObject1.getString("Details");

                            GetPopUpImageModel getPreEaxmModel = new GetPopUpImageModel(ImageId,ImagePath,ImageCategory,ImageSubCategory,Heading,Details);

                            getPopUpImageModels.add(getPreEaxmModel);
                            myViewPagerAdapter = new MyViewPagerAdapter();
                            viewPager.setAdapter(myViewPagerAdapter);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }
                    }else
                    {

                       // Toast.makeText(IntroActivity.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
Log.d("Errorvolly",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Button btnNext;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            GetPopUpImageModel getPopUpImageModel=getPopUpImageModels.get(position);
            View view = layoutInflater.inflate(R.layout.item_card_wizard_light, container, false);
            ImageView imageView=(ImageView) view.findViewById(R.id.image);
//            Glide.with(IntroActivity.this).load(BaseUrl.BANN_IMG_URL + getPopUpImageModel.getImagePath()).into(imageView);
            Picasso.with(getApplicationContext()).load(BaseUrl.BANN_IMG_URL + getPopUpImageModel.getImagePath()).into(imageView);
            btnNext = (Button) view.findViewById(R.id.btn_next);
            if (position == getPopUpImageModels.size() - 1) {
                btnNext.setText("Get Started");
            } else {
                btnNext.setText("Next");
            }
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = viewPager.getCurrentItem() + 1;
                    if (current < getPopUpImageModels.size()) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {

                        Intent intent=new Intent(IntroActivity.this,PrePareExam.class);
                        startActivity(intent);
                    }
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return getPopUpImageModels.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}