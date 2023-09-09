package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

public class MocktestViewAnswerWebView extends AppCompatActivity {
    private WebView webView;
    private WebViewClient client;
    ProgressDialog progressDialog;
    String examid="",userid="",urlquiz="";
    String str = BaseUrl.BASE_URL_QUIZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocktest_view_answer_web_view);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);    examid=getIntent().getStringExtra("EXAM_ID");
        userid=getIntent().getStringExtra("USERID");
        urlquiz=str+"/"+"home/MockTestSolution?UserId="+userid+"&ExamId="+examid.trim();
        Log.d("webviewurl",urlquiz);
        webView = findViewById(R.id.web);

        webView.setBackgroundColor(Color.parseColor("#ffffff"));
        startWebView(urlquiz);
    }

    private void startWebView(String url) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);




        //  webView.addJavascriptInterface(new WebAppInterface(this), "submit");
        progressDialog = new ProgressDialog(MocktestViewAnswerWebView.this,R.style.AlertDialog);


        progressDialog.setCancelable(false);
        progressDialog.show();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
//                webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();", new ValueCallback<String>() {
//
//                    @Override
//                    public void onReceiveValue(final String value) {
//                        Log.d("valuehtml:-", value.toString());
//                        if(value.contains("Your Exam Result"))
//                        {
//                            Intent intent=new Intent(MocktestViewAnswerWebView.this,ResultWebView.class);
//                            intent.putExtra("EXAMID",examid);
//                            startActivity(intent);
//                        }
//                        /*JsonReader reader = new JsonReader(new StringReader(value));
//                        reader.setLenient(true);
//                        try {
//                            if (reader.peek() == JsonToken.STRING) {
//                                String domStr = reader.nextString();
//                                if (domStr != null) {
//                                    Toast.makeText(MockTestWebView.this, "reader:--" + domStr, Toast.LENGTH_SHORT).show();
//                                    Log.d("ReaderHtml:-", domStr.toString());
//                                }
//                            }
//                        } catch (IOException e) {
//                            // handle exception
//                        }*/
//                    }
//                });

                if (progressDialog.isShowing()) {

                    progressDialog.dismiss();

                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MocktestViewAnswerWebView.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MocktestViewAnswerWebView.this,ResultWebView.class);
        intent.putExtra("EXAMID",examid);
        startActivity(intent);
        super.onBackPressed();
    }

//    public class WebAppInterface {
//        Context mContext;
//
//        /** Instantiate the interface and set the context */
//        WebAppInterface(Context c) {
//            mContext = c;
//        }
//
//        @JavascriptInterface
//        public void performClick() {
//            Intent intRef=new Intent(MockTestWebView.this,Dashboard.class);
//            startActivity(intRef);
//        }
//    }
}