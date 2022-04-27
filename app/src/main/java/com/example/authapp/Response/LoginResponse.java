package com.example.authapp.Response;

import com.example.authapp.Model.ModelRegister;
import java.util.ArrayList;

public class LoginResponse {
    private String token;
    private String page;

    public String getToken() {
        return token;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
