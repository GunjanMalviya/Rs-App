package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

public class No_Internet_Conn extends AppCompatActivity {
    private ProgressBar progress_bar;
    private LinearLayout lyt_no_connection;
    private AppCompatButton bt_retry;
    boolean fst=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no__internet__conn);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this); initComponent();
    }
        private void initComponent() {
            progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
            lyt_no_connection = (LinearLayout) findViewById(R.id.lyt_no_connection);
            bt_retry = (AppCompatButton) findViewById(R.id.bt_retry);

            progress_bar.setVisibility(View.GONE);
            lyt_no_connection.setVisibility(View.VISIBLE);

            bt_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    progress_bar.setVisibility(View.VISIBLE);
                    lyt_no_connection.setVisibility(View.GONE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress_bar.setVisibility(View.GONE);
                            if(isConnectedToInternet())
                            {

                                Intent intent=new Intent(No_Internet_Conn.this,Splash.class);
                                startActivity(intent);


                            }

                            else
                            {
                                lyt_no_connection.setVisibility(View.VISIBLE);
                            }

                        }
                    }, 1000);
                }
            });
        }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}