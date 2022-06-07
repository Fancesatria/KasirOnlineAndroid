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
import com.example.authapp.ViewModel.ModelViewStruk;
import com.example.authapp.util.Modul;

import java.util.List;

public class StrukAdapter extends RecyclerView.Adapter<StrukAdapter.ViewHolder> {
    Context context;
    private List<ModelViewStruk> modelDetailJualList;
    private List<ModelBarang> modelBarangList;

    public StrukAdapter(Context context, List<ModelViewStruk> modelDetailJualList, List<ModelBarang> modelBarangList) {
        this.context = context;
        this.modelDetailJualList = modelDetailJualList;
        this.modelBarangList = modelBarangList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_struk, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelViewStruk detailJual = modelDetailJualList.get(position);
//        ModelBarang modelBarang = modelBarangList.get(position);
//        holder.tNama.setText(modelBarang.getBarang());
        holder.tHitung.setText(Modul.toString(detailJual.getJumlahjual())+" x "+"Rp. "+Modul.removeE(detailJual.getHargajual()));
        holder.tJumlah.setText(Modul.removeE(detailJual.getHargajual()*detailJual.getJumlahjual()));
    }

    @Override
    public int getItemCount() {
        return modelDetailJualList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNama, tHitung, tJumlah;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tNama = itemView.findViewById(R.id.itemNamaBarang);
            tHitung = itemView.findViewById(R.id.jmlBarang);
            tJumlah = itemView.findViewById(R.id.totalHargaBarang);
        }
    }
}
