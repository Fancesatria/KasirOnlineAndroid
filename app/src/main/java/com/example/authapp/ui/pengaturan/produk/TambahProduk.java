package com.example.authapp.ui.pengaturan.produk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.authapp.Adapter.ProdukAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.BarangRepository;
import com.example.authapp.Database.Repository.KategoriRepository;
import com.example.authapp.Database.Repository.SatuanRepository;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.Model.ModelSatuan;
import com.example.authapp.R;
import com.example.authapp.Response.BarangResponse;
import com.example.authapp.Response.KategoriGetResp;
import com.example.authapp.Response.SatuanGetResp;
import com.example.authapp.Response.SatuanResponse;
import com.example.authapp.databinding.InsertProdukBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProduk extends AppCompatActivity {
    InsertProdukBinding bind;
    //spinner kategori
    List<ModelKategori> dataKategori = new ArrayList<>();
    ArrayAdapter adapterKategori ;
    List<String> kategori = new ArrayList<>();
    KategoriRepository kategoriRepository;

    //spinner satuan
    List<ModelSatuan> dataSatuan = new ArrayList<>();
    ArrayAdapter adapterSatuan;
    List<String> satuan = new ArrayList<>();
    SatuanRepository satuanRepository;

    //buat postdata
    BarangRepository barangRepository;
    private EditText inNama, inKode,inHarga, inStok, inHargaBeli;
    private AutoCompleteTextView inKategori, inSatuan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = InsertProdukBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tambah Produk");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        //Spinner Kategori
        kategoriRepository = new KategoriRepository(getApplication());
        kategoriRepository.getAllKategori().observe(this, new Observer<List<ModelKategori>>() {
            @Override
            public void onChanged(List<ModelKategori> modelKategoris) {
                dataKategori.clear();
                dataKategori.addAll(modelKategoris);
                for(ModelKategori kategorimodel : modelKategoris){
                    kategori.add(kategorimodel.getNama_kategori());

                }
                adapterKategori = new ArrayAdapter(TambahProduk.this, android.R.layout.simple_spinner_dropdown_item, kategori);
                bind.opsiKategori.setAdapter(adapterKategori);
                //memaggil function kategori/satuan biar bisa berjalan saat online
                refreshKategori();
            }
        });

        //Spinner satuan
        satuanRepository = new SatuanRepository(getApplication());
        satuanRepository.getAllSatuan().observe(this, new Observer<List<ModelSatuan>>() {
            @Override
            public void onChanged(List<ModelSatuan> modelSatuans) {
                dataSatuan.clear();
                dataSatuan.addAll(modelSatuans);
                for (ModelSatuan satuanModel : modelSatuans){
                    satuan.add(satuanModel.getNama_satuan());
                }
                adapterSatuan = new ArrayAdapter(TambahProduk.this, android.R.layout.simple_spinner_dropdown_item, satuan);
                bind.opsiSatuan.setAdapter(adapterSatuan);

                //memaggil function kategori/satuan biar bisa berjalan saat online
                refreshSatuan();
            }
        });



        //post data
        barangRepository = new BarangRepository(getApplication());

        inNama = bind.namaProduk;
        inKode = bind.kodeProduk;
        inHarga = bind.hargaJual;
        inHargaBeli = bind.hargaAwal;
        inStok = bind.stokAwal;
        inKategori = bind.opsiKategori;
        inSatuan = bind.opsiSatuan;

        bind.btnAddProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = inNama.getText().toString();
                String kode = inKode.getText().toString();
//                String kategori = inKategori.getSelectedItem().toString();
//                String satuan = inSatuan.getSelectedItem().toString();
                String harga = inHarga.getText().toString();
                String hargaBeli = inHargaBeli.getText().toString();
                String stok = inStok.getText().toString();

                //ngambil value dr idkategori dan idsatuan
                String idkategori = String.valueOf(kategoriSelected().getIdkategori());
                String idsatuan = String.valueOf(satuanSelected().getIdsatuan());

                if (nama.isEmpty() || kode.isEmpty() || harga.isEmpty() || hargaBeli.isEmpty() || stok.isEmpty()){
                    inNama.setError("Harap isi dengan benar");
                    inKode.setError("Harap isi dengan benar");
                    inHarga.setError("Harap isi dengan benar");
                    inStok.setError("Harap isi dengan benar");
                } else {
                    //diubah ke double lagi buat ke db
                    double Harga = Double.parseDouble(harga);
                    double HargaBeli = Double.parseDouble(hargaBeli);
                    double stokBarang = Double.parseDouble(stok);
                    ModelBarang modelBarang = new ModelBarang();
                    modelBarang.setIdbarang(kode);
                    modelBarang.setBarang(nama);
                    modelBarang.setIdkategori(idkategori);
                    modelBarang.setIdsatuan(idsatuan);
                    modelBarang.setHarga(Harga);
                    modelBarang.setHargabeli(HargaBeli);
                    modelBarang.setStok(stokBarang);
                    addBarang(modelBarang);
                }
            }
        });
    }

    //menjalankan saat online
    public void refreshSatuan(){
        Call<SatuanGetResp> satuanResponseCall = Api.Satuan(TambahProduk.this).getSat("");
        satuanResponseCall.enqueue(new Callback<SatuanGetResp>() {
            @Override
            public void onResponse(Call<SatuanGetResp> call, Response<SatuanGetResp> response) {
                if (dataSatuan.size() != response.body().getData().size() ) {
                    dataSatuan.clear();
                    dataSatuan.addAll(response.body().getData());
                    for (ModelSatuan satuanModel : response.body().getData()){
                        satuan.add(satuanModel.getNama_satuan());
                    }
                    adapterSatuan = new ArrayAdapter(TambahProduk.this, android.R.layout.simple_spinner_dropdown_item, satuan);
                    bind.opsiSatuan.setAdapter(adapterSatuan);
                }

            }

            @Override
            public void onFailure(Call<SatuanGetResp> call, Throwable t) {
                Toast.makeText(TambahProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshKategori(){
        Call<KategoriGetResp> kategoriGetRespCall = Api.Kategori(TambahProduk.this).getKat("");
        kategoriGetRespCall.enqueue(new Callback<KategoriGetResp>() {
            @Override
            public void onResponse(Call<KategoriGetResp> call, Response<KategoriGetResp> response) {
                if(dataKategori.size() != response.body().getData().size()){
                    dataKategori.clear();
                    dataKategori.addAll(response.body().getData());
                    for(ModelKategori kategorimodel : response.body().getData()){
                        kategori.add(kategorimodel.getNama_kategori());

                    }
                    adapterKategori = new ArrayAdapter(TambahProduk.this, android.R.layout.simple_spinner_dropdown_item, kategori);
                    bind.opsiKategori.setAdapter(adapterKategori);
                }
            }

            @Override
            public void onFailure(Call<KategoriGetResp> call, Throwable t) {
                Toast.makeText(TambahProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //buat ngambil
    public ModelKategori kategoriSelected(){
//        return dataKategori.get(bind.opsiKategori.getSelectedItemPosition);
        return null;
    }

    public ModelSatuan satuanSelected(){
//        return dataSatuan.get(bind.opsiKategori.getSelectedItemPosition());
        return null;
    }

    public void addBarang(ModelBarang modelBarang) {
//        String idkategori = String.valueOf(kategoriSelected().getIdkategori());
//        String idsatuan = String.valueOf(satuanSelected().getIdsatuan());
//        ModelBarang model = new ModelBarang();

        inNama.setEnabled(false);
        inKode.setEnabled(false);
        inKategori.setEnabled(false);
        inSatuan.setEnabled(false);
        inNama.setEnabled(false);
        inHarga.setEnabled(false);
        inStok.setEnabled(false);

        LoadingDialog.load(this);
        Call<BarangResponse> barangResponseCall = Api.Barang(TambahProduk.this).postBarang(modelBarang);
        barangResponseCall.enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                LoadingDialog.close();
                if (response.isSuccessful() || response.body().isStatus()) {
                    SuccessDialog.message(TambahProduk.this, getString(R.string.success_added), bind.getRoot());

                    bind.namaProduk.getText().clear();
                    bind.kodeProduk.getText().clear();
                    bind.hargaAwal.getText().clear();
                    bind.hargaJual.getText().clear();
                    bind.stokAwal.getText().clear();

                    //pakai fungsi dr repository sqlite
                    barangRepository.insert(modelBarang);

                    startActivity(new Intent(TambahProduk.this, MasterProduk.class));
                    finish();
                } else {
                    ErrorDialog.message(TambahProduk.this, getString(R.string.add_kategori_error), bind.getRoot());
                }
                inNama.setEnabled(true);
                inKode.setEnabled(true);
                inKategori.setEnabled(true);
                inSatuan.setEnabled(true);
                inNama.setEnabled(true);
                inHarga.setEnabled(true);
                inStok.setEnabled(true);
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {
                Toast.makeText(TambahProduk.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
