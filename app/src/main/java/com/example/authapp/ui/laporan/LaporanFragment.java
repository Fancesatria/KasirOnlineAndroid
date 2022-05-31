package com.example.authapp.ui.laporan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.authapp.databinding.FragmentLaporanBinding;

public class LaporanFragment extends Fragment {

    private FragmentLaporanBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LaporanViewModel laporanViewModel =
                new ViewModelProvider(this).get(LaporanViewModel.class);

        binding = FragmentLaporanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

//        final TextView textView = binding.textLaporan;
//        laporanViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void init(){
        binding.layoutLaporanPendapatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LaporanPendapatan.class));
            }
        });

        binding.layoutLaporanPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LaporanPenjualan.class));
            }
        });
    }
}