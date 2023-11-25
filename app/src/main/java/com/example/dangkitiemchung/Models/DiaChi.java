package com.example.dangkitiemchung.Models;

import com.google.firebase.database.PropertyName;

public class DiaChi {

    private String Huyen;

    private String SoNha;

    private String Tinh;

    private String Xa;

    // Constructor
    public DiaChi() {
        // Default constructor required for calls to DataSnapshot.getValue(DiaChi.class)
    }

    public DiaChi(String huyen, String soNha, String tinh, String xa) {
        Huyen = huyen;
        SoNha = soNha;
        Tinh = tinh;
        Xa = xa;
    }


    // Getters and Setters
    @PropertyName("Huyen")
    public String getHuyen() {
        return Huyen;
    }
    @PropertyName("Huyen")
    public void setHuyen(String huyen) {
        this.Huyen = huyen;
    }

    @PropertyName("SoNha")
    public String getSoNha() {
        return SoNha;
    }
    @PropertyName("SoNha")
    public void setSoNha(String soNha) {
        this.SoNha = soNha;
    }
    @PropertyName("Tinh")
    public String getTinh() {
        return Tinh;
    }
    @PropertyName("Tinh")
    public void setTinh(String tinh) {
        this.Tinh = tinh;
    }
    @PropertyName("Xa")
    public String getXa() {
        return Xa;
    }
    @PropertyName("Xa")
    public void setXa(String xa) {
        this.Xa = xa;
    }
}
