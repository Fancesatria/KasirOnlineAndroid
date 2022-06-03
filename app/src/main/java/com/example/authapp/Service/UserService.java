package com.example.authapp.Service;

import com.example.authapp.Model.ModelLogin;
import com.example.authapp.Model.ModelRegister;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.ViewModel.ViewModelBarang;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.Response.LoginResponse;
import com.example.authapp.Response.OtpResponse;
import com.example.authapp.Response.RegisBarangResponse;
import com.example.authapp.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    //LOGIN
    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body ModelLogin modelLogin);

    //REGISTER
    @POST("auth/register")
    Call<RegisterResponse> registerUsers(@Body ModelRegister modelRegister);

    //MINTA OTP(ini butuh token yg hrs dimasukkan dulu lewat okhttp)
    @POST("register/minta")
    Call<OtpResponse> mintaOtp(@Body ModelToko modelToko);

    //VERIFIKASI OTP(ini butuh token yg hrs dimasukkan dulu lewat okhttp)
    @FormUrlEncoded
    @POST("register/verifikasi")
    Call<OtpResponse> verifOtp(@Field("otp") String otp);

    //REGISTER INFORMASI BISNIS
    @POST("register/profile")
    Call<InfoBisnisResponse> masukProfil(@Body ModelToko modelToko);

    //TAMBAHKAN PRODUK
    @POST("register/barang")
    Call<RegisBarangResponse> regisBarang(@Body ViewModelBarang modelView);




}
