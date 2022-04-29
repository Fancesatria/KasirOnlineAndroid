package com.example.authapp.Database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.authapp.Database.Dao.KategoriDao;
import com.example.authapp.Database.KasirDatabase;
import com.example.authapp.Model.ModelKategori;

import java.util.List;

public class KategoriRepository {
    public KategoriDao kategoriDao;
    public List<ModelKategori> allKategori;
    private KasirDatabase kasirDatabase;

    public KategoriRepository(Application application){
        kasirDatabase = KasirDatabase.getInstance(application);
        kategoriDao = kasirDatabase.kategoriDao();
        allKategori = kategoriDao.getKategori().getValue();
    }

    public void insertAll(List<ModelKategori> data, boolean truncate){
        new InsertKategoriAll(kategoriDao,truncate).execute(data);
    }

    private static class InsertKategoriAll extends AsyncTask<List<ModelKategori>,Void,Void> {
        private KategoriDao kategoriDao;
        private boolean truncate;

        public InsertKategoriAll(KategoriDao kategoriDao, boolean truncate) {
            this.kategoriDao = kategoriDao;
            this.truncate = truncate;
        }

        @Override
        protected Void doInBackground(List<ModelKategori>... lists) {
            if(truncate){
                kategoriDao.deleteAll();
            }
            kategoriDao.insertAll(lists[0]);
            return null;
        }
    }


}