package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
import com.squareup.picasso.Picasso;

public class MentorPostSingleTestDescription extends AppCompatActivity {
    ImageView imgBack,image_video;
    TextView tv_toolbar_title,txt_title,txt_date,details_txt1,details_txt2,txt_readMore;
    String date="",title="",link1="", link2="",image="",feestatus="",href1="",status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_post_single_test_description);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        status = preferences.getString("Status", "");

        imgBack=findViewById(R.id.imgBack);
        txt_title=findViewById(R.id.txt_title);
        txt_date=findViewById(R.id.txt_date);
        details_txt1=findViewById(R.id.details_txt1);
        details_txt2=findViewById(R.id.details_txt2);
        txt_readMore=findViewById(R.id.txt_readMore);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        image_video=findViewById(R.id.image_video);
        tv_toolbar_title.setText("Details..");


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

date=getIntent().getStringExtra("DATE");
title=getIntent().getStringExtra("TITLE");
link1=getIntent().getStringExtra("LINK1");
link2=getIntent().getStringExtra("LINK2");
image=getIntent().getStringExtra("IMAGE");
feestatus=getIntent().getStringExtra("FEESTATUS");
href1=getIntent().getStringExtra("HREF1");
        Log.d("feestatus_paid",feestatus+":--status:--"+status);
if (feestatus.equals("Free"))
{
    init();
}else {
    if (feestatus.equals("Paid"))
    {
        if (status.equals("True"))
        {
            init();
        }else {
            Intent intent=new Intent(MentorPostSingleTestDescription.this, PaymentCheckOut.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        }
    }
}


    }
    public void init()
    {
    txt_date.setText(date);
    txt_title.setText(title);
    details_txt1.setText(link1);
    details_txt2.setText(link2);
    Picasso.with(MentorPostSingleTestDescription.this).load(BaseUrl.BANN_IMG_URL + image).into(image_video);
        txt_readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                i.setData(Uri.parse(href1));
                startActivity(i);
            }
        });
    }
}