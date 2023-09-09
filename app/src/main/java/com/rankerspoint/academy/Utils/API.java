package com.rankerspoint.academy.Utils;
import com.rankerspoint.academy.Model.LoginModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface API {
    @POST("token")
    @FormUrlEncoded
    Call<LoginModel> DoLogin(@FieldMap Map<String, String> fields);
}
