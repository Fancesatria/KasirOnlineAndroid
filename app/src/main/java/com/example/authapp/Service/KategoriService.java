package com.example.authapp.Service;

import com.example.authapp.Model.ModelKategori;
import com.example.authapp.Response.KategoriGetResp;
import com.example.authapp.Response.KategoriResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KategoriService {
    //GET DATA
    @GET("kategori")
    Call<KategoriGetResp> getKat(@Query("cari") String cari);

    //POST DATA
    @POST("kategori")
    Call<KategoriResponse> postKat(@Body ModelKategori modelKategori);

    //UPDATE DATA
    @POST("kategori/{id}")
    Call<KategoriResponse> updateKat(@Path("id") int id,@Body ModelKategori modelKategori);

    //DELETE DATA
    @DELETE("kategori/{id}")
    Call<KategoriResponse> deleteKat(@Path("id") int id);

}
