package com.example.authapp.ui.logout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.authapp.LoginActivity;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.databinding.FragmentLogoutTokoBinding;

public class LogoutToko extends Fragment {
    FragmentLogoutTokoBinding bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentLogoutTokoBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah anda ingin keluar dan menghapus data toko ini ?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpHelper sp = new SpHelper(getContext());
                sp.clearAll();

                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

        return bind.getRoot();
    }


}
