package com.example.authapp.Response;

import com.example.authapp.Model.ModelRegister;
import com.example.authapp.Model.ModelToko;


public class RegisterResponse {
    private Boolean status;
    private ModelToko data; // ini bntknya objek, nnti buat model toko
    private String token;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ModelToko getData() {
        return data;
    }

    public void setData(ModelToko data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
