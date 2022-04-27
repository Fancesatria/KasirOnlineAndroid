package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.OtpResponse;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.ActivityOtpverificationBinding;
import com.example.authapp.databinding.ActivityTelpVerificationBinding;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerification extends AppCompatActivity {

    ActivityOtpverificationBinding bind;
    Context context;
    String NoHp ;
    SpHelper sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityOtpverificationBinding.inflate(getLayoutInflater());
        SpHelper sp = new SpHelper(OTPVerification.this);
        setContentView(bind.getRoot());
        sp = new SpHelper(this);
        NoHp = sp.getValue("NoTelp");

        EditText kode = bind.kodeOTP;

        bind.submitKodeOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                bind.kirimUlang.setVisibility(View.INVISIBLE);
//                long time = 6;
//                while (time>0){
//                    try {
//                        time--;
//                        Thread.sleep(1000L);
//
//                        if(time==0) {
//                            bind.kirimUlang.setVisibility(View.VISIBLE);
//                        }
//                    } catch (InterruptedException e) {
//
//                    }
//                }

                if  (kode.getText().toString().isEmpty()) {
                        kode.requestFocus();
                        kode.setError("Harap diisi");

                } else if (kode.getText().toString().length() < 4 || kode.getText().toString().length() > 4){
                    kode.requestFocus();
                    kode.setError("OTP terdiri dari 4 angka");
                } else {
                    Toast.makeText(OTPVerification.this, "Nomor telepon berhasil disimpan", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(OTPVerification.this, InformasiBisnis.class));
                    finish();
                }

            }
        });

        bind.kirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelToko modelToko = new ModelToko();
                modelToko.setNomer_toko(NoHp);
                MintaOtp(modelToko);
            }

            public void MintaOtp(ModelToko modelToko) {
                Call<OtpResponse> OtpResponseCall = Api.getService(OTPVerification.this).mintaOtp(modelToko);
                OtpResponseCall.enqueue(new Callback<OtpResponse>() {
                    @Override
                    public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                        if(response.isSuccessful()){
                            String message = "Kode OTP terkirim";
                            Toast.makeText(OTPVerification.this, message , Toast.LENGTH_LONG).show();

                        } else {
                            String message = "Nomor telepon tidak valid";
                            Toast.makeText(OTPVerification.this, message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpResponse> call, Throwable t) {
                        String message = t.getLocalizedMessage();
                        Toast.makeText(OTPVerification.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}