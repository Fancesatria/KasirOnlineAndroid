package com.example.authapp.Response;

import com.example.authapp.Model.ModelKategori;

import java.util.ArrayList;

public class KategoriGetResp {
    private String status;
    private ArrayList<ModelKategori> data;

    public KategoriGetResp(String status, ArrayList<ModelKategori> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ModelKategori> getData() {
        return data;
    }

    public void setData(ArrayList<ModelKategori> data) {
        this.data = data;
    }


}
