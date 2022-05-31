package com.example.authapp.ui.home.bottom_nav.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.ActivityTransactionSuccessNotifBinding;
import com.example.authapp.ui.home.PrintStruk;
import com.example.authapp.util.Modul;

import java.text.NumberFormat;
import java.util.Locale;

public class TransactionSuccess extends AppCompatActivity {
    ActivityTransactionSuccessNotifBinding bind;
    private OrderService service = OrderService.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityTransactionSuccessNotifBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

         init();

    }

    public void init(){
        NumberFormat kurensi = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        bind.txtTotalTransaksi.setText("Rp "+ Modul.removeE(getIntent().getStringExtra("total")));
        bind.txtChange.setText("Rp "+Modul.removeE(getIntent().getStringExtra("kembali")));
        int idjual = getIntent().getIntExtra("idjual", 0);

        bind.txtCetakStruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionSuccess.this, PrintStruk.class);
                intent.putExtra("idjual", idjual);
                startActivity(intent);
            }
        });
    }
}
