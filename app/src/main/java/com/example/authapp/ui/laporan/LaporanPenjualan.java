package com.example.authapp.ui.laporan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.LapPendapatanAdapter;
import com.example.authapp.Adapter.LapPenjualanAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.PenjualanGetResp;
import com.example.authapp.ViewModel.ViewModelDetailJual;
import com.example.authapp.databinding.ActivityLaporanPenjualanBinding;
import com.example.authapp.util.Modul;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPenjualan extends AppCompatActivity {
    ActivityLaporanPenjualanBinding bind;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private List<ViewModelDetailJual> data = new ArrayList<>();
    private LapPenjualanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityLaporanPenjualanBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Laporan Penjualan");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        init();

        bind.dateFrom.setText(Modul.getDate("yyyy-MM-dd"));
        bind.dateTo.setText(Modul.getDate("yyyy-MM-dd"));

        //inisiasi recyclerview
        bind.itemPenjualan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LapPenjualanAdapter(LaporanPenjualan.this, data);
        bind.itemPenjualan.setAdapter(adapter);

        refreshData(true);

    }

    public void init(){
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        bind.dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateFrom();
            }
        });

        bind.dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTo();
            }
        });

//        bind.icSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bind.layouticsearch.setVisibility(View.GONE);
//                bind.layoutpenjualan.setVisibility(View.GONE);
//                bind.layouttotalpenjualan.setVisibility(View.GONE);
//
//                bind.layoutsearch.setVisibility(View.VISIBLE);
//            }
//        });

//        bind.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                refreshData(false);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()){
//                    refreshData(false);
//                }
//                return false;
//            }
//        });
    }

    public void refreshData(boolean fetch){
        String cari = bind.searchView.getQuery().toString();
        String mulai = bind.dateFrom.getText().toString();
        String sampai = bind.dateTo.getText().toString();

        if (true){
            Call<PenjualanGetResp> penjualanGetRespCall = Api.Penjualan(LaporanPenjualan.this).getPenjualan(mulai, sampai, cari);
            penjualanGetRespCall.enqueue(new Callback<PenjualanGetResp>() {
                @Override
                public void onResponse(Call<PenjualanGetResp> call, Response<PenjualanGetResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PenjualanGetResp> call, Throwable t) {
                    Toast.makeText(LaporanPenjualan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showDateFrom(){
        Calendar kalender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                bind.dateFrom.setText(dateFormatter.format(newDate.getTime()));
                refreshData(true);
            }
        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void showDateTo(){
        Calendar kalender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                bind.dateTo.setText(dateFormatter.format(newDate.getTime()));
                refreshData(true);
            }
        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
