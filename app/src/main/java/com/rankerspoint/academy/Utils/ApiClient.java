package com.rankerspoint.academy.Utils;
import android.content.Context;

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;
import com.rankerspoint.academy.BaseUrl.BaseUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Pankaj on 11/13/2017.
 */
public class ApiClient {
    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    public static Retrofit getClient(Context context) {
        if (okHttpClient == null)
            initOkHttp(context);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl.LOGIN)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static void initOkHttp(final Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpProfilerInterceptor())
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.MINUTES);
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.addInterceptor(interceptor);
        okHttpClient = httpClient.build();
    }
}
