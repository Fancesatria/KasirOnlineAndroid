package com.example.authapp.Response;

import com.example.authapp.Model.ModelJual;

import java.util.List;

public class PendapatanGetResp {
    private boolean status;
    private List<ModelJual> data;

    public PendapatanGetResp(boolean status, List<ModelJual> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ModelJual> getData() {
        return data;
    }

    public void setData(List<ModelJual> data) {
        this.data = data;
    }
}
