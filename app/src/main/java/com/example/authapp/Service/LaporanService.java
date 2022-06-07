package com.example.authapp.Service;

import com.example.authapp.Response.PendapatanGetResp;
import com.example.authapp.Response.PenjualanGetResp;
import com.example.authapp.Response.RekapBarangResp;
import com.example.authapp.Response.RekapKategoriResp;
import com.example.authapp.Response.RekapPegawaiResp;
import com.example.authapp.Response.RekapPelangganResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LaporanService {

    //PENDAPATAN
    @GET("report/pendapatan")
    Call<PendapatanGetResp> getPendapatan(@Query("mulai") String mulai, @Query("sampai") String sampai, @Query("cari") String cari);

    //PENJUALAN/LABA
    @GET("report/laba")
    Call<PenjualanGetResp> getPenjualan(@Query("mulai") String mulai, @Query("sampai") String sampai, @Query("cari") String cari);

    //REKAP KATEGORI
    @GET("report/kategori")
    Call<RekapKategoriResp> getRekapKategori(@Query("cari") String cari);

    //REKAP BARANG
    @GET("report/barang")
    Call<RekapBarangResp> getRekapBarang(@Query("cari") String cari);

    //REKAP PELANGAGN
    @GET("report/pelanggan")
    Call<RekapPelangganResp> getRekapPelanggan(@Query("cari") String cari);

    //REKAP PEGAWAU
    @GET("report/pegawai")
    Call<RekapPegawaiResp> getRekapPegawai(@Query("cari") String cari);
}
