package com.rankerspoint.academy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Model.GetUserAllDoubts;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.GPSTracker;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Utils.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    ImageView imgBack,img_live_location;
    Toolbar toolbar;
    RadioGroup radioGroup1;
    RadioButton radio_male,radio_female;
    TextView tv_toolbar_title;
    private String filePath = "";
    int selectionpo=0;
    private long exitTime = 0;
    CircleImageView userImage;
    RelativeLayout rl_edit_profile_pic,btn_pro_edit;
    private Bitmap bitmap;
    String str = BaseUrl.BASE_URL_QUIZ;
    private String getimage,getimageimg,MyProfileImage="";
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_FROM_GALLERY = 1;
    String TAG = "checkaddress";
    Context context;
    String state="";
    String gender="";
    List<Address> addresses = new ArrayList<>();
    double latitude, longitude;
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    EditText et_pro_state,et_pro_name,et_pro_phone,et_pro_email;
    ProgressDialog progressDialog;
    String userId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  setContentView(R.layout.activity_my_profile);
        tv_toolbar_title=findViewById(R.id.tv_toolbar_title);
        img_live_location=findViewById(R.id.img_live_location);
        et_pro_state=findViewById(R.id.et_pro_state);
        tv_toolbar_title.setText("Edit Profile");
        et_pro_name=findViewById(R.id.et_pro_name);
        et_pro_phone=findViewById(R.id.et_pro_phone);
        et_pro_phone.setEnabled(false);
        et_pro_email=findViewById(R.id.et_pro_email);
        btn_pro_edit=findViewById(R.id.btn_pro_edit);
        radioGroup1=findViewById(R.id.radioGroup1);
        radio_male=findViewById(R.id.radio_male);

        radio_female=findViewById(R.id.radio_female);
        selectionpo = radioGroup1.getCheckedRadioButtonId();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        userId = preferences.getString("user_id", "");
        Log.d("userId:--",userId);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        gender=radio_male.getText().toString();
                        break;
                    case R.id.radio_female:
                        gender=radio_female.getText().toString();
                        break;
                }
            }
        });

        btn_pro_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_pro_name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter name !!", Toast.LENGTH_SHORT).show();
                } else if (et_pro_phone.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter mobile!!", Toast.LENGTH_SHORT).show();
                }
                else if (et_pro_email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter email!!", Toast.LENGTH_SHORT).show();
                }
                else if (et_pro_state.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz enter address!!", Toast.LENGTH_SHORT).show();

                }
                else if (radioGroup1.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Plz select gender !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    UpdatetUserDetails();
                }
            }
        });
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        userImage=findViewById(R.id.userImage);
        setSupportActionBar(toolbar);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
            }
        });
        imgBack.setVisibility(View.GONE);
        img_live_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveLocation();
            }
        });
        rl_edit_profile_pic=findViewById(R.id.rl_edit_profile_pic);
        rl_edit_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  urlquiz=str+"/"+"Home/StudentImage?UserId="+userId.trim();

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(urlquiz));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    context.startActivity(intent);
                }

//                Intent intent=new Intent(MyProfile.this,UploadImageWebView.class);
//                startActivity(intent);
//                ActivityCompat.requestPermissions(MyProfile.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    2);
                //selectImage();
            }
        });


        //location  // check if GPS enabled
        context = this;

        getLocation();
        getUserDetails();
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this,R.style.MyAlertDialogStyle);
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
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(getApplicationContext(), "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, MY_CAMERA_PERMISSION_CODE);
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(), "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }else if (requestCode== PICK_FROM_GALLERY)
//        {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
//            } else {
//                Toast.makeText(getApplicationContext(), "gallery permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
        //request permission location

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                photo.compress(Bitmap.CompressFormat.JPEG,40,outFile);
                userImage.setImageBitmap(photo);
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
                    filePath = file.getAbsolutePath();
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

                        userImage.setImageBitmap(bitmap);
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
        //  uploadimage();

    }
    public String imageToString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String dat= Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.d("My str Data", dat);
        return dat;
    }



    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d(TAG, "getLocation: permissions granted");
        }
    }
    public void liveLocation()
    {
        //getLocation();
        GPSTracker mGPS = new GPSTracker(getApplicationContext());
        if (mGPS.canGetLocation) {
            mGPS.getLocation();

            latitude = mGPS.getLatitude();
            longitude = mGPS.getLongitude();

            Log.d("lat", String.valueOf(latitude));
            Log.d("long", String.valueOf(longitude));

            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(context, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    Log.d("Address:--", address);
                    System.out.println(address + "-------------");
                    String adddrrss=city+" "+state+" "+country;
                    et_pro_state.setTextSize(15);
                    et_pro_state.setText(adddrrss);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception ex) {
                Toast.makeText(context, "ex:--" + ex, Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void getUserDetails() {
        //  getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.GETSINGLEUSER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getuserdetails", response);
                try {
                    // getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getuserdetailss",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            GetUserAllDoubts getPreEaxmModel = new GetUserAllDoubts();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("GETUSERDetails", jsonObject1.toString());

                            String UserId = jsonObject1.getString("UserId");
                            String Firstname = jsonObject1.getString("Firstname");
                            String Lastname = jsonObject1.getString("Lastname");
                            String Gender = jsonObject1.getString("Gender");
                            String Email = jsonObject1.getString("Email");
                            String Mobile = jsonObject1.getString("Mobile");
                            String HouseNo = jsonObject1.getString("HouseNo");
                            String Street = jsonObject1.getString("Street");
                            String Landmark = jsonObject1.getString("Landmark");
                            String State = jsonObject1.getString("State");
                            String Country = jsonObject1.getString("Country");
                            String ZipCode = jsonObject1.getString("ZipCode");
                            String Longitude = jsonObject1.getString("Longitude");
                            String Latitude = jsonObject1.getString("Latitude");
                            String DoubtsAnswered = jsonObject1.getString("DoubtsAnswered");
                            String ThanksRecieved = jsonObject1.getString("ThanksRecieved");
                            String AnsweredCorrectly = jsonObject1.getString("AnsweredCorrectly");
                            String Posts = jsonObject1.getString("Posts");
                            String Followers = jsonObject1.getString("Followers");
                            String Following = jsonObject1.getString("Following");
                            String Pic = jsonObject1.getString("Pic");
                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("UserPic", Pic);
                            editor.apply();


                            Picasso.with(MyProfile.this).load(BaseUrl.BANN_IMG_URL + "/"+Pic).into(userImage);

                            et_pro_name.setText(Firstname);
                            et_pro_phone.setText(userId);
                            et_pro_email.setText(Email);

                            if (Gender.equals("Male"))
                            {

                                radio_male.setChecked(true);
                                //Toast.makeText(context, "Male", Toast.LENGTH_SHORT).show();
                            }else if (Gender.equals("Female"))
                            {
                                radio_female.setChecked(true);
                                //Toast.makeText(context, "Female", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(context, "gender not selected", Toast.LENGTH_SHORT).show();
                            }
                            et_pro_state.setText(Country);
                        }
                    }else
                    {

                        // Toast.makeText(MyProfile.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

                params.put("UserId",userId);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void UpdatetUserDetails() {
        //  getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, BaseUrl.UPDATEUSERDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("userdetails12", response);
                try {
                    // getPreEaxmModels.clear();

//                    JSONArray jsonObject = new JSONArray(response);
//                    Log.d("userdetailss",jsonObject.toString());

                    if (response.equals("1")) {
                        SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Firstname",et_pro_name.getText().toString());
                        editor.apply();
                        Toast.makeText(context, "Successfully Updated !! ", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MyProfile.this,Dashboard.class);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(MyProfile.this, "No Update !!", Toast.LENGTH_SHORT).show();
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

                params.put("UserId",userId);
                params.put("Firstname",et_pro_name.getText().toString());
                params.put("Gender",gender);
                params.put("Mobile",et_pro_phone.getText().toString());
                params.put("Email",et_pro_email.getText().toString());

                params.put("Country",et_pro_state.getText().toString());
                params.put("Longitude",String.valueOf(longitude));
                params.put("Latitude",String.valueOf(latitude));
//                params.put("UserId","7309338957");
//                params.put("Firstname","jay");
//                params.put("Gender","Male");
//                params.put("Mobile","7309338957");
//                params.put("Email","jk@gmail.com");
//
//                params.put("Country","");
//                params.put("Longitude","");
//                params.put("Latitude","");


                Log.d("paramat",params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        if (et_pro_name.getText().toString().equalsIgnoreCase("Firstname")||et_pro_name.getText().toString().equalsIgnoreCase(""))
        {
//            new AlertDialog.Builder(this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Closing Activity")
//                    .setMessage("Are you sure you want to close this activity?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
            final androidx.appcompat.app.AlertDialog alertDialog;
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MyProfile.this);
            View view = LayoutInflater.from(MyProfile.this).inflate(R.layout.my_profile_dialog, null);
            builder.setView(view);
            final EditText edt_feedback = view.findViewById(R.id.edt_feedback);
            TextView btn_submit = view.findViewById(R.id.btn_submit);

            alertDialog = builder.create();


            alertDialog.show();

            btn_submit.setOnClickListener(v -> {

                alertDialog.dismiss();

            });

        }else
        {
            Intent intent =new Intent(MyProfile.this,Dashboard.class);
            startActivity(intent);
        }

    }
}