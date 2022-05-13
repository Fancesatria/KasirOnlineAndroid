package com.example.authapp.Model;

public class ModelLoginPegawai {
    private String idpegawai;
    private String pin;

    public ModelLoginPegawai(String idpegawai, String pin) {
        this.idpegawai = idpegawai;
        this.pin = pin;
    }

    public String getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(String idpegawai) {
        this.idpegawai = idpegawai;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
