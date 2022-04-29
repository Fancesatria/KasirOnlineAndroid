package com.example.authapp.ui.pengaturan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.Adapter.KategoriAdapter;
import com.example.authapp.Api;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.R;
import com.example.authapp.Response.KategoriGetResp;
import com.example.authapp.Response.KategoriResponse;
import com.example.authapp.Service.KategoriService;
import com.example.authapp.databinding.ActivityMasterDaftarKategoriBinding;
import com.example.authapp.databinding.ItemKategoriBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterDaftarKategori extends AppCompatActivity {
    ActivityMasterDaftarKategoriBinding bind;
    ItemKategoriBinding bind2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterDaftarKategoriBinding.inflate(getLayoutInflater());
        //bind2 = ItemKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());
        //setContentView(bind2.getRoot());

        EditText inKategori = bind.eKategori;

        refresh();

        bind.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelKategori mk = new ModelKategori();
                mk.setNama_kategori(inKategori.getText().toString());
                PostKat(mk);
            }
        });

//        bind2.TxtHapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DeleteKat();
//            }
//        });
    }

    public void refresh() {
        final RecyclerView item = bind.item;
        item.setLayoutManager(new LinearLayoutManager(this));
        item.setHasFixedSize(true);
        KategoriService ks = Api.Kategori(MasterDaftarKategori.this);
        Call<KategoriGetResp> call = ks.getKat();
        call.enqueue(new Callback<KategoriGetResp>() {
            @Override
            public void onResponse(Call<KategoriGetResp> call, Response<KategoriGetResp> response) {
                item.setAdapter(new KategoriAdapter(MasterDaftarKategori.this, response.body().getData()));
            }

            @Override
            public void onFailure(Call<KategoriGetResp> call, Throwable t) {
                Toast.makeText(MasterDaftarKategori.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PostKat(ModelKategori modelKategori){
        Call<KategoriResponse> kategoriResponseCall = Api.Kategori(MasterDaftarKategori.this).postKat(modelKategori);
        kategoriResponseCall.enqueue(new Callback<KategoriResponse>() {
            @Override
            public void onResponse(Call<KategoriResponse> call, Response<KategoriResponse> response) {
                EditText inKategori = bind.eKategori;
                if (response.isSuccessful()) {
                    Toast.makeText(MasterDaftarKategori.this, "Data ditambahkan", Toast.LENGTH_SHORT).show();
                    inKategori.getText().clear();
                } else {
                    Toast.makeText(MasterDaftarKategori.this, "DAta gagal ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KategoriResponse> call, Throwable t) {
                Toast.makeText(MasterDaftarKategori.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteKat(){
        String id = getIntent().getStringExtra("idtoko");
        Call<KategoriResponse> kategoriResponseCall = Api.Kategori(this).deleteKat(id);
        kategoriResponseCall.enqueue(new Callback<KategoriResponse>() {
            @Override
            public void onResponse(Call<KategoriResponse> call, Response<KategoriResponse> response) {
                Toast.makeText(MasterDaftarKategori.this, "Data dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<KategoriResponse> call, Throwable t) {
                Toast.makeText(MasterDaftarKategori.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}