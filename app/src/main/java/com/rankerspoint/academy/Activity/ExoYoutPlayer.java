package com.rankerspoint.academy.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

public class ExoYoutPlayer extends Activity {
    private static final String HTML = "  <!DOCTYPE html>\n" +
            "<html>\n" +
            " <style type=\"text/css\">\n" +
            "        html, body {\n" +
            "-webkit-user-select: none;\n" +
            "     -moz-user-select: -moz-none;\n" +
            "      -ms-user-select: none;\n" +
            "          user-select: none; " +
            "            height: 100%;\n" +
            "            width: 100%;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            background-color: #000000;\n" +
            "            overflow: hidden;\n" +
            "            position: fixed;\n" +
            "        }\n" +
            "    </style>\n" +
            "<body>\n" +
            "\n" +
            " <div id=\"player\"></div>\n" +
            "<script>\n" +
            "       var tag = document.createElement('script');\n" +
            "       tag.src = \"https://www.youtube.com/player_api\";\n" +
            "       var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
            "       firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
            "       var player;\n" +
            "       function onYouTubePlayerAPIReady() {\n" +
            "            player = new YT.Player('player', {\n" +
            "                    height: '100%',\n" +
            "                    width: '100%',\n" +
            "                    videoId: 'CUSTOM_ID',\n" +
            "                    events: {\n" +
            "                       'onReady': onPlayerReady,\n" +
            "                       'onStateChange': onPlayerStateChange,\n" +
            "                       'contextmenu': onContextMenu\n" +
            "                  },\n" +
            "                  playerVars: {\n" +
            "                         'loop': 1,\n"     +
            "                         'rel': 0,\n"     +
            "                        'autoplay': 1,\n" +
            "                        'showinfo': 0,\n" +
            "                        'modestbranding': 1,\n" +
            "                        'controls': 1\n" +
            "                                }\n" +
            "                            });\n" +
            "                        }\n" +
            "                        function onContextMenu(event) {\n" +
            "                            event.target.preventDefault();\n" +
            "                            event.target.stopPropagation();\n" +
            "                            event.target.stopImmediatePropagation();\n" +
            "                            return false;\n" +
            "\n" +
            "                        }\n" +
            "\n" +
            "                        function onPlayerReady(event) {\n" +
            "                            event.target.playVideo();\n" +
            "                            event.target.preventDefault();\n" +
            "                            event.target.stopPropagation();\n" +
            "                            event.target.stopImmediatePropagation();\n"+
            "\n" +
            "                        }\n" +
            "\n" +
            "                        var done = false;\n" +
            "                        function onPlayerStateChange(event) {\n" +
            "                            if (event.data == YT.PlayerState.PLAYING && !done) {\n" +
            "                                done = true;\n" +
            "                            }\n" +
            "                        }\n" +
            "                        function stopVideo() {\n" +
            "                            player.stopVideo();\n" +
            "                        }\n" +
            "                    </script> \n" +
            "\n" +
            "</body>\n" +
            "</html>";
    // Replace video id with your video Id
    // private String YOUTUBE_VIDEO_ID = "uZnWUZW1hQo";
//    private String BASE_URL = "https://www.youtube.com/watch?v=uZnWUZW1hQo";
//    private String mYoutubeLink = BASE_URL + "/watch?v=" + YOUTUBE_VIDEO_ID;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    // YouTube player view
    private YouTubePlayerView youTubeView;
    // String v_id = "",v_title="";
    private View mPlayButtonLayout;
    private TextView mPlayTimeTextView, tv_title;
    private Handler mHandler = null;
    private SeekBar mSeekBar;
    private YouTubePlayer mPlayer;
    boolean mAutoRotation = false;
    String liveVideoId = "", status = "", FeeStatusMentor = "";
    WebView webView;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exo_yout_player);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        status = preferences.getString("Status", "");
        FeeStatusMentor = preferences.getString("FeeStatusMentor", "");
//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        liveVideoId = getIntent().getStringExtra("VIDEOURL");
        Log.d("VIDEOURL", liveVideoId);
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }        // Initializing video player with developer key
        if (FeeStatusMentor.equals("Free")) {
            init();
        } else {
            if (FeeStatusMentor.equals("Paid")) {
                if (status.equals("True")) {
                    init();
                } else {
                    Intent intent = new Intent(ExoYoutPlayer.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                Log.d("action", "" + action);
                switch (action) {
                    case (MotionEvent.ACTION_POINTER_DOWN):
                        finish();
                        return false;
                    case (MotionEvent.ACTION_CANCEL):
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        webView.saveState(outState);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void init() {
        mView = findViewById(R.id.view);
        webView = findViewById(R.id.youtube_web_view);
        int never = WebSettings.MIXED_CONTENT_NEVER_ALLOW;
        webView.setLongClickable(true);
//        webView.setWebViewClient(new Browser());
//        webView.setWebChromeClient(new MyWebClient());
        webView.setWebChromeClient(new WebChromeClient());

//        webView.setWebChromeClient(new MyChrome());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
//        webSettings.setAppCacheEnabled(true);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                finish();
//                Toast.makeText(ExoYoutPlayer.this, "App doesn't allow this." + statusliv, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        webView.loadDataWithBaseURL("https://www.youtube.com/", HTML.replace("CUSTOM_ID", liveVideoId), "text/html", "utf-8", null);

//        webView.loadUrl("https://www.youtube.com/embed/" + liveVideoId + "?modestbranding=1&autoplay=1&playlist=" + liveVideoId + "&loop=1");
//        youTubeView.initialize(BaseUrl.DEVELOPER_KEY, this);
//        //Add play button to explicitly play video in YouTubePlayerView
//        mPlayButtonLayout = findViewById(R.id.video_control);
//        findViewById(R.id.play_video).setOnClickListener(this);
//        findViewById(R.id.pause_video).setOnClickListener(this);
//        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
//        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
//        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
//        mSeekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.theme_purple), PorterDuff.Mode.SRC_IN);
//        mSeekBar.getThumb().setColorFilter(getResources().getColor(R.color.theme_purple), PorterDuff.Mode.SRC_IN);
//        mHandler = new Handler();
    }
    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        webView.restoreState(savedInstanceState);
    }
}