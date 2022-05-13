package com.example.authapp.ui.pengaturan.pegawai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.LoginActivity;
import com.example.authapp.Model.ModelLoginPegawai;
import com.example.authapp.R;
import com.example.authapp.Response.LoginPegawaiResponse;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.LoginPegawaiBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPegawai extends AppCompatActivity {
    LoginPegawaiBinding bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = LoginPegawaiBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        bind.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idpegawai = bind.idpegawai.getText().toString();
                String pin = bind.pin.getText().toString();
                if (idpegawai.isEmpty() || pin.isEmpty()) {
                    bind.idpegawai.setError("Harap isi dengan benar");
                    bind.pin.setError("Harap isi dengan benar");

                } else {
                    ModelLoginPegawai mLP = new ModelLoginPegawai(idpegawai, pin);
                    LoginPeg(mLP);
                }
            }
        });
    }

    public void LoginPeg(ModelLoginPegawai modelLoginPegawai){
        SpHelper sp = new SpHelper(LoginPegawai.this);
        Call<LoginPegawaiResponse> loginPegawaiResponseCall = Api.Pegawai(LoginPegawai.this).loginPegawai(modelLoginPegawai);
        loginPegawaiResponseCall.enqueue(new Callback<LoginPegawaiResponse>() {
            @Override
            public void onResponse(Call<LoginPegawaiResponse> call, Response<LoginPegawaiResponse> response) {
                if (response.isSuccessful()){
                    SuccessDialog.message(LoginPegawai.this, "Login berhasil", bind.getRoot());

                    startActivity(new Intent(LoginPegawai.this, MasterPegawai.class));
                    finish();
                } else {
                    ErrorDialog.message(LoginPegawai.this, "Akun tidak ditemukan", bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<LoginPegawaiResponse> call, Throwable t) {
                ErrorDialog.message(LoginPegawai.this, t.getMessage(), bind.getRoot());
            }
        });
    }
}
