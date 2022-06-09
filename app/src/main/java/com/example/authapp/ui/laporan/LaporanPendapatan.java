package com.example.authapp.ui.laporan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.authapp.Adapter.LapPendapatanAdapter;
import com.example.authapp.Api;
import com.example.authapp.Model.ModelJual;
import com.example.authapp.Response.PendapatanGetResp;
import com.example.authapp.ViewModel.ViewModelJual;
import com.example.authapp.databinding.ActivityLaporanPendapatanBinding;
import com.example.authapp.util.Modul;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPendapatan extends AppCompatActivity {
    ActivityLaporanPendapatanBinding bind;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private LapPendapatanAdapter adapter;
    private List<ViewModelJual> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityLaporanPendapatanBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        init();
        bind.dateFrom.setText(Modul.getDate("yyyy-MM-dd"));
        bind.dateTo.setText(Modul.getDate("yyyy-MM-dd"));
        //inisiasi recyclerview
        bind.itemPendapatan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LapPendapatanAdapter(LaporanPendapatan.this, data);
        bind.itemPendapatan.setAdapter(adapter);

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

        bind.icSeacrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.layouticsearch.setVisibility(View.GONE);
                bind.layoutpendapatan.setVisibility(View.GONE);
                bind.layouttotal.setVisibility(View.GONE);

                bind.layoutsearch.setVisibility(View.VISIBLE);
            }
        });

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

    public void refreshData(boolean fetch){
        String cari = bind.searchView.getQuery().toString();
        String mulai = bind.dateFrom.getText().toString();
        String sampai = bind.dateTo.getText().toString();

        if (true){
            Call<PendapatanGetResp> pendapatanGetRespCall = Api.Pendapatan(LaporanPendapatan.this).getPendapatan(mulai, sampai, cari);
            pendapatanGetRespCall.enqueue(new Callback<PendapatanGetResp>() {
                @Override
                public void onResponse(Call<PendapatanGetResp> call, Response<PendapatanGetResp> response) {
                    if (response.isSuccessful()){

                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();

                        //Toast.makeText(LaporanPendapatan.this, String.valueOf(response.body().getData().size()), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PendapatanGetResp> call, Throwable t) {
                    Toast.makeText(LaporanPendapatan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                bind.dateFrom.setFocusable(false);
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
                bind.dateFrom.setFocusable(false);
            }
        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

}