package com.example.authapp.Service;

import com.example.authapp.Model.ModelLoginPegawai;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.IdentitasGetResp;
import com.example.authapp.Response.IdentitasResponse;
import com.example.authapp.Response.LoginPegawaiResponse;
import com.example.authapp.Response.PegawaiGetResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TokoInterface {
    //POST
    @POST("toko/identitas")
    Call<IdentitasResponse> postIdentitas(@Body ModelToko modelToko);

    //GET DATA
    @GET("toko/identitas")
    Call<IdentitasGetResp> getIdentitas();
}
