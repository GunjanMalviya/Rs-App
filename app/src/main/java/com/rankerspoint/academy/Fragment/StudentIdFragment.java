package com.rankerspoint.academy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rankerspoint.academy.Adapter.BannerAdapter;
import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Utils.CustomVolleyJsonRequest;
import com.rankerspoint.academy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import static android.Manifest.permission.CALL_PHONE;

public class StudentIdFragment extends Fragment {

    private RecyclerView recyclerImages,recycler_image_slider1;
    private BannerAdapter bannerAdapter;
    ArrayList<Integer> array_image = new ArrayList<Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_id, container, false);
        bannerAdapter = new BannerAdapter(getActivity(), array_image);
        recyclerImages = view.findViewById(R.id.recycler_image_slider);
        recycler_image_slider1=view.findViewById(R.id.recycler_image_slider1);

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String moblie = preferences.getString("Mobile1", "Mobile1");

        Log.d("mobile11",moblie);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   Intent callIntent = new Intent(Intent.ACTION_CALL);
                   callIntent.setData(Uri.parse("tel:" +moblie ));
//if sim 1
                   callIntent.putExtra("simSlot", 0);

                   //else if sim 2
                   callIntent.putExtra("simSlot", 1);
                   if (ContextCompat.checkSelfPermission(getActivity(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                       startActivity(callIntent);
                   } else {
                       requestPermissions(new String[]{CALL_PHONE}, 1);
                   }
               }catch (Exception ex)
               {
                   Toast.makeText(getActivity(), ""+ex, Toast.LENGTH_SHORT).show();
               }
            }
        });
        array_image.add(R.drawable.slider04);
        array_image.add(R.drawable.slider2);
        array_image.add(R.drawable.slider3);
        array_image.add(R.drawable.slider1);
        // recyclerImages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerImages.setAdapter(bannerAdapter);
        recycler_image_slider1.setAdapter(bannerAdapter);

        final int time =  5000; // it's the delay time for sliding between items in recyclerview
        // recyclerView.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerImages.setLayoutManager(linearLayoutManager);
        recycler_image_slider1.setLayoutManager(linearLayoutManager1);
        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerImages);
       // linearSnapHelper.attachToRecyclerView(recycler_image_slider1);
        if (isOnline()) {
            //makeGetSliderRequest();

        }
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (bannerAdapter.getItemCount() - 1)) {

                    linearLayoutManager.smoothScrollToPosition(recyclerImages, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }

                else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (bannerAdapter.getItemCount() - 1)) {

                    linearLayoutManager.smoothScrollToPosition(recyclerImages, new RecyclerView.State(), 0);
                }
            }
        }, 0, time);
        return  view;
    }
    private void makeGetSliderRequest() {
      //  imageString.clear();
        String tag_json_obj = "json_category_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("parent", "");
        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.GET, BaseUrl.BANN_IMG_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("fghgh", response.toString());
                        try {
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("data");
                            if (jsonArray.length() <= 0) {
                                recyclerImages.setVisibility(View.GONE);
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    HashMap<String, String> url_maps = new HashMap<String, String>();
                                    url_maps.put("banner_name", jsonObject.getString("banner_name"));
                                    url_maps.put("banner_id", jsonObject.getString("banner_id"));
                                    url_maps.put("banner_image", BaseUrl.BANN_IMG_URL + jsonObject.getString("banner_image"));
                                   // imageString.add(BANN_IMG_URL + jsonObject.getString("banner_image"));
                                    listarray.add(url_maps);
                                }
                                //bannerAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjReq);
    }
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}