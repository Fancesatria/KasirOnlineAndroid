package com.example.authapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.R;
import com.example.authapp.util.Modul;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context context;
    private List<ModelDetailJual> modelDetailJualList;
    private List<ModelBarang> modelBarangList;


    public CartAdapter(Context context, List<ModelDetailJual> modelDetailJualList, List<ModelBarang> modelBarangList) {
        this.context = context;
        this.modelDetailJualList = modelDetailJualList;
        this.modelBarangList = modelBarangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelBarang modelBarang = modelBarangList.get(position);
        ModelDetailJual detailJual = modelDetailJualList.get(position);
        holder.tNama.setText(Modul.upperCaseFirst(modelBarang.getBarang()));
        holder.tjumlah.setText(String.valueOf(detailJual.getJumlahjual()));
        holder.tHitung.setText(Modul.toString(detailJual.getJumlahjual())+" x "+ "Rp. "+Modul.removeE(detailJual.getHargajual())+" = Rp. "+Modul.removeE(detailJual.getHargajual()*detailJual.getJumlahjual()));
    }

    @Override
    public int getItemCount() {
        return modelDetailJualList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNama, tHitung, tjumlah;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tNama = itemView.findViewById(R.id.txtProduk);
            tjumlah = itemView.findViewById(R.id.txtJumlah);
            tHitung = itemView.findViewById(R.id.txtHitung);
        }
    }
}
