package com.example.authapp.Response;

import com.example.authapp.Model.ModelKategori;

import java.util.ArrayList;
import java.util.List;

public class KategoriGetResp {
    private boolean status;
    private List<ModelKategori> data;

    public KategoriGetResp(boolean status, List<ModelKategori> data) {
        this.status = status;
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelKategori> getData() {
        return data;
    }

    public void setData(List<ModelKategori> data) {
        this.data = data;
    }


}
