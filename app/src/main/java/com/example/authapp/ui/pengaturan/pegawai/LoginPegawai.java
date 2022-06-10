package com.example.authapp.ui.pengaturan.pegawai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.KategoriRepository;
import com.example.authapp.Database.Repository.PegawaiRepository;
import com.example.authapp.HomePage;
import com.example.authapp.LoginActivity;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.Model.ModelLoginPegawai;
import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.R;
import com.example.authapp.Response.LoginPegawaiResponse;
import com.example.authapp.Response.PegawaiGetResp;
import com.example.authapp.Service.PegawaiService;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.LoginPegawaiBinding;
import com.example.authapp.ui.pengaturan.produk.TambahProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPegawai extends AppCompatActivity {
    LoginPegawaiBinding bind;

    List<ModelPegawai> dataPegawai = new ArrayList<>();
    ArrayAdapter adapterPegawai ;
    List<String> pegawai = new ArrayList<>();
    List<String> idpegawai = new ArrayList<>();
    PegawaiRepository pegawaiRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = LoginPegawaiBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        //Spinner prgawai
        pegawaiRepository = new PegawaiRepository(getApplication());
        pegawaiRepository.getAllPegawai().observe(this, new Observer<List<ModelPegawai>>() {
            @Override
            public void onChanged(List<ModelPegawai> modelPegawais) {
                dataPegawai.clear();
                dataPegawai.addAll(modelPegawais);
                for(ModelPegawai modelPegawai : modelPegawais){
                    pegawai.add(modelPegawai.getNama_pegawai());
                    idpegawai.add(String.valueOf(modelPegawai.getIdpegawai()));
                }
                adapterPegawai = new ArrayAdapter(LoginPegawai.this, android.R.layout.simple_spinner_dropdown_item, pegawai);
                bind.namaPegawai.setAdapter(adapterPegawai);

                refreshPegawai();
            }
        });

        bind.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pegawai = idpegawai.get(bind.namaPegawai.getSelectedItemPosition());
                String pin = bind.pin.getText().toString();
                if (pin.isEmpty()) {
                    bind.pin.setError("Harap isi dengan benar");

                } else {
                    ModelLoginPegawai mLP = new ModelLoginPegawai(pegawai, pin);
                    LoginPeg(mLP);
                }
            }
        });
    }

    public void LoginPeg(ModelLoginPegawai modelLoginPegawai){
        LoadingDialog.load(LoginPegawai.this);
        //Toast.makeText(this, modelLoginPegawai.toString(), Toast.LENGTH_SHORT).show();
        SpHelper sp = new SpHelper(LoginPegawai.this);
        Call<LoginPegawaiResponse> loginPegawaiResponseCall = Api.Pegawai(LoginPegawai.this).loginPegawai(modelLoginPegawai);
        loginPegawaiResponseCall.enqueue(new Callback<LoginPegawaiResponse>() {
            @Override
            public void onResponse(Call<LoginPegawaiResponse> call, Response<LoginPegawaiResponse> response) {
                LoadingDialog.close();
                if (response.code() != 200){
                    ErrorDialog.message(LoginPegawai.this, "Akun tidak ditemukan", bind.getRoot());
                } else {
                    sp.setIdPegawai(modelLoginPegawai.getIdpegawai());
                    SuccessDialog.message(LoginPegawai.this, "Login berhasil", bind.getRoot());

                    startActivity(new Intent(LoginPegawai.this, HomePage.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginPegawaiResponse> call, Throwable t) {
                LoadingDialog.close();
                ErrorDialog.message(LoginPegawai.this, t.getMessage(), bind.getRoot());
            }
        });
    }

    public void refreshPegawai(){
        Call<PegawaiGetResp> pegawaiGetRespCall = Api.Pegawai(LoginPegawai.this).getPeg("");
        pegawaiGetRespCall.enqueue(new Callback<PegawaiGetResp>() {
            @Override
            public void onResponse(Call<PegawaiGetResp> call, Response<PegawaiGetResp> response) {
                dataPegawai.clear();
                dataPegawai.addAll(response.body().getData());
                for(ModelPegawai modelPegawai : response.body().getData()){
                    pegawai.add(modelPegawai.getNama_pegawai());
                }
                adapterPegawai = new ArrayAdapter(LoginPegawai.this, android.R.layout.simple_spinner_dropdown_item, pegawai);
                bind.namaPegawai.setAdapter(adapterPegawai);
            }

            @Override
            public void onFailure(Call<PegawaiGetResp> call, Throwable t) {
                Toast.makeText(LoginPegawai.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void refreshData(boolean fetch){
//        //get SQLite
//        pegawaiRepository.getAllPegawai("").observe(this, new Observer<List<ModelPegawai>>() {
//            @Override
//            public void onChanged(List<ModelPegawai> modelPegawais) {
//                dataPegawai.clear();
//                dataPegawai.addAll(modelPegawais);
//            }
//        });
//        if(fetch) {
//            //get Retrofit
//            PegawaiService ps = Api.Pegawai(MasterPegawai.this);
//            Call<PegawaiGetResp> pegawaiGetRespCall = ps.getPeg("");
//            pegawaiGetRespCall.enqueue(new Callback<PegawaiGetResp>() {
//                @Override
//                public void onResponse(Call<PegawaiGetResp> call, Response<PegawaiGetResp> response) {
//                    if (pegawaiRepository.size() != response.body().getData().size() || !pegawaiRepository.equals(response.body().getData())) {
//                        //Memasukkan ke db kalau gada yg sm
//                        pegawaiRepository.insertAll(response.body().getData(), true);
//
//                        //merefresh adapter
//                        pegawaiRepository.clear();
//                        pegawaiRepository.addAll(response.body().getData());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<PegawaiGetResp> call, Throwable t) {
//                    Toast.makeText(MasterPegawai.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
}
