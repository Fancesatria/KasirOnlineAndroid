package com.example.authapp.Service;

import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BarangService {
    //GET DATA
    @GET("barang")
    Call<InfoBisnisResponse> barang(@Body ModelToko modelToko);

    //SELECT DATA
    @GET("barang/{id}")
    Call<InfoBisnisResponse> barang(@Path("id") String id);

    //POST DATA
    @POST("barang")
    Call<InfoBisnisResponse> barang(@Body ModelToko modelToko);

    //UPDATE DATA
    @POST("barang")
    Call<InfoBisnisResponse> barang(@Body ModelToko modelToko);
}
