package com.rankerspoint.academy.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.OfflineVideoModel;
import com.rankerspoint.academy.Utils.Tools;
import com.rankerspoint.academy.Utils.TrackSelectionDialog;
import com.rankerspoint.academy.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EmbadedPlayer extends AppCompatActivity {
    private boolean isShowingTrackSelectionDialog;
    private DefaultTrackSelector trackSelector;
    String[] speed = {"0.25x", "0.5x", "Normal", "1.5x", "2x"};
    String url = BaseUrl.BANN_IMG_URL.trim();
   // String streamUrl = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4";
   // String testUrl = "https://www.youtube.com/watch?v=uZnWUZW1hQo";
    private ExoPlayer player;
    String geturl="",streamUrl="",status="",FeeStatusMentor="",contentid="",categoryid="",userId="",pic="";
    private TextView txtProgress;
    private ProgressBar progressBar;
    RelativeLayout relay_progress;

    PlayerView playerView;
    private StringBuilder mFormatBuilder;
    private android.app.DownloadManager.Request request;
    SimpleExoPlayer simpleExoPlayer;
    ImageView download_item,img_save_video;
    private List<OfflineVideoModel> getAllQuestionModels = new ArrayList<>();
String title="";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        GetFiles(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+getPackageName()+"/files"+"/"+"Movies");
        //AudioManager.setAllowedCapturePolicy(ALLOW_CAPTURE_BY_NONE
        HlsPlaylistParser parser=new HlsPlaylistParser();
        setContentView(R.layout.activity_embaded_player);
        geturl=getIntent().getStringExtra("VIDEOURLNORMAL");
        title=getIntent().getStringExtra("TITLE");
        pic=getIntent().getStringExtra("PIC");


        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
        categoryid = preferences.getString("CAT_ID", "");
        Log.d("categoryidd",categoryid);

        status = preferences.getString("Status", "");
contentid=getIntent().getStringExtra("CONTENTID");
        FeeStatusMentor = preferences.getString("FeeStatusMentor", "");

        Log.d("status:--",status+":--feestructure:--"+FeeStatusMentor);
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
                    Intent intent=new Intent(EmbadedPlayer.this, PaymentCheckOut.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(intent);
                }
            }
        }
    }
public void init()
{
    streamUrl=url.toString().trim()+geturl.trim();
    getFilePlay();
    download_item=findViewById(R.id.download_item);
    img_save_video=findViewById(R.id.img_save_video);
    boolean flg=false;
    for(OfflineVideoModel offlineVideoModel:getAllQuestionModels)
    {
        if(title.equalsIgnoreCase(offlineVideoModel.getVideoName().replace(".mp4","")))
        {
            flg=true;
            break;
        }
    }
    if(!flg) {

    }
    else{

        download_item.setImageDrawable(getResources().getDrawable(R.drawable.ic_download_success));
    }

    download_item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean flg=false;
            for(OfflineVideoModel offlineVideoModel:getAllQuestionModels)
            {
                if(title.equalsIgnoreCase(offlineVideoModel.getVideoName().replace(".mp4","")))
                {
                    flg=true;
                    break;
                }
            }
            if(!flg) {
                downloadVideo(streamUrl);
                download_item.setVisibility(View.GONE);
                relay_progress.setVisibility(View.VISIBLE);
            }
            else{

                Toast.makeText(EmbadedPlayer.this, "This video is already downloaded...!!!", Toast.LENGTH_SHORT).show();
            }
        }
    });
    img_save_video.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu popupMenu=new PopupMenu(EmbadedPlayer.this,img_save_video);
            popupMenu.inflate(R.menu.save_notes_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId())
                    {
                        case R.id.item_save:
                            addSaveNotes(categoryid,userId,title,contentid,pic,FeeStatusMentor,geturl,status,FeeStatusMentor);
                            break;

                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    });
    trackSelector = new DefaultTrackSelector(this);
    simpleExoPlayer = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
    playerView = findViewById(R.id.exoPlayerView);
    playerView.setPlayer(simpleExoPlayer);
    MediaItem mediaItem = MediaItem.fromUri(streamUrl);
    simpleExoPlayer.addMediaItem(mediaItem);
    simpleExoPlayer.prepare();
    simpleExoPlayer.play();

    ImageView farwordBtn = playerView.findViewById(R.id.fwd);
    ImageView rewBtn = playerView.findViewById(R.id.rew);
    ImageView setting = playerView.findViewById(R.id.exo_track_selection_view);
    ImageView speedBtn = playerView.findViewById(R.id.exo_playback_speed);
    TextView speedTxt = playerView.findViewById(R.id.speed);
    relay_progress = playerView.findViewById(R.id.relay_progress);
    // pro_txt = findViewById(R.id.pro_txt);
    txtProgress = playerView.findViewById(R.id.txtProgress);
    progressBar = playerView.findViewById(R.id.progressBar);
    Resources res = getResources();
    Drawable drawable = res.getDrawable(R.drawable.custom_progressbar_drawable);
    progressBar.setProgress(0);   // Main Progress
    progressBar.setSecondaryProgress(100); // Secondary Progress
    progressBar.setMax(100); // Maximum Progress
    progressBar.setProgressDrawable(drawable);

    speedBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            AlertDialog.Builder builder = new AlertDialog.Builder(EmbadedPlayer.this);
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
                        Toast.makeText(EmbadedPlayer.this, "after laod video", Toast.LENGTH_SHORT).show();
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

    // ImageView fullscreenButton = playerView.findViewById(R.id.fullscreen);
//        fullscreenButton.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SourceLockedOrientationActivity")
//            @Override
//            public void onClick(View view) {
//
//
//                int orientation = EmbadedPlayer.this.getResources().getConfiguration().orientation;
//                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    // code for portrait mode
//
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//
//                } else {
//                    // code for landscape mode
//
//                    Toast.makeText(EmbadedPlayer.this, "Land", Toast.LENGTH_SHORT).show();
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//                }
//
//
//            }
//        });


    findViewById(R.id.exo_play).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                simpleExoPlayer.play();
            }catch (Exception ex)
            {
                Toast.makeText(EmbadedPlayer.this, "after load video", Toast.LENGTH_SHORT).show();
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


    setting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                try {
//
//
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo =
//                        Assertions.checkNotNull(trackSelector.getCurrentMappedTrackInfo());
//                TrackSelectionDialog trackSelectionDialog = new TrackSelectionDialog();
//                TrackSelectionDialog mytrackselection = TrackSelectionDialog.createForMappedTrackInfoAndParameters(R.string.app_name,
//                        mappedTrackInfo, trackSelector.getParameters(), false, false, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                DefaultTrackSelector.SelectionOverride selectionOverride = new DefaultTrackSelector.SelectionOverride(0, which);
//                                trackSelector.setParameters(
//                                        trackSelector
//                                                .buildUponParameters()
//                                                .setSelectionOverride(0, trackSelector.getCurrentMappedTrackInfo().getTrackGroups(0), selectionOverride));
//
//
//                            }
//
//
//                        }, new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//
//                            }
//                        }
//                );
//
//                mytrackselection.show(getSupportFragmentManager(), /* tag= */ null);
//
//                }
//                catch (Exception ex)
//                {
//                    Toast.makeText(EmbadedPlayer.this, "ex:--"+ex.getMessage(), Toast.LENGTH_SHORT).show();
//                }




            try {



                if (!isShowingTrackSelectionDialog
                        && TrackSelectionDialog.willHaveContent(trackSelector)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog =
                            TrackSelectionDialog.createForTrackSelector(
                                    trackSelector,
                                    /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);


                }

            }catch (Exception ex)
            {
                Toast.makeText(EmbadedPlayer.this, "after load video", Toast.LENGTH_SHORT).show();
            }
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
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
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
        request.setDestinationInExternalFilesDir(EmbadedPlayer.this, Environment.DIRECTORY_MOVIES, title+".hsl".trim()); //For private destination

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
                                download_item.setVisibility(View.VISIBLE);
                                download_item.setImageDrawable(getResources().getDrawable(R.drawable.ic_download_success));

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

    public void getFilePlay()
    {
        File file= new File(EmbadedPlayer.this.getExternalFilesDir(Environment.DIRECTORY_MOVIES),title);
       // Toast.makeText(this, "file url:-"+file, Toast.LENGTH_SHORT).show();
    }
    private String GetFiles(String path) {
        getAllQuestionModels.clear();
        File file=new File(path);
        File[] allfiles=file.listFiles();
        if(allfiles==null || allfiles.length==0) {
            return "";
        }
        else {
            for(int i=0;i<allfiles.length;i++) {
                OfflineVideoModel offlineVideoModel=new OfflineVideoModel();
                offlineVideoModel.setVideoName(allfiles[i].getName());
                getAllQuestionModels.add(offlineVideoModel);
            }
        }
        return "abc";
    }
    private void addSaveNotes(String CategoryId,String UserId,String Title, String ContentId,String Pic1,String Status,String Videourl,String status,String FeeStatus ) {
        //  getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.ADDSAVENOTESDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getresponeaddsavenotes", response);
                try {
                    // getPreEaxmModels.clear();


                    if (response.length()>0) {

                        Toast.makeText(EmbadedPlayer.this, "Save Notes !!", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        //Toast.makeText(EmbadedPlayer.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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
                params.put("ContentType","Video");
                params.put("ContentId",ContentId);
                params.put("VideoType","Normal");
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
    static void encrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        // Here you read the cleartext.
        FileInputStream fis = new FileInputStream("data/cleartext");
        // This stream write the encrypted text. This stream will be wrapped by another stream.
        FileOutputStream fos = new FileOutputStream("data/encrypted");

        // Length is 16 byte
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(), "AES");
        // Create cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // Wrap the output stream
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        // Write bytes
        int b;
        byte[] d = new byte[8];
        while((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        // Flush and close streams.
        cos.flush();
        cos.close();
        fis.close();
    }

    static void decrypt() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        FileInputStream fis = new FileInputStream("data/encrypted");

        FileOutputStream fos = new FileOutputStream("data/decrypted");
        SecretKeySpec sks = new SecretKeySpec("MyDifficultPassw".getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }
}
