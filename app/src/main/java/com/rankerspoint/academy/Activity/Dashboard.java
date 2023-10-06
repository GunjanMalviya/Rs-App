package com.rankerspoint.academy.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.rankerspoint.academy.BuildConfig;
import com.rankerspoint.academy.Fragment.CourseFragment;
import com.rankerspoint.academy.Fragment.HomeeeFragment;
import com.rankerspoint.academy.Fragment.TestSeriesFragment;
import com.rankerspoint.academy.Model.GetPreEaxmModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity {
    CardView createLead,view_agent_dt;
    ActionBarDrawerToggle toggle;
    Button btn_pay;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    public static BottomNavigationView navigation;
    private long exitTime = 0;
    TextView txt_copy,txt_coupon_head;
    ImageView img_copy,Search;
    private TextView CategorySpnner;
    private CardView preExamCardView;
    String userId="",cityName="",Firstname="",VersionName="";
    private ClipboardManager myClipboard;
    private ClipData myClip;
    Toolbar toolbar;
    private List<GetPreEaxmModel> getPreEaxmModels = new ArrayList<>();
    ProgressDialog progressDialog;
    String cate_nm="";
    ArrayList<String> productmessagelist = new ArrayList<>();
    private static final int TEZ_REQUEST_CODE = 123;
    private static final String GOOGLE_TEZ_PACKAGE_NAME ="com.google.android.apps.nbu.paisa.user";
    private int selectedSpinnedIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        txt_copy=findViewById(R.id.txt_copy);
        txt_coupon_head=findViewById(R.id.txt_coupon_head);
        img_copy=findViewById(R.id.img_copy);
         Search=findViewById(R.id.Search);
        CategorySpnner=findViewById(R.id.CategorySpnner);
        preExamCardView=findViewById(R.id.pre_exam);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        VersionName= BuildConfig.VERSION_NAME;

        img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String text = txt_copy.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });
        navigation = findViewById(R.id.nav_view12);

        UpdateApp();
        Popup_course();
        getInstitueDetails();
        getCategoryHome();


        //  loadFragment(new HomeeeFragment());
//navigation
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView menuSlider = findViewById(R.id.sliderr);
        menuSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        //drawer.setBackgroundResource(R.color.black_dark);
        toggle.syncState();
        //marquee
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                displayContentView(menuItem.getItemId());
                return true;
            }
        });
        View headerview = navigationView.getHeaderView(0);

//        LinearLayout nav_prof=(LinearLayout)headerview.findViewById(R.id.nav_prof);
        TextView txt_userNm=(TextView) headerview.findViewById(R.id.txt_userNm);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");
        Firstname = preferences.getString("Firstname", "");
        cate_nm=preferences.getString("cate_nm","");
        CategorySpnner.setText(cate_nm);
        Log.d("userId:--",userId+"firstname::-"+Firstname);
        txt_userNm.setText(Firstname);
        if (Firstname.equalsIgnoreCase("Firstname"))
        {
            Intent intent=new Intent(Dashboard.this,MyProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        }else {
            initComponent();
            navigation.setSelectedItemId(R.id.navigation_notifications123);
            loadFragment(new CourseFragment());
        }
        /*nav_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dashboard.this,YourProfile.class);
                startActivity(intent);
                drawer.closeDrawers();
            }
        });*/
        preExamCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, PrePareExam.class);
                startActivity(intent);
                finish();
            }
        });
        /*CategorySpnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(16f);
                ((TextView) adapterView.getChildAt(0)).setPadding(2,2,10,2);
                // ((TextView)  adapterView.getChildAt(0).setPaddingRelative(2,2,10,2);

                ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);

                cityName = getPreEaxmModels.get(i).getName();
                SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("CAT_ID", getPreEaxmModels.get(i).getCategoryId());
                editor.putString("cate_nm", getPreEaxmModels.get(i).getName());
                editor.putString("cityName", cityName);
//                            editor.putString("SubCatId", getPreEaxmModels.get(i).getSubCategoryId());
                editor.apply();
                if(selectedSpinnedIndex==-1){
                    Intent intent = new Intent(Dashboard.this, PrePareExam.class);
                    startActivity(intent);
                    finish();
                }else{
                    selectedSpinnedIndex = -1;
                    CategorySpnner.setSelection(i);
                }
            *//*getFreeLiveVideo(getPreEaxmModels.get(i).getCategoryId());
            loadFragment(new CourseFragment());*//*}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Intent intent = new Intent(Dashboard.this, PrePareExam.class);
                startActivity(intent);
                finish();
            }

        });
*/
    }
    private void displayContentView(int id) {

        Intent intent;

        switch (id) {

            case R.id.nav_home:
                Intent intent1=new Intent(getApplicationContext(),YourPdfNotesOffline.class);
                startActivity(intent1);
                break;

            case R.id.nav_down_app:
                intent=new Intent(Dashboard.this,SaveNotesAllPdfandNotes.class);
                startActivity(intent);
                break;

            case R.id.nav_Create_lead_agent:

                Intent intent8=new Intent(getApplicationContext(),OfflineVideoList.class);
                startActivity(intent8);
                break;

            case R.id.nav_view_payment:
                intent=new Intent(Dashboard.this,PaymentSuccessfully.class);
                startActivity(intent);
                break;
            case R.id.nav_your_exam:
               intent=new Intent(Dashboard.this,YourExamsList.class);
               startActivity(intent);
                break;

            case R.id.nav_settings:
                 Intent intent2=new Intent(Dashboard.this,Settings.class);
                 startActivity(intent2);
                break;

            case R.id.nav_joinUs:
                String Url="https://t.me/vaakanpur";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                i.setData(Uri.parse(Url));
                startActivity(i);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doExitApp();
        }
    }
    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.press_again_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }
    private void loadFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentPanel, fragment)
                .commitAllowingStateLoss();
    }
    private void initComponent() {
        navigation.setOnNavigationItemSelectedListener(item -> {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new HomeeeFragment());

//                        HomeeeFragment appNewsHome1Fragment = new HomeeeFragment();
//                        FragmentManager manager = getSupportFragmentManager();
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        transaction.replace(R.id.contentPanel, appNewsHome1Fragment);
//                        transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    //loadFragment(new PrePareFragment());
//                        CategoryFragment category_fragment = new CategoryFragment();
//                        FragmentManager manager2 = getSupportFragmentManager();
//                        FragmentTransaction transaction2 = manager2.beginTransaction();
//                        transaction2.replace(R.id.contentPanel, category_fragment);
//                        transaction2.commit();
                    return true;

//                case R.id.navigation_notifications1:
//                    loadFragment(new StudentIdFragment());
//                        SearchFragment trending_fragment = new SearchFragment();
//                        FragmentManager m = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = m.beginTransaction();
//                        fragmentTransaction.replace(R.id.contentPanel, trending_fragment);
////                        fragmentTransaction.commit();
//                    return true;


                case R.id.navigation_notifications12:
                    loadFragment(new TestSeriesFragment());
                    return true;
//
                case R.id.navigation_notifications123:
                    loadFragment(new CourseFragment());
//
                    return true;
            }
            return false;
        });
    }
//        public void showDialog(){
//            final Dialog dialog = new Dialog(Dashboard.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCancelable(false);
//            dialog.setContentView(R.layout.custom_dialog_dashboard);
//
//
//            ImageView dialogButton = (ImageView) dialog.findViewById(R.id.button_close);
//            btn_pay=dialog.findViewById(R.id.btn_pay);
//            btn_pay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    String Url="https://rzp.io/l/fnPrnUQ0Hh".trim();
////
////                    Intent i = new Intent(Intent.ACTION_VIEW);
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
////                    i.setData(Uri.parse(Url));
////                    startActivity(i);
//                }
//            });
//            dialogButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_dash, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) Dashboard.this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(Dashboard.this.getComponentName()));
//        }
//        return super.onCreateOptionsMenu(menu);
//    }


    private void getInstitueDetails() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.GETINSTITUTEDETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("institute", response);
                try {


                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("institutede",jsonObject.toString());

                    if (response!=null) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String InstituteId = jsonObject1.getString("InstituteId");
                            String TagLine = jsonObject1.getString("TagLine");
                            String Details = jsonObject1.getString("Details");
                            String Address1 = jsonObject1.getString("Address1");
                            String Logo = jsonObject1.getString("Logo");
                            String Mobile1 = jsonObject1.getString("Mobile1");
                            String Email = jsonObject1.getString("Email");

                            SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("InstituteId", InstituteId);
                            editor.putString("TagLine", TagLine);
                            editor.putString("Details", Details);
                            editor.putString("Address1", Address1);
                            editor.putString("Logo", Logo);
                            editor.putString("Mobile1", Mobile1);
                            editor.putString("Email", Email);
                            editor.apply();


                        }
                    }else
                    {

                       // Toast.makeText(Dashboard.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    private void getCategoryHome() {
        getPreEaxmModels.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.GETALLCATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Prod_detail", response);
                try {
                    getPreEaxmModels.clear();

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("preexam",jsonObject.toString());

                    if (response!=null) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String CategoryId = jsonObject1.getString("CategoryId");
                            String Name = jsonObject1.getString("Name");
                            String Details = jsonObject1.getString("Details");
                            String Pic = jsonObject1.getString("Pic");
                            String Logo = jsonObject1.getString("Logo");
                            GetPreEaxmModel getPreEaxmModel = new GetPreEaxmModel(CategoryId, Name, Details, Pic, Logo);

                            getPreEaxmModels.add(getPreEaxmModel);


                        }

                        productmessagelist.clear();
                        for (int i = 0; i < getPreEaxmModels.size(); i++) {

                            productmessagelist.add(getPreEaxmModels.get(i).getName());

                        }
                        String SelStr=cate_nm;
                        /*selectedSpinnedIndex=productmessagelist.indexOf(SelStr);

                        ArrayAdapter adapter = new ArrayAdapter(Dashboard.this, android.R.layout.simple_spinner_item, productmessagelist);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        CategorySpnner.setAdapter(adapter);
                        if(selectedSpinnedIndex!=-1)
                        {
                            CategorySpnner.setSelection(selectedSpinnedIndex);
                        }*/

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

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void UpdateApp() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.UPDATEAPP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("update", response);
                try {


                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("update",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String ImageId = jsonObject1.getString("ImageId");
                            String ImagePath = jsonObject1.getString("ImagePath");
                            String ImageType = jsonObject1.getString("ImageType");
                            String Heading = jsonObject1.getString("Heading");
                            String Details = jsonObject1.getString("Details");
                            String LinkType = jsonObject1.getString("LinkType");
                            String LinkId = jsonObject1.getString("LinkId");
                            String Extra3 = jsonObject1.getString("Extra3");
                            //Toast.makeText(Dashboard.this, "Extra3:--"+Extra3+"Versionname:-"+VersionName, Toast.LENGTH_SHORT).show();

                            if (!Extra3.equals(VersionName))
                            {
                               // Toast.makeText(Dashboard.this, "Extra3:--"+Extra3+"Versionname:-"+VersionName, Toast.LENGTH_SHORT).show();
                                final Dialog dialog = new Dialog(Dashboard.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.custom_dialog_dashboard);


                                ImageView dialogButton = (ImageView) dialog.findViewById(R.id.button_close);
                                ImageView img_design = (ImageView) dialog.findViewById(R.id.img_design);
                                Picasso.with(getApplicationContext()).load(BaseUrl.BANN_IMG_URL + ImagePath).into(img_design);

                                btn_pay=dialog.findViewById(R.id.btn_pay);
                               TextView txt_details=dialog.findViewById(R.id.txt_details);
                               btn_pay.setText(Heading);
                               txt_details.setText(Details);
                                btn_pay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                    String Url=LinkId.trim();

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    i.setData(Uri.parse(Url));
                    startActivity(i);
                                    }
                                });
                                dialogButton.setVisibility(View.GONE);
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }

                        }
                    }else
                    {

                        //Toast.makeText(Dashboard.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    private void Popup_course() {

String url= BaseUrl.GETPOPUPIMAGE+"/"+"StartPopup";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("popup_image", response);
                try {


                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("popup_image",jsonObject.toString());

                    if (jsonObject.length()>0) {

                        for (int i = 0; i < jsonObject.length(); i++) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("jsoonoobj", jsonObject1.toString());
                            String ImageId = jsonObject1.getString("ImageId");
                            String ImagePath = jsonObject1.getString("ImagePath");
                            String ImageType = jsonObject1.getString("ImageType");
                            String ImageCategory = jsonObject1.getString("ImageCategory");
                            String ImageCourse = jsonObject1.getString("ImageCourse");
                            String Heading = jsonObject1.getString("Heading");
                            String Details = jsonObject1.getString("Details");
                            String LinkType = jsonObject1.getString("LinkType");
                            String LinkId = jsonObject1.getString("LinkId");
                            //Toast.makeText(Dashboard.this, "Extra3:--"+Extra3+"Versionname:-"+VersionName, Toast.LENGTH_SHORT).show();

                                // Toast.makeText(Dashboard.this, "Extra3:--"+Extra3+"Versionname:-"+VersionName, Toast.LENGTH_SHORT).show();
                                final Dialog dialog = new Dialog(Dashboard.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.custom_dialog_dashboard);

                                ImageView dialogButton = (ImageView) dialog.findViewById(R.id.button_close);
                                ImageView img_design = (ImageView) dialog.findViewById(R.id.img_design);
                                Picasso.with(getApplicationContext()).load(BaseUrl.BANN_IMG_URL + ImagePath).into(img_design);

                                btn_pay=dialog.findViewById(R.id.btn_pay);
                                TextView txt_details=dialog.findViewById(R.id.txt_details);
                                btn_pay.setText(Heading);
                                txt_details.setText(Details);
                                btn_pay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(Dashboard.this,SingleCourseDetails.class);
                                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                      intent.putExtra("COURSE_ID",LinkId);
                                      startActivity(intent);
                                    }
                                });
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }


                    }else
                    {

                       // Toast.makeText(Dashboard.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void getFreeLiveVideo(String categoryid) {

        // String SUBCATALL=GETALLVIDEOBYCATEGORY+"/"+categoryid.trim();
        String SUBCATALL= BaseUrl.GETALLTOPICVIDEOSERIES+"/"+"FrontCoupon"+"/"+categoryid.trim();
        Log.d("couponcodedata:-",SUBCATALL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SUBCATALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getcouponcode", response);
                try {

                    JSONArray jsonObject = new JSONArray(response);
                    Log.d("getfreelive",jsonObject.toString());

                    if (jsonObject.length()>0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(i);
                            Log.d("getfreelive_abc", jsonObject1.toString());
//                            String LivClassId = jsonObject1.getString("LivClassId");
                            String ImageId = jsonObject1.getString("ImageId");
                            String ImagePath = jsonObject1.getString("ImagePath");

                            String Heading = jsonObject1.getString("Heading");
                            String Details = jsonObject1.getString("Details");

                            txt_copy.setText(Details);
                                    txt_coupon_head.setText(Heading);


                            // GetExamCategoryAdapter getExamCategoryAdapter = new GetExamCategoryAdapter(PrePareExam.this, getPreEaxmModels);
                            // getExamCategoryAdapter.notifyDataSetChanged();
                        }


                        // displaying selected image first

                    }else
                    {
                        txt_copy.setText("");
                        txt_coupon_head.setText("");
                       // Toast.makeText(Dashboard.this, "No Data Found !!", Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}