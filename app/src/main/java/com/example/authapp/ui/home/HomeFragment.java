package com.example.authapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.HomeAdapter;
import com.example.authapp.Adapter.ProdukAdapter;
import com.example.authapp.Api;
import com.example.authapp.Database.Repository.BarangRepository;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Response.BarangGetResp;
import com.example.authapp.Response.BarangResponse;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    BarangRepository barangRepository;
    HomeAdapter produkAdapter;
    private List<ModelBarang> data = new ArrayList<>();

    private static OrderService service;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        //ini buat order
        service = new OrderService();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //memanggil db/repo
        barangRepository = new BarangRepository(getActivity().getApplication());

        //recyclerview
        binding.item.setLayoutManager(new GridLayoutManager(getActivity(), 3)); //buat grid biar 1 row ada 3 item
        produkAdapter = new HomeAdapter(getActivity(), data, service, binding);
        binding.item.setAdapter(produkAdapter);

        refreshData(true);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(false);
                Toast.makeText(getContext(), String.valueOf(service.getBarang().size()), Toast.LENGTH_SHORT).show();
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


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
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
                    if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())){
                        //memasukkan ke repo / db
                        barangRepository.insertAll(response.body().getData(), true);

                        //merefresh adapter
                        data.clear();
                        data.addAll(response.body().getData());
                        produkAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<BarangGetResp> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}