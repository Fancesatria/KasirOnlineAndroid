package com.example.authapp.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.authapp.Model.ModelDetailJual;

import java.util.List;

@Dao
public interface DetailJualDao {
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insertAll(List<ModelDetailJual> detailjuals);

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(ModelDetailJual detailjual);

    @Query("select * from tbldetailjual")
    LiveData<List<ModelDetailJual>> getAllJual();\

    @Update
    void update(ModelDetailJual detailJual);

    @Delete
    void delete(ModelDetailJual detailjual);

    @Query("DELETE FROM tbldetailjual")
    void deleteAll();
}