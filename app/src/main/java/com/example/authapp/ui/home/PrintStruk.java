package com.example.authapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
//changes
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.authapp.Adapter.StrukAdapter;
import com.example.authapp.Api;
import com.example.authapp.Database.Repository.DetailJualRepository;
import com.example.authapp.Database.Repository.JualRepository;
import com.example.authapp.HomePage;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.Model.ModelJual;
import com.example.authapp.R;
import com.example.authapp.Response.DetailOrderResponse;
import com.example.authapp.ViewModel.ModelViewStruk;
import com.example.authapp.databinding.ActivityPrintStrukBinding;
import com.example.authapp.ui.home.bottom_nav.shopping.TransactionSuccess;
import com.example.authapp.util.Modul;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintStruk extends AppCompatActivity {

    ActivityPrintStrukBinding bind;
    Toolbar toolbar;
    private JualRepository jualRepository;
    private DetailJualRepository detailJualRepository;
    private ModelJual modelJual;
    private StrukAdapter adapter;
    private List<ModelViewStruk> modelDetailJualList = new ArrayList<>();
    private List<ModelBarang> modelBarangList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityPrintStrukBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());
        setSupportActionBar(bind.toolbar);
        setTitle("Print Struk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        jualRepository = new JualRepository(getApplication());
        detailJualRepository = new DetailJualRepository(getApplication());

        detailJualRepository.getDetailStruk(getIntent().getIntExtra("idjual",0)).observe(this, new Observer<List<ModelViewStruk>>() {
            @Override
            public void onChanged(List<ModelViewStruk> modelViewStruks) {
                setData(modelViewStruks);
            }
        });

        Call<DetailOrderResponse> call = Api.Order(this).getOrderDetail(getIntent().getStringExtra("idjual"));
        call.enqueue(new Callback<DetailOrderResponse>() {
            @Override
            public void onResponse(Call<DetailOrderResponse> call, Response<DetailOrderResponse> response) {
                if (response.isSuccessful()) {
                    if(modelDetailJualList.size() == 0){
                        setData(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailOrderResponse> call, Throwable t) {

            }
        });
        toolbar = bind.toolbar;
        setSupportActionBar(toolbar);
    }



    public  void setData(List<ModelViewStruk> data){
        modelDetailJualList.clear();
        modelDetailJualList.addAll(data);
        if(modelDetailJualList.size() > 0){
            ModelViewStruk struk = modelDetailJualList.get(0);
            bind.txtNoOrder.setText("No. Order : "+struk.getFakturjual());
            bind.jmlTotalOrder.setText("Rp. "+Modul.removeE(struk.getTotal()));
            bind.jmlTunai.setText("Rp. "+Modul.removeE(struk.getBayar()));
            bind.jmlKembalian.setText("Rp. "+Modul.removeE(struk.getKembali()));
            bind.txtDate.setText(struk.getTanggal_jual());

            bind.item.setLayoutManager(new LinearLayoutManager(this));
            adapter = new StrukAdapter(PrintStruk.this, modelDetailJualList);
            bind.item.setAdapter(adapter);

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
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
        }else  if (id == R.id.share) {
            Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.unduh) {
            Toast.makeText(getApplicationContext(), "Unduh", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.print) {
            Toast.makeText(getApplicationContext(), "Print", Toast.LENGTH_SHORT).show();
        } return true;
    }
}