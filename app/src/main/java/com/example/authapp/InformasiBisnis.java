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
        NamaPemilik = (EditText) findViewById(R.id.namaPemilik);
        NamaUsaha = (EditText) findViewById(R.id.namaUsaha);
        Lokasi = (EditText) findViewById(R.id.lokasiUsaha);
        Spinner mySpinner = (Spinner) findViewById(R.id.jenisUsaha);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(InformasiBisnis.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        btnNext = (Button) findViewById(R.id.nextToTambahProduk);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelRegister modelRegister = new ModelRegister();
                modelRegister.setNamaPemilik(NamaPemilik.getText().toString());
                modelRegister.setNamaUsaha(NamaUsaha.getText().toString());
                modelRegister.setLokasi(Lokasi.getText().toString());
                modelRegister.setJenisUsaha(mySpinner.getSelectedItem().toString());
                registerUser(modelRegister);
                Toast.makeText(InformasiBisnis.this, mySpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ModelToko modelToko = new ModelToko();
//                modelToko.setNama_pemilik(NamaPemilik.getText().toString());
//                modelToko.setNama_toko(NamaUsaha.getText().toString());
//                modelToko.setAlamat_toko(Lokasi.getText().toString());
//                modelToko.setJenis_toko(mySpinner.getSelectedItem().toString());
//                registerUser(modelToko);
//                Toast.makeText(InformasiBisnis.this, mySpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void registerUser(ModelRegister modelRegister){
        Call<RegisterResponse> registerResponseCall = Api.getService().registerUsers(modelRegister);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    String message = "Registrasi akun berhasil";
                    Toast.makeText(com.example.authapp.InformasiBisnis.this, message, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(InformasiBisnis.this, TelpVerification.class));
                    finish();

                } else {
                    String message = "Terjadi error, mohon coba lagi";
                    Toast.makeText(com.example.authapp.InformasiBisnis.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(com.example.authapp.InformasiBisnis.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void registerUser(ModelToko modelToko){
//        Call<RegisterResponse> registerResponseCall = Api.getService().registerUsers(modelToko);
//        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                if(response.isSuccessful()) {
//                    String message = "Registrasi akun berhasil";
//                    Toast.makeText(com.example.authapp.InformasiBisnis.this, message, Toast.LENGTH_LONG).show();
//
//                    startActivity(new Intent(InformasiBisnis.this, TelpVerification.class));
//                    finish();
//
//                } else {
//                    String message = "Terjadi error, mohon coba lagi";
//                    Toast.makeText(com.example.authapp.InformasiBisnis.this, message, Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                String message = t.getLocalizedMessage();
//                Toast.makeText(com.example.authapp.InformasiBisnis.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}