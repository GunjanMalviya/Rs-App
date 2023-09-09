package com.rankerspoint.academy.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rankerspoint.academy.Adapter.MessageAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.Chat;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//public class YoutubePlayerLiveClasses extends YouTubeBaseActivity implements
//        YouTubePlayer.OnInitializedListener, View.OnClickListener {
//    private static final int RECOVERY_DIALOG_REQUEST = 1;
//    Button QualityPlay, Chats_message, fullscreen_btn, smallscreen_btn;
//    RecyclerView recyclerView_chat, recyclerView_chat1;
//    List<Chat> chats;
//    String time;
//    private TextView txtProgress;
//    RelativeLayout relay_progress;
//    private String url = BaseUrl.BANN_IMG_URL;
//    MessageAdapter ada;
//    String user_name;
//    ProgressBar progressBar_chat;
//    TextView tv_title, tv_title_2;
//    RelativeLayout rr_chat, bottomrd2, bottomrd1;
//    LinearLayout rlrl, llay_pdf, linea_notstart;
//    ImageButton btn_Sent1;
//    ImageView refresh, refresh1;
//    private ProgressBar progressBar;
//    private EditText edt_Sent, edt_Sent1;
//    private android.app.DownloadManager.Request request;
//    // YouTube player view
//    private YouTubePlayerView youTubeView;
//    // String v_id = "",v_title="";
//    private View mPlayButtonLayout;
//    private TextView mPlayTimeTextView, txt_livename, txt_liveMessage;
//    private Handler mHandler = null;
//    private SeekBar mSeekBar;
//    ProgressDialog progressDialog;
//    ImageView img_pdf_download;
//    String currentTime = "", completeurl = "";
//    boolean mAutoRotation = false;
//    private com.google.android.youtube.player.YouTubePlayer mPlayer;
//    String liveVideoId = "", LICLASS_NAME = "", feestatus = "", PDF_URL = "", LIVE_CLASS_ID = "", MESSAGELIV = "", COURSE_ID = "";
//    String user_id = "", status = "", statusliv = "", user_pic = "";
//    WebView webView;
//    String video_url = "KK9bwTlAvgo", html = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_youtube_player_live_classes);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        user_id = preferences.getString("user_id", "");
//        statusliv = preferences.getString("StatusLive", "");
//        status = preferences.getString("Status", "");
//        user_name = preferences.getString("Firstname", "");
//        user_pic = preferences.getString("UserPic", "");
//        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        txt_livename = findViewById(R.id.txt_livename);
//        relay_progress = findViewById(R.id.relay_progress);
//        // pro_txt = findViewById(R.id.pro_txt);
//        txtProgress = (TextView) findViewById(R.id.txtProgress);
//        txt_liveMessage = findViewById(R.id.txt_liveMessage);
//        img_pdf_download = findViewById(R.id.img_pdf_download);
//        linea_notstart = findViewById(R.id.linea_notstart);
//        MESSAGELIV = getIntent().getStringExtra("MESSAGELIV");
//        tv_title = findViewById(R.id.tv_title);
//        tv_title_2 = findViewById(R.id.tv_title_2);
//        tv_title.setText(MESSAGELIV);
//        tv_title_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(YoutubePlayerLiveClasses.this, Dashboard.class);
//                startActivity(intent);
//            }
//        });
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        llay_pdf = findViewById(R.id.llay_pdf);
//        Log.d("Statusliv:--", statusliv);
////       Toast.makeText(this, "statusliv:--"+statusliv, Toast.LENGTH_SHORT).show();
//        feestatus = getIntent().getStringExtra("FEESTATUS");
//        if (feestatus.equalsIgnoreCase("Free")) {
//            if (statusliv.equalsIgnoreCase("Start")) {
//                init();
//            } else {
//                linea_notstart.setVisibility(View.VISIBLE);
//            }
//        } else {
//            if (feestatus.equalsIgnoreCase("Paid")) {
//                if (status.equalsIgnoreCase("True")) {
//                    if (statusliv.equalsIgnoreCase("Start")) {
//                        init();
//                    } else {
//                        linea_notstart.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    Intent intent = new Intent(YoutubePlayerLiveClasses.this, PaymentCheckOut.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                    startActivity(intent);
//                }
//            }
//        }
//    }
//
//    public void init() {
//        LIVE_CLASS_ID = getIntent().getStringExtra("LIVE_CLASS_ID");
//        liveVideoId = getIntent().getStringExtra("VIDEO_URL");
//        PDF_URL = getIntent().getStringExtra("PDF_URL");
//        LICLASS_NAME = getIntent().getStringExtra("LICLASS_NAME");
//        MESSAGELIV = getIntent().getStringExtra("MESSAGELIV");
//        COURSE_ID = getIntent().getStringExtra("COURSE_ID");
//        txt_livename.setText(LICLASS_NAME);
//        txt_liveMessage.setText(MESSAGELIV);
//        completeurl = url + PDF_URL.trim();
//        progressDialog = new ProgressDialog(YoutubePlayerLiveClasses.this, R.style.MyAlertDialogStyle);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        // Initializing video player with developer key
//        youTubeView.initialize(BaseUrl.DEVELOPER_KEY, this);
//        Log.d("live_class_id", LIVE_CLASS_ID);
//        //Add play button to explicitly play video in YouTubePlayerView
//        mPlayButtonLayout = findViewById(R.id.video_control);
//        findViewById(R.id.play_video).setOnClickListener(this);
//        findViewById(R.id.pause_video).setOnClickListener(this);
//        webView =  findViewById(R.id.webview);
//        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
//        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
//        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
//        mSeekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//        mSeekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//        mHandler = new Handler();
//        Chats_message = findViewById(R.id.Chats);
//        rr_chat = findViewById(R.id.rr_chat);
//        rlrl = findViewById(R.id.lllrout);
//        bottomrd2 = findViewById(R.id.bottomrd2);
//        bottomrd1 = findViewById(R.id.bottomrd1);
//        btn_Sent1 = findViewById(R.id.btn_sent1);
//        recyclerView_chat1 = findViewById(R.id.rcview_chat1);
//        recyclerView_chat = findViewById(R.id.rcview_chat);
//        edt_Sent1 = findViewById(R.id.edt_sent1);
//        refresh = findViewById(R.id.refresh);
//        refresh1 = findViewById(R.id.refresh1);
//        refresh1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showChatUrl1("0", recyclerView_chat1);
//                showChatView1();
//                Toast.makeText(YoutubePlayerLiveClasses.this, "refresh", Toast.LENGTH_SHORT).show();
//            }
//        });
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showChatUrl("0", recyclerView_chat);
//                showChatView();
//                Toast.makeText(YoutubePlayerLiveClasses.this, "refresh", Toast.LENGTH_SHORT).show();
//                rr_chat.setVisibility(View.VISIBLE);
//            }
//        });
//        img_pdf_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                relay_progress.setVisibility(View.VISIBLE);
//                downloadVideo(completeurl);
//            }
//        });
//        fullscreen_btn = findViewById(R.id.fullscreen);
//        smallscreen_btn = findViewById(R.id.smallscreen);
//        // showChatUrl1("0", recyclerView_chat1);
//        //showChatView1();
//        fullscreen_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //relativeLayout_chat.setVisibility(View.GONE);
//                bottomrd2.setVisibility(View.GONE);
//                bottomrd1.setVisibility(View.GONE);
//                smallscreen_btn.setVisibility(View.VISIBLE);
//                fullscreen_btn.setVisibility(View.GONE);
//                Chats_message.setVisibility(View.VISIBLE);
//                llay_pdf.setVisibility(View.GONE);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//// Gets the layout params that will allow you to resize the layout
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlrl.getLayoutParams();
////// Changes the height and width to the specified *pixels*
//                params.height = rlrl.getResources().getDimensionPixelOffset(R.dimen.youtube_height);
////               params.height = rlrl.getResources().getDimensionPixelOffset(MATCH_PARENT);
//                rlrl.setLayoutParams(params);
//            }
//        });
//        smallscreen_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //relativeLayout_chat.setVisibility(View.VISIBLE);
//                smallscreen_btn.setVisibility(View.GONE);
//                bottomrd2.setVisibility(View.VISIBLE);
//                bottomrd1.setVisibility(View.VISIBLE);
//                fullscreen_btn.setVisibility(View.VISIBLE);
//                Chats_message.setVisibility(View.GONE);
//                llay_pdf.setVisibility(View.VISIBLE);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlrl.getLayoutParams();
//// Changes the height and width to the specified *pixels*
//                // params.height = rlrl.getResources().getDimensionPixelOffset(MATCH_PARENT);
//                params.height = rlrl.getResources().getDimensionPixelOffset(R.dimen.youtube_height_small);
//                rlrl.setLayoutParams(params);
//                rr_chat.setVisibility(View.GONE);
//            }
//        });
//        //showChatView();
//        Chats_message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mPlayer && mPlayer.isPlaying())
////                    showAlertchat();
//                    rr_chat.setVisibility(View.VISIBLE);
//            }
//        });
////        layout_play.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (null != mPlayer && !mPlayer.isPlaying())
////                    mPlayer.play();
////            }
////        });
//        showChatUrl("0", recyclerView_chat);
//        showChatView();
//        new Runnable() {
//            int updateInterval = 20000; //=one second
//
//            @Override
//            public void run() {
//                // Any code which goes here will be executed every 'updateInterval'
//                // change your background here
//                showChatUrl1("0", recyclerView_chat1);
//                showChatView1();
//                // Toast.makeText(YoutubePlayerActivityLadscape.this, "timer", Toast.LENGTH_SHORT).show();
////
//                recyclerView_chat1.postDelayed(this, updateInterval);
//                //recyclerView_chat.postDelayed(this, updateInterval);
//            }
//        }.run();
//    }
//
//    @Override
//    public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider,
//                                        YouTubeInitializationResult errorReason) {
//        if (errorReason.isUserRecoverableError()) {
//            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
//        } else {
//            String errorMessage = String.format(
//                    getString(R.string.error_player), errorReason.toString());
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider,
//                                        com.google.android.youtube.player.YouTubePlayer player, boolean wasRestored) {
//        mPlayer = player;
//        displayCurrentTime();
//        // Start buffvideo_controlering
//        if (!liveVideoId.equals("")) {
////            player.loadVideo(liveVideoId);
//            player.cueVideo(liveVideoId);
////            WebSettings ws = webView.getSettings();
////            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
////            ws.setPluginState(WebSettings.PluginState.ON);
////            ws.setJavaScriptEnabled(true);
////            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
////            webView.reload();
////                html = getHTML(liveVideoId);
////            webView.loadData(html, "text/html", "UTF-8");
////            WebClientClass webViewClient = new WebClientClass(progressBar);
////            webView.setWebViewClient(webViewClient);
////            WebChromeClient webChromeClient = new WebChromeClient();
////            webView.setWebChromeClient(webChromeClient);
//
//        }else {
//            Toast.makeText(this, "Video not loaded...", Toast.LENGTH_LONG).show();
//            finish();
//        }
////        player.setPlayerStyle(YouTubePlayer.PlayerStyle.);
////        mPlayButtonLayout.setVisibility(View.VISIBLE);
//        // Add listeners to YouTubePlayer instance
//        player.setPlayerStateChangeListener(mPlayerStateChangeListener);
//        player.setPlaybackEventListener(mPlaybackEventListener);
//    }
//
//    com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener() {
//        @Override
//        public void onBuffering(boolean arg0) {
//        }
//
//        @Override
//        public void onPaused() {
//            mHandler.removeCallbacks(runnable);
//        }
//
//        @Override
//        public void onPlaying() {
//            mHandler.postDelayed(runnable, 100);
//            displayCurrentTime();
//        }
//
//        @Override
//        public void onSeekTo(int arg0) {
//            mHandler.postDelayed(runnable, 100);
//        }
//
//        @Override
//        public void onStopped() {
//            mHandler.removeCallbacks(runnable);
//        }
//    };
//    com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener() {
//        @Override
//        public void onAdStarted() {
//        }
//
//        @Override
//        public void onError(com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
//        }
//
//        @Override
//        public void onLoaded(String arg0) {
//        }
//
//        @Override
//        public void onLoading() {
//        }
//
//        @Override
//        public void onVideoEnded() {
//            finish();
//        }
//
//        @Override
//        public void onVideoStarted() {
//            displayCurrentTime();
//        }
//    };
//    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////            long lengthPlayed = (mPlayer.getDurationMillis() * progress) / 100;
////            mPlayer.seekToMillis((int) lengthPlayed);
//            //Only when the user change the progress
//            if (fromUser) {
//                long lengthPlayed = (mPlayer.getDurationMillis() * progress) / 100;
//                mPlayer.seekToMillis((int) lengthPlayed);
//            }
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//        }
//    };
//    public class WebClientClass extends WebViewClient {
//        ProgressBar ProgressBar = null;
//
//        WebClientClass(ProgressBar progressBar) {
//            ProgressBar = progressBar;
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            ProgressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            ProgressBar.setVisibility(View.GONE);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
////            LogShowHide.LogShowHideMethod("webview-click :", "" + url.toString());
//            view.loadUrl(getHTML(video_url));
//            return true;
//        }
//    }
//
//    public String getHTML(String videoId) {
//        String html = "<iframe class=\"youtube-player\" " + "style=\"border: 0; width: 100%; height: 96%;"
//                + "padding:0px; margin:0px\" " + "id=\"ytplayer\" type=\"text/html\" "
//                + "src=\"http://www.youtube.com/embed/" + videoId
//                + "?&theme=dark&autohide=2&modestbranding=1&showinfo=0&autoplay=1\fs=0\" frameborder=\"0\" "
//                + "allowfullscreen autobuffer " + "controls onclick=\"this.play()\">\n" + "</iframe>\n";
////        LogShowHide.LogShowHideMethod("video-id from html url= ", "" + html);
//        return html;
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.play_video:
//                if (null != mPlayer && !mPlayer.isPlaying())
//                    mPlayer.play();
//                break;
//            case R.id.pause_video:
//                if (null != mPlayer && mPlayer.isPlaying())
//                    mPlayer.pause();
//                break;
//        }
//    }
//
//    private void displayCurrentTime() {
////        if (null == mPlayer) return;
//        String formattedTime = formatTime(mPlayer.getDurationMillis() - mPlayer.getCurrentTimeMillis());
//        mPlayTimeTextView.setText(formattedTime);
//    }
//
//    private String formatTime(int millis) {
//        int seconds = millis / 1000;
//        int minutes = seconds / 60;
//        int hours = minutes / 60;
//        return (hours == 0 ? "--:" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
//    }
//
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
////            displayCurrentTime();
////            mHandler.postDelayed(this, 100);
//            displayCurrentTime();
//            //Progress in percentage
//            int progress = mPlayer.getCurrentTimeMillis() * 100 / mPlayer.getDurationMillis();
//            mSeekBar.setProgress(progress);
//            mHandler.postDelayed(this, 100);
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RECOVERY_DIALOG_REQUEST) {
//            // Retry initialization if user performed a recovery action
//            getYouTubePlayerProvider().initialize(BaseUrl.DEVELOPER_KEY, this);
//        }
//    }
//
//    private YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return (YouTubePlayerView) findViewById(R.id.youtube_view);
//    }
//
//    public void setMessageChat(JSONObject jsonObject) {
////        Utils.showProgressDialog(this, "Message...");
////        Utils.showProgress();
//        progressBar_chat.setVisibility(View.VISIBLE);
//        RequestQueue queue = Volley.newRequestQueue(this);
//        Log.d("json123", jsonObject.toString());
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.SET_CHAT, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("chatsetdata:-", response.toString());
//                        if (response != null) {
////                            Chat chat = new Chat();
////                            chat.setMessage(edt_Sent.getText().toString());
////                            chat.settimechat(currentTime);
////                            chat.setSender(user_name);
////                            chats.add(chat);
////                            ada.notifyDataSetChanged();
////                            recyclerView_chat.scrollToPosition(chats.size() - 1);
//                            edt_Sent.setText("");
////                            Utils.dissmisProgress();
//                            progressBar_chat.setVisibility(View.GONE);
//                            String mssg = null;
//                            int status;
//                            try {
//                                Toast.makeText(getApplicationContext(), "Message added!!", Toast.LENGTH_SHORT).show();
//                                showChatUrl("0", recyclerView_chat);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//        queue.add(jsonObjReq);
//    }
//
//    private void showChatUrl(final String pos, final RecyclerView r) {
//        String getChatUser = BaseUrl.GET_CHAT + "/" + LIVE_CLASS_ID;
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, getChatUser, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("chat", response);
//                try {
//                    //JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = new JSONArray(response);
//                    //  Toast.makeText(getApplicationContext(), "response :  "+response, Toast.LENGTH_SHORT).show();
//                    chats = new ArrayList<>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        //   String class_id = jsonObject1.getString("class_id");
//                        String user_name = jsonObject1.getString("UserName");
//                        String message = jsonObject1.getString("Text");
//                        String timechat = jsonObject1.getString("AddDate");
//                        String UserPic = jsonObject1.getString("UserPic");
//                        // String[] split=timechat.split(" ");
//                        time = timechat.substring(11, 16);
//                        Chat c = new Chat();
//                        c.setMessage(message);
//                        c.setSender(user_name);
//                        c.settimechat(time);
//                        c.setUserPic(UserPic);
//                        //c.settimechat(split[1]);
//                        chats.add(c);
////                            dlist.add(ss);
//                    }
//                    ada = new MessageAdapter(getApplicationContext(), chats);
//                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubePlayerLiveClasses.this);
//                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
//                    linearLayoutManager.setStackFromEnd(true);
//                    recyclerView_chat.setLayoutManager(linearLayoutManager);
//                    recyclerView_chat.setAdapter(ada);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<>();
//                return param;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(YoutubePlayerLiveClasses.this);
//        requestQueue.getCache().clear();
//        requestQueue.add(stringRequest);
//    }
//
//    private void showChatView() {
//        rr_chat.setVisibility(View.GONE);
//        Button cancelCmd = findViewById(R.id.cancel_alert_dialog);
//        edt_Sent = findViewById(R.id.edt_sent);
////        TextView textView = findViewById(R.id.class_name);
////        textView.setText(sharedPreferences.getString("course", ""));
//        ImageView sent_msg = findViewById(R.id.btn_sent);
//        progressBar_chat = findViewById(R.id.progressBar_chat);
//        cancelCmd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rr_chat.setVisibility(View.GONE);
//            }
//        });
//        showChatUrl("0", recyclerView_chat);
//        sent_msg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //video pause
//                if (edt_Sent.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "Plz enter your text", Toast.LENGTH_SHORT).show();
//                } else {
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("UserId", user_id);
//                        jsonObject.put("UserName", user_name);
//                        jsonObject.put("UserType", "");
//                        jsonObject.put("UserPic", user_pic);
//                        jsonObject.put("ClassId", LIVE_CLASS_ID);
//                        jsonObject.put("Time", currentTime);
//                        jsonObject.put("Text", edt_Sent.getText().toString().trim());
//                        if (isNetworkConnected()) {
//                            // edt_Sent.setText("");
//                            setMessageChat(jsonObject);
//                            //firebase
//                        } else {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    public void setMessageChat1(JSONObject jsonObject) {
////        Utils.showProgressDialog(this, "Message...");
////        Utils.showProgress();
////        progressBar_chat.setVisibility(View.VISIBLE);
//        RequestQueue queue = Volley.newRequestQueue(this);
//        Log.d("json", jsonObject.toString());
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.SET_CHAT, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("chatsetdata1:-", response.toString());
//                        if (response != null) {
////                            Chat chat = new Chat();
////                            chat.setMessage(edt_Sent1.getText().toString());
////                            chat.settimechat(time);
////                            chat.setSender(user_name);
////                            chats.add(chat);
////                            ada.notifyDataSetChanged();
////                            recyclerView_chat1.scrollToPosition(chats.size() - 1);
//                            edt_Sent1.setText("");
////                            Utils.dissmisProgress();
//                            //progressBar_chat.setVisibility(View.GONE);
//                            String mssg = null;
//                            int status;
//                            try {
//                                Toast.makeText(getApplicationContext(), "Message added!!", Toast.LENGTH_SHORT).show();
//                                showChatUrl1("0", recyclerView_chat1);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//        queue.add(jsonObjReq);
//    }
//
//    private void showChatUrl1(final String pos, final RecyclerView r) {
//        String getChatUser = BaseUrl.GET_CHAT + "/" + LIVE_CLASS_ID;
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, getChatUser, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("chat", response);
//                try {
//                    //JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = new JSONArray(response);
//                    //  Toast.makeText(getApplicationContext(), "response :  "+response, Toast.LENGTH_SHORT).show();
//                    chats = new ArrayList<>();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        //   String class_id = jsonObject1.getString("class_id");
//                        String user_name = jsonObject1.getString("UserName");
//                        String message = jsonObject1.getString("Text");
//                        String timechat = jsonObject1.getString("AddDate");
//                        String UserPic = jsonObject1.getString("UserPic");
//                        // String[] split=timechat.split(" ");
//                        time = timechat.substring(11, 16);
//                        Chat c = new Chat();
//                        c.setMessage(message);
//                        c.setSender(user_name);
//                        c.settimechat(time);
//                        c.setUserPic(UserPic);
//                        //c.settimechat(split[1]);
//                        chats.add(c);
////                            dlist.add(ss);
//                    }
//                    ada = new MessageAdapter(getApplicationContext(), chats);
//                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubePlayerLiveClasses.this);
//                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
//                    linearLayoutManager.setStackFromEnd(true);
//                    recyclerView_chat1.setLayoutManager(linearLayoutManager);
//                    recyclerView_chat1.setAdapter(ada);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<>();
//                return param;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(YoutubePlayerLiveClasses.this);
//        requestQueue.getCache().clear();
//        requestQueue.add(stringRequest);
//    }
//
//    private void showChatView1() {
//        // rr_chat.setVisibility(View.GONE);
//        showChatUrl1("0", recyclerView_chat1);
//        btn_Sent1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //video pause
//                if (edt_Sent1.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "Plz enter your text", Toast.LENGTH_SHORT).show();
//                } else {
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("UserId", user_id);
//                        jsonObject.put("UserName", user_name);
//                        jsonObject.put("UserType", "");
//                        jsonObject.put("UserPic", user_pic);
//                        jsonObject.put("ClassId", LIVE_CLASS_ID);
//                        jsonObject.put("Time", currentTime);
//                        jsonObject.put("Text", edt_Sent1.getText().toString().trim());
//                        if (isNetworkConnected()) {
//                            // edt_Sent.setText("");
//                            setMessageChat1(jsonObject);
//                            //firebase
//                        } else {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @SuppressLint("MissingPermission")
//    private boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
//    }
//
//    public void downloadVideo(String url) {
//        Uri Download_Uri = Uri.parse(url);
//        request = new android.app.DownloadManager.Request(Download_Uri);
//        //Restrict the types of networks over which this download may proceed.
//        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI | android.app.DownloadManager.Request.NETWORK_MOBILE);
//        //Set whether this download may proceed over a roaming connection.
//        request.setAllowedOverRoaming(false);
//        // Visibility of the download Notification
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//        //Set the title of this download, to be displayed in notifications (if enabled).
//        request.setTitle("Downloading");
//        //Set a description of this download, to be displayed in notifications (if enabled)
//        request.setDescription("Downloading File");
//        request.setVisibleInDownloadsUi(false);
//        //Set the local destination for the downloaded file to a path within the application's external files directory
//        request.setDestinationInExternalFilesDir(YoutubePlayerLiveClasses.this, Environment.DIRECTORY_DOCUMENTS, LICLASS_NAME); //For private destination
//        //Set the local destination for the downloaded file to a path within the application's external files directory
//        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, "jay.mp4"); // for public destination
//        android.app.DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//        long downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                boolean downloading = true;
//                while (downloading) {
//                    DownloadManager.Query q = new DownloadManager.Query();
//                    q.setFilterById(downloadID);
//                    Cursor cursor = downloadManager.query(q);
//                    cursor.moveToFirst();
//                    int bytes_downloaded = cursor.getInt(cursor
//                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
//                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
//                        downloading = false;
//                    }
//                    final double dl_progress = (bytes_downloaded / bytes_total) * 100;
//                    int percentage = (bytes_downloaded * 100 / bytes_total);
//                    Log.d("progressssssss:-", percentage + "");
//                    Log.d("bytes_downloaded:-", bytes_downloaded + "");
//                    Log.d("bytes_total:-", bytes_total + "");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setProgress(percentage);
//                            txtProgress.setText(percentage + "%");
//                            if (bytes_downloaded == bytes_total) {
//                                relay_progress.setVisibility(View.GONE);
//                            }
////                            pro_txt.setText(""+percentage);
////                            mProgressDialog.setProgress(percentage);
//                        }
//                    });
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    //pStatus++;
//                    Log.d("setMassage", statusMessage(cursor));
//                    cursor.close();
//                }
//            }
//        }).start();
//        Toast.makeText(this, "download id:-" + downloadID, Toast.LENGTH_SHORT).show();
//    }
//
//    private String statusMessage(Cursor c) {
//        String msg = "???";
//        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
//            case DownloadManager.STATUS_FAILED:
//                msg = "Download failed!";
//                break;
//            case DownloadManager.STATUS_PAUSED:
//                msg = "Download paused!";
//                break;
//            case DownloadManager.STATUS_PENDING:
//                msg = "Download pending!";
//                break;
//            case DownloadManager.STATUS_RUNNING:
//                msg = "Download in progress!";
//                break;
//            case DownloadManager.STATUS_SUCCESSFUL:
//                msg = "Download complete!";
//                break;
//            default:
//                msg = "Download is nowhere in sight";
//                break;
//        }
//        return (msg);
//    }
//}

public class YoutubePlayerLiveClasses extends Activity {
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
            ".ytp-menuitem-icon {\n" +
            "    padding: 0 10px;\n" +
            "    display: none;\n" +
            "}"+
            "    </style>\n" +
            "<body>\n" +
            "\n" +
            " <script src=\"https://code.jquery.com/jquery-3.6.4.min.js\" integrity=\"sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=\" crossorigin=\"anonymous\">\n" +
            "</script> \n" +
            "<script>\n" +
            "$(document).ready(function(){\n" +
            " $('body').click(function() {\n" +
            "$('.ytp-contextmenu').hide();\n" +
            "});\n" +
            "});\n" +
            "</script> \n"+
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
            "                        'loop': 1,\n"     +
            "                        'enablejsapi': 0,\n"     +
            "                        'disablekb': 1,\n"     +
            "                        'rel': 0,\n"     +
            "                        'autoplay': 1,\n" +
            "                        'showinfo': 0,\n" +
            "                        'modestbranding': 1,\n" +
            "                        'controls': 1\n" +
            "                                }\n" +
            "                            });\n" +
            "                        }\n" +
            "                        function onContextMenu(event) {\n" +
            "                             return false;\n" +
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
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    Button QualityPlay, Chats_message, fullscreen_btn, smallscreen_btn;
    RecyclerView recyclerView_chat1;
    List<Chat> chats;
    String time;
    private TextView txtProgress;
    RelativeLayout relay_progress;
    private String url = BaseUrl.BANN_IMG_URL;
    MessageAdapter ada;
    String user_name;
    ProgressBar progressBar_chat;
    TextView tv_title, tv_title_2;
    RelativeLayout rr_chat, bottomrd2, bottomrd1;
    LinearLayout rlrl, llay_pdf, linea_notstart;
    ImageButton btn_Sent1;
    ImageView refresh, refresh1;
    private ProgressBar progressBar;
    private EditText edt_Sent, edt_Sent1;
    private android.app.DownloadManager.Request request;
    private View mPlayButtonLayout;
    private TextView mPlayTimeTextView, txt_livename, txt_liveMessage;
    private Handler mHandler = null;
    private SeekBar mSeekBar;
    ProgressDialog progressDialog;
    ImageView img_pdf_download;
    String currentTime = "", completeurl = "";
    String liveVideoId = "", LICLASS_NAME = "", feestatus = "", PDF_URL = "", LIVE_CLASS_ID = "", MESSAGELIV = "", COURSE_ID = "";
    String user_id = "", status = "", statusliv = "", user_pic = "";
    WebView webView;
    private View mView;
    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_youtube_player_live_classes);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        llMain = findViewById(R.id.ll_main);
        mView = findViewById(R.id.view);
        webView = findViewById(R.id.youtube_web_view);
        user_id = preferences.getString("user_id", "");
        statusliv = preferences.getString("StatusLive", "");
        status = preferences.getString("Status", "");
        user_name = preferences.getString("Firstname", "");
        user_pic = preferences.getString("UserPic", "");
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        txt_livename = findViewById(R.id.txt_livename);
        relay_progress = findViewById(R.id.relay_progress);
        // pro_txt = findViewById(R.id.pro_txt);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        txt_liveMessage = findViewById(R.id.txt_liveMessage);
        img_pdf_download = findViewById(R.id.img_pdf_download);
        linea_notstart = findViewById(R.id.linea_notstart);
        MESSAGELIV = getIntent().getStringExtra("MESSAGELIV");
        tv_title = findViewById(R.id.tv_title);
        tv_title_2 = findViewById(R.id.tv_title_2);
        tv_title.setText(MESSAGELIV);
        tv_title_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YoutubePlayerLiveClasses.this, Dashboard.class);
                startActivity(intent);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        llay_pdf = findViewById(R.id.llay_pdf);
        Log.d("Statusliv:--", statusliv);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(false);
        ws.setGeolocationEnabled(false);
        ws.setAllowContentAccess(false);
        int never = WebSettings.MIXED_CONTENT_NEVER_ALLOW;
        ws.setMixedContentMode(never);
        webView.setLongClickable(true);
/*        webView.setOnTouchListener(new View.OnTouchListener() {
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
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                finish();
                Toast.makeText(YoutubePlayerLiveClasses.this, "App doesn't allow this.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        feestatus = getIntent().getStringExtra("FEESTATUS");
        if (feestatus.equalsIgnoreCase("Free")) {
            if (statusliv.equalsIgnoreCase("Start")) {
                init();
            } else {
                linea_notstart.setVisibility(View.VISIBLE);
            }
        } else {
            if (feestatus.equalsIgnoreCase("Paid")) {
                if (status.equalsIgnoreCase("True")) {
                    if (statusliv.equalsIgnoreCase("Start")) {
                        init();
                    } else {
                        linea_notstart.setVisibility(View.VISIBLE);
                    }
                } else {
                    Intent intent = new Intent(YoutubePlayerLiveClasses.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }
    }

 /*   @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        Log.d("action", "" + action);
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                return true;
//            case (MotionEvent.ACTION_MOVE) :
//                Log.d(DEBUG_TAG,"Action was MOVE");
//                return true;
//            case (MotionEvent.ACTION_UP) :
//                Log.d(DEBUG_TAG,"Action was UP");
//                return true;
//            case (MotionEvent.ACTION_CANCEL) :
//                Log.d(DEBUG_TAG,"Action was CANCEL");
//                return true;
//            case (MotionEvent.ACTION_OUTSIDE) :
//                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
//                        "of current screen element");
//                return true;
//            default :
//                return super.onTouchEvent(event);
//        }
        finish();
        return true;
    }*/
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//        View v = getCurrentFocus();
//        boolean ret = super.dispatchTouchEvent(event);
//        Log.d("action", "" + event.get);
////        finish();
//        return false;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    public void init() {
        LIVE_CLASS_ID = getIntent().getStringExtra("LIVE_CLASS_ID");
        liveVideoId = getIntent().getStringExtra("VIDEO_URL");
        PDF_URL = getIntent().getStringExtra("PDF_URL");
        LICLASS_NAME = getIntent().getStringExtra("LICLASS_NAME");
        MESSAGELIV = getIntent().getStringExtra("MESSAGELIV");
        COURSE_ID = getIntent().getStringExtra("COURSE_ID");
        txt_livename.setText(LICLASS_NAME);
        txt_liveMessage.setText(MESSAGELIV);
        completeurl = url + PDF_URL.trim();
        progressDialog = new ProgressDialog(YoutubePlayerLiveClasses.this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        // Initializing video player with developer key
//        youTubeView.initialize(BaseUrl.DEVELOPER_KEY, this);
        Log.d("live_class_id", liveVideoId);
        //Add play button to explicitly play video in YouTubePlayerView
        mPlayButtonLayout = findViewById(R.id.video_control);
//        findViewById(R.id.play_video).setOnClickListener(this);
//        findViewById(R.id.pause_video).setOnClickListener(this);
        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
//        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
        mSeekBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        mSeekBar.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        mHandler = new Handler();
        Chats_message = findViewById(R.id.Chats);
//        rr_chat = findViewById(R.id.rr_chat);
        rlrl = findViewById(R.id.lllrout);
        bottomrd2 = findViewById(R.id.bottomrd2);
        bottomrd1 = findViewById(R.id.bottomrd1);
        btn_Sent1 = findViewById(R.id.btn_sent1);
        recyclerView_chat1 = findViewById(R.id.rcview_chat1);
//        recyclerView_chat = findViewById(R.id.rcview_chat);
        edt_Sent1 = findViewById(R.id.edt_sent1);
//        refresh = findViewById(R.id.refresh);
        refresh1 = findViewById(R.id.refresh1);
        rlrl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
        refresh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatUrl1("0", recyclerView_chat1);
                showChatView1();
                Toast.makeText(YoutubePlayerLiveClasses.this, "refresh", Toast.LENGTH_SHORT).show();
            }
        });
        img_pdf_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relay_progress.setVisibility(View.VISIBLE);
                downloadVideo(completeurl);
            }
        });
        fullscreen_btn = findViewById(R.id.fullscreen);
        smallscreen_btn = findViewById(R.id.smallscreen);
        // showChatUrl1("0", recyclerView_chat1);
        //showChatView1();
        fullscreen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //relativeLayout_chat.setVisibility(View.GONE);
                bottomrd2.setVisibility(View.GONE);
                bottomrd1.setVisibility(View.GONE);
                smallscreen_btn.setVisibility(View.VISIBLE);
                fullscreen_btn.setVisibility(View.GONE);
                Chats_message.setVisibility(View.VISIBLE);
                llay_pdf.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
// Gets the layout params that will allow you to resize the layout
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlrl.getLayoutParams();
//// Changes the height and width to the specified *pixels*
                params.height = rlrl.getResources().getDimensionPixelOffset(R.dimen.youtube_height);
//               params.height = rlrl.getResources().getDimensionPixelOffset(MATCH_PARENT);
                rlrl.setLayoutParams(params);
            }
        });
        smallscreen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //relativeLayout_chat.setVisibility(View.VISIBLE);
                smallscreen_btn.setVisibility(View.GONE);
                bottomrd2.setVisibility(View.VISIBLE);
                bottomrd1.setVisibility(View.VISIBLE);
                fullscreen_btn.setVisibility(View.VISIBLE);
                Chats_message.setVisibility(View.GONE);
                llay_pdf.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlrl.getLayoutParams();
// Changes the height and width to the specified *pixels*
                // params.height = rlrl.getResources().getDimensionPixelOffset(MATCH_PARENT);
                params.height = rlrl.getResources().getDimensionPixelOffset(R.dimen.youtube_height_small);
                rlrl.setLayoutParams(params);
                rr_chat.setVisibility(View.GONE);
            }
        });
        //showChatView();
        Chats_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mPlayer && mPlayer.isPlaying())
////                    showAlertchat();
//                    rr_chat.setVisibility(View.VISIBLE);
            }
        });
//        layout_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mPlayer && !mPlayer.isPlaying())
//                    mPlayer.play();
//            }
//        });
//        showChatUrl("0", recyclerView_chat);
        showChatView();
        new Runnable() {
            int updateInterval = 20000; //=one second

            @Override
            public void run() {
                // Any code which goes here will be executed every 'updateInterval'
                // change your background here
                showChatUrl1("0", recyclerView_chat1);
                showChatView1();
                // Toast.makeText(YoutubePlayerActivityLadscape.this, "timer", Toast.LENGTH_SHORT).show();
//
                recyclerView_chat1.postDelayed(this, updateInterval);
                //recyclerView_chat.postDelayed(this, updateInterval);
            }
        }.run();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bottomrd1.setVisibility(View.GONE);   // In landscape
        } else {
            bottomrd1.setVisibility(View.VISIBLE);   // In portrait
        }
        webView.loadDataWithBaseURL("https://www.youtube-nocookie.com/", HTML.replace("CUSTOM_ID", liveVideoId), "text/html", "utf-8", null);
//        webView.loadUrl("https://www.youtube.com/embed/" + liveVideoId + "title=''?modestbranding=1&modestbrowsing=1&rel=0&playlist=" + liveVideoId + "&loop=1&showinfo=0");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bottomrd1.setVisibility(View.GONE);
            rlrl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottomrd1.setVisibility(View.VISIBLE);
            rlrl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000));
        }
    }

    public void setMessageChat(JSONObject jsonObject) {
//        Utils.showProgressDialog(this, "Message...");
//        Utils.showProgress();
        progressBar_chat.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json123", jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.SET_CHAT, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("chatsetdata:-", response.toString());
                        if (response != null) {
//                            Chat chat = new Chat();
//                            chat.setMessage(edt_Sent.getText().toString());
//                            chat.settimechat(currentTime);
//                            chat.setSender(user_name);
//                            chats.add(chat);
//                            ada.notifyDataSetChanged();
//                            recyclerView_chat.scrollToPosition(chats.size() - 1);
                            edt_Sent.setText("");
//                            Utils.dissmisProgress();
                            progressBar_chat.setVisibility(View.GONE);
                            String mssg = null;
                            int status;
                            try {
                                Toast.makeText(getApplicationContext(), "Message added!!", Toast.LENGTH_SHORT).show();
//                                showChatUrl("0", recyclerView_chat);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjReq);
    }

    private void showChatUrl(final String pos, final RecyclerView r) {
        String getChatUser = BaseUrl.GET_CHAT + "/" + LIVE_CLASS_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getChatUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("chat", response);
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    //  Toast.makeText(getApplicationContext(), "response :  "+response, Toast.LENGTH_SHORT).show();
                    chats = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        //   String class_id = jsonObject1.getString("class_id");
                        String user_name = jsonObject1.getString("UserName");
                        String message = jsonObject1.getString("Text");
                        String timechat = jsonObject1.getString("AddDate");
                        String UserPic = jsonObject1.getString("UserPic");
                        // String[] split=timechat.split(" ");
                        time = timechat.substring(11, 16);
                        Chat c = new Chat();
                        c.setMessage(message);
                        c.setSender(user_name);
                        c.settimechat(time);
                        c.setUserPic(UserPic);
                        //c.settimechat(split[1]);
                        chats.add(c);
//                            dlist.add(ss);
                    }
                    ada = new MessageAdapter(getApplicationContext(), chats);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubePlayerLiveClasses.this);
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                    linearLayoutManager.setStackFromEnd(true);
//                    recyclerView_chat.setLayoutManager(linearLayoutManager);
//                    recyclerView_chat.setAdapter(ada);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(YoutubePlayerLiveClasses.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void showChatView() {
//        rr_chat.setVisibility(View.GONE);
//        Button cancelCmd = findViewById(R.id.cancel_alert_dialog);
//        edt_Sent = findViewById(R.id.edt_sent);
////        TextView textView = findViewById(R.id.class_name);
////        textView.setText(sharedPreferences.getString("course", ""));
//        ImageView sent_msg = findViewById(R.id.btn_sent);
//        progressBar_chat = findViewById(R.id.progressBar_chat);
//        cancelCmd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rr_chat.setVisibility(View.GONE);
//            }
//        });
//        showChatUrl("0", recyclerView_chat);
//        sent_msg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //video pause
//                if (edt_Sent.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "Plz enter your text", Toast.LENGTH_SHORT).show();
//                } else {
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("UserId", user_id);
//                        jsonObject.put("UserName", user_name);
//                        jsonObject.put("UserType", "");
//                        jsonObject.put("UserPic", user_pic);
//                        jsonObject.put("ClassId", LIVE_CLASS_ID);
//                        jsonObject.put("Time", currentTime);
//                        jsonObject.put("Text", edt_Sent.getText().toString().trim());
//                        if (isNetworkConnected()) {
//                            // edt_Sent.setText("");
//                            setMessageChat(jsonObject);
//                            //firebase
//                        } else {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    public void setMessageChat1(JSONObject jsonObject) {
//        Utils.showProgressDialog(this, "Message...");
//        Utils.showProgress();
//        progressBar_chat.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.SET_CHAT, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("chatsetdata1:-", response.toString());
                        if (response != null) {
//                            Chat chat = new Chat();
//                            chat.setMessage(edt_Sent1.getText().toString());
//                            chat.settimechat(time);
//                            chat.setSender(user_name);
//                            chats.add(chat);
//                            ada.notifyDataSetChanged();
//                            recyclerView_chat1.scrollToPosition(chats.size() - 1);
                            edt_Sent1.setText("");
//                            Utils.dissmisProgress();
                            //progressBar_chat.setVisibility(View.GONE);
                            String mssg = null;
                            int status;
                            try {
                                Toast.makeText(getApplicationContext(), "Message added!!", Toast.LENGTH_SHORT).show();
                                showChatUrl1("0", recyclerView_chat1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjReq);
    }

    private void showChatUrl1(final String pos, final RecyclerView r) {
        String getChatUser = BaseUrl.GET_CHAT + "/" + LIVE_CLASS_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getChatUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("chat", response);
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    //  Toast.makeText(getApplicationContext(), "response :  "+response, Toast.LENGTH_SHORT).show();
                    chats = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        //   String class_id = jsonObject1.getString("class_id");
                        String user_name = jsonObject1.getString("UserName");
                        String message = jsonObject1.getString("Text");
                        String timechat = jsonObject1.getString("AddDate");
                        String UserPic = jsonObject1.getString("UserPic");
                        // String[] split=timechat.split(" ");
                        time = timechat.substring(11, 16);
                        Chat c = new Chat();
                        c.setMessage(message);
                        c.setSender(user_name);
                        c.settimechat(time);
                        c.setUserPic(UserPic);
                        //c.settimechat(split[1]);
                        chats.add(c);
//                            dlist.add(ss);
                    }
                    ada = new MessageAdapter(getApplicationContext(), chats);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubePlayerLiveClasses.this);
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerView_chat1.setLayoutManager(linearLayoutManager);
                    recyclerView_chat1.setAdapter(ada);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(YoutubePlayerLiveClasses.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void showChatView1() {
        // rr_chat.setVisibility(View.GONE);
        showChatUrl1("0", recyclerView_chat1);
        btn_Sent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //video pause
                if (edt_Sent1.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter your text", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("UserId", user_id);
                        jsonObject.put("UserName", user_name);
                        jsonObject.put("UserType", "");
                        jsonObject.put("UserPic", user_pic);
                        jsonObject.put("ClassId", LIVE_CLASS_ID);
                        jsonObject.put("Time", currentTime);
                        jsonObject.put("Text", edt_Sent1.getText().toString().trim());
                        if (isNetworkConnected()) {
                            // edt_Sent.setText("");
                            setMessageChat1(jsonObject);
                            //firebase
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.plz_check_network_connection), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("MissingPermission")
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void downloadVideo(String url) {
        Uri Download_Uri = Uri.parse(url);
        request = new android.app.DownloadManager.Request(Download_Uri);
        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI | android.app.DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        // Visibility of the download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Downloading");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File");
        request.setVisibleInDownloadsUi(false);
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(YoutubePlayerLiveClasses.this, Environment.DIRECTORY_DOCUMENTS, LICLASS_NAME); //For private destination
        //Set the local destination for the downloaded file to a path within the application's external files directory
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, "jay.mp4"); // for public destination
        android.app.DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloading = true;
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadID);
                    Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                    final double dl_progress = (bytes_downloaded / bytes_total) * 100;
                    int percentage = (bytes_downloaded * 100 / bytes_total);
                    Log.d("progressssssss:-", percentage + "");
                    Log.d("bytes_downloaded:-", bytes_downloaded + "");
                    Log.d("bytes_total:-", bytes_total + "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(percentage);
                            txtProgress.setText(percentage + "%");
                            if (bytes_downloaded == bytes_total) {
                                relay_progress.setVisibility(View.GONE);
                            }
//                            pro_txt.setText(""+percentage);
//                            mProgressDialog.setProgress(percentage);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //pStatus++;
                    Log.d("setMassage", statusMessage(cursor));
                    cursor.close();
                }
            }
        }).start();
        Toast.makeText(this, "download id:-" + downloadID, Toast.LENGTH_SHORT).show();
    }

    private String statusMessage(Cursor c) {
        String msg = "???";
        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;
            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;
            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;
            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                break;
            default:
                msg = "Download is nowhere in sight";
                break;
        }
        return (msg);
    }
}