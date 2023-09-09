package com.rankerspoint.academy.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.rankerspoint.academy.BaseUrl.BaseUrl;

public class AppController extends Application {

    private static AppController mInstance;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    private boolean networkCheck;

    private String email="";
    private String userID="";
    private String phone="";
    private String userName="";
    private String FCM_Id="";

//    private String course_name;

    private boolean loadActivity ;

    private boolean settingSelected ;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        BaseUrl.overrideFont(this, "DEFAULT", "fonts/Sumana-Regular.ttf");
        sharedpreferences = getSharedPreferences(BaseUrl.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        loadData();


    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public void loadData(){
        userID = sharedpreferences.getString("user_id", "");
        userName=sharedpreferences.getString("user_name","");

        networkCheck = sharedpreferences.getBoolean("networkCheck", true);
    }

    public void saveData(){

        editor.putString("user_id",userID);
        editor.putString("user_name",userName);

        editor.commit();
    }

//    public void saveCourse()
//    {
//        editor.putString("course",course_name);
//        editor.commit();
//    }
//
//    public void getCourse()
//    {
//        course_name = sharedpreferences.getString("course", "");
//    }

    public boolean getSettingSelected() {
        return settingSelected;
    }

    public void setSettingSelected(boolean settingSelected) {
        this.settingSelected = settingSelected;
    }

    public boolean getLoadActivity() {
        return loadActivity;
    }

    public void setLoadActivity(boolean loadActivity) {
        this.loadActivity = loadActivity;
    }

    public boolean getNetworkCheck() {
        return networkCheck;
    }

    public void setNetworkCheck(boolean networkCheck) {
        this.networkCheck = networkCheck;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getFCM_Id() {
        return FCM_Id;
    }

    public void setFCM_Id(String FCM_Id) {
        this.FCM_Id = FCM_Id;
    }

//    public String getCourse_name() {
//        return course_name;
//    }
//
//    public void setCourse_name(String course_name) {
//        this.course_name = course_name;
//    }

    public void logout() {

        userID = null;
        userName=null;


        editor.clear();
        editor.commit();
    }

    public String getDeviceId() {
        String deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (deviceId != null && !deviceId.equals("")) {
            return deviceId;
        } else {
            return null;
        }


    }
}
