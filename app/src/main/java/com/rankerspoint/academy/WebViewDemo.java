package com.rankerspoint.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewDemo extends AppCompatActivity {
    WebView mWebView;
    ProgressBar progressBar;
    String video_url = "KK9bwTlAvgo", html = "";
    @SuppressLint("JavascriptInterface")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_demo);

        mWebView = findViewById(R.id.web);
        mWebView.setBackgroundColor(Color.parseColor("#ffffff"));

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String frameVideo = "<html><body>Youtube video .. <br><iframe src=\"https://www.youtube-nocookie.com/embed/QH2-TGUlwu4?vq=hd1080&autoplay=1&loop=1&modestbranding=1&showinfo=0&rel=0&cc_load_policy=1&iv_load_policy=3&theme=light&fs=0&color=white&autohide=0&disablekb=1\" width=\"100%\" height=\"500\" frameborder=\"0\"></iframe></body></html>";
        mWebView.loadData(frameVideo, "text/html", "utf-8");
      //  mWebView.loadUrl("https://www.youtube.com/embed/j8HFjmEUyBM");
        mWebView.setWebViewClient(new WebViewClient());
    }
}