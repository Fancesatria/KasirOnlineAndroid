package com.example.authapp.ui.laporan;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.RekapKategoriAdapter;
import com.example.authapp.Api;
import com.example.authapp.Database.Repository.KategoriRepository;
import com.example.authapp.Response.RekapKategoriResp;
import com.example.authapp.ViewModel.ViewModelRekapBarang;
import com.example.authapp.ViewModel.ViewModelRekapKategori;
import com.example.authapp.databinding.ActivityRekapKategoriBinding;
import com.example.authapp.util.Modul;
import com.example.authapp.util.ModulExcel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapKategori extends AppCompatActivity {
    ActivityRekapKategoriBinding bind;
    private List<ViewModelRekapKategori> data = new ArrayList<>();
    private RekapKategoriAdapter adapter;
    private KategoriRepository kategoriRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bind = ActivityRekapKategoriBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        //manggil database (sqlite)
        kategoriRepository = new KategoriRepository(getApplication());

        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RekapKategoriAdapter(RekapKategori.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

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

//        kategoriRepository.getRekapKategori(137).observe(this, new Observer<List<ViewModelRekapKategori>>() {
//            @Override
//            public void onChanged(List<ViewModelRekapKategori> viewModelRekapKategoris) {
//                data.clear();
//                data.addAll(viewModelRekapKategoris);
//                adapter.notifyDataSetChanged();
//            }
//        });

        if (true){
            Call<RekapKategoriResp> rekapKategoriRespCall = Api.RekapKategori(RekapKategori.this).getRekapKategori(cari);
            rekapKategoriRespCall.enqueue(new Callback<RekapKategoriResp>() {
                @Override
                public void onResponse(Call<RekapKategoriResp> call, Response<RekapKategoriResp> response) {
                    if (response.isSuccessful()){
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<RekapKategoriResp> call, Throwable t) {

                }
            });
        }
    }

    private void ExportExcel() throws IOException, WriteException {

        String nama = "LaporanRekapKategori";
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
                "Kategori",
                "Jumlah Penjualan",
                "Total Pendapatan"
        };
        ModulExcel.setJudul(sheet, judul);
        int row = ModulExcel.row;
        int no = 1;
        for (ViewModelRekapKategori detail : data) {
            int col = 0;
            ModulExcel.addLabel(sheet, col++, row, Modul.intToStr(no));
            ModulExcel.addLabel(sheet, col++, row, detail.getNama_kategori());
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
