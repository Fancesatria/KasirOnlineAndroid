package com.example.authapp.Model;

public class ModelPendapatan {
    private boolean status;
    private ModelJual data;

    public ModelPendapatan(boolean status, ModelJual data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ModelJual getData() {
        return data;
    }

    public void setData(ModelJual data) {
        this.data = data;
    }
}
