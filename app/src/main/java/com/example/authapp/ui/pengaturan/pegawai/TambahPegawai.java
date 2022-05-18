package com.example.authapp.ui.pengaturan.pegawai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Adapter.PegawaiAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.PegawaiRepository;
import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.R;
import com.example.authapp.Response.PegawaiResponse;
import com.example.authapp.databinding.InsertPegawaiBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPegawai extends AppCompatActivity {
    InsertPegawaiBinding bind;
    List<ModelPegawai> data;
    PegawaiAdapter pa;
    PegawaiRepository pr;
    private EditText inNama, inAlamat, inTelp, inPin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = InsertPegawaiBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        inNama = bind.addNamaPagawai;
        inAlamat = bind.addAlamatPagawai;
        inTelp = bind.addNomorPagawai;
        inPin = bind.addPinPegawai;

        //memanggil db
        pr = new PegawaiRepository(getApplication());

        bind.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = inNama.getText().toString();
                String alamat = inAlamat.getText().toString();
                String telp = inTelp.getText().toString();
                String pin = inPin.getText().toString();

                if (nama.isEmpty() || alamat.isEmpty() || telp.isEmpty() || pin.isEmpty() || pin.length() < 4 || pin.length() > 4) {
                    inNama.setError("Harap isi dengan benar");
                    inAlamat.setError("Harap isi dengan benar");
                    inTelp.setError("Harap isi dengan benar");
                    inPin.setError("Harap isi dengan benar");
                } else {
                    ModelPegawai mp = new ModelPegawai(nama, alamat, telp, pin);
                    PostPeg(mp);
                }
            }
        });
    }

    public void PostPeg(ModelPegawai modelPegawai){
        inNama.setEnabled(false);
        inAlamat.setEnabled(false);
        inTelp.setEnabled(false);
        inPin.setEnabled(false);
        bind.btnSubmit.setEnabled(false);
        LoadingDialog.load(TambahPegawai.this);
        Call<PegawaiResponse> pegawaiResponseCall = Api.Pegawai(TambahPegawai.this).postPeg(modelPegawai);
        pegawaiResponseCall.enqueue(new Callback<PegawaiResponse>() {
            @Override
            public void onResponse(Call<PegawaiResponse> call, Response<PegawaiResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful() && response.body().isStatus()) {
                    SuccessDialog.message(TambahPegawai.this, getString(R.string.success_added), bind.getRoot());

                    bind.addNamaPagawai.getText().clear();
                    bind.addAlamatPagawai.getText().clear();
                    bind.addNomorPagawai.getText().clear();
                    bind.addPinPegawai.getText().clear();

                    pr.insert(modelPegawai);

                    startActivity(new Intent(TambahPegawai.this, MasterPegawai.class));
                    finish();

                } else {
                    LoadingDialog.close();
                    ErrorDialog.message(TambahPegawai.this, getString(R.string.add_kategori_error), bind.getRoot());
                }
            }

            @Override
            public void onFailure(Call<PegawaiResponse> call, Throwable t) {
                Toast.makeText(TambahPegawai.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}