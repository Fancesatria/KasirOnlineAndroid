package com.example.authapp.Response;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.ModelKategori;
import com.example.authapp.R;

import java.util.List;

public class KategoriResponse extends RecyclerView.Adapter<KategoriResponse.ViewHolder> {
    Context context;
    List<ModelKategori> data;

    public KategoriResponse(Context context, List<ModelKategori> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_kategori, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //memasukkan ke dalam list item
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtKategori;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtKategori = (TextView)itemView.findViewById(R.id.txtKategori);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
