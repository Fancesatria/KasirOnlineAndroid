package com.example.authapp.ui.laporan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.LapPendapatanAdapter;
import com.example.authapp.Adapter.LapPenjualanAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.PenjualanGetResp;
import com.example.authapp.ViewModel.ViewModelDetailJual;
import com.example.authapp.databinding.ActivityLaporanPenjualanBinding;

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
        setContentView(bind.getRoot());

        init();

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
    }

    public void refreshData(boolean fetch){
        String mulai = bind.dateFrom.getText().toString();
        String sampai = bind.dateTo.getText().toString();

        if (fetch){
            Call<PenjualanGetResp> penjualanGetRespCall = Api.Penjualan(LaporanPenjualan.this).getPenjualan(mulai, sampai, "");
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
