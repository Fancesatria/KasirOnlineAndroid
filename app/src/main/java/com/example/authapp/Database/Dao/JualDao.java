package com.example.authapp.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.authapp.Model.ModelJual;
import com.example.authapp.ViewModel.ViewModelJual;

import java.util.List;

@Dao
public interface JualDao {
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insertAll(List<ModelJual> juals);

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    Long  insert(ModelJual jual);

    @Query("select * from tbljual where fakturjual like :keyword")
    LiveData<List<ModelJual>> getJual(String keyword);

    @Query("select * from tbljual where idjual=:idjual")
    LiveData<ModelJual> getOrder(int idjual);

    @Delete
    void delete(ModelJual jual);

    @Update
    void update(ModelJual jual);

    @Query("DELETE FROM tbljual")
    void deleteAll();

    @Query("select * from viewJual")
    LiveData<List<ViewModelJual>> getPendapatan();
}