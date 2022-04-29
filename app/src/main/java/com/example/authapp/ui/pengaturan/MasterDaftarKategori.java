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
    RecyclerView item ;
    List<ModelKategori> data= new ArrayList<>();
    KategoriAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterDaftarKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());
         item = bind.item;
        item.setLayoutManager(new LinearLayoutManager(this));
        item.setHasFixedSize(true);
        adapter = new KategoriAdapter(MasterDaftarKategori.this, data);
        item.setAdapter(adapter);

        EditText inKategori = bind.eKategori;
        refresh();

        bind.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelKategori data = new ModelKategori();
                data.setNama_kategori(inKategori.getText().toString());
                PostKat(data);
                refresh();
            }
        });

    }



    public void refresh() {
        KategoriService ks = Api.Kategori(MasterDaftarKategori.this);
        Call<KategoriGetResp> call = ks.getKat();
        call.enqueue(new Callback<KategoriGetResp>() {
            @Override
            public void onResponse(Call<KategoriGetResp> call, Response<KategoriGetResp> response) {
                   if(data.size() != response.body().getData().size()) {
                       data = response.body().getData();
                       item.invalidate();
                       adapter.setData(data);
                       adapter.notifyDataSetChanged();
                   }

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
                    data.add(modelKategori);
                    adapter.setData(data);

                    adapter.notifyItemInserted(data.size());
                    
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

    public void DeleteKat(String id){
        Call<KategoriResponse> kategoriResponseCall = Api.Kategori(this).deleteKat(id);
        kategoriResponseCall.enqueue(new Callback<KategoriResponse>() {
            @Override
            public void onResponse(Call<KategoriResponse> call, Response<KategoriResponse> response) {
                refresh();
            }

            @Override
            public void onFailure(Call<KategoriResponse> call, Throwable t) {
                refresh();
            }
        });
    }

}