package com.example.authapp.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.authapp.Model.ModelJual;

import java.util.List;

@Dao
public interface JualDao {
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insertAll(List<ModelJual> juals);

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert(ModelJual jual);

    @Query("select * from tbljual where fakturjual like :keyword")
    LiveData<List<ModelJual>> getJual(String keyword);

    @Delete
    void delete(ModelJual jual);

    @Query("DELETE FROM tbljual")
    void deleteAll();
}