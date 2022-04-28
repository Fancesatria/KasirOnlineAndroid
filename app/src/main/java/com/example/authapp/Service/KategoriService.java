package com.example.authapp.Service;

import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.Response.KategoriResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KategoriService {
    //GET DATA
    @GET("kategori")
    Call<KategoriResponse> getKat();

//    //SELECT DATA
//    @GET("kategori/{id}")
//    Call<KategoriResponse> selectKat(@Path("id") String id);
//
//    //POST DATA
//    @POST("kategori")
//    Call<KategoriResponse> postKat(@Body ModelToko modelToko);
//
//    //UPDATE DATA
//    @POST("kategori/{id}")
//    Call<KategoriResponse> updateKat(@Path("id") String id,@Body ModelBarang barang);
//
//    //DELETE DATA
//    @DELETE("kategori/{id}")
//    Call<KategoriResponse> deleteKat(@Path("id") String id);

}
