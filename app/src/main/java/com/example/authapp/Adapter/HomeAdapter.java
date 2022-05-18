package com.example.authapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.R;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.FragmentHomeBinding;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    private List<ModelBarang> data;
    private OrderService service;
    private FragmentHomeBinding bind;

    public HomeAdapter(Context context, List<ModelBarang> data, OrderService service, FragmentHomeBinding bind) {
        this.context = context;
        this.data = data;
        this.service = service;
        this.bind = bind;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(service.getBarang().size() == 0){
            bind.viewTotal.setVisibility(View.GONE);
        } else {
            bind.viewTotal.setVisibility(View.VISIBLE);
        }
        ModelBarang modelBarang = data.get(position);
        //ngecek barang masuk atau gk
        ModelDetailJual detail = null;
        for(ModelBarang item: service.getBarang()){
            //ketika direfresh modelbarangnya ganti krn di clear, jd perlu pake yg lama buat mencocokkan idnya
            if (modelBarang.getIdbarang().equals(item.getIdbarang())){
                detail = service.getDetailJual(item);
                modelBarang = item;

            }
        }
        holder.tNama.setText(modelBarang.getBarang());
        holder.tHarga.setText(String.valueOf(modelBarang.getHarga()));
        if (detail == null){
            holder.tGambar.setText(modelBarang.getBarang().substring(0, 1));
        } else {
            holder.tGambar.setText(String.valueOf(detail.getJumlahjual()));
        }

        ModelBarang finalModelBarang = modelBarang;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.add(finalModelBarang);
                notifyItemChanged(position); //mereload posisi item
                Toast.makeText(context, String.valueOf(service.getTotal()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNama, tHarga, tGambar;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tNama = (TextView) itemView.findViewById(R.id.textCardOne);
            tHarga = (TextView) itemView.findViewById(R.id.txtHarga);
            tGambar = (TextView) itemView.findViewById(R.id.imageCardOne);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}
