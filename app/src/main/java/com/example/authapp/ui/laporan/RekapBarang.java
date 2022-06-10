package com.example.authapp.ui.laporan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapBarangAdapter;
import com.example.authapp.Api;
import com.example.authapp.Response.RekapBarangResp;
import com.example.authapp.ViewModel.ViewModelRekapBarang;
import com.example.authapp.databinding.ActivityRekapBarangBinding;

import java.io.File;
import java.io.IOException;
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

    private void ExportExcel() throws IOException, WriteException {

        String nama = "LaporanRekapBarang";
        String path = Environment.getExternalStorageDirectory().toString() + "/Download/";
        File file = new File(path + nama + " " + Modul.getDate("dd-MM-yyyy_HHmmss") + ".xls");
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet sheet = workbook.getSheet(0);

        ModulExcel.createLabel(sheet);
//        setHeader(db,sheet,5);
        ModulExcel.excelNextLine(sheet, 2);

        String[] judul = {"No.",
                "Kode Barang",
                "Barang",
                "Kategori",
                "Satuan",
                "Jumlah Penjualan",
                "Total Pendapatan"
        };
        ModulExcel.setJudul(sheet, judul);
        int row = ModulExcel.row;
        int no = 1;
        for (ViewModelRekapBarang detail : data) {
            int col = 0;
            ModulExcel.addLabel(sheet, col++, row, Modul.intToStr(no));
            ModulExcel.addLabel(sheet, col++, row, detail.getIdbarang());
            ModulExcel.addLabel(sheet, col++, row, detail.getBarang());
            ModulExcel.addLabel(sheet, col++, row, detail.getNama_kategori());
            ModulExcel.addLabel(sheet, col++, row, detail.getNama_satuan());
            ModulExcel.addLabel(sheet, col++, row, detail.getTotal_jual());
            ModulExcel.addLabel(sheet, col++, row, "Rp. "+Modul.removeE(detail.getTotal_pendapatan()));
            row++;
            no++;
        }
        workbook.write();
        workbook.close();
        Toast.makeText(this, "Berhasil disimpan di "+file.getPath(), Toast.LENGTH_SHORT).show();

    }

}
