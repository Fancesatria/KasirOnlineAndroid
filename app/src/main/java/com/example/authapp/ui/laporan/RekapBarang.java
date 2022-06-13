package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapBarangAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.RekapBarangResp;
import com.example.authapp.ViewModel.ViewModelRekapBarang;
import com.example.authapp.databinding.ActivityRekapBarangBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapBarang extends AppCompatActivity {
    ActivityRekapBarangBinding bind;
    private RekapBarangAdapter adapter;
    private List<ViewModelRekapBarang> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapBarangBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Rekap per Barang");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        //Recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapBarangAdapter(RekapBarang.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

//        //search
//        bind.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                refreshData(false);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()){
//                    refreshData(false);
//                }
//                return false;
//            }
//        });
    }

    public void refreshData(boolean fetch){
        String cari = bind.searchView.getQuery().toString();
        if (true){
            Call<RekapBarangResp> rekapBarangRespCall = Api.RekapBarang(RekapBarang.this).getRekapBarang(cari);
            rekapBarangRespCall.enqueue(new Callback<RekapBarangResp>() {
                @Override
                public void onResponse(Call<RekapBarangResp> call, Response<RekapBarangResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RekapBarangResp> call, Throwable t) {

                }
            });
        }
    }

}
