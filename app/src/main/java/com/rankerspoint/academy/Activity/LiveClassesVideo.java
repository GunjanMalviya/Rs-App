package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rankerspoint.academy.Fragment.RecentLiveVideo;
import com.rankerspoint.academy.Fragment.UpcomingLiveClasses;
import com.google.android.material.tabs.TabLayout;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class LiveClassesVideo extends AppCompatActivity {
    private ViewPager view_pager;
    private TabLayout tab_layout;
    String courseid = "", Name = "";
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_classes_video);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        courseid = preferences.getString("CourseId", "CourseId");
        Name = preferences.getString("Name", "Name");
        Log.d("CourseIdNAME", courseid);
        //toolbar
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("All Live Classes");

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
       SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(RecentLiveVideo.newInstance(), "Recent");
        adapter.addFragment(UpcomingLiveClasses.newInstance(), "Upcoming");

        viewPager.setAdapter(adapter);
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