package com.example.authapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelKategori;
import com.example.authapp.databinding.ItemKategoriBinding;
import com.example.authapp.ui.pengaturan.kategori.MasterDaftarKategori;

import java.util.List;


public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder>{
    Context context;
    private List<ModelKategori> data; //ini perhatikan bentuknya, kalau object btkny kaya gini. klau btknya gk tepat nnti masuk ke localizederror

    //constructor kategori adapter
    public KategoriAdapter(Context context, List<ModelKategori> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemKategoriBinding bind;
        bind = ItemKategoriBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
      //  View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        return new ViewHolder(bind);
    }

    //ini utk menampilkan data atau menambahlan event
    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.ViewHolder holder, int position) {
        ModelKategori kategori = data.get(position);

        holder.bind.txtKategori.setText(kategori.getNama_kategori());
        //ini buat detail
        holder.bind.TxtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //memanggil function dr file master kategori
                ((MasterDaftarKategori)context).DeleteKat(kategori.getIdkategori());
            }
        });
        holder.bind.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ((MasterDaftarKategori)context).dialogEditKategori(kategori);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //ini constructor untuk viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemKategoriBinding bind;

        public ViewHolder(@NonNull ItemKategoriBinding itemView) {
            super(itemView.getRoot());
            bind= itemView;

        }
    }
}
