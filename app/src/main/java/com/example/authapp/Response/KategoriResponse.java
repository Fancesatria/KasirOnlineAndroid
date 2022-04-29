package com.example.authapp.Response;

import android.content.Context;
import com.example.authapp.Model.ModelKategori;

public class KategoriResponse {
    private String status;
    private ModelKategori data; //ini perhatikan bentuknya, kalau object btkny kaya gini. klau btknya gk tepat nnti masuk ke localizederror

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelKategori getData() {
        return data;
    }

    public void setData(ModelKategori data) {
        this.data = data;
    }
}
