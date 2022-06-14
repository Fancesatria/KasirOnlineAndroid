package com.example.authapp.ui.home.bottom_nav;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.PelangganOrderAdapter;
import com.example.authapp.Api;
import com.example.authapp.Database.Repository.PelangganRepository;
import com.example.authapp.Model.ModelPelanggan;
import com.example.authapp.Response.PelangganGetResp;
import com.example.authapp.databinding.ActivityPelangganOrderBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PelangganOrder extends AppCompatActivity {
    private ActivityPelangganOrderBinding bind;
    private List<ModelPelanggan> data = new ArrayList<>();
    private PelangganOrderAdapter pelangganOrderAdapter;
    private PelangganRepository pelangganRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityPelangganOrderBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Data Pelanggan");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        //inisiasi repositoy / db
        pelangganRepository = new PelangganRepository(getApplication());

        //inisiasi recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        pelangganOrderAdapter = new PelangganOrderAdapter(PelangganOrder.this, data);
        bind.item.setAdapter(pelangganOrderAdapter);

        refreshData(true);

        //buat search
//        bind.searchPelanggan.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                refreshData(false);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        bind.searchPelanggan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                refreshData(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()){
                    refreshData(false);
                }
                return false;
            }
        });
    }

    public void refreshData(boolean fetch){
        //inisiasi search dari layoutnya
        String cari = bind.searchPelanggan.getQuery().toString();
        //get SQL
        pelangganRepository.getAllPelanggan(cari).observe(this, new Observer<List<ModelPelanggan>>() {
            @Override
            public void onChanged(List<ModelPelanggan> pelanggans) {
                data.clear();
                data.addAll(pelanggans);
                pelangganOrderAdapter.notifyDataSetChanged();
            }
        });

        //get retrofit
        if (fetch){
            Call<PelangganGetResp> pelangganGetRespCall = Api.Pelanggan(PelangganOrder.this).getPel(cari);
            pelangganGetRespCall.enqueue(new Callback<PelangganGetResp>() {
                @Override
                public void onResponse(Call<PelangganGetResp> call, Response<PelangganGetResp> response) {
                    if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())){
                        pelangganRepository.insertAll(response.body().getData(), true); //memasukka data ke db klou gk ada yg sama

                        //merefresh adapter
                        data.clear();
                        data.addAll(response.body().getData());
                        pelangganOrderAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PelangganGetResp> call, Throwable t) {
                    Toast.makeText(PelangganOrder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
