package com.example.authapp.ui.home.bottom_nav.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.ActivityBayarBinding;
import com.example.authapp.util.Modul;

public class Payment extends AppCompatActivity {
    ActivityBayarBinding bind;
    TextView inBayar;

    String Bayar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityBayarBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        initTV();
        refreshData();
        Action();


    }

    public void initTV(){
        inBayar = bind.inBayar;
    }

    public void setBayar(String givenValue){
        Bayar = Bayar + givenValue;

        inBayar.setText(Modul.toString(Modul.strToDouble(Bayar)));

        if (inBayar.getText() == "0"){
            bind.tvRp.setTextColor(getColor(R.color.darkgrey));
            bind.inBayar.setTextColor(getColor(R.color.darkgrey));
        } else {
            bind.tvRp.setTextColor(getColor(R.color.teal_700));
            bind.inBayar.setTextColor(getColor(R.color.teal_700));
        }
    }

    public void refreshData(){
        OrderService service = OrderService.getInstance();

        bind.tvTotal.setText(Modul.removeE(service.getTotal()));
    }

    public void Del(View view){
        String recentValue = bind.inBayar.getText().toString();
        if (recentValue.length() >= 2 ){
            bind.inBayar.setText(recentValue.substring(0, recentValue.length() - 1));
        } else {
            bind.inBayar.setText("0");
        }
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
                bind.inBayar.setText("0");
                Bayar = "0";
            }
        });

        bind.shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void txtSeven(View view){
        setBayar("7");
    }

    public void txtEight(View view){
        setBayar("8");
    }

    public void txtNine(View view){
        setBayar("9");
    }

    public void txtFour(View view){
        setBayar("4");
    }

    public void txtFive(View view){
        setBayar("5");
    }

    public void txtSix(View view){
        setBayar("6");
    }

    public void txtOne(View view){
        setBayar("1");
    }

    public void txtTwo(View view){
        setBayar("2");
    }

    public void txtThree(View view){
        setBayar("3");
    }

    public void txtZero(View view){
        setBayar("0");
    }

    public void txtTripleZero(View view){
        setBayar("000");
    }

    public void txtPoint(View view){
        setBayar(".");
    }
}
