package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private TextView forgotPassword;
    private TextView tester;
    private TextView mainpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.userRegister);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        tester = (TextView) findViewById(R.id.beta);
        mainpage = (TextView) findViewById(androidx.navigation.ui.R.id.home);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, UserRegister.class));
                break;
                case R.id.forgotPassword:
                    startActivity(new Intent(this, ForgotPassword.class));
                    break;
            case R.id.beta:
                startActivity(new Intent(this, InformasiBisnis.class));
                break;
            case R.id.homePage:
                startActivity(new Intent(this,HomePage.class));
        }
    }

    public void register(View v) {
        startActivity(new Intent(this, UserRegister.class));
    }

    public void forgotPassword(View v) {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    public void tester(View v) {
        startActivity(new Intent(this, InformasiBisnis.class));
    }

    public void mainPage(View v) {startActivity(new Intent(this, HomePage.class));
    }
}
