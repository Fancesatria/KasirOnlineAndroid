package com.example.authapp;

import androidx.annotation.NonNull;

import com.example.authapp.Service.UserService;

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
    public static Retrofit getRetrofit() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        //ini membuat header/authorizationnya
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHRva28iOjExLCJyb2xlIjoiMiIsInByb2plY3QiOiJLYXNpck9ubGluZSIsImlhdCI6MTY1MDI1NDUzMH0.LNWM0J9axCQZwj_CjI2o1cwvY_5v7pFezH7j572X2sg";
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

    public static UserService getService() {
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }
}
