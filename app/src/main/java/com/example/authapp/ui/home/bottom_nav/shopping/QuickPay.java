package com.example.authapp.ui.home.bottom_nav.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.authapp.databinding.FragmentBayarCepatBinding;

public class QuickPay extends Fragment {
    FragmentBayarCepatBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentBayarCepatBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        View root = bind.getRoot();

        return root;
    }
}
