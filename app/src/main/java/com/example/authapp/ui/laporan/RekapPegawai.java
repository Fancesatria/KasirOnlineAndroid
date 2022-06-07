package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapPegawaiAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.RekapPegawaiResp;
import com.example.authapp.ViewModel.ViewModelRekapPegawai;
import com.example.authapp.databinding.ActivityRekapPegawaiBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapPegawai extends AppCompatActivity {
    ActivityRekapPegawaiBinding bind;
    private List<ViewModelRekapPegawai> data = new ArrayList<>();
    private RekapPegawaiAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapPegawaiBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapPegawaiAdapter(RekapPegawai.this, data);
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
            Call<RekapPegawaiResp> rekapPegawaiRespCall = Api.RekapPegawai(RekapPegawai.this).getRekapPegawai(cari);
            rekapPegawaiRespCall.enqueue(new Callback<RekapPegawaiResp>() {
                @Override
                public void onResponse(Call<RekapPegawaiResp> call, Response<RekapPegawaiResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RekapPegawaiResp> call, Throwable t) {

                }
            });
        }
    }
}
