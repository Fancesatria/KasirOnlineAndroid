package com.example.authapp.Service;

import com.example.authapp.Response.PendapatanGetResp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LaporanService {

    @GET("report/pendapatan")
    Call<PendapatanGetResp> getPendapatan();
}
