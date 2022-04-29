package com.example.authapp.Database.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import androidx.lifecycle.LiveData;

import com.example.authapp.Model.ModelKategori;

import java.util.List;

@Dao
public interface KategoriDao {
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insertAll(List<ModelKategori> kategoris);

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(ModelKategori kategori);

    @Query("select * from tblkategori")
    LiveData<List<ModelKategori>> getKategori();

    @Query("DELETE FROM tblkategori")
    void deleteAll();


}