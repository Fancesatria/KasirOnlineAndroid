package com.example.authapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelKategori;
import com.example.authapp.R;
import com.example.authapp.UserRegister;
import com.example.authapp.databinding.ItemKategoriBinding;
import com.example.authapp.ui.pengaturan.DetailKategori;
import com.example.authapp.ui.pengaturan.MasterDaftarKategori;

import java.util.ArrayList;
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
        ModelKategori nama_kategori = data.get(position);

        holder.bind.txtKategori.setText(nama_kategori.getNama_kategori());
        //ini buat detail
        holder.bind.TxtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //memanggil function dr file master kategori
                ((MasterDaftarKategori)context).DeleteKat(nama_kategori.getIdkategori());
            }
        });
        holder.bind.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailKategori.class);
                i.putExtra("idkategori", nama_kategori.getIdkategori());
                i.putExtra("nama_kategori", nama_kategori.getNama_kategori());
                i.putExtra("idtoko", nama_kategori.getIdtoko());
                context.startActivity(i);
            }
        });
    }

    public void setData(List<ModelKategori> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
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
