package com.example.dangkitiemchung.Models;

public class VacXin {
    int id_vx,   gia, slton;
    String baoquan,hinh, chongchidinh, mota, nguongoc, phongbenh,  tenvx;

    public VacXin() {
    }

    public VacXin(int id_vx, String hinh, int gia, int slton, String baoquan, String chongchidinh, String mota, String nguongoc, String phongbenh, String tenvx) {
        this.id_vx = id_vx;
        this.hinh = hinh;
        this.gia = gia;
        this.slton = slton;
        this.baoquan = baoquan;
        this.chongchidinh = chongchidinh;
        this.mota = mota;
        this.nguongoc = nguongoc;
        this.phongbenh = phongbenh;
        this.tenvx = tenvx;
    }

    public int getId_vx() {
        return id_vx;
    }

    public void setId_vx(int id_vx) {
        this.id_vx = id_vx;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSlton() {
        return slton;
    }

    public void setSlton(int slton) {
        this.slton = slton;
    }

    public String getBaoquan() {
        return baoquan;
    }

    public void setBaoquan(String baoquan) {
        this.baoquan = baoquan;
    }

    public String getChongchidinh() {
        return chongchidinh;
    }

    public void setChongchidinh(String chongchidinh) {
        this.chongchidinh = chongchidinh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNguongoc() {
        return nguongoc;
    }

    public void setNguongoc(String nguongoc) {
        this.nguongoc = nguongoc;
    }

    public String getPhongbenh() {
        return phongbenh;
    }

    public void setPhongbenh(String phongbenh) {
        this.phongbenh = phongbenh;
    }

    public String getTenvx() {
        return tenvx;
    }

    public void setTenvx(String tenvx) {
        this.tenvx = tenvx;
    }
}
