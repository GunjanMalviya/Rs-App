package com.rankerspoint.academy.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.R;
import com.rankerspoint.academy.Utils.Tools;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentCheckOut extends AppCompatActivity implements PaymentResultWithDataListener {
    TextView txt_course, txt_amount, tv_checkout_apply, txt_total_amount, txt_discount_amount;
    String Price, Name, current_date, CourseId, UserId;
    CardView btn_pay;
    ImageView imgBack;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    ProgressDialog progressDialog;
    int coupon_apply = 0;
    EditText ed_checkout_promocode;
    float amountRupees = 0;
    int CourseAmount = 0, DiscountedAmount = 0, strAmountPay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_check_out);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
        Tools.setSystemBarLight(this);  txt_amount = findViewById(R.id.txt_amount);
        txt_course = findViewById(R.id.txt_course);
        btn_pay = findViewById(R.id.btn_pay);
        toolbar = findViewById(R.id.toolbar);
        ed_checkout_promocode = findViewById(R.id.ed_checkout_promocode);
        tv_checkout_apply = findViewById(R.id.tv_checkout_apply);
        txt_discount_amount = findViewById(R.id.txt_discount_amount);
        txt_total_amount = findViewById(R.id.txt_total_amount);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Checkout");
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentCheckOut.this, Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Name = preferences.getString("FirstName", "");
        UserId = preferences.getString("user_id", "");
        CourseId = preferences.getString("CourseId", "");
        Price = preferences.getString("PriceC", "");
        Log.d("userId:--", Name + CourseId + Price);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss");
        current_date = df.format(c.getTime());
        //  String  orderId=  ((new Date().getTime() / 1000L) % Integer.MAX_VALUE)+"";
        txt_course.setText(Name);
        txt_amount.setText(Price);
        txt_total_amount.setText(Price);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String Url="https://rzp.io/l/fnPrnUQ0Hh".trim();
//
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                i.setData(Uri.parse(Url));
//                startActivity(i);
                //razorpayGateWay();
                verifyPaymentOnServer1("", "", "", "", "", false);
            }
        });
        tv_checkout_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_checkout_promocode.getText().toString().equals("")) {
                    ed_checkout_promocode.setError("Please enter coupon code");
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("CouponCode", ed_checkout_promocode.getText().toString().trim());
                        jsonObject.put("UserId", UserId);
                        jsonObject.put("CourseId", CourseId);
                        jsonObject.put("CourseAmount", Price);
                        applyCouponCode(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void razorpayGateWay(String OrderId) {
        progressDialog.show();
        final Activity activity = this;
        final Checkout co = new Checkout();
        if (coupon_apply == 1) {
            amountRupees = Float.valueOf(DiscountedAmount);
        } else {
            amountRupees = Float.valueOf(Price);
        }
        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_title));
            options.put("description", Name);
            //You can omit the image option to fetch the image from dashboard
            // options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", OrderId);
            options.put("currency", "INR");
            //options.put("amount", amountRupees);
            options.put("amount", amountRupees);
            JSONObject preFill = new JSONObject();
            // preFill.put("email", "jaieyeweb@gmail.com");
            preFill.put("contact", UserId);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            Log.i("razor_pay", paymentData.getPaymentId() + ", " + paymentData.getSignature() + "oder_id" + paymentData.getOrderId());
            //Toast.makeText(this, "Payment Successful: " + paymentData.getPaymentId(), Toast.LENGTH_SHORT).show();
            progressDialog.show();
            UpdatePaymentOnServer(paymentData.getPaymentId(), paymentData.getOrderId(), paymentData.getSignature());
        } catch (Exception ex) {
            Toast.makeText(this, "success:--" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
//            if(!paymentData.getOrderId().equals("") && paymentData.getOrderId()!=null){
//                UpdatePaymentOnServer(paymentData.getOrderId(), paymentData.getSignature(), paymentData.getPaymentId(), "Failed", "razorpay", false);
//            }
            Toast.makeText(PaymentCheckOut.this, "Payment is cancelled", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Error:--" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyPaymentOnServer1(final String orderId, final String sig, final String paymentId, String paymentStatus, String payment_gateway, boolean flag) {
        JSONObject jsonObject = new JSONObject();
        try {
            //  jsonObject.put("UserId", UserId);
            jsonObject.put("courseId", CourseId);
            jsonObject.put("name", Name);
            jsonObject.put("email", "");
            jsonObject.put("contactNumber", "");
            //  jsonObject.put("ValidFrom", current_date);
            // jsonObject.put("TranxId", paymentId);
            // jsonObject.put("TranDate", current_date);
            //jsonObject.put("PaymentMethod", "");
            //change code by raka
            //Toast.makeText(getApplicationContext(),"Tv Total : "+tvFinalTotal.getText().toString(),Toast.LENGTH_LONG).show();
            amountRupees = Float.valueOf(DiscountedAmount);
            Log.d("stramount:--", String.valueOf(amountRupees));
//            //jsonObject.put("coupon_applied", coupon_apply);
            if (coupon_apply == 1) {
                jsonObject.put("amount", amountRupees);
            } else {
                float amountRupees1 = Float.valueOf(Price);
                jsonObject.put("amount", amountRupees1);
            }
            //  jsonObject.put("Status", paymentStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("api_savedata", jsonObject.toString());
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.CREATEORDER_PAY, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.i("apidata_savepayment", response.toString());
                            String payment_status;
                            try {
//                                payment_status = response.getString("PaymnetStatus");
                                String order_id = response.getString("OrderId");
                                if (order_id.equals("")) {
                                    Toast.makeText(PaymentCheckOut.this, "Payment failed please try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    razorpayGateWay(order_id); // add on 29-03-2020
                                    SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("order_id", order_id);
                                    editor.apply();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(jsonObjReq);
    }
//    private void UpdatePaymentOnServer(final String orderId, final String sig, final String paymentId, String paymentStatus, String payment_gateway, boolean flag) {
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//
//            if (flag) {
//                jsonObject.put("Extra2", orderId);
//                jsonObject.put("Extra1", sig);
//                jsonObject.put("Status", paymentStatus);
//                jsonObject.put("TranxId", paymentId);
//            } else {
//                jsonObject.put("Extra2", orderId);
//                jsonObject.put("Extra1", "");
//                jsonObject.put("Status", paymentStatus);
//                jsonObject.put("TranxId", "");
//            }
//
//
//            jsonObject.put("UserId", UserId);
//            jsonObject.put("PurchasedServiceId", CourseId);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.i("apidata", jsonObject.toString());
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//
//        String order_id = preferences.getString("order_id", "");
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT, UPDATEPAYMENTDETAILS+"/"+order_id, jsonObject,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("apiupdatedata", response.toString());
//                        if (response.length()>0) {
//
//                            Log.i("apiupdatedata", response.toString());
//                            String payment_status,message="";
//
//                            try {
//////                                object = response.getJSONObject("record");
////                                payment_status = response.getString("status");
////                                message = response.getString("message");
//
//                                if (response.equals("1")) {
//                                    Toast.makeText(PaymentCheckOut.this, message, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(PaymentCheckOut.this, Dashboard.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    onBackPressed();
//                                }
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            progressDialog.dismiss();
//                        }else
//                        {
//                            Toast.makeText(PaymentCheckOut.this, "response=null", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error instanceof AuthFailureError) {
//                    Toast.makeText(PaymentCheckOut.this, "Auth ERROR: " + error, Toast.LENGTH_SHORT ).show();
//                }
//                else {
//                    Toast.makeText(PaymentCheckOut.this, "ERROR: " + error, Toast.LENGTH_SHORT ).show();
//                    Log.e("TAG", error.getMessage(), error);
//                }
//                progressDialog.dismiss();
//            }
//
//
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Accept", "application/json");
//                return params;
//            }
//        };
//        queue.getCache().clear();
//        queue.add(jsonObjReq);
//
//    }

    private void UpdatePaymentOnServer(final String paymentId, final String orderId, final String sig) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        //  String order_id = preferences.getString("order_id", "");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.COMPLETEORDER_PAY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("apiupdatedata", response.toString());
                if (response.length() > 0) {
                    Log.i("apiupdatedata", response.toString());
                    try {
                        if (response.contains("True")) {
                            updateDetails(UserId, Name, CourseId, String.valueOf(amountRupees), orderId);
                            Toast.makeText(PaymentCheckOut.this, "Payment Successful !!", Toast.LENGTH_SHORT).show();
                        } else {
                            onBackPressed();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(PaymentCheckOut.this, "response=null", Toast.LENGTH_SHORT).show();
                }
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
//
//                if (flag) {
//                  //  params.put("Extra2", orderId);
//                   // params.put("Extra1", sig);
//                    params.put("Status", paymentStatus);
//                    params.put("TranxId", paymentId);
//                } else {
//                  //  params.put("Extra2", orderId);
//                   // params.put("Extra1", "");
//                    params.put("Status", paymentStatus);
//                    params.put("TranxId", "");
//                }
                params.put("razorpay_payment_id", paymentId);
                params.put("razorpay_order_id", orderId);
                params.put("razorpay_signature", sig);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void applyCouponCode(JSONObject jsonObject) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.APPLYCOUPONCODE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("couponApplied:-", response.toString());
                        if (response.length() > 0) {
                            String Status, Message;
                            try {
                                Status = response.getString("Status");
                                Message = response.getString("Message");
                                CourseAmount = response.getInt("CourseAmount");
                                DiscountedAmount = response.getInt("DiscountedAmount");
                                Log.d("amounttxt:--", CourseAmount + "DiscountedAmount" + DiscountedAmount);
                                if (Status.equals("True")) {
                                    coupon_apply = 1;
                                    ed_checkout_promocode.setClickable(false);
                                    ed_checkout_promocode.setFocusable(false);
                                    tv_checkout_apply.setBackgroundColor(ContextCompat.getColor(PaymentCheckOut.this, R.color.overlay_dark_50));
                                    tv_checkout_apply.setClickable(false);
                                    // txt_discount_amount.setText(DiscountedAmount+"");
                                    //  strAmountPay = (CourseAmount - DiscountedAmount);
                                    txt_total_amount.setText(DiscountedAmount + "");
                                    Toast.makeText(PaymentCheckOut.this, Message.toString(), Toast.LENGTH_SHORT).show();
                                    ////// Ramkush  ///////
                                } else {
                                    coupon_apply = 0;
                                    Toast.makeText(PaymentCheckOut.this, Message.toString(), Toast.LENGTH_SHORT).show();
                                }
                                /// cost=amountPay;
                                ////////////////////////
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            coupon_apply = 0;
                            Toast.makeText(PaymentCheckOut.this, "No Data!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentCheckOut.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(jsonObjReq);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PaymentCheckOut.this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
        super.onBackPressed();
    }

    private void updateDetails(final String UserId, final String UserName, final String PurchasedServiceId, String Amount, String TranxId) {
        JSONObject jsonObject = new JSONObject();
        try {
            //  jsonObject.put("UserId", UserId);
            jsonObject.put("UserId", UserId);
            jsonObject.put("UserName", UserName);
            jsonObject.put("PurchasedServiceId", PurchasedServiceId);
            jsonObject.put("Amount", Amount);
            jsonObject.put("TranxId", TranxId);
        } catch (Exception ex) {
            ex.getMessage();
        }
        Log.i("api_savedata", jsonObject.toString());
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, BaseUrl.ADDAPPONLINEPAYDETAILS_PAY, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Log.i("apidata_addDetails", response.toString());
                            String payment_status;
                            try {
                                // payment_status = response.getString("PaymnetStatus");
                                String order_id = response.getString("PaymentId");
                                if (order_id.equals("")) {
                                    Intent intent = new Intent(PaymentCheckOut.this, Dashboard.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                    //Toast.makeText(PaymentCheckOut.this, "razorpayfile", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(PaymentCheckOut.this, Dashboard.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(jsonObjReq);
    }
}