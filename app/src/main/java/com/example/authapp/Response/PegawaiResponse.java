package com.example.authapp.Response;

import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.Model.ModelSatuan;

public class PegawaiResponse {
    private boolean status;
    private ModelPegawai data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ModelPegawai getData() {
        return data;
    }

    public void setData(ModelPegawai data) {
        this.data = data;
    }
}
