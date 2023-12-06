package com.example.dangkitiemchung.Models;

public class GioHang {
    public int getIdvx() {
        return idvx;
    }

    public void setIdvx(int idvx) {
        this.idvx = idvx;
    }

    int idvx;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    String Username;

    public GioHang() {
    }

    public GioHang(int idvx, String username) {
        this.idvx = idvx;
        this.Username = username;
    }

}
