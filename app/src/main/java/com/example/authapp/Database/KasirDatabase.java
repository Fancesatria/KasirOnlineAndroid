package com.example.authapp.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.authapp.Database.Dao.BarangDao;
import com.example.authapp.Database.Dao.DetailJualDao;
import com.example.authapp.Database.Dao.JualDao;
import com.example.authapp.Database.Dao.KategoriDao;
import com.example.authapp.Database.Dao.PegawaiDao;
import com.example.authapp.Database.Dao.PelangganDao;
import com.example.authapp.Database.Dao.SatuanDao;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.Model.ModelJual;
import com.example.authapp.Model.ModelKategori;
import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.Model.ModelPelanggan;
import com.example.authapp.Model.ModelSatuan;

@Database(entities ={
        ModelKategori.class,
        ModelSatuan.class,
        ModelBarang.class,
        ModelPegawai.class,
        ModelPelanggan.class,
        ModelJual.class,
        ModelDetailJual.class
},version = 5)
public abstract class KasirDatabase extends RoomDatabase {
    private static final String name_database = "KasirDB";

//    Abstract Dao
    public abstract KategoriDao kategoriDao();
    public abstract BarangDao barangDao();
    public abstract DetailJualDao detailJualDao();
    public abstract JualDao jualDao();
    public abstract PegawaiDao pegawaiDao();
    public abstract PelangganDao pelangganDao();
    public abstract SatuanDao satuanDao();

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