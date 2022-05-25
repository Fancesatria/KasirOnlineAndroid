package com.example.authapp.ui.laporan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.authapp.R;
import com.example.authapp.databinding.ActivityLaporanPendapatanBinding;

public class LaporanPendapatan extends AppCompatActivity {
    ActivityLaporanPendapatanBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityLaporanPendapatanBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());
    }
}