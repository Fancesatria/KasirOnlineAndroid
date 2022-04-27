package com.example.authapp.Service;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SatuanService {
    //GET DATA
    @POST("satuan")
    Call<InfoBisnisResponse> satuan(@Body ModelToko modelToko);

    //SELECT DATA
    @GET("satuan/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //POST DATA
    @POST("satuan")
    Call<InfoBisnisResponse> kategori(@Body ModelToko modelToko);

    //UPDATE DATA
    @POST("satuan/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id,@Body ModelBarang barang);

    //DELETE DATA
    @DELETE("satuan/{id}")
    Call<ModelBarang> deleteData(@Path("id") String id);
}
