package com.example.authapp.ui.home.bottom_nav.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.CartAdapter;
import com.example.authapp.Database.Repository.DetailJualRepository;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.R;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.ActivityCartBinding;
import com.example.authapp.util.Modul;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private ActivityCartBinding bind;
    private DetailJualRepository detailJualRepository;
    private CartAdapter cartAdapter;
    private List<ModelDetailJual> modelDetailJualList = new ArrayList<>();
    private List<ModelBarang> modelBarangList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityCartBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Keranjang");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        //insiiasi db/repo
        detailJualRepository = new DetailJualRepository(getApplication());

        //inisiasi recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(ShoppingCart.this, modelDetailJualList, modelBarangList);
        bind.item.setAdapter(cartAdapter);

        refreshData();
        BayarButton();

    }

    public void refreshData(){
        OrderService orderService = OrderService.getInstance();

        if (orderService.getBarang().size() == 0 || orderService.getDetail().size() == 0){
            bind.txtKeranjang.setVisibility(View.VISIBLE);
            bind.item.setVisibility(View.GONE);
            bind.btnBayar.setBackgroundColor(getColor(R.color.darkgrey));
            bind.btnBayar.setEnabled(false);

        } else {
            bind.tvTotal.setText(Modul.removeE(orderService.getTotal()));//menampilkan total harga
            modelDetailJualList.clear();
            modelBarangList.clear();
            modelDetailJualList.addAll(orderService.getDetail());
            modelBarangList.addAll(orderService.getBarang());
            cartAdapter.notifyDataSetChanged();
        }
    }

    public void BayarButton(){
        bind.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingCart.this, Payment.class));
                finish();
            }
        });
    }
}
