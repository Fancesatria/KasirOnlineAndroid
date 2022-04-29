package com.example.authapp.ui.pengaturan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.authapp.Api;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.R;
import com.example.authapp.Response.KategoriResponse;
import com.example.authapp.databinding.ActivityMasterDaftarKategoriBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKategori extends AppCompatActivity {
    ActivityMasterDaftarKategoriBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterDaftarKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kategori);


    }


}