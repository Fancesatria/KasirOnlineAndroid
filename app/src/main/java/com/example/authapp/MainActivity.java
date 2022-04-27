package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authapp.Model.ModelLogin;
import com.example.authapp.Response.LoginResponse;
import com.example.authapp.Response.RegisterResponse;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private TextView forgotPassword;
    private TextView tester;
    private TextView mainpage;
    ActivityMainBinding bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        register = (TextView) findViewById(R.id.userRegister);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        tester = (TextView) findViewById(R.id.beta);
        mainpage = (TextView) findViewById(androidx.navigation.ui.R.id.home);

        EditText Email = bind.email;
        EditText Password = bind.password;

        bind.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validasi()) {
                    Toast.makeText(MainActivity.this, "Email atau Password tidak valid", Toast.LENGTH_SHORT).show();
                } else {
                    ModelLogin MLog = new ModelLogin();
                    MLog.setEmail(Email.getText().toString());
                    MLog.setPassword(Password.getText().toString());
                    LoginUser(MLog);
                }
            }
        });
    }

    public void LoginUser(ModelLogin modelLogin){
        //SpHelper sp = new SpHelper(this);
        Call<LoginResponse> loginResponseCall = Api.getService(MainActivity.this).loginUser(modelLogin);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {

                    String message = "Login berhasil";
                    //sp.setToken(response.body().getToken());
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                    if (response.body().getPage().equals("Dashboard")) {
                        startActivity(new Intent(MainActivity.this, HomePage.class));
                    } else if (response.body().getPage().equals("Verifikasi OTP")) {
                        startActivity(new Intent(MainActivity.this, TelpVerification.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, TambahkanProduk.class));
                    }


                    finish();

                } else {
                    String message = "Akun tidak ditemukan";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
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

    public boolean Validasi(){
        EditText Email = bind.email;
        EditText Password = bind.password;

        if(Email.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {
            Email.requestFocus();
            Email.setError("Harap diisi");

            Password.requestFocus();
            Password.setError("Harap diisi");
            return false;

        } else if (!Email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            Email.requestFocus();
            Email.setError("Masukkan email yang valid");
            return false;
        } else {
            return true;
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
