package com.example.authapp.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Model.ModelLogin;
import com.example.authapp.Model.ModelRegister;
import com.example.authapp.databinding.NavHeaderHomePageBinding;

public class NavHeader extends AppCompatActivity {
    NavHeaderHomePageBinding bind;
    private TextView user, email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = NavHeaderHomePageBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        user = bind.user;
        email = bind.email;

        ModelLogin modelRegister = new ModelLogin();
        //user.setText(modelRegister.getNamaPemilik());
        email.setText(modelRegister.getEmail());
    }
}
