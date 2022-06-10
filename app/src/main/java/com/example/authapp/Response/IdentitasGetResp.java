package com.example.authapp.Response;

import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.Model.ModelToko;

import java.util.List;

public class IdentitasGetResp {
    private boolean status;
    private ModelToko data;

    public IdentitasGetResp(boolean status, ModelToko data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ModelToko getData() {
        return data;
    }

    public void setData(ModelToko data) {
        this.data = data;
    }
}
