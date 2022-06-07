package com.example.authapp.ui.pengaturan.produk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.ProdukAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.BarangRepository;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.R;
import com.example.authapp.Response.BarangGetResp;
import com.example.authapp.Response.BarangResponse;
import com.example.authapp.databinding.ActivityMasterProdukBinding;
import com.example.authapp.ui.pengaturan.pelanggan.MasterPelanggan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterProduk extends AppCompatActivity {
    ActivityMasterProdukBinding bind;
    List<ModelBarang> data = new ArrayList<>();
    BarangRepository br;
    ProdukAdapter pa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityMasterProdukBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Produk");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        //memanggil db
        br = new BarangRepository(getApplication());

        //memanggil dan mengisi recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        pa = new ProdukAdapter(MasterProduk.this, data);
        bind.item.setAdapter(pa);

        refreshData(true);

        bind.plusBtnProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MasterProduk.this, TambahProduk.class));
                finish();
            }
        });

        //buat search
        bind.searchProduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                refreshData(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void refreshData(boolean fetch){
        //inisiasi search
        String cari = bind.searchProduk.getText().toString();

        //ini dibuat getnya ada 2 yaitu rerofit dan sqlite, biar kalau salah satu ada error, appnya msh bisa erjalan
        //get sqlite
        br.getAllBarang(cari).observe(this, new Observer<List<ModelBarang>>() {
            @Override
            public void onChanged(List<ModelBarang> modelBarangs) {
                data.clear();
                data.addAll(modelBarangs);
                pa.notifyDataSetChanged();
            }
        });

        //get Retrofit
        if (fetch){
            Call<BarangGetResp> barangGetRespCall = Api.Barang(MasterProduk.this).getBarang(cari);
            barangGetRespCall.enqueue(new Callback<BarangGetResp>() {
                @Override
                public void onResponse(Call<BarangGetResp> call, Response<BarangGetResp> response) {
                    if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())){
                        //memasukkan data ke / dr db
                        br.insertAll(response.body().getData(), true);

                        //merefresh adapter
                        data.clear();
                        data.addAll(response.body().getData());
                        pa.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<BarangGetResp> call, Throwable t) {
                    Toast.makeText(MasterProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void DeleteProduk(String id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MasterProduk.this);
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah anda yakin untuk menghapus data ini ?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoadingDialog.load(MasterProduk.this);
                Call<BarangResponse> barangResponseCall = Api.Barang(MasterProduk.this).delBarang(id);
                barangResponseCall.enqueue(new Callback<BarangResponse>() {
                    @Override
                    public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                        LoadingDialog.close();
                        if(response.isSuccessful()){
                            if (response.body().isStatus()){
                                br.delete(response.body().getData());
                                SuccessDialog.message(MasterProduk.this, getString(R.string.deleted_success), bind.getRoot());
                            }
                        } else {
                            //ErrorDialog.message(MasterProduk.this, getString(R.string.error_produk_message), bind.getRoot());
                            Toast.makeText(MasterProduk.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                        refreshData(true);
                    }

                    @Override
                    public void onFailure(Call<BarangResponse> call, Throwable t) {
                        LoadingDialog.close();
                        Toast.makeText(MasterProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

}
