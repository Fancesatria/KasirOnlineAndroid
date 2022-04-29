package com.example.authapp.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity(tableName = "tblkategori")
public class ModelKategori {
    @PrimaryKey
    private String idkategori;

    private String nama_kategori;

    private String idtoko;

    public ModelKategori() {
        this.nama_kategori = nama_kategori;
        this.idtoko = idtoko;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getIdtoko() {
        return idtoko;
    }

    public void setIdtoko(String idtoko) {
        this.idtoko = idtoko;
    }

}
