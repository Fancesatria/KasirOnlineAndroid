package com.example.authapp.ui.pengaturan.toko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Api;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.InformasiBisnis;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.R;
import com.example.authapp.Response.BarangResponse;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.TambahkanProduk;
import com.example.authapp.databinding.ActivityIdentitasBinding;
import com.example.authapp.ui.pengaturan.produk.EditProduk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentitasToko extends AppCompatActivity {
    ActivityIdentitasBinding bind;
    private ModelToko modelToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityIdentitasBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        init();

        AutoCompleteTextView JenisUsaha = bind.JenisUsaha;

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(IdentitasToko.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.jenisUsaha));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        JenisUsaha.setAdapter(myAdapter);

        bind.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelToko modelToko = new ModelToko();
                modelToko.setNama_pemilik(bind.namaPemilik.getText().toString());
                modelToko.setNama_toko(bind.namaUsaha.getText().toString());
                modelToko.setAlamat_toko(bind.lokasiUsaha.getText().toString());
                modelToko.setJenis_toko(JenisUsaha.toString());
                MasukProfil(modelToko);
            }
        });
    }

    public void init(){
        ModelToko modelToko = new ModelToko();
        bind.namaPemilik.setText(modelToko.getNama_pemilik());
        bind.namaUsaha.setText(modelToko.getNama_toko());
        bind.noTelp.setText(modelToko.getNomer_toko());
        bind.JenisUsaha.setText(modelToko.getJenis_toko());
        bind.lokasiUsaha.setText(modelToko.getAlamat_toko());
    }

    public void MasukProfil(ModelToko modelToko){
        Call<InfoBisnisResponse> infoBisnisResponseCall = Api.getService(this).masukProfil(modelToko);
        infoBisnisResponseCall.enqueue(new Callback<InfoBisnisResponse>() {
            @Override
            public void onResponse(Call<InfoBisnisResponse> call, Response<InfoBisnisResponse> response) {
                if (response.isSuccessful()){
                    String message = "Data berhasil ditambahkan";
                    Toast.makeText(IdentitasToko.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    String message = "Data gagal ditambahkan";
                    Toast.makeText(IdentitasToko.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InfoBisnisResponse> call, Throwable t) {

            }
        });
    }
}
