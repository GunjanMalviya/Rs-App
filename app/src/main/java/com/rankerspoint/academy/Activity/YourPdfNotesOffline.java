package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.Adapter.GetAllOfflinePdfAdapter;
import com.rankerspoint.academy.Model.OfflinePdfModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YourPdfNotesOffline extends AppCompatActivity {
    ImageView imgBack;
    RecyclerView recyclerViewvideo;
    GetAllOfflinePdfAdapter getAllOfflineAdapter;
    TextView tv_toolbar_title;
    LinearLayout lnr_layout;
    public static BottomSheetBehavior mBehavior;
    public static BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    private List<OfflinePdfModel> getAllQuestionModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_pdf_notes_offline);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        imgBack=findViewById(R.id.imgBack);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        lnr_layout=findViewById(R.id.lnr_layout);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_toolbar_title.setText("Downloaded PDF");
        // ArrayList<String> arr1=GetFiles(OfflineVideoList.this.getExternalFilesDir(Environment.DIRECTORY_MOVIES).list());

//        adapter= new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_expandable_list_item_1,arr1);
        recyclerViewvideo =  findViewById(R.id.recyclerViewvideo);
//        lv.setAdapter(adapter);

        recyclerViewvideo.setLayoutManager(new GridLayoutManager(YourPdfNotesOffline.this,1));
        String getUrl=GetFiles(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+getPackageName()+"/files"+"/"+"Documents");
        Log.d("filepath:--",getUrl.toString());
    }
    private String GetFiles(String path) {
        getAllQuestionModels.clear();
        File file=new File(path);
        File[] allfiles=file.listFiles();
        if(allfiles !=null && allfiles.length>0) {
            for(int i=0;i<allfiles.length;i++) {
                recyclerViewvideo.setVisibility(View.VISIBLE);
                lnr_layout.setVisibility(View.GONE);
                OfflinePdfModel offlinePdfModel=new OfflinePdfModel();
                offlinePdfModel.setVideoName(allfiles[i].getName());
                offlinePdfModel.setPdfSize(allfiles[i].length());
             Toast.makeText(this, "size:-"+allfiles[i].length(), Toast.LENGTH_SHORT).show();
                getAllQuestionModels.add(offlinePdfModel);
            }
        }
        else{
            recyclerViewvideo.setVisibility(View.GONE);
            lnr_layout.setVisibility(View.VISIBLE);
        }
       // Toast.makeText(this, "Data count : "+getAllQuestionModels.size(), Toast.LENGTH_SHORT).show();
        getAllOfflineAdapter=new GetAllOfflinePdfAdapter(YourPdfNotesOffline.this,getAllQuestionModels);
        recyclerViewvideo.setAdapter(getAllOfflineAdapter);
        return "abc";
    }

    public void showBottomSheetDialog(String Filename) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.bottomsheet_video_offline, null);
        Button bt_cancel=view.findViewById(R.id.bt_cancel);
        Button bt_Delete=view.findViewById(R.id.bt_Delete);
        ImageView img_close=view.findViewById(R.id.img_close);
        mBottomSheetDialog = new BottomSheetDialog(this,R.style.Theme_Design_BottomSheetDialog);
        mBottomSheetDialog.setContentView(view);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        bt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+getPackageName()+"/files"+"/"+"Documents/"+Filename);
                if(f.exists()) {
                    f.delete();
                    Toast.makeText(YourPdfNotesOffline.this, "File deleted...!!!", Toast.LENGTH_SHORT).show();
                    GetFiles(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"android"+"/"+"data"+"/"+getPackageName()+"/files"+"/"+"Documents");
                    mBottomSheetDialog.dismiss();

                }
                else{
                    Toast.makeText(YourPdfNotesOffline.this, "File not found...!!!", Toast.LENGTH_SHORT).show();
                }
                //remove file
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }



}