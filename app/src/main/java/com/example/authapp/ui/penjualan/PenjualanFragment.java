package com.example.authapp.ui.penjualan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.authapp.databinding.FragmentPenjualanBinding;

public class PenjualanFragment extends Fragment {

    private FragmentPenjualanBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PenjualanViewModel galleryViewModel =
                new ViewModelProvider(this).get(PenjualanViewModel.class);

        binding = FragmentPenjualanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textPenjualan;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}