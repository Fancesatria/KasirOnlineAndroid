package com.example.authapp.ui.pengaturan.pelanggan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.PelangganAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.PelangganRepository;
import com.example.authapp.LoginActivity;
import com.example.authapp.Model.ModelPelanggan;
import com.example.authapp.R;
import com.example.authapp.Response.PelangganGetResp;
import com.example.authapp.Response.PelangganResponse;
import com.example.authapp.Service.PelangganService;
import com.example.authapp.databinding.ActivityMasterPegawaiBinding;
import com.example.authapp.databinding.ActivityMasterPelangganBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterPelanggan extends AppCompatActivity {
    private ActivityMasterPelangganBinding bind;
    private List<ModelPelanggan> data = new ArrayList<>();
    private PelangganAdapter adapter;
    private PelangganRepository pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterPelangganBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        //memanggil database
        pr = new PelangganRepository(getApplication());

        //mendefinisikan recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PelangganAdapter(MasterPelanggan.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

        bind.plusBtnProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MasterPelanggan.this, TambahPelanggan.class));
                finish();
            }
        });

        //buat search
        bind.searchPelanggan.addTextChangedListener(new TextWatcher() {
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

    //ini dkasi boolean biar gk merequest terusn klo request terus2 an bakal repot
    public void refreshData(boolean fetch){
        //inisiasi search dari layoutnya
        String cari = bind.searchPelanggan.getText().toString();
        //get sqlite
        pr.getAllPelanggan(cari).observe(this, new Observer<List<ModelPelanggan>>() {
            @Override
            public void onChanged(List<ModelPelanggan> modelPelanggans) {
                data.clear();
                data.addAll(modelPelanggans);
                adapter.notifyDataSetChanged();
            }
        });

        //get retrofit
        if (fetch) {
            PelangganService ps = Api.Pelanggan(MasterPelanggan.this);
            Call<PelangganGetResp> call = ps.getPel(cari);
            call.enqueue(new Callback<PelangganGetResp>() {
                @Override
                public void onResponse(Call<PelangganGetResp> call, Response<PelangganGetResp> response) {
                    //Toast.makeText(MasterPelanggan.this, String.valueOf(response.body().getData().size()), Toast.LENGTH_SHORT).show();
                    if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())){
                        //memasukkan ke db kalau gada data yg sama
                        pr.insertAll(response.body().getData(), true);

                        //merefresh adapter
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PelangganGetResp> call, Throwable t) {
                    Toast.makeText(MasterPelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void DeletePel(int id){
        AlertDialog.Builder alert = new AlertDialog.Builder(MasterPelanggan.this);
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah anda yakin untuk menghapus data ini ?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoadingDialog.load(MasterPelanggan.this);
                Call<PelangganResponse> pelangganResponseCall = Api.Pelanggan(MasterPelanggan.this).deletePel(id);
                pelangganResponseCall.enqueue(new Callback<PelangganResponse>() {
                    @Override
                    public void onResponse(Call<PelangganResponse> call, Response<PelangganResponse> response) {
                        LoadingDialog.close();
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                pr.delete(response.body().getData());
                                SuccessDialog.message(MasterPelanggan.this,getString(R.string.deleted_success) ,bind.getRoot());
                            }
                        } else {
                            ErrorDialog.message(MasterPelanggan.this, getString(R.string.error_pelanggan_message), bind.getRoot());
                        }
                        refreshData(true);
                    }

                    @Override
                    public void onFailure(Call<PelangganResponse> call, Throwable t) {
                        LoadingDialog.close();
                        Toast.makeText(MasterPelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
