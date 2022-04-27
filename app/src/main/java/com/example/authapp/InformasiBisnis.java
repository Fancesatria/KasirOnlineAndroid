package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authapp.Model.ModelRegister;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.InfoBisnisResponse;
import com.example.authapp.Response.RegisterResponse;
import com.example.authapp.databinding.ActivityInformasiBisnisBinding;
import com.example.authapp.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformasiBisnis extends AppCompatActivity {
    private Button btnNext;
    private EditText NamaPemilik, NamaUsaha, Lokasi;
    private ProgressBar progressBar;
    ActivityInformasiBisnisBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityInformasiBisnisBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        //EditText NamaPemilik = bind.namaPemilik;
        EditText NamaPemilik = bind.namaPemilik;
        EditText NamaUsaha = bind.namaUsaha;
        EditText Lokasi = bind.lokasiUsaha;
        Spinner mySpinner = bind.jenisUsaha;

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(InformasiBisnis.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        bind.nextToTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelToko modelToko = new ModelToko();
                modelToko.setNama_pemilik(NamaPemilik.getText().toString());
                modelToko.setNama_toko(NamaUsaha.getText().toString());
                modelToko.setAlamat_toko(Lokasi.getText().toString());
                modelToko.setJenis_toko(mySpinner.getSelectedItem().toString());
                MasukProfil(modelToko);
                //Toast.makeText(InformasiBisnis.this, Lokasi.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void MasukProfil(ModelToko modelToko){
        Call<InfoBisnisResponse> infoBisnisResponseCall = Api.getService(this).masukProfil(modelToko);
        infoBisnisResponseCall.enqueue(new Callback<InfoBisnisResponse>() {
            @Override
            public void onResponse(Call<InfoBisnisResponse> call, Response<InfoBisnisResponse> response) {
                if (response.isSuccessful()){
                    String message = "Data berhasil ditambahkan";
                    Toast.makeText(InformasiBisnis.this, message, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(InformasiBisnis.this, TambahkanProduk.class));
                    finish();
                } else {
                    String message = "Data gagal ditambahkan";
                    Toast.makeText(InformasiBisnis.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InfoBisnisResponse> call, Throwable t) {

            }
        });
    }


}