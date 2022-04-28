package com.example.authapp.ui.pengaturan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.Api;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.R;
import com.example.authapp.Response.KategoriResponse;
import com.example.authapp.databinding.ActivityMasterDaftarKategoriBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterDaftarKategori extends AppCompatActivity {
    ActivityMasterDaftarKategoriBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterDaftarKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_daftar_kategori);

        EditText inKategori = bind.eKategori;

        bind.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ModelKategori mk = new ModelKategori();
//                mk.setNama_kategori(inKategori.getText().toString());
//                PostKat(mk);
                Toast.makeText(MasterDaftarKategori.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PostKat(ModelKategori modelKategori){
        Call<KategoriResponse> kategoriResponseCall = Api.Kategori(MasterDaftarKategori.this).postKat(modelKategori);
        kategoriResponseCall.enqueue(new Callback<KategoriResponse>() {
            @Override
            public void onResponse(Call<KategoriResponse> call, Response<KategoriResponse> response) {
                EditText inKategori = bind.eKategori;
                if (response.isSuccessful()) {
                    Toast.makeText(MasterDaftarKategori.this, "Data ditambahkan", Toast.LENGTH_SHORT).show();
                    inKategori.getText().clear();
                } else {
                    Toast.makeText(MasterDaftarKategori.this, "DAta gagal ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KategoriResponse> call, Throwable t) {
                Toast.makeText(MasterDaftarKategori.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}