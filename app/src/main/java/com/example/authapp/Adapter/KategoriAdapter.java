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
import com.example.authapp.ui.pengaturan.DetailKategori;

import java.util.ArrayList;


public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder>{
    Context context;
    private ArrayList<ModelKategori> data; //ini perhatikan bentuknya, kalau object btkny kaya gini. klau btknya gk tepat nnti masuk ke localizederror

    //constructor kategori adapter
    public KategoriAdapter(Context context, ArrayList<ModelKategori> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        return new ViewHolder(v);
    }

    //ini utk menampilkan data atau menambahlan event
    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.ViewHolder holder, int position) {
        ModelKategori nama_kategori = data.get(position);
        holder.tKategori.setText(nama_kategori.getNama_kategori());
        //ini buat detail
        holder.Cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailKategori.class);
                i.putExtra("nama_kategori", nama_kategori.getNama_kategori());
                i.putExtra("idtoko", nama_kategori.getIdtoko());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //ini constructor untuk viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tKategori;
        CardView Cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tKategori = (TextView)itemView.findViewById(R.id.txtKategori);
            Cv = itemView.findViewById(R.id.cv);
        }
    }
}
