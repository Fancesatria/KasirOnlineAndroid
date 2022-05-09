package com.example.authapp.Service;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelSatuan;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.Response.SatuanGetResp;
import com.example.authapp.Response.SatuanResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SatuanService {
    //GET DATA
    @GET("satuan")
    Call<SatuanGetResp> getSat();
//
//    //SELECT DATA
//    @GET("satuan/{id}")
//    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //POST DATA
    @POST("satuan")
    Call<SatuanResponse> postSat(@Body ModelSatuan modelSatuan);

    //UPDATE DATA
    @POST("satuan/{id}")
    Call<SatuanResponse> updateSat(@Path("id") int id,@Body ModelSatuan modelSatuan);

    //DELETE DATA
    @DELETE("satuan/{id}")
    Call<SatuanResponse> deleteSat(@Path("id") int id);
}
