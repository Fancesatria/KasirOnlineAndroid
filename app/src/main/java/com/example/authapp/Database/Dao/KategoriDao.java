package com.example.authapp.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;

import com.example.authapp.Model.ModelKategori;

import java.util.List;

@Dao
public interface KategoriDao {
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insertAll(List<ModelKategori> kategoris);

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(ModelKategori kategori);

    @Update
    void update(ModelKategori kategori);

    @Query("select * from tblkategori order by LOWER(nama_kategori) ASC")
    LiveData<List<ModelKategori>> getKategori();


    @Delete
    void delete(ModelKategori kategori);

    @Query("DELETE FROM tblkategori")
    void deleteAll();


}