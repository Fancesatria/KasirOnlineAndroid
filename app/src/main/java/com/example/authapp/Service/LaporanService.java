package com.example.authapp.Service;

import com.example.authapp.Response.PendapatanGetResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LaporanService {

    @GET("report/pendapatan")
    Call<PendapatanGetResp> getPendapatan(@Query("mulai") String mulai, @Query("sampai") String sampai, @Query("cari") String cari);
}
