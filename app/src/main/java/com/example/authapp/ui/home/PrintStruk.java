package com.example.authapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.authapp.R;

public class PrintStruk extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
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