package com.example.authapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelJual;
import com.example.authapp.R;
import com.example.authapp.ViewModel.ViewModelJual;
import com.example.authapp.util.Modul;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class LapPendapatanAdapter extends RecyclerView.Adapter<LapPendapatanAdapter.ViewHolder> {
    Context context;
    private List<ModelJual> data;

    public LapPendapatanAdapter(Context context, List<ModelJual> data) {
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
        ModelJual modelJual = data.get(position);
        ViewModelJual viewModelJual = new ViewModelJual();
        String inputPattern = "yyyy-MM-dd HH:mm";
        String OutputPattern = "dd-MMM-yyyy HH:mm";

        SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        String tgl = modelJual.getTanggal_jual();
        holder.tanggal.setText(date.format(Date.parse(tgl)));
        holder.pendapatan.setText("Rp. "+viewModelJual.getTotal());
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
