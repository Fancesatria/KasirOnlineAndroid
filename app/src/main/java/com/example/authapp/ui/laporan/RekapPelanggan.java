package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapPelangganAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.RekapPelangganResp;
import com.example.authapp.ViewModel.ViewModelRekapPelanggan;
import com.example.authapp.databinding.ActivityRekapPelangganBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapPelanggan extends AppCompatActivity {
    ActivityRekapPelangganBinding bind;
    private List<ViewModelRekapPelanggan> data = new ArrayList<>();
    private RekapPelangganAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapPelangganBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapPelangganAdapter(RekapPelanggan.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

        bind.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    refreshData(false);
                }
                return false;
            }
        });
    }

    public void refreshData(boolean fetch){
        String cari = bind.searchView.getQuery().toString();

        if (true){
            Call<RekapPelangganResp> rekapPelangganRespCall = Api.RekapPelanggan(RekapPelanggan.this).getRekapPelanggan(cari);
            rekapPelangganRespCall.enqueue(new Callback<RekapPelangganResp>() {
                @Override
                public void onResponse(Call<RekapPelangganResp> call, Response<RekapPelangganResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RekapPelangganResp> call, Throwable t) {

                }
            });
        }
    }
}
