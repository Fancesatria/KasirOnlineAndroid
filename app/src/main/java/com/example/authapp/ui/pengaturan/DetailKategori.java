package com.example.authapp.ui.pengaturan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.Api;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.R;
import com.example.authapp.Response.KategoriResponse;
import com.example.authapp.databinding.ActivityDetailKategoriBinding;
import com.example.authapp.databinding.ActivityMasterDaftarKategoriBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKategori extends AppCompatActivity {
    ActivityDetailKategoriBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityDetailKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        EditText inKategori = bind.txtKategori;

        bind.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("idkategori");
                ModelKategori data = new ModelKategori(id, inKategori.getText().toString());
                UpdateKat(id,data);
            }
        });

    }

    public void UpdateKat(String id, ModelKategori modelKategori){
        Call<KategoriResponse> kategoriResponseCall = Api.Kategori(this).updateKat(id, modelKategori);
        kategoriResponseCall.enqueue(new Callback<KategoriResponse>() {
            @Override
            public void onResponse(Call<KategoriResponse> call, Response<KategoriResponse> response) {
                startActivity(new Intent(DetailKategori.this, MasterDaftarKategori.class));
            }

            @Override
            public void onFailure(Call<KategoriResponse> call, Throwable t) {

            }
        });

    }


}