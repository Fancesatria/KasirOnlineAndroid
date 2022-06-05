package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapKategoriAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.RekapKategoriResp;
import com.example.authapp.ViewModel.ViewModelRekapKategori;
import com.example.authapp.databinding.ActivityRekapKategoriBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapKategori extends AppCompatActivity {
    ActivityRekapKategoriBinding bind;
    private List<ViewModelRekapKategori> data = new ArrayList<>();
    private RekapKategoriAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapKategoriAdapter(RekapKategori.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

        bind.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    refreshData(false);
                }
                return false;
            }
        });
    }

    public void refreshData(boolean fetch){
        String cari = bind.searchView.getQuery().toString();
        if (fetch){
            Call<RekapKategoriResp> rekapKategoriRespCall = Api.RekapKategori(RekapKategori.this).getRekapKategori(cari);
            rekapKategoriRespCall.enqueue(new Callback<RekapKategoriResp>() {
                @Override
                public void onResponse(Call<RekapKategoriResp> call, Response<RekapKategoriResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RekapKategoriResp> call, Throwable t) {

                }
            });
        }
    }
}
