package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapKategoriAdapter;
import com.example.authapp.Api;
import com.example.authapp.Database.Repository.KategoriRepository;
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
    private KategoriRepository kategoriRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Rekap per Kategori");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        //manggil database (sqlite)
        kategoriRepository = new KategoriRepository(getApplication());

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapKategoriAdapter(RekapKategori.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

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

//        kategoriRepository.getRekapKategori(137).observe(this, new Observer<List<ViewModelRekapKategori>>() {
//            @Override
//            public void onChanged(List<ViewModelRekapKategori> viewModelRekapKategoris) {
//                data.clear();
//                data.addAll(viewModelRekapKategoris);
//                adapter.notifyDataSetChanged();
//            }
//        });

        if (true){
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
