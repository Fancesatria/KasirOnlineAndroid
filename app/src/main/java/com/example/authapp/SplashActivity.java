package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.authapp.SharedPref.SpHelper;


public class SplashActivity extends AppCompatActivity{
    private static int SPLASH_SCREEN_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //pengecekan
        SpHelper sp = new SpHelper(this);
        String cek = sp.getValue("page");

        if (cek.equals("Dashboard")){
            startActivity(new Intent(SplashActivity.this, HomePage.class));
        } else if (cek.equals("Verifikasi OTP")) {
            startActivity(new Intent(SplashActivity.this, OTPVerification.class));
        } else if (cek.equals("Ubah Data Toko")) {
            startActivity(new Intent(SplashActivity.this, TambahkanProduk.class));
        } else {

        }

        setContentView(R.layout.splash_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);


        Animation fadeOut=new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(1800);
        ImageView image=findViewById(R.id.imageView2);

        image.setAnimation(fadeOut);
    }

}
