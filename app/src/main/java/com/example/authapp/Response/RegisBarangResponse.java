package com.example.authapp.Response;

import com.example.authapp.Model.ModelViewBarang;

public class RegisBarangResponse {
    private Boolean status;
    private ModelViewBarang data; //ini krn objectnya itu berii sm ky modelview barang

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ModelViewBarang getData() {
        return data;
    }

    public void setData(ModelViewBarang data) {
        this.data = data;
    }
}
