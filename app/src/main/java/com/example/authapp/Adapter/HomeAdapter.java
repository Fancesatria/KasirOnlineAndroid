package com.example.authapp.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.authapp.ui.home.HomeFragment;
import com.example.authapp.util.Modul;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    private List<ModelBarang> data;
    private OrderService service;
    private FragmentHomeBinding bind;
    private HomeFragment fragment;


    public HomeAdapter(Context context, List<ModelBarang> data, OrderService service, HomeFragment fragment) {
        this.context = context;
        this.data = data;
        this.service = service;
        this.fragment = fragment;
        bind = fragment.binding;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelBarang modelBarang = data.get(position);
        //ngecek barang masuk atau gk
        ModelDetailJual detail = service.getDetailJual(modelBarang);
        holder.tNama.setText(Modul.upperCaseFirst(modelBarang.getBarang()));

        if (detail == null){
            holder.tGambar.setText(modelBarang.getBarang().substring(0, 1).toUpperCase()); //klu jmlhnya nol mka akan tampil huruf
            holder.linear.setBackgroundColor(context.getColor(R.color.darkgrey));
            holder.tGambar.setBackgroundColor(context.getColor(R.color.darkgrey));
            holder.tGambar.setTextColor(context.getColor(R.color.black)); //ganti warna teks
            holder.tHarga.setText(Modul.removeE(modelBarang.getHarga()));
        } else {
            holder.tGambar.setText(String.valueOf(detail.getJumlahjual()));
            holder.tGambar.setBackgroundColor(context.getColor(R.color.teal_700)); //ganti warna background
            holder.tGambar.setTextColor(context.getColor(R.color.white)); //ganti warna teks
            holder.linear.setBackgroundColor(context.getColor(R.color.teal_700));
            holder.tHarga.setText(Modul.removeE(detail.getHargajual()));
        }

        ModelBarang finalModelBarang = modelBarang;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.viewTotal.setVisibility(View.VISIBLE);

                service.add(finalModelBarang);
                notifyItemChanged(position); //mereload posisi item
                //Toast.makeText(context, String.valueOf(service.getTotal()), Toast.LENGTH_SHORT).show();

                fragment.setTotal();
            }
        });

        //memunculkan tambah/kurang order
        ModelDetailJual finalDetail = detail;
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(finalDetail == null){
                    return false; //mengembalikan ke onclick
                }
                fragment.DialogTotal(finalDetail,finalModelBarang);

//                Toast.makeText(context, "long", Toast.LENGTH_SHORT).show();


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNama, tHarga, tGambar;
        LinearLayout linear;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tNama = (TextView) itemView.findViewById(R.id.textCardOne);
            tHarga = (TextView) itemView.findViewById(R.id.txtHarga);
            tGambar = (TextView) itemView.findViewById(R.id.imageCardOne);
            linear = itemView.findViewById(R.id.linear);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}
