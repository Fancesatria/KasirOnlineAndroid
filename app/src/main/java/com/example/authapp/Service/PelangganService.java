package com.example.authapp.Service;

import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PelangganService {
    //GET DATA
    @POST("register/profile")
    Call<InfoBisnisResponse> pelanggan(@Body ModelToko modelToko);

    //SELECT DATA
    @GET("pelanggan/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //POST DATA
    @POST("pelanggan")
    Call<InfoBisnisResponse> kategori(@Body ModelToko modelToko);

    //UPDATE DATA
    @POST("pelanggan/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id,@Body ModelBarang barang);

    //DELETE DATA
    @DELETE("pelanggan/{id}")
    Call<ModelBarang> deleteData(@Path("id") String id);
}
