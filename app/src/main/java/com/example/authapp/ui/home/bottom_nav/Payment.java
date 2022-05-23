package com.example.authapp.ui.home.bottom_nav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.ActivityBayarBinding;
import com.example.authapp.util.Modul;

public class Payment extends AppCompatActivity {
    ActivityBayarBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityBayarBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        refreshData();
        Action();
    }

    public void refreshData(){
        OrderService service = OrderService.getInstance();

        bind.tvTotal.setText(Modul.removeE(service.getTotal()));
    }


    public void Action(){
        bind.tcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payment.this, ShoppingCart.class));
                finish();
            }
        });

        bind.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bind.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.inBayar.setText("");
            }
        });
    }

    public void inputBayar(){

    }
}
