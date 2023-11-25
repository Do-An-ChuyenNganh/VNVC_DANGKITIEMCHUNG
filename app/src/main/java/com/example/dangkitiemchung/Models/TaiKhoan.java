package com.example.dangkitiemchung.Models;

import com.google.firebase.database.PropertyName;

public class TaiKhoan {
    @PropertyName("DanToc")
    private String DanToc;
    @PropertyName("DiaChi")
    private DiaChi DiaChi;
    @PropertyName("Email")
    private String Email;
    @PropertyName("GioiTinh")
    private String GioiTinh;

    private String HoTen;
    @PropertyName("NgaySinh")
    private String NgaySinh;
    @PropertyName("PassWord")
    private String PassWord;
    @PropertyName("QuocTich")
    private String QuocTich;
    @PropertyName("UserName")
    private String UserName;

    // Constructor
    public TaiKhoan() {
        // Default constructor required for calls to DataSnapshot.getValue(TaiKhoan.class)
    }

    // Constructor with parameters
    public TaiKhoan(String danToc, DiaChi diaChi, String email, String gioiTinh,
                    String hoTen, String ngaySinh, String passWord,
                    String quocTich, String userName) {
        DanToc = danToc;
        DiaChi = diaChi;
        Email = email;
        GioiTinh = gioiTinh;
        HoTen = hoTen;
        NgaySinh = ngaySinh;
        PassWord = passWord;
        QuocTich = quocTich;
        UserName = userName;
    }

    // Getters and Setters
    @PropertyName("DanToc")
    public String getDanToc() {
        return DanToc;
    }
    @PropertyName("DanToc")
    public void setDanToc(String danToc) {
        this.DanToc = danToc;
    }
    @PropertyName("DiaChi")
    public DiaChi getDiaChi() {
        return DiaChi;
    }
    @PropertyName("DiaChi")
    public void setDiaChi(DiaChi diaChi) {
        this.DiaChi = diaChi;
    }
    @PropertyName("Email")
    public String getEmail() {
        return Email;
    }
    @PropertyName("Email")
    public void setEmail(String email) {
        this.Email = email;
    }
    @PropertyName("GioiTinh")
    public String getGioiTinh() {
        return GioiTinh;
    }

    @PropertyName("GioiTinh")
    public void setGioiTinh(String gioiTinh) {
        this.GioiTinh = gioiTinh;
    }

    @PropertyName("HoTen")
    public String getHoTen() {
        return HoTen;
    }
    @PropertyName("HoTen")
    public void setHoTen(String hoTen) {
        this.HoTen = hoTen;
    }
    @PropertyName("NgaySinh")
    public String getNgaySinh() {
        return NgaySinh;
    }
    @PropertyName("NgaySinh")
    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    @PropertyName("PassWord")
    public String getPassWord() {
        return PassWord;
    }
    @PropertyName("PassWord")
    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }
    @PropertyName("QuocTich")
    public String getQuocTich() {
        return QuocTich;
    }
    @PropertyName("QuocTich")
    public void setQuocTich(String quocTich) {
        this.QuocTich = quocTich;
    }
    @PropertyName("UserName")
    public String getUserName() {
        return UserName;
    }

    @PropertyName("UserName")
    public void setUserName(String userName) {
        this.UserName = userName;
    }
}
