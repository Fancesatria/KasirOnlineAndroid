package com.example.authapp.ui.pengaturan.pegawai;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.PegawaiAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.PegawaiRepository;
import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.R;
import com.example.authapp.Response.PegawaiGetResp;
import com.example.authapp.Response.PegawaiResponse;
import com.example.authapp.Service.PegawaiService;
import com.example.authapp.Service.PelangganService;
import com.example.authapp.databinding.ActivityMasterPegawaiBinding;
import com.example.authapp.ui.pengaturan.pelanggan.MasterPelanggan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterPegawai extends AppCompatActivity {
    ActivityMasterPegawaiBinding bind;
    List<ModelPegawai> data = new ArrayList<>();
    PegawaiAdapter pa;
    PegawaiRepository pr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityMasterPegawaiBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        //memanggil db
        pr = new PegawaiRepository(getApplication());

        //menginisiasi adapter dan recycler view
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        pa = new PegawaiAdapter(MasterPegawai.this, data);
        bind.item.setAdapter(pa);

        refreshData(true);

        bind.plusBtnProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MasterPegawai.this, TambahPegawai.class));
                finish();
            }
        });

        //buat search
        bind.searchPegawai.addTextChangedListener(new TextWatcher() {
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
        //inisiasi cari dr file layout
        String cari = bind.searchPegawai.getText().toString();
        //get SQLite
        pr.getAllPegawai(cari).observe(this, new Observer<List<ModelPegawai>>() {
            @Override
            public void onChanged(List<ModelPegawai> modelPegawais) {
                data.clear();
                data.addAll(modelPegawais);
                pa.notifyDataSetChanged();
            }
        });
        if(fetch) {
            //get Retrofit
            PegawaiService ps = Api.Pegawai(MasterPegawai.this);
            Call<PegawaiGetResp> pegawaiGetRespCall = ps.getPeg(cari);
            pegawaiGetRespCall.enqueue(new Callback<PegawaiGetResp>() {
                @Override
                public void onResponse(Call<PegawaiGetResp> call, Response<PegawaiGetResp> response) {
                    if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())) {
                        //Memasukkan ke db kalau gada yg sm
                        pr.insertAll(response.body().getData(), true);

                        //merefresh adapter
                        data.clear();
                        data.addAll(response.body().getData());
                        pa.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PegawaiGetResp> call, Throwable t) {
                    Toast.makeText(MasterPegawai.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void DeletePeg(int id){
        AlertDialog.Builder alert = new AlertDialog.Builder(MasterPegawai.this);
        alert.setTitle("Konfirmasi");
        alert.setMessage("Anda yakin ingin menghapus data ini?");
        alert.setPositiveButton("iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoadingDialog.load(MasterPegawai.this);
                Call<PegawaiResponse> modelPegawaiCall = Api.Pegawai(MasterPegawai.this).deletePeg(id);
                modelPegawaiCall.enqueue(new Callback<PegawaiResponse>() {
                    @Override
                    public void onResponse(Call<PegawaiResponse> call, Response<PegawaiResponse> response) {
                        LoadingDialog.close();
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                pr.delete(response.body().getData());
                                SuccessDialog.message(MasterPegawai.this, getString(R.string.deleted_success), bind.getRoot());
                            }
                        } else {
                            ErrorDialog.message(MasterPegawai.this, getString(R.string.error_pegawai_message), bind.getRoot());
                        }
                        refreshData(true);
                    }

                    @Override
                    public void onFailure(Call<PegawaiResponse> call, Throwable t) {
                        LoadingDialog.close();
                        Toast.makeText(MasterPegawai.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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