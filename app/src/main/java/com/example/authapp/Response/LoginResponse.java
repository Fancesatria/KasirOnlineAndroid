package com.example.authapp.Response;

import com.example.authapp.Model.ModelRegister;
import java.util.ArrayList;

public class LoginResponse {
    private String token;
    private ArrayList<ModelRegister> data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<ModelRegister> getData() {
        return data;
    }

    public void setData(ArrayList<ModelRegister> data) {
        this.data = data;
    }
}
