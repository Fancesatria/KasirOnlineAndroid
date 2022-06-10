package com.example.authapp.ui.laporan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapBarangAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.RekapBarangResp;
import com.example.authapp.ViewModel.ViewModelRekapBarang;
import com.example.authapp.databinding.ActivityRekapBarangBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapBarang extends AppCompatActivity {
    ActivityRekapBarangBinding bind;
    private RekapBarangAdapter adapter;
    private List<ViewModelRekapBarang> data = new ArrayList<>();

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapBarangBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        //Recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapBarangAdapter(RekapBarang.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

        //search
        bind.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    refreshData(false);
                }
                return false;
            }
        });
    }

//    public void init(){
//        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//
//        bind.dateFrom.setFocusable(false);
//        bind.dateFrom.setClickable(true);
//        bind.dateTo.setFocusable(false);
//        bind.dateTo.setClickable(true);
//
//        bind.dateFrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateFrom();
//            }
//        });
//
//        bind.dateTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateTo();
//            }
//        });
//
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
//
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
//    }

    public void refreshData(boolean fetch){
        String cari = bind.searchView.getQuery().toString();
        if (true){
            Call<RekapBarangResp> rekapBarangRespCall = Api.RekapBarang(RekapBarang.this).getRekapBarang(cari);
            rekapBarangRespCall.enqueue(new Callback<RekapBarangResp>() {
                @Override
                public void onResponse(Call<RekapBarangResp> call, Response<RekapBarangResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RekapBarangResp> call, Throwable t) {

                }
            });
        }
    }

//    public void showDateFrom(){
//        Calendar kalender = Calendar.getInstance();
//
//        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, month, dayOfMonth);
//
//                bind.dateFrom.setText(dateFormatter.format(newDate.getTime()));
//                refreshData(true);
//            }
//        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.show();
//    }
//
//    public void showDateTo(){
//        Calendar kalender = Calendar.getInstance();
//
//        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, month, dayOfMonth);
//
//                bind.dateTo.setText(dateFormatter.format(newDate.getTime()));
//                refreshData(true);
//            }
//        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));
//
//        datePickerDialog.show();
//    }

}
