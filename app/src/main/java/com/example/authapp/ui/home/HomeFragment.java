package com.example.authapp.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.authapp.Adapter.HomeAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Database.Repository.BarangRepository;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.R;
import com.example.authapp.Response.BarangGetResp;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.DialogKeteranganOrderBinding;
import com.example.authapp.databinding.FragmentHomeBinding;
import com.example.authapp.ui.home.bottom_nav.PelangganOrder;
import com.example.authapp.ui.home.bottom_nav.shopping.Payment;
import com.example.authapp.ui.home.bottom_nav.shopping.ShoppingCart;
import com.example.authapp.util.Modul;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public FragmentHomeBinding binding;
    BarangRepository barangRepository;
    HomeAdapter produkAdapter;
    private List<ModelBarang> data = new ArrayList<>();

    private static OrderService service;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //ini buat order
        service = OrderService.getInstance();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //memanggil db/repo
        barangRepository = new BarangRepository(getActivity().getApplication());

        binding.titlePelanggan.setText(service.getPelanggan().getNama_pelanggan());

        //recyclerview
        binding.item.setLayoutManager(new GridLayoutManager(getActivity(), 3)); //buat grid biar 1 row ada 3 item
        produkAdapter = new HomeAdapter(getActivity(), data, service, this);
        binding.item.setAdapter(produkAdapter);

        refreshData(true);
        setTotal();

        binding.searchView.setFocusable(false);
        binding.searchView.setClickable(true);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    refreshData(false);
                }
                return false;
            }
        });

        binding.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(service.getBarang().size() == 0){
                    ErrorDialog.message(getContext(),"Keranjang masih kosong", binding.getRoot());
                }else{
                    clearCart();
                }
            }
        });

        binding.QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(ScanScreen.class);
            }
        });

        binding.cvPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PelangganOrder.class));

            }
        });

        binding.cvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ShoppingCart.class));
            }
        });

        binding.viewTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Payment.class));
            }
        });


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private Class<?> mClss;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(getContext(), clss);
            startActivityForResult(intent,101);
//            startActivity(intent);
        }
    }

    @Override
    public void onResume() {//ketika buka halaman lain maka function ini berjalan/merefresh data yg sblmnya
        super.onResume();
        binding.titlePelanggan.setText(service.getPelanggan().getNama_pelanggan());
        if (produkAdapter != null){
            produkAdapter.notifyDataSetChanged();
            setTotal();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == getActivity().RESULT_OK){
            scanProduk(data.getStringExtra("idproduk"));
//            new TaskScan(modul,getActivity().getApplication(), this).execute(data.getStringExtra("idproduk"));

        }
    }


    public void scanProduk(String idproduk){
        barangRepository.get(idproduk, new BarangRepository.OnSearch() {
            @Override
            public void findResult(ModelBarang modelBarang) {
                service.add(modelBarang);
                produkAdapter.notifyDataSetChanged();
                setTotal();
            }

            @Override
            public void notFound() {
                ErrorDialog.message(getContext(),"Barang tidak ditemukan", binding.getRoot());
            }
        });

    }

    public void clearCart(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Konfirmasi").setMessage("Apakah anda yakin untuk mengosongkan keranjang ?").setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                service.clearCart();
                setTotal();
                produkAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void refreshData(boolean fetch){
        //krn pake serch bar, hrs pke getquery bukan gettext
        String cari = binding.searchView.getQuery().toString();
        //get sql (buat berjalan offline krn retrofit berjalan dlm online
        barangRepository.getAllBarang(cari).observe(getViewLifecycleOwner(), new Observer<List<ModelBarang>>() {
            @Override
            public void onChanged(List<ModelBarang> modelBarangs) {
                data.clear();
                data.addAll(modelBarangs);
                produkAdapter.notifyDataSetChanged();
            }
        });
        //get retrofit
        if (fetch){
            Call<BarangGetResp> barangGetRespCall = Api.Barang(getActivity()).getBarang(cari);
            barangGetRespCall.enqueue(new Callback<BarangGetResp>() {
                @Override
                public void onResponse(Call<BarangGetResp> call, Response<BarangGetResp> response) {
                    if (response.isSuccessful()){
                        if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())){

                            if (response.body().getData().size() == 0) {
                                binding.txtKosong.setVisibility(View.VISIBLE);
                                binding.item.setVisibility(View.GONE);
                            }
                            //memasukkan ke repo / db
                            barangRepository.insertAll(response.body().getData(), true);

                            //merefresh adapter
                            data.clear();
                            data.addAll(response.body().getData());
                            produkAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BarangGetResp> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public void DialogTotal(ModelDetailJual modelDetailJual, ModelBarang modelBarang){

        DialogKeteranganOrderBinding binder = DialogKeteranganOrderBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(binder.getRoot());
        alertBuilder.setTitle("JUMLAH ORDER");
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int jumlah = Integer.parseInt(binder.tvJumlah.getText().toString());
                double hargaBaru = Modul.strToDouble(binder.eHarga.getText().toString());

                service.setJumlahBeli(modelBarang,  modelDetailJual.getJumlahjual(),jumlah,hargaBaru);
                //Toast.makeText(getContext(), String.valueOf(modelDetailJual.getJumlahjual()), Toast.LENGTH_SHORT).show();
                setTotal();
                produkAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        binder.eHarga.setText(Modul.toString(modelDetailJual.getHargajual()));
        binder.cbHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binder.eHarga.setVisibility(View.VISIBLE);
                }else{
                    binder.eHarga.setVisibility(View.GONE);
                }
                binder.eHarga.setText(Modul.toString(modelDetailJual.getHargajual()));
            }
        });
        binder.tvJumlah.setText(String.valueOf(modelDetailJual.getJumlahjual()));
        binder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlah = Integer.parseInt(binder.tvJumlah.getText().toString());
                jumlah++;
                binder.tvJumlah.setText(String.valueOf(jumlah));
            }
        });
        binder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jumlah = Integer.parseInt(binder.tvJumlah.getText().toString());

                if (jumlah == 1){
                    binder.kurang.setEnabled(false);
                    binder.kurang.setTextColor(getContext().getColor(R.color.darkgrey));
                }
                jumlah--;
                binder.tvJumlah.setText(String.valueOf(jumlah));
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    public void setTotal(){
        if(service.getBarang().size() == 0){
            binding.viewTotal.setVisibility(View.GONE);
        }else{
            binding.viewTotal.setVisibility(View.VISIBLE);
        }
        binding.tvJumlah.setText(String.valueOf(service.getJumlah()));
        binding.tvTotal.setText(Modul.removeE(service.getTotal()));
    }
}