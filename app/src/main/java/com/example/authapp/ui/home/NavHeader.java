package com.example.authapp.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Model.ModelLogin;
import com.example.authapp.Model.ModelRegister;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.NavHeaderHomePageBinding;

public class NavHeader extends AppCompatActivity {
    NavHeaderHomePageBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = NavHeaderHomePageBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        //sharedpref
        SpHelper sp = new SpHelper(this);
        ModelLogin modelRegister = new ModelLogin();
        //user.setText(modelRegister.getNamaPemilik());
        bind.email.setText("yop");
    }
}
