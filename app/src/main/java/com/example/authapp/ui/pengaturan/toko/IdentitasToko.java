package com.example.authapp.ui.pengaturan.toko;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.databinding.ActivityIdentitasBinding;

public class IdentitasToko extends AppCompatActivity {
    ActivityIdentitasBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityIdentitasBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());
    }
}
