package com.example.authapp.Service;

import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KategoriService {
    //GET DATA
    @GET("kategori")
    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //SELECT DATA
    @GET("kategori/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //POST DATA
    @POST("kategori")
    Call<InfoBisnisResponse> kategori(@Body ModelToko modelToko);

    //UPDATE DATA
    @POST("kategori/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id,@Body ModelBarang barang);

    //DELETE DATA
    @DELETE("kategori/{id}")
    Call<ModelBarang> deleteData(@Path("id") String id);

}
