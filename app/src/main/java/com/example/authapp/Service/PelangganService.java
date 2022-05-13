package com.example.authapp.Service;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelPelanggan;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.Response.PelangganGetResp;
import com.example.authapp.Response.PelangganResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PelangganService {
    //GET DATA
    @GET("pelanggan")
    Call<PelangganGetResp> getPel(@Query("cari") String cari);

//    //SELECT DATA
//    @GET("pelanggan/{id}")
//    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //POST DATA
    @POST("pelanggan")
    Call<PelangganResponse> postPel(@Body ModelPelanggan modelPelanggan);

    //UPDATE DATA
    @POST("pelanggan/{id}")
    Call<PelangganResponse> updatePel(@Path("id") int id,@Body ModelPelanggan modelPelanggan);

    //DELETE DATA
    @DELETE("pelanggan/{id}")
    Call<PelangganResponse> deletePel(@Path("id") int id);

    //SELECT DATA
    @GET("pelanggan/{id}")
    Call<PelangganResponse> cariData(@Path("id") int id,@Body ModelPelanggan modelPelanggan);
}
