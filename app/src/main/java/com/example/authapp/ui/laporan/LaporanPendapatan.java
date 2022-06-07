package com.example.authapp.ui.laporan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.authapp.Adapter.LapPendapatanAdapter;
import com.example.authapp.Api;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.Model.ModelPendapatan;
import com.example.authapp.R;
import com.example.authapp.Response.PendapatanGetResp;
import com.example.authapp.databinding.ActivityLaporanPendapatanBinding;

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
    private List<ModelPendapatan> data= new ArrayList<>();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityLaporanPendapatanBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        init();

        //inisiasi recyclerview
        bind.itemPendapatan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LapPendapatanAdapter(LaporanPendapatan.this, data);
        bind.itemPendapatan.setAdapter(adapter);

        refreshData();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.laporan_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.print) {
            Toast.makeText(getApplicationContext(), "Print", Toast.LENGTH_SHORT).show();
        } return true;
    }

    public void init(){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", new Locale("in", "ID"));

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

    public void refreshData(){
        Call<PendapatanGetResp> pendapatanGetRespCall = Api.Pendapatan(LaporanPendapatan.this).getPendapatan();
        pendapatanGetRespCall.enqueue(new Callback<PendapatanGetResp>() {
            @Override
            public void onResponse(Call<PendapatanGetResp> call, Response<PendapatanGetResp> response) {
                if (response.isSuccessful()){

                    data.clear();
//                    data.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PendapatanGetResp> call, Throwable t) {

            }
        });
    }

    public void showDateFrom(){
        Calendar kalender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance(); 
                newDate.set(year, month, dayOfMonth);

                bind.dateFrom.setText(dateFormatter.format(newDate.getTime()));
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
            }
        }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

}