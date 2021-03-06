package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.ViewModel.ViewModelBarang;
import com.example.authapp.Response.RegisBarangResponse;
import com.example.authapp.databinding.ActivityTambahkanProdukBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahkanProduk extends AppCompatActivity {
    ActivityTambahkanProdukBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityTambahkanProdukBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        EditText idBarang = bind.kodeProduk;
        EditText NamaBarang = bind.namaProduk;
        EditText Kategori = bind.kategori;
        EditText Satuan = bind.satuan;
        EditText Harga = bind.harga;
        EditText HargaJual = bind.hargaJual;
        EditText Stok = bind.stokAwal;


        bind.nextToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModelBarang modelVB = new ViewModelBarang();
                modelVB.setIdbarang(idBarang.getText().toString());
                modelVB.setBarang(NamaBarang.getText().toString());
                modelVB.setNama_kategori(Kategori.getText().toString());
                modelVB.setNama_satuan(Satuan.getText().toString());
                modelVB.setHarga(Harga.getText().toString());
                modelVB.setHargabeli(HargaJual.getText().toString());
                modelVB.setStok(Stok.getText().toString());
                RegisBarang(modelVB);
            }
        });
    }

    public void RegisBarang(ViewModelBarang viewModelBarang) {
        Call<RegisBarangResponse> RegisBarangResponseCall = Api.getService(TambahkanProduk.this).regisBarang(viewModelBarang);
        RegisBarangResponseCall.enqueue(new Callback<RegisBarangResponse>() {
            @Override
            public void onResponse(Call<RegisBarangResponse> call, Response<RegisBarangResponse> response) {
                if(response.isSuccessful()){
                    String message = "Data berhasil ditambahkan";
                    Toast.makeText(TambahkanProduk.this, message , Toast.LENGTH_LONG).show();

                    startActivity(new Intent(TambahkanProduk.this, HomePage.class));
                    finish();
                } else {
                    String message = "Data gagal ditambahkan";
                    Toast.makeText(TambahkanProduk.this, message , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisBarangResponse> call, Throwable t) {

            }
        });
    }
}