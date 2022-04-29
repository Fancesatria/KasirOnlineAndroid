package com.example.authapp.Model;

public class ModelKategori {
    private String nama_kategori;
    private String idtoko;

    public ModelKategori(String nama_kategori, String idtoko, String idkategori) {
        this.nama_kategori = nama_kategori;
        this.idtoko = idtoko;
        this.idkategori = idkategori;
    }

    public ModelKategori() {
        this.nama_kategori = nama_kategori;
        this.idtoko = idtoko;
        this.idkategori = idkategori;
    }


    public String getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(String idkategori) {
        this.idkategori = idkategori;
    }

    private  String idkategori;

    public ModelKategori(String id, String s) {
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
