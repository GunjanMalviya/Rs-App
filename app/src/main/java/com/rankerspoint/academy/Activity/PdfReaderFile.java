package com.rankerspoint.academy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.OfflinePdfModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class PdfReaderFile extends AppCompatActivity  {

    ProgressDialog mProgressDialog;
    private String url= BaseUrl.BANN_IMG_URL;
    private TextView txtProgress;
    private ProgressBar progressBar;
    ImageView imgBack,option;
    PDFView pdfView;
    private android.app.DownloadManager.Request request;
    TextView tv_toolbar_title,pro_txt;
    private LinearLayout llb_out2,llb_out1;
    RelativeLayout relay_progress;
    private List<OfflinePdfModel> getAllQuestionModels = new ArrayList<>();
    String pdfurl="",pdftitle="",completeurl="",UserId="",pdfurl2="",sharestatus="",CONTENTID="",Pic="",completeurl1="",FeeStatusMentor="",status="",categoryid="";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader_file);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  //set the Visibility of the progressbar to visible
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        GetFiles(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+getPackageName()+"/files"+"/"+"Documents");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        imgBack = findViewById(R.id.imgBack);
        option = findViewById(R.id.option);
        relay_progress = findViewById(R.id.relay_progress);
       // pro_txt = findViewById(R.id.pro_txt);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        llb_out1 = findViewById(R.id.llb_out1);
        llb_out2 = findViewById(R.id.llb_out2);
        pdfView = findViewById(R.id.pdfView);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.custom_progressbar_drawable);
        progressBar.setProgress(0);   // Main Progress
        progressBar.setSecondaryProgress(100); // Secondary Progress
        progressBar.setMax(100); // Maximum Progress
        progressBar.setProgressDrawable(drawable);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        mProgressDialog = new ProgressDialog(PdfReaderFile.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);

        mProgressDialog.show();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);


        //initialize the pdfLayout
        pdfurl=getIntent().getStringExtra("PDFURL");
        pdfurl2=getIntent().getStringExtra("PDFURL2");
        pdftitle=getIntent().getStringExtra("PDFTITLE");
        CONTENTID=getIntent().getStringExtra("CONTENTID");
        Pic=getIntent().getStringExtra("PIC");

        tv_toolbar_title.setText(pdftitle);

        Log.d("pdf_url1",pdfurl+":----pdf_url2"+pdfurl2);
        completeurl=url+pdfurl.trim();
        completeurl1=url+pdfurl2.trim();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        status = preferences.getString("Status", "");
        UserId = preferences.getString("user_id", "");
        categoryid = preferences.getString("CAT_ID", "");
        sharestatus = preferences.getString("SHARE", "");

        FeeStatusMentor = preferences.getString("FeeStatusMentor", "");
        Log.d("FeeStatusMentor",FeeStatusMentor+"/"+status+"/"+UserId+"/"+categoryid+"/"+sharestatus);
        if (FeeStatusMentor.equals("Free"))
        {
            init();
        }else {
            if (FeeStatusMentor.equals("Paid"))
            {
                if (status.equals("True"))
                {
                    init();
                }else
                {
                    Intent intent=new Intent(PdfReaderFile.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }

    }
 public void init()
    {
    llb_out1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new RetrivePDFfromUrl().execute(completeurl);
            llb_out2.setVisibility(View.VISIBLE);
            llb_out1.setVisibility(View.GONE);
        }
    });
    llb_out2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new RetrivePDFfromUrl().execute(completeurl1);
            llb_out1.setVisibility(View.VISIBLE);
            llb_out2.setVisibility(View.GONE);
        }
    });

    new RetrivePDFfromUrl().execute(completeurl);
Log.d("pdfurlloading",completeurl);
Log.d("pdfurlloading2",completeurl1);
    option.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            PopupMenu popupMenu=new PopupMenu(PdfReaderFile.this,option);
            popupMenu.inflate(R.menu.menu_pdf);
            if (sharestatus.equalsIgnoreCase("Yes"))
            {

                popupMenu.getMenu().findItem(R.id.item_share).setVisible(true);
            }else if(sharestatus.equalsIgnoreCase("")) {

               popupMenu.getMenu().findItem(R.id.item_share).setVisible(false);
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId())
                    {
                        case R.id.item_download:
                            relay_progress.setVisibility(View.VISIBLE);
                            boolean flg=false;
                            for(OfflinePdfModel offlinePdfModel:getAllQuestionModels)
                            {
                                Toast.makeText(PdfReaderFile.this, "name:=="+offlinePdfModel.getVideoName(), Toast.LENGTH_SHORT).show();
                                if(pdftitle.equalsIgnoreCase(offlinePdfModel.getVideoName()))
                                {
                                    flg=true;
                                    break;
                                }
                            }
                            if(!flg) {
                                downloadVideo(completeurl);

                                relay_progress.setVisibility(View.VISIBLE);
                            }
                            else{

                                Toast.makeText(PdfReaderFile.this, "This video is already downloaded...!!!", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case R.id.item_save:
                            addSaveNotes(categoryid,UserId,pdftitle,CONTENTID,Pic,FeeStatusMentor,pdfurl,status,FeeStatusMentor);
                         break;

                        case R.id.item_share:

                            sharedownloadafter(completeurl);


                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    });
}

//    private void startWebView(String urlpdf) {
//
//
//
//        progressDialog = new ProgressDialog(PdfReaderFile.this,R.style.AlertDialog);
//
//
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//    }
private void addSaveNotes(String CategoryId,String UserId,String Title, String ContentId,String Pic1,String Status,String Videourl,String status,String FeeStatus ) {
    //  getPreEaxmModels.clear();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.ADDSAVENOTESDETAILS, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("getaddsavenotespdf", response);
            try {
                // getPreEaxmModels.clear();


                if (response.length()>0) {

                    Toast.makeText(PdfReaderFile.this, "Save Notes !!", Toast.LENGTH_SHORT).show();
                }else
                {
                    //Toast.makeText(PdfReaderFile.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //  progressDialog.dismiss();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //progressDialog.dismiss();
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<>();

            params.put("CategoryId",CategoryId);
            params.put("UserId",UserId);
            params.put("Title",Title);
            params.put("ContentType","Pdf");
            params.put("ContentId",ContentId);
            params.put("VideoType","");
            params.put("Pic1",Pic1);
            params.put("Status",Status);
            params.put("Extra1",Videourl);
            params.put("Extra2",status);
            params.put("Extra3",FeeStatus);

            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.getCache().clear();
    requestQueue.add(stringRequest);
}

   public class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {



       @Override
        protected InputStream doInBackground(String... strings) {

            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
           // pdfView.fromStream(inputStream).load();
            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                mProgressDialog.dismiss();

                }
            }).load();

        }

    }
    public void sharedownloadafter(String url) {
        Uri Download_Uri = Uri.parse(url);
        request = new android.app.DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI | android.app.DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        // Visibility of the download Notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Share");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Share File");
        request.setVisibleInDownloadsUi(false);
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(PdfReaderFile.this, Environment.DIRECTORY_DOCUMENTS, pdftitle+".pdf".trim()); //For private destination

        //Set the local destination for the downloaded file to a path within the application's external files directory
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, "jay.mp4"); // for public destination

        android.app.DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
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

                    int percentage =  (bytes_downloaded * 100 / bytes_total);
                    Log.d("progressssssss:-",percentage+"");
                    Log.d("bytes_downloaded:-",bytes_downloaded+"");
                    Log.d("bytes_total:-",bytes_total+"");

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            progressBar.setProgress(percentage);
                            txtProgress.setText(percentage + "%");
                            if (bytes_downloaded==bytes_total) {
                                relay_progress.setVisibility(View.GONE);


                                            try {


                                     File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+getPackageName()+"/files"+"/"+"Documents", pdftitle+".pdf");
                                  //  File outputFile = new File(PdfReaderFile.this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), pdftitle + System.currentTimeMillis() + ".pdf");

                                               // File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), pdftitle + System.currentTimeMillis() + ".pdf");



                                                // Uri uri = Uri.fromFile(outputFile);
                                    Uri uri = FileProvider.getUriForFile(PdfReaderFile.this, getApplicationContext().getPackageName()+".fileprovider", outputFile);
                                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                    Log.d("datauri:--", uri.toString());
                                    intent.setType("application/pdf");
                                                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                                                for (ResolveInfo resolveInfo : resInfoList) {
                                                    String packageName = resolveInfo.activityInfo.packageName;
                                                    grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                }
                                    startActivity(Intent.createChooser(intent, "Share Pdf"));
                                }catch (Exception ex)
                                            {
                                                Toast.makeText(PdfReaderFile.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
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
        Toast.makeText(this, "Share File"+downloadID, Toast.LENGTH_SHORT).show();
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
        request.setDestinationInExternalFilesDir(PdfReaderFile.this, Environment.DIRECTORY_DOCUMENTS, pdftitle+".hsl".trim()); //For private destination

        //Set the local destination for the downloaded file to a path within the application's external files directory
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, "jay.mp4"); // for public destination

        android.app.DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
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

                    int percentage =  (bytes_downloaded * 100 / bytes_total);
                    Log.d("progressssssss:-",percentage+"");
                    Log.d("bytes_downloaded:-",bytes_downloaded+"");
                    Log.d("bytes_total:-",bytes_total+"");

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            progressBar.setProgress(percentage);
                            txtProgress.setText(percentage + "%");
                            if (bytes_downloaded==bytes_total)
                            {
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
        Toast.makeText(this, "download id:-"+downloadID, Toast.LENGTH_SHORT).show();
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

    private void showPermissionDialog() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                startActivityForResult(intent, 2000);
            } catch (Exception e) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2000);

            }

        } else
            ActivityCompat.requestPermissions(PdfReaderFile.this,
                    new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 333);
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getApplicationContext(),
                    WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 333) {
            if (grantResults.length > 0) {
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (read && write){

                }else {

                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                } else {

                }
            }
        }
    }
    private String GetFiles(String path) {
        getAllQuestionModels.clear();
        File file=new File(path);
        File[] allfiles=file.listFiles();
       // Log.d("pathurl:--",path.toString()+"allfiles:--"+allfiles.length);

        if(allfiles==null || allfiles.length==0) {
            return "";
        }
        else {
            for(int i=0;i<allfiles.length;i++) {
                OfflinePdfModel offlinePdfModel=new OfflinePdfModel();
                offlinePdfModel.setVideoName(allfiles[i].getName());
                getAllQuestionModels.add(offlinePdfModel);
            }
        }
        return "abc";
    }

}