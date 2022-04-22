package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authapp.Model.ModelRegister;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.RegisterResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRegister extends AppCompatActivity {

    private TextView userRegister;
    private EditText editTextEmail, editTextPassword, editTextConfirm;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirm = (EditText) findViewById(R.id.confirmPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        userRegister = (Button) findViewById(R.id.register);

        //ini buat waktu onclick, isi dr textviewnya keambil trs diproses ke model biar bisa masuk ke database
        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelRegister modelRegister = new ModelRegister();
                modelRegister.setEmail(editTextEmail.getText().toString());
                modelRegister.setPassword(editTextPassword.getText().toString());
                modelRegister.setPassword(editTextConfirm.getText().toString());
                registerUser(modelRegister);
            }
        });

//        userRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ModelToko modelToko = new ModelToko();
//                modelToko.setEmail_toko(editTextEmail.getText().toString());
//                modelToko.setPassword_toko(editTextPassword.getText().toString());
//                modelToko.setPassword_toko(editTextConfirm.getText().toString());
//                registerUser(modelToko);
//            }
//        });
    }

    //function register ini ngisi func registerusers yg diambil dr interface file useRegister, jadi diisi dulu baru dipanggil waktu onclick nnti
    public void registerUser(ModelRegister modelRegister){
        Call<RegisterResponse> registerResponseCall = Api.getService().registerUsers(modelRegister);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    String message = "Registrasi akun berhasil";
                    Toast.makeText(com.example.authapp.UserRegister.this, message, Toast.LENGTH_LONG).show();

                    //startActivity(new Intent(UserRegister.this, InformasiBisnis.class));
                    startActivity(new Intent(UserRegister.this, TelpVerification.class));
                    finish();

                } else {
                    String message = "Terjadi error, mohon coba lagi";
                    Toast.makeText(com.example.authapp.UserRegister.this, message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(com.example.authapp.UserRegister.this, message, Toast.LENGTH_LONG).show();
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
//                    Toast.makeText(com.example.authapp.UserRegister.this, message, Toast.LENGTH_LONG).show();
//
//                    //startActivity(new Intent(UserRegister.this, InformasiBisnis.class));
//                    startActivity(new Intent(UserRegister.this, TelpVerification.class));
//                    finish();
//
//                } else {
//                    String message = "Terjadi error, mohon coba lagi";
//                    Toast.makeText(com.example.authapp.UserRegister.this, message, Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                String message = t.getLocalizedMessage();
//                Toast.makeText(com.example.authapp.UserRegister.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}