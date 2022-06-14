package com.example.authapp.ui.logout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.authapp.Model.ModelJual;
import com.example.authapp.Service.OrderService;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.FragmentLogoutPegawaiBinding;
import com.example.authapp.ui.pengaturan.pegawai.LoginPegawai;

public class LogoutPegawai extends Fragment {
    FragmentLogoutPegawaiBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentLogoutPegawaiBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah anda yakin ingin keluar ?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpHelper sp = new SpHelper(getContext());
                sp.clearPegawai();

                startActivity(new Intent(getContext(), LoginPegawai.class));
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        return bind.getRoot();
    }
}
