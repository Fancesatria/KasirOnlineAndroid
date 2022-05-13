package com.example.authapp.Service;

import com.example.authapp.Model.ModelLogin;
import com.example.authapp.Model.ModelLoginPegawai;
import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.Response.LoginPegawaiResponse;
import com.example.authapp.Response.LoginResponse;
import com.example.authapp.Response.PegawaiGetResp;
import com.example.authapp.Response.PegawaiResponse;
import com.example.authapp.Response.PelangganGetResp;
import com.example.authapp.Response.SatuanResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PegawaiService {
    //LOGIN
    @POST("pegawai/login")
    Call<LoginPegawaiResponse> loginPegawai(@Body ModelLoginPegawai modelLoginPegawai);

    //GET DATA
    @GET("pegawai")
    Call<PegawaiGetResp> getPeg(@Query("cari") String cari);

    //DELETE DATA
    @DELETE("pegawai/{id}")
    Call<PegawaiResponse> deletePeg(@Path("id") int id);

    //POST DATA
    @POST("pegawai")
    Call<PegawaiResponse> postPeg(@Body ModelPegawai modelPegawai);

    //UPDATE DATA
    @POST("pegawai/{id}")
    Call<PegawaiResponse> updatePeg(@Path("id") int id,@Body ModelPegawai modelPegawai); //kalau gk pakai @body bakal eror
}
