package com.example.authapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.authapp.Database.Repository.DetailJualRepository;
import com.example.authapp.Database.Repository.JualRepository;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.Model.ModelJual;
import com.example.authapp.R;
import com.example.authapp.databinding.ActivityPrintStrukBinding;

import java.util.List;

public class PrintStruk extends AppCompatActivity {

    ActivityPrintStrukBinding bind;
    Toolbar toolbar;
    private JualRepository jualRepository;
    private DetailJualRepository detailJualRepository;
    private ModelJual modelJual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityPrintStrukBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        jualRepository = new JualRepository(getApplication());
        detailJualRepository = new DetailJualRepository(getApplication());

//        jualRepository.getOrder(getIntent().getIntExtra("idjual", 0)).observe(PrintStruk.this, new Observer<ModelJual>() {
//            @Override
//            public void onChanged(ModelJual modelJual) {
//                bind.txtCustomerName.setText(modelJual.get);
//            }
//        });

//        detailJualRepository.getDetailOrder(getIntent().getIntExtra("idjual", 0)).observe(PrintStruk.this, new Observer<List<ModelDetailJual>>() {
//            @Override
//            public void onChanged(List<ModelDetailJual> modelDetailJuals) {
//
//            }
//        });

        toolbar = bind.toolbar;
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.unduh) {
            Toast.makeText(getApplicationContext(), "Unduh", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.print) {
            Toast.makeText(getApplicationContext(), "Print", Toast.LENGTH_SHORT).show();
        } return true;
    }
}