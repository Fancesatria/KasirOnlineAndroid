package com.example.authapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelPendapatan;
import com.example.authapp.R;

import java.util.List;

public class LapPendapatanAdapter extends RecyclerView.Adapter<LapPendapatanAdapter.ViewHolder> {
    Context context;
    private List<ModelPendapatan> data;

    public LapPendapatanAdapter(Context context, List<ModelPendapatan> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pendapatan, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPendapatan modelPendapatan = data.get(position);
        holder.tanggal.setText(modelPendapatan.getData().getTanggal_jual());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, jumlah, pendapatan, keuntungan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.txtDate);
            jumlah = itemView.findViewById(R.id.txtJmlTransaksi);
            pendapatan = itemView.findViewById(R.id.txtJmlPendapatan);
            keuntungan = itemView.findViewById(R.id.txtJmlKeuntungan);
        }
    }
}
