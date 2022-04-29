package com.example.authapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.authapp.Database.Dao.KategoriDao;
import com.example.authapp.Model.ModelKategori;

@Database(entities ={ModelKategori.class},version = 5)
public abstract class KasirDatabase extends RoomDatabase {
    private static final String name_database = "KasirDB";

//    Abstract Dao
    public abstract KategoriDao kategoriDao();

    public static volatile KasirDatabase INSTANCE= null ;
//    SINGLETON
    public  static KasirDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (KasirDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,KasirDatabase.class,name_database).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}