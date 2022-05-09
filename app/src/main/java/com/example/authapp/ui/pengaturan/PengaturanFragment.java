package com.example.authapp.ui.pengaturan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.authapp.R;
import com.example.authapp.TelpVerification;
import com.example.authapp.UserRegister;
import com.example.authapp.databinding.FragmentPengaturanBinding;

public class PengaturanFragment extends Fragment {

    private FragmentPengaturanBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PengaturanViewModel pengaturanViewModel =
                new ViewModelProvider(this).get(PengaturanViewModel.class);

        binding = FragmentPengaturanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPengaturan;
        pengaturanViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}