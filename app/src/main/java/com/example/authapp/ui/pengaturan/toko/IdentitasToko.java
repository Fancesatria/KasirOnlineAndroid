package com.example.authapp.ui.pengaturan.toko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.InformasiBisnis;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.R;
import com.example.authapp.Response.BarangResponse;
import com.example.authapp.Response.IdentitasGetResp;
import com.example.authapp.Response.IdentitasResponse;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.TambahkanProduk;
import com.example.authapp.databinding.ActivityIdentitasBinding;
import com.example.authapp.ui.pengaturan.kategori.MasterDaftarKategori;
import com.example.authapp.ui.pengaturan.produk.EditProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class IdentitasToko extends AppCompatActivity {
    ActivityIdentitasBinding bind;
    private ModelToko data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityIdentitasBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        refreshData();

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
                modelToko.setJenis_toko(JenisUsaha.getText().toString());
                updateProfil(modelToko);
            }
        });
    }

//    public void MasukProfil(ModelToko modelToko){
//        Call<InfoBisnisResponse> infoBisnisResponseCall = Api.getService(this).masukProfil(modelToko);
//        infoBisnisResponseCall.enqueue(new Callback<InfoBisnisResponse>() {
//            @Override
//            public void onResponse(Call<InfoBisnisResponse> call, Response<InfoBisnisResponse> response) {
//                if (response.isSuccessful()){
//                    SuccessDialog.message(IdentitasToko.this,getString(R.string.success_added),bind.getRoot());
//                } else {
//                    ErrorDialog.message(IdentitasToko.this,getString(R.string.add_kategori_error),bind.getRoot());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InfoBisnisResponse> call, Throwable t) {
//                Toast.makeText(IdentitasToko.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void refreshData(){
        Call<IdentitasGetResp> identitasGetRespCall = Api.Identitas(IdentitasToko.this).getIdentitas();
        identitasGetRespCall.enqueue(new Callback<IdentitasGetResp>() {
            @Override
            public void onResponse(Call<IdentitasGetResp> call, Response<IdentitasGetResp> response) {
                if (response.isSuccessful()){
                    ModelToko modelToko = response.body().getData();
                    bind.namaPemilik.setText(modelToko.getNama_pemilik());
                    bind.namaUsaha.setText(modelToko.getNama_toko());
                    bind.JenisUsaha.setText(modelToko.getJenis_toko());
                    bind.lokasiUsaha.setText(modelToko.getAlamat_toko());
                }
            }

            @Override
            public void onFailure(Call<IdentitasGetResp> call, Throwable t) {

            }
        });
    }

    public void updateProfil(ModelToko modelToko){
        Call<IdentitasResponse> identitasResponseCall = Api.Identitas(IdentitasToko.this).postIdentitas(modelToko);
        identitasResponseCall.enqueue(new Callback<IdentitasResponse>() {
            @Override
            public void onResponse(Call<IdentitasResponse> call, Response<IdentitasResponse> response) {
                if (response.isSuccessful()){
                    SuccessDialog.message(IdentitasToko.this,getString(R.string.success_added),bind.getRoot());
                } else {
                    ErrorDialog.message(IdentitasToko.this,getString(R.string.add_kategori_error),bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<IdentitasResponse> call, Throwable t) {
                Toast.makeText(IdentitasToko.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
