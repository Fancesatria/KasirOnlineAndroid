package com.example.authapp.ui.pengaturan.satuan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.SatuanAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.SatuanRepository;
import com.example.authapp.Model.ModelSatuan;
import com.example.authapp.R;
import com.example.authapp.Response.SatuanGetResp;
import com.example.authapp.Response.SatuanResponse;
import com.example.authapp.Service.SatuanService;
import com.example.authapp.databinding.ActivityMasterSatuanBinding;
import com.example.authapp.databinding.DialogDetailSatuanBinding;
import com.example.authapp.ui.pengaturan.pelanggan.MasterPelanggan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterSatuan extends AppCompatActivity {
    ActivityMasterSatuanBinding bind;
    List<ModelSatuan> data = new ArrayList<>();
    SatuanAdapter adapter;
    SatuanRepository satuanRepository;
    private EditText inSatuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterSatuanBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        inSatuan = bind.eSatuan;

        //memanggil database
        satuanRepository = new SatuanRepository(getApplication());

        //mendefinisikan recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SatuanAdapter(MasterSatuan.this, data);
        bind.item.setAdapter(adapter);

        refreshData();

        bind.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama_satuan = inSatuan.getText().toString();
                if(nama_satuan.isEmpty()){
                    inSatuan.setError("Harap isi dengan benar");

                } else {
                    inSatuan.setError(null);
                    ModelSatuan data = new ModelSatuan(nama_satuan);
                    PostSat(data);
                }
            }
        });
    }

    public void dialogEditSatuan(ModelSatuan modelSatuan){
        DialogDetailSatuanBinding binder = DialogDetailSatuanBinding.inflate(LayoutInflater.from(this));
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(binder.getRoot());
        alertBuilder.setTitle("Edit Satuan");
        AlertDialog dialog = alertBuilder.create();
        binder.txtSatuan.setText(modelSatuan.getNama_satuan());
        binder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama_satuan = binder.txtSatuan.getText().toString();
                if (nama_satuan.isEmpty()){
                    binder.txtSatuan.setError("Harap isi dengan benar");

                } else {
                    binder.txtSatuan.setError(null);
                    modelSatuan.setNama_satuan(nama_satuan);
                    UpdateSat(modelSatuan.getIdsatuan(), modelSatuan);
                    dialog.cancel();
                }
            }
        });
        dialog.show();
    }

    public void refreshData(){
        //get sqlite
        satuanRepository.getAllSatuan().observe(this, new Observer<List<ModelSatuan>>() {
            @Override
            public void onChanged(List<ModelSatuan> modelSatuans) {
                data.clear();
                data.addAll(modelSatuans);
                adapter.notifyDataSetChanged();
            }
        });

        //get retrofit
        SatuanService ss = Api.Satuan(MasterSatuan.this);
        Call<SatuanGetResp> call = ss.getSat();
        call.enqueue(new Callback<SatuanGetResp>() {
            @Override
            public void onResponse(Call<SatuanGetResp> call, Response<SatuanGetResp> response) {
                if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())) {
                    // memasukkan inputan ke sqlite jika tak ada yg sama
                    satuanRepository.insertAll(response.body().getData(), true);
                    //merefresh adapter
                    data.clear();
                    data.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SatuanGetResp> call, Throwable t) {
                Toast.makeText(MasterSatuan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void PostSat(ModelSatuan modelSatuan){
        inSatuan.setEnabled(false);
        bind.btnSimpan.setEnabled(false);
        LoadingDialog.load(this);
        Call<SatuanResponse> satuanResponseCall = Api.Satuan(MasterSatuan.this).postSat(modelSatuan);
        satuanResponseCall.enqueue(new Callback<SatuanResponse>() {
            @Override
            public void onResponse(Call<SatuanResponse> call, Response<SatuanResponse> response) {
                LoadingDialog.close();
                inSatuan = bind.eSatuan;
                if (response.isSuccessful() && response.body().isStatus()) {
                    SuccessDialog.message(MasterSatuan.this, getString(R.string.success_added), bind.getRoot());
                    inSatuan.getText().clear();

                    satuanRepository.insert(modelSatuan);
                    data.add(response.body().getData());
                    adapter.notifyItemInserted(data.size());
                } else {
                    ErrorDialog.message(MasterSatuan.this, getString(R.string.add_kategori_error), bind.getRoot());
                }
                inSatuan.setEnabled(true);
                bind.btnSimpan.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SatuanResponse> call, Throwable t) {
                LoadingDialog.close();
                Toast.makeText(MasterSatuan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                inSatuan.setEnabled(true);
                bind.btnSimpan.setEnabled(true);
            }
        });
    }

    public void DeleteSat(int id){
        AlertDialog.Builder alert = new AlertDialog.Builder(MasterSatuan.this);
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah anda yakin untuk menghapus data ini ?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoadingDialog.load(MasterSatuan.this);
                Call<SatuanResponse> satuanResponseCall = Api.Satuan(MasterSatuan.this).deleteSat(id);
                satuanResponseCall.enqueue(new Callback<SatuanResponse>() {
                    @Override
                    public void onResponse(Call<SatuanResponse> call, Response<SatuanResponse> response) {
                        LoadingDialog.close();
                        if(response.isSuccessful()) {
                            if(response.body().isStatus()){
                                satuanRepository.delete(response.body().getData()); //dia menghapus dr data dlm body
                                SuccessDialog.message(MasterSatuan.this, getString(R.string.deleted_success), bind.getRoot());
                            }
                        } else {
                            ErrorDialog.message(MasterSatuan.this, getString(R.string.error_satuan_message), bind.getRoot());
                        }

                        refreshData();
                    }

                    @Override
                    public void onFailure(Call<SatuanResponse> call, Throwable t) {
                        LoadingDialog.close();
                        Toast.makeText(MasterSatuan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void UpdateSat(int id, ModelSatuan modelSatuan){
        LoadingDialog.load(MasterSatuan.this);
        Call<SatuanResponse> satuanResponseCall = Api.Satuan(MasterSatuan.this).updateSat(id, modelSatuan);
        satuanResponseCall.enqueue(new Callback<SatuanResponse>() {
            @Override
            public void onResponse(Call<SatuanResponse> call, Response<SatuanResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().isStatus()){
                        satuanRepository.update(modelSatuan);
                        refreshData();
                        LoadingDialog.close();
                        SuccessDialog.message(MasterSatuan.this,getString(R.string.updated_success) ,bind.getRoot());
                    }
                } else {
                    ErrorDialog.message(MasterSatuan.this, getString(R.string.updated_error), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<SatuanResponse> call, Throwable t) {
                LoadingDialog.close();
                Toast.makeText(MasterSatuan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}