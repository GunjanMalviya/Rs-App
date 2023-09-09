package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.io.File;

public class ExoplayerOffline extends AppCompatActivity {
    private boolean isShowingTrackSelectionDialog;
    private DefaultTrackSelector trackSelector;
    String[] speed = {"0.25x", "0.5x", "Normal", "1.5x", "2x"};
   // String url = "http://allexamtricks.tpwprintyard.com/ServiceImages/";
    // String streamUrl = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4";
    //String streamUrl = "https://www.youtube.com/watch?v=a31pq4gMBhU";
//    private ExoPlayer player;
//    String geturl="",streamUrl="";


    PlayerView playerView;
    private StringBuilder mFormatBuilder;
    private android.app.DownloadManager.Request request;
    SimpleExoPlayer simpleExoPlayer;
    ImageView download_item;
    String title="",VIDEONAME="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer_offline);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        playerView = findViewById(R.id.exoPlayerView);
        download_item=findViewById(R.id.download_item);
        VIDEONAME=getIntent().getStringExtra("VIDEONAME");
      download_item.setVisibility(View.GONE);
        File file= new File(ExoplayerOffline.this.getExternalFilesDir(Environment.DIRECTORY_MOVIES),VIDEONAME);
       // Toast.makeText(this, ""+file, Toast.LENGTH_SHORT).show();
        Log.d("file:--",file.toString());
        trackSelector = new DefaultTrackSelector(this);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();

        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(String.valueOf(file));
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();

        ImageView farwordBtn = playerView.findViewById(R.id.fwd);
        ImageView rewBtn = playerView.findViewById(R.id.rew);
        ImageView setting = playerView.findViewById(R.id.exo_track_selection_view);
        ImageView speedBtn = playerView.findViewById(R.id.exo_playback_speed);
        TextView speedTxt = playerView.findViewById(R.id.speed);

        setting.setVisibility(View.GONE);


        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ExoplayerOffline.this);
                builder.setTitle("Set Speed");
                builder.setItems(speed, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        try {


                            if (which == 0) {

                                speedTxt.setVisibility(View.VISIBLE);
                                speedTxt.setText("0.25X");
                                PlaybackParameters param = new PlaybackParameters(0.5f);
                                simpleExoPlayer.setPlaybackParameters(param);


                            }
                            if (which == 1) {

                                speedTxt.setVisibility(View.VISIBLE);
                                speedTxt.setText("0.5X");
                                PlaybackParameters param = new PlaybackParameters(0.5f);
                                simpleExoPlayer.setPlaybackParameters(param);


                            }
                            if (which == 2) {

                                speedTxt.setVisibility(View.GONE);
                                PlaybackParameters param = new PlaybackParameters(1f);
                                simpleExoPlayer.setPlaybackParameters(param);


                            }
                            if (which == 3) {
                                speedTxt.setVisibility(View.VISIBLE);
                                speedTxt.setText("1.5X");
                                PlaybackParameters param = new PlaybackParameters(1.5f);
                                simpleExoPlayer.setPlaybackParameters(param);

                            }
                            if (which == 4) {


                                speedTxt.setVisibility(View.VISIBLE);
                                speedTxt.setText("2X");

                                PlaybackParameters param = new PlaybackParameters(2f);
                                simpleExoPlayer.setPlaybackParameters(param);


                            }


                        }catch (Exception ex)
                        {
                            Toast.makeText(ExoplayerOffline.this, "after laod video", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();


            }
        });


        farwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition() + 10000);

            }
        });
        rewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long num = simpleExoPlayer.getCurrentPosition() - 10000;
                if (num < 0) {

                    simpleExoPlayer.seekTo(0);


                } else {

                    simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition() - 10000);

                }
            }
        });




        findViewById(R.id.exo_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    simpleExoPlayer.play();
                }catch (Exception ex)
                {
                    Toast.makeText(ExoplayerOffline.this, "after load video", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.exo_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simpleExoPlayer.pause();

            }
        });


        simpleExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == ExoPlayer.STATE_ENDED) {

                }

            }
        });


        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {

            }
        });


    }


///////////////////////////////.....................Methods...........................//////////////////////////////


    protected void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            trackSelector = null;
        }

    }


    @Override
    public void onStop() {
        super.onStop();

        releasePlayer();
    }

}
