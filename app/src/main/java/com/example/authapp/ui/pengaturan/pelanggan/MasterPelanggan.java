package com.example.authapp.ui.pengaturan.pelanggan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.authapp.Adapter.PelangganAdapter;
import com.example.authapp.Api;
import com.example.authapp.Component.ErrorDialog;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Component.SuccessDialog;
import com.example.authapp.Database.Repository.PelangganRepository;
import com.example.authapp.HomePage;
import com.example.authapp.LoginActivity;
import com.example.authapp.Model.ModelPelanggan;
import com.example.authapp.R;
import com.example.authapp.Response.PelangganGetResp;
import com.example.authapp.Response.PelangganResponse;
import com.example.authapp.Service.PelangganService;
import com.example.authapp.ViewModel.ViewModelRekapPelanggan;
import com.example.authapp.databinding.ActivityMasterPegawaiBinding;
import com.example.authapp.databinding.ActivityMasterPelangganBinding;
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

public class MasterPelanggan extends AppCompatActivity {
    private ActivityMasterPelangganBinding bind;
    private List<ModelPelanggan> data = new ArrayList<>();
    private PelangganAdapter adapter;
    private PelangganRepository pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityMasterPelangganBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pelanggan");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(bind.getRoot());

        // memanggil database
        pr = new PelangganRepository(getApplication());

        // mendefinisikan recyclerview
        bind.item.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PelangganAdapter(MasterPelanggan.this, data);
        bind.item.setAdapter(adapter);

        refreshData(true);

        bind.plusBtnPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MasterPelanggan.this, TambahPelanggan.class));
                finish();
            }
        });

        bind.searchPelanggan.setFocusable(false);
        bind.searchPelanggan.setClickable(true);

        // buat search
        bind.searchPelanggan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshData(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    refreshData(false);
                }
                return false;
            }
        });
    }

    // ini dkasi boolean biar gk merequest terusn klo request terus2 an bakal repot
    public void refreshData(boolean fetch) {
        // inisiasi search dari layoutnya
        String cari = bind.searchPelanggan.getQuery().toString();
        // get sqlite
        pr.getAllPelanggan(cari).observe(this, new Observer<List<ModelPelanggan>>() {
            @Override
            public void onChanged(List<ModelPelanggan> modelPelanggans) {
                data.clear();
                data.addAll(modelPelanggans);
                adapter.notifyDataSetChanged();
            }
        });

        // get retrofit
        if (fetch) {
            PelangganService ps = Api.Pelanggan(MasterPelanggan.this);
            Call<PelangganGetResp> call = ps.getPel(cari);
            call.enqueue(new Callback<PelangganGetResp>() {
                @Override
                public void onResponse(Call<PelangganGetResp> call, Response<PelangganGetResp> response) {
                    // Toast.makeText(MasterPelanggan.this,
                    // String.valueOf(response.body().getData().size()), Toast.LENGTH_SHORT).show();
                    if (data.size() != response.body().getData().size() || !data.equals(response.body().getData())) {
                        // memasukkan ke db kalau gada data yg sama
                        pr.insertAll(response.body().getData(), true);

                        // merefresh adapter
                        data.clear();
                        data.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PelangganGetResp> call, Throwable t) {
                    Toast.makeText(MasterPelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void DeletePel(int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MasterPelanggan.this);
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah anda yakin untuk menghapus data ini ?");
        alert.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoadingDialog.load(MasterPelanggan.this);
                Call<PelangganResponse> pelangganResponseCall = Api.Pelanggan(MasterPelanggan.this).deletePel(id);
                pelangganResponseCall.enqueue(new Callback<PelangganResponse>() {
                    @Override
                    public void onResponse(Call<PelangganResponse> call, Response<PelangganResponse> response) {
                        LoadingDialog.close();
                        if (response.isSuccessful()) {
                            if (response.body().isStatus()) {
                                pr.delete(response.body().getData());
                                SuccessDialog.message(MasterPelanggan.this, getString(R.string.deleted_success),
                                        bind.getRoot());
                            }
                        } else {
                            ErrorDialog.message(MasterPelanggan.this, getString(R.string.error_pelanggan_message),
                                    bind.getRoot());
                        }
                        refreshData(true);
                    }

                    @Override
                    public void onFailure(Call<PelangganResponse> call, Throwable t) {
                        LoadingDialog.close();
                        Toast.makeText(MasterPelanggan.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_export, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, HomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.export) {
            Toast.makeText(getApplicationContext(), "Exported", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void ExportExcel() throws IOException, WriteException {

        String nama = "LaporanPelanggan";
        String path = Environment.getExternalStorageDirectory().toString() + "/Download/";
        File file = new File(path + nama + " " + Modul.getDate("dd-MM-yyyy_HHmmss") + ".xls");
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet sheet = workbook.getSheet(0);

        ModulExcel.createLabel(sheet);
        // setHeader(db,sheet,5);
        ModulExcel.excelNextLine(sheet, 2);

        String[] judul = { "No.",
                "Pelanggan",
                "Alamat",
                "No Telepon"
        };
        ModulExcel.setJudul(sheet, judul);
        int row = ModulExcel.row;
        int no = 1;
        for (ModelPelanggan detail : data) {
            int col = 0;
            ModulExcel.addLabel(sheet, col++, row, Modul.intToStr(no));
            ModulExcel.addLabel(sheet, col++, row, detail.getNama_pelanggan());
            ModulExcel.addLabel(sheet, col++, row, detail.getAlamat_pelanggan());
            ModulExcel.addLabel(sheet, col++, row, detail.getNo_telepon());
            row++;
            no++;
        }
        workbook.write();
        workbook.close();
        Toast.makeText(this, "Berhasil disimpan di " + file.getPath(), Toast.LENGTH_SHORT).show();

    }

}
