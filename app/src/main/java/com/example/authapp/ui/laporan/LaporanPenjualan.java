package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.databinding.ActivityLaporanPenjualanBinding;

public class LaporanPenjualan extends AppCompatActivity {
    ActivityLaporanPenjualanBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityLaporanPenjualanBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());
    }
}
