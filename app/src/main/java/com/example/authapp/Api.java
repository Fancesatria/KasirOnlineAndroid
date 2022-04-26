package com.example.authapp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.authapp.Service.UserService;
import com.example.authapp.SharedPref.SpHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static String BASE_URL = "http://45.77.245.19/";
    public static Retrofit getRetrofit(Context context) {
        SpHelper sp = new SpHelper(context);//inisiasi sp helper

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String token = sp.getToken(); //sp.getValue("token2");//ini ambil token dr response di postman
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization",token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//            @NonNull
//            @Override
//            public Response intercept(@NonNull Chain chain) throws IOException {
//                String token = sp.getToken();//ini ambil token dr response di postman
//                Request newRequest = chain.request().newBuilder()
//                        .addHeader("Authorization",token)
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
    // tanpa hedaer (register login)
    public static UserService getService() {
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }

    //dg header
    public static UserService getService(Context context) {
        UserService userService = getRetrofit(context).create(UserService.class);

        return userService;
    }
}
