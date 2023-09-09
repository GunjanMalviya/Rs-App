package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.io.File;

public class PdfViewDemo extends AppCompatActivity {
static PDFView pdfView;
String title;
    ImageView imgBack;
    TextView tv_toolbar_title;
String pdfurl="https://www.cbic.gov.in/resources//htdocs-cbec/gst/156-12-2021%20GST%20Circular.pdf;jsessionid=38AE7986C330C7B68F617703B56C997C";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        setContentView(R.layout.activity_pdf_view_demo);
        pdfView=findViewById(R.id.pdfView);
        imgBack=findViewById(R.id.imgBack);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        title=getIntent().getStringExtra("PDFName");
        Log.d("title",title);
        tv_toolbar_title.setText(title);
        File file= new File(PdfViewDemo.this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),title);

        Log.d("file:--",file.toString());
//        pdfView.fromUri(Uri.parse(String.valueOf(file)))
//
//                .load();
        pdfView.fromFile(file)
                .load();

    }

    // create an async task class for loading pdf file from URL.
//    static class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
//        @Override
//        protected InputStream doInBackground(String... strings) {
//            // we are using inputstream
//            // for getting out PDF.
//            InputStream inputStream = null;
//            try {
//                URL url = new URL(strings[0]);
//                // below is the step where we are
//                // creating our connection.
//                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//                if (urlConnection.getResponseCode() == 200) {
//                    // response is success.
//                    // we are getting input stream from url
//                    // and storing it in our variable.
//                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                }
//
//            } catch (IOException e) {
//                // this is the method
//                // to handle errors.
//                e.printStackTrace();
//                return null;
//            }
//            return inputStream;
//        }
//
//        @Override
//        protected void onPostExecute(InputStream inputStream) {
//            // after the execution of our async
//            // task we are loading our pdf in our pdf view.
//            pdfView.fromStream(inputStream).load();
//        }
//    }
}