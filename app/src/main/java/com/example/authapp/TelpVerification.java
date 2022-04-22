package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authapp.Model.ModelOtp;
import com.example.authapp.Model.ModelRegister;
import com.example.authapp.Model.ModelOtp;
import com.example.authapp.Model.ModelToko;
import com.example.authapp.Response.OtpResponse;
import com.example.authapp.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelpVerification extends AppCompatActivity {
    private EditText NoTelp;
    private TextView btnOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telp_verification);

        NoTelp = (EditText) findViewById(R.id.noTelp);
        btnOtp = (Button) findViewById(R.id.sendKodeOTP);
        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ModelToko modelToko = new ModelToko();
                //modelToko.setNomer_toko(NoTelp.getText().toString());
                //MintaOtp(modelToko);
            }
        });
    }


    public void MintaOtp(ModelToko modelToko) {
        Call<OtpResponse> OtpResponseCall = Api.getService().mintaOtp(modelToko);
        OtpResponseCall.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if(response.isSuccessful()){
                    String message = "Kode OTP terkirim";
                    Toast.makeText(com.example.authapp.TelpVerification.this, message, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(TelpVerification.this, OTPVerification.class));
                    finish();
                } else {
                    String message = "Nomor telepon tidak valid";
                    Toast.makeText(com.example.authapp.TelpVerification.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(com.example.authapp.TelpVerification.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}