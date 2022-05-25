package com.example.authapp.ui.home.bottom_nav.shopping;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.ActivityTransactionSuccessNotifBinding;
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
        bind.txtTotalTransaksi.setText(kurensi.format(service.getTotal()));
    }
}
