package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rankerspoint.academy.Fragment.Dashboard_Single;
import com.rankerspoint.academy.Fragment.Doubts_Single;
import com.rankerspoint.academy.Fragment.LiveClassFragment;
import com.rankerspoint.academy.Fragment.RecentLiveVideo;
import com.rankerspoint.academy.Fragment.ReportCard_Single;
import com.rankerspoint.academy.Fragment.Syllabus_Single;
import com.google.android.material.tabs.TabLayout;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class MultiTabSyllabus extends AppCompatActivity {
    private ViewPager view_pager;
    private TabLayout tab_layout;
    String courseid = "", Name = "";
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title, tvPrice;
    private String catId;
    private boolean isFromContent = false;
    private CardView buyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_tab_syllabus);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        Bundle extras = getIntent().getExtras();
        isFromContent = extras.getBoolean("fromContent");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        courseid = preferences.getString("CourseId", "CourseId");
        catId = getIntent().getStringExtra("CAT_ID");
        Name = preferences.getString("Name", "Name");
        Log.d("CourseIdNAME", courseid);
        buyNow = findViewById(R.id.buy_now);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setElevation(0);
        tvPrice = findViewById(R.id.tv_dis_pricing);
        tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initComponent();
    }

    private void initComponent() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(view_pager);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
       /* SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(Dashboard_Single.newInstance(), "DASHBOARD");
        //adapter.addFragment(Doubts_Single.newInstance(), "DOUBTS");
       // adapter.addFragment(MockTest_Single.newInstance(), "MOCK TESTS");
        adapter.addFragment(ReportCard_Single.newInstance(), "REPORT CARD");
        adapter.addFragment(Syllabus_Single.newInstance(), "SYLLABUS");
        viewPager.setAdapter(adapter);*/
        if (isFromContent) {
            buyNow.setVisibility(View.GONE);
            tv_toolbar_title.setText("All Content");
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(RecentLiveVideo.newInstance(), "Lectures");
            adapter.addFragment(Syllabus_Single.newInstance(), "Notes");
            adapter.addFragment(LiveClassFragment.newInstance(catId, courseid), "DPP");
            adapter.addFragment(ReportCard_Single.newInstance(), "DPP PDF");
            adapter.addFragment(Syllabus_Single.newInstance(), "DPP VIDEOS");
            viewPager.setAdapter(adapter);
        } else {
            buyNow.setVisibility(View.VISIBLE);
            tv_toolbar_title.setText(Name);
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(Dashboard_Single.newInstance(), "Description");
            adapter.addFragment(Syllabus_Single.newInstance(), "All classes");
            adapter.addFragment(LiveClassFragment.newInstance(catId, courseid), "Test");
            // adapter.addFragment(MockTest_Single.newInstance(), "MOCK TESTS");
            adapter.addFragment(ReportCard_Single.newInstance(), "Announcements");
//        adapter.addFragment(Syllabus_Single.newInstance(), "CONTENT");
//        adapter.addFragment(ReportCard_Single.newInstance(), "REPORT CARD");
//        adapter.addFragment(Doubts_Single.newInstance(), "DOUBTS");
            viewPager.setAdapter(adapter);
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}