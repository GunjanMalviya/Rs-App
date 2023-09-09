package com.rankerspoint.academy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Doubts_Topic extends AppCompatActivity {
RadioGroup radioGroup1;
RadioButton radio0,radio1,radio2;
Button btnNext;
LinearLayout linearlayout_camera,linearlayout_cancel;
TextView tv_toolbar_title,tv_toolbar_date;
EditText edt_doubt_title,edt_add_description;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_FROM_GALLERY = 1;
int seletionpos=0;
    String courseid="",catId="",subCatId="",uname="",radiockeckedtext="",UserId="",user_name="";
    ImageView imgBack,close_image;
    private Bitmap bitmap;
    Context context;
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts__topic); Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
        radioGroup1 = findViewById(R.id.radioGroup1);
        btnNext = findViewById(R.id.btnNext);
        radio0=findViewById(R.id.radio0);
        radio1=findViewById(R.id.radio1);
        radio2=findViewById(R.id.radio2);
        SharedPreferences preferences =getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        courseid = preferences.getString("CourseId", "CourseId");
        catId = preferences.getString("CategoryId", "CategoryId");
        subCatId = preferences.getString("SubCategoryId", "SubCategoryId");
        UserId = preferences.getString("user_id", "user_id");
        user_name = preferences.getString("Firstname", "");

        Log.d("CourseId",courseid+UserId);

        progressDialog = new ProgressDialog(Doubts_Topic.this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        context=this;
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText(user_name);
        tv_toolbar_date = findViewById(R.id.tv_toolbar_date);
        linearlayout_camera=findViewById(R.id.linearlayout_camera);
        linearlayout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        seletionpos = radioGroup1.getCheckedRadioButtonId();
        linearlayout_cancel=findViewById(R.id.linearlayout_cancel);
        close_image=findViewById(R.id.close_image);
        close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearlayout_camera.setVisibility(View.VISIBLE);
                linearlayout_cancel.setVisibility(View.GONE);
            }
        });

        edt_doubt_title = findViewById(R.id.edt_doubt_title);
        edt_add_description = findViewById(R.id.edt_add_description);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        tv_toolbar_date.setText(formattedDate);

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_doubt_title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter title !!", Toast.LENGTH_SHORT).show();
                } else if (edt_add_description.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter description !!", Toast.LENGTH_SHORT).show();

                } else if (radioGroup1.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Plz select type !!", Toast.LENGTH_SHORT).show();

                }
                else {
                    progressDialog.show();
                    addDoubt_Details();
                }
            }
        });



        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        btnNext.setBackgroundColor(getResources().getColor(R.color.theme_purple));
                        radiockeckedtext=radio0.getText().toString();
                         break;
                    case R.id.radio1:
                        btnNext.setBackgroundColor(getResources().getColor(R.color.theme_purple));
                        radiockeckedtext=radio1.getText().toString();
                        break;

                    case R.id.radio2:
                        btnNext.setBackgroundColor(getResources().getColor(R.color.theme_purple));
                        radiockeckedtext=radio2.getText().toString();
                        break;
                }

            }
        });
        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.white_color));
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
//        getWindow().setStatusBarColor(ContextCompat.getColor(Doubts_Topic.this,R.color.white_color));// set status background white
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Doubts_Topic.this,R.style.MyAlertDialogStyle);
        builder.setIcon(R.drawable.my_logo);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else  if (options[item].equals("Take Photo"))
                {

//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode== PICK_FROM_GALLERY)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "gallery permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                photo.compress(Bitmap.CompressFormat.JPEG,40,outFile);
                //userImage.setImageBitmap(photo);
                linearlayout_camera.setVisibility(View.GONE);
                linearlayout_cancel.setVisibility(View.VISIBLE);
                // MyProfileImage=imageToString(((BitmapDrawable) userImage.getDrawable()).getBitmap());
                // Toast.makeText(contexts, "MyProfileImage :", Toast.LENGTH_SHORT).show();
                //uploadimage();


            } else if (requestCode == 2) {

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    //filePath = imgDecodableString;

                    Bitmap b = BitmapFactory.decodeFile(imgDecodableString);
                    Bitmap out = Bitmap.createScaledBitmap(b, 100, 100, false);

                    //getting image from gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);


                    File file = new File(imgDecodableString);

                    FileOutputStream fOut;
                    try {
                        fOut = new FileOutputStream(file);
                        out.compress(Bitmap.CompressFormat.JPEG, 40, fOut);
                        fOut.flush();
                        fOut.close();
                        //b.recycle();
                        //out.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (requestCode == 2) {

                      //  userImage.setImageBitmap(bitmap);
                        linearlayout_camera.setVisibility(View.GONE);
                        linearlayout_cancel.setVisibility(View.VISIBLE);
                        //MyProfileImage=imageToString(((BitmapDrawable) userImage.getDrawable()).getBitmap());
                        //  Toast.makeText(contexts, "MyProfileImage : ", Toast.LENGTH_SHORT).show();
                        //uploadimage();

                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
    private void addDoubt_Details() {
        //getSubCourseModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETADDDOUBTSDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Doubt_topic", response);
                try {
                   // getSubCourseModels.clear();

                    //JSONArray jsonObject = new JSONArray(response);
                    Log.d("Doubt_topic_xs",response.toString());


                 
                    if (response.equals(null))
                    {
                        Toast.makeText(context, "Wrong Submission !!!", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(context, "Successfully submit !!", Toast.LENGTH_SHORT).show();

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
                   params.put("VideoId","");
                    params.put("TopicId","");
                    params.put("SubjectId","");
                    params.put("CourseId",courseid);
                    params.put("CategoryId",catId);
                    params.put("SubCategoryId",subCatId);
                    params.put("UserId",UserId);
                    params.put("UserName",user_name);
                    params.put("UserPic","");
                    params.put("Pic","");
                    params.put("Title",edt_doubt_title.getText().toString());
                    params.put("Description",edt_add_description.getText().toString());
                    params.put("Type",radiockeckedtext);

                    Log.d("paramdoubts:--",params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}