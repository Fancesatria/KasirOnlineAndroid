package com.example.authapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.R;
import com.example.authapp.ViewModel.ViewModelRekapBarang;
import com.example.authapp.util.Modul;

import java.util.List;

public class RekapBarangAdapter extends RecyclerView.Adapter<RekapBarangAdapter.ViewHolder>{
    Context context;
    private List<ViewModelRekapBarang> data;

    public RekapBarangAdapter(Context context, List<ViewModelRekapBarang> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rekap_barang, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewModelRekapBarang viewModelRekapBarang = data.get(position);
        holder.nama.setText(viewModelRekapBarang.getBarang());
        holder.idbarang.setText(viewModelRekapBarang.getIdbarang());
        holder.satuan.setText(viewModelRekapBarang.getNama_satuan());
        holder.tanggal.setText(viewModelRekapBarang.getTanggal_jual());
        holder.totalJual.setText(viewModelRekapBarang.getTotal_jual());
        holder.pendapatan.setText("Rp. "+ Modul.removeE(viewModelRekapBarang.getTotal_pendapatan()));
        holder.keuntungan.setText("Rp. "+ Modul.removeE(viewModelRekapBarang.getTotal_keuntungan()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, idbarang, satuan, tanggal, totalJual, pendapatan, keuntungan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtBarang);
            idbarang = itemView.findViewById(R.id.txtIdbarang);
            satuan = itemView.findViewById(R.id.isiSatuan);
            tanggal = itemView.findViewById(R.id.isitanggal);
            totalJual = itemView.findViewById(R.id.isitotaljual);
            pendapatan = itemView.findViewById(R.id.isipendapatan);
            keuntungan = itemView.findViewById(R.id.isiKeuntungan);
        }
    }
}