package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

public class MockTestWebView extends AppCompatActivity {
    private WebView webView;
    private WebViewClient client;
    ProgressDialog progressDialog;
    String examid="",userid="",urlquiz="",status="",FeeStatusMentor="";
    String str = BaseUrl.BASE_URL_QUIZ;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_mock_test_web_view);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        examid=getIntent().getStringExtra("EXAM_ID");
        userid=getIntent().getStringExtra("USERID");
        urlquiz=str+"/"+"Home/AppMockTest?UserId="+userid+"&ExamId="+examid.trim();
        Log.d("webviewurl",urlquiz);
        webView = findViewById(R.id.web);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        status = preferences.getString("Status", "");

        FeeStatusMentor = preferences.getString("FeeStatusMentor", "");
        Log.d("paidstatus:--",status+"Feestatus:--"+FeeStatusMentor);
        webView.setBackgroundColor(Color.parseColor("#ffffff"));
        if (FeeStatusMentor.equals("Free"))
        {
            startWebView(urlquiz);
        }else {
            if (FeeStatusMentor.equals("Paid"))
            {
                if (status.equals("True"))
                {
                    startWebView(urlquiz);
                }else
                {
                    Intent intent=new Intent(MockTestWebView.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }

    }

    private void startWebView(String url) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);




      //  webView.addJavascriptInterface(new WebAppInterface(this), "submit");
        progressDialog = new ProgressDialog(MockTestWebView.this,R.style.AlertDialog);


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
                webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();", new ValueCallback<String>() {

                    @Override
                    public void onReceiveValue(final String value) {
                        Log.d("valuehtml:-", value.toString());
if(value.contains("Your Exam Result"))
{
    Intent intent=new Intent(MockTestWebView.this,ResultWebView.class);
    intent.putExtra("EXAMID",examid);
    startActivity(intent);
}
                        /*JsonReader reader = new JsonReader(new StringReader(value));
                        reader.setLenient(true);
                        try {
                            if (reader.peek() == JsonToken.STRING) {
                                String domStr = reader.nextString();
                                if (domStr != null) {
                                    Toast.makeText(MockTestWebView.this, "reader:--" + domStr, Toast.LENGTH_SHORT).show();
                                    Log.d("ReaderHtml:-", domStr.toString());
                                }
                            }
                        } catch (IOException e) {
                            // handle exception
                        }*/
                    }
                });

                if (progressDialog.isShowing()) {

                    progressDialog.dismiss();

                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MockTestWebView.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(url);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(MockTestWebView.this, getResources().getString(R.string.back_press_disabled), Toast.LENGTH_SHORT).show();
        }

        return false;
    }
//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(MockTestWebView.this,ResultWebView.class);
//        intent.putExtra("EXAMID",examid);
//        startActivity(intent);
//        super.onBackPressed();
//    }

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