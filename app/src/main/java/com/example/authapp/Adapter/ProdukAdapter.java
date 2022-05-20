package com.example.authapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.R;
import com.example.authapp.ui.pengaturan.produk.EditProduk;
import com.example.authapp.ui.pengaturan.produk.MasterProduk;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    Context context;
    private List<ModelBarang> data;

    public ProdukAdapter(Context context, List<ModelBarang> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //panggil model
        ModelBarang mb = data.get(position);
        holder.tBarang.setText(mb.getBarang());
        holder.tKode.setText(mb.getIdbarang());
        holder.tHj.setText(String.valueOf(mb.getHarga()));
        holder.tHb.setText(String.valueOf(mb.getHargabeli()));
        holder.tHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MasterProduk)context).DeleteProduk(mb.getIdbarang());
            }
        });
        holder.tEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditProduk.class);
                i.putExtra("barang", mb.getBarang());
                i.putExtra("idbarang", mb.getIdbarang());
                i.putExtra("idkategori", mb.getIdkategori());
                i.putExtra("idsatuan", mb.getIdsatuan());
                i.putExtra("harga", Double.toString(mb.getHarga()));
                i.putExtra("hargaBeli", Double.toString(mb.getHargabeli()));
                i.putExtra("stok", Integer.toString((int) mb.getStok()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tBarang, tKode, tHj, tHb, tStok, tEdit, tHapus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tBarang = (TextView) itemView.findViewById(R.id.txtProduk);
            tKode = (TextView) itemView.findViewById(R.id.txtKode);
            tHj = (TextView) itemView.findViewById(R.id.txtHargaJual);
            tHb = (TextView) itemView.findViewById(R.id.txtHargaBeli);
            tStok = (TextView) itemView.findViewById(R.id.stokAwal);
            tEdit = (TextView) itemView.findViewById(R.id.txtEdit);
            tHapus = (TextView) itemView.findViewById(R.id.txtHapus);
        }
    }
}
