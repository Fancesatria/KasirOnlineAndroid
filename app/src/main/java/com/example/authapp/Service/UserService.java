package com.example.authapp.Service;

import com.example.authapp.Model.ModelLogin;
import com.example.authapp.Model.ModelOtp;
import com.example.authapp.Model.ModelRegister;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.LoginResponse;
import com.example.authapp.Response.OtpResponse;
import com.example.authapp.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    //ini buat login
    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body ModelLogin modelLogin);

    //ini buat register
    @POST("auth/register")
    Call<RegisterResponse> registerUsers(@Body ModelRegister modelRegister);

    //ini buat minta otp(ini butuh token yg hrs dimasukkan dulu lewat okhttp)
    @POST("register/minta")
    Call<OtpResponse> mintaOtp(@Body ModelToko modelToko);

    //ini buat register infor,masi bisnis
    @POST("register/profil")
    Call<OtpResponse> masukOtp(@Body ModelToko modelToko);
}
