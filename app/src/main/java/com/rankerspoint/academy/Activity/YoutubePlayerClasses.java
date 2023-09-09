package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubePlayerView;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

public class YoutubePlayerClasses extends AppCompatActivity {
    String liveVideoId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player_classes);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
       /* liveVideoId=getIntent().getStringExtra("VIDEOURL");
        Log.d("VIDEOURL",liveVideoId);
        initYouTubePlayerView();*/
    }

/*
    private void initYouTubePlayerView() {
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);

//        youTubePlayerView.getPlayerUIController().showYouTubeButton(false);
//        youTubePlayerView.getPlayerUIController().showPlayPauseButton(true);
//
//        youTubePlayerView.getPlayerUIController().showSeekBar(true);
//        youTubePlayerView.getPlayerUIController().showVideoTitle(false);






        getLifecycle().addObserver(youTubePlayerView);

//        youTubePlayerView.initialize(youTubePlayer -> {
//
////            youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady() {
//                    youTubePlayer.loadVideo(liveVideoId,0f);
//                }
//
//            });
//        }, true);


    }
*/


}
