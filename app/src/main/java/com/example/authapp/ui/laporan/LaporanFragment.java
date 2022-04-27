package com.example.authapp.ui.laporan;

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

        final TextView textView = binding.textLaporan;
        laporanViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}