package com.example.authapp.Response;

import com.example.authapp.Model.ModelPegawai;
import com.example.authapp.Model.ModelToko;

import java.util.List;

public class IdentitasResponse {
    private boolean status;
    private String message;

    public IdentitasResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
