package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.Adapter.GetAllSaveNotesAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.SaveNotesModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveNotesAllPdfandNotes extends AppCompatActivity {

    ImageView imgBack,option;
    RecyclerView recyclerViewvideo;
    GetAllSaveNotesAdapter getAllSaveNotesAdapter;
    TextView tv_toolbar_title;
    LinearLayout lnr_layout;
    public static BottomSheetBehavior mBehavior;
    public static BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    String categoryid="", userid="";
    private List<SaveNotesModel> getSaveNotesModels = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_notes_all_pdfand_notes);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);    progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        imgBack=findViewById(R.id.imgBack);
        option=findViewById(R.id.option);
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
        tv_toolbar_title.setText("All Save Notes..");
        // ArrayList<String> arr1=GetFiles(OfflineVideoList.this.getExternalFilesDir(Environment.DIRECTORY_MOVIES).list());
        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        categoryid = preferences.getString("CAT_ID", "");
        userid = preferences.getString("user_id", "");
//        adapter= new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_expandable_list_item_1,arr1);
        recyclerViewvideo =  findViewById(R.id.recyclerViewvideo);
//        lv.setAdapter(adapter);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PopupMenu popupMenu=new PopupMenu(SaveNotesAllPdfandNotes.this,option);
                popupMenu.inflate(R.menu.menu_save_notes);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.item_All:
                                GetAllSaveNotes(categoryid,userid);
                                break;
                            case R.id.item_Video:
                                GetAllSaveNotesAll(categoryid,userid,"Video");
                                break;
                            case R.id.item_Pdf:
                                GetAllSaveNotesAll(categoryid,userid,"Pdf");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        recyclerViewvideo.setLayoutManager(new GridLayoutManager(SaveNotesAllPdfandNotes.this,1));

        GetAllSaveNotes(categoryid,userid);
    }
    public void GetAllSaveNotes(String CategoryId,String UserId) {
        getSaveNotesModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETALLSAVENOTESCATEGORYANDUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSaveNotes", response);
                try {
                    getSaveNotesModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllResults",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                              recyclerViewvideo.setVisibility(View.VISIBLE);
                              lnr_layout.setVisibility(View.GONE);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("results1", jsonObject1.toString());
                            String SaveNoteId=jsonObject1.getString("SaveNoteId");
                            String CategoryId=jsonObject1.getString("CategoryId");
                            String UserId=jsonObject1.getString("UserId");
                            String Title=jsonObject1.getString("Title");
                            String ContentType=jsonObject1.getString("ContentType");
                            String ContentId=jsonObject1.getString("ContentId");
                            String VideoType=jsonObject1.getString("VideoType");
                            String Pic1=jsonObject1.getString("Pic1");
                            String Extra1=jsonObject1.getString("Extra1");

                            String Extra2=jsonObject1.getString("Extra2");
                            String Extra3=jsonObject1.getString("Extra3");

                            SaveNotesModel getPreEaxmModel=new SaveNotesModel(SaveNoteId,CategoryId,UserId,Title,ContentType,ContentId,VideoType,Pic1,Extra1,Extra2,Extra3);
                            getSaveNotesModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        }
                        getAllSaveNotesAdapter = new GetAllSaveNotesAdapter(SaveNotesAllPdfandNotes.this, getSaveNotesModels);
                        recyclerViewvideo.setAdapter(getAllSaveNotesAdapter);
                        getAllSaveNotesAdapter.notifyDataSetChanged();
                        //  getExamCategoryAdapter.notifyDataSetChanged();


                    }else
                    {
                       lnr_layout.setVisibility(View.VISIBLE);
                        //Toast.makeText(SaveNotesAllPdfandNotes.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("CategoryId",CategoryId);
                params.put("UserId",UserId);
//                params.put("ExamId","ExamvAWRoa8ruUmOpgAVF404aw");
//                params.put("UserId","7309338957");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void showBottomSheetDialog(String saveNotesId) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
       // Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
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
                //remove file
                GetAllDeleteNotes(saveNotesId);
                mBottomSheetDialog.dismiss();
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
    private void GetAllDeleteNotes(String saveid) {

String urlData= BaseUrl.DELETESAVENOTES+"/"+saveid;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, urlData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSaveNotes", response);
                try {

                    if (response.length()>0) {

                        Toast.makeText(SaveNotesAllPdfandNotes.this, "Successfully Deleted !!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SaveNotesAllPdfandNotes.this,SaveNotesAllPdfandNotes.class);
                        startActivity(intent);
                       // GetAllSaveNotes(categoryid,userid);
                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(SaveNotesAllPdfandNotes.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

//                params.put("ExamId","ExamvAWRoa8ruUmOpgAVF404aw");
//                params.put("UserId","7309338957");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    public void GetAllSaveNotesAll(String CategoryId,String UserId,String ContentYpe) {
        getSaveNotesModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETALLSAVENOTESBYCATEGORYUSERANDTYPE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getSaveNotes", response);
                try {
                    getSaveNotesModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getAllResults",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {
                            recyclerViewvideo.setVisibility(View.VISIBLE);
                            lnr_layout.setVisibility(View.GONE);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("results1", jsonObject1.toString());
                            String SaveNoteId=jsonObject1.getString("SaveNoteId");
                            String CategoryId=jsonObject1.getString("CategoryId");
                            String UserId=jsonObject1.getString("UserId");
                            String Title=jsonObject1.getString("Title");
                            String ContentType=jsonObject1.getString("ContentType");
                            String ContentId=jsonObject1.getString("ContentId");
                            String VideoType=jsonObject1.getString("VideoType");
                            String Pic1=jsonObject1.getString("Pic1");
                            String Extra1=jsonObject1.getString("Extra1");

                            String Extra2=jsonObject1.getString("Extra2");
                            String Extra3=jsonObject1.getString("Extra3");

                            SaveNotesModel getPreEaxmModel=new SaveNotesModel(SaveNoteId,CategoryId,UserId,Title,ContentType,ContentId,VideoType,Pic1,Extra1,Extra2,Extra3);
                            getSaveNotesModels.add(getPreEaxmModel);
                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);

                        }
                        getAllSaveNotesAdapter = new GetAllSaveNotesAdapter(SaveNotesAllPdfandNotes.this, getSaveNotesModels);
                        recyclerViewvideo.setAdapter(getAllSaveNotesAdapter);
                        getAllSaveNotesAdapter.notifyDataSetChanged();
                        //  getExamCategoryAdapter.notifyDataSetChanged();


                    }else
                    {
                        lnr_layout.setVisibility(View.VISIBLE);
                       // Toast.makeText(SaveNotesAllPdfandNotes.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("CategoryId",CategoryId);
                params.put("UserId",UserId);
                params.put("ContentType",ContentYpe);
//                params.put("ExamId","ExamvAWRoa8ruUmOpgAVF404aw");
//                params.put("UserId","7309338957");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}