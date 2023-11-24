package com.example.dangkitiemchung.Models;

public class Vaccine {
    private Integer id;
    private String TenVX;
    private String PhongBenh;
    private Integer Gia;
    private String Hinh;

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public String getChongChiDinh() {
        return ChongChiDinh;
    }

    public void setChongChiDinh(String chongChiDinh) {
        ChongChiDinh = chongChiDinh;
    }

    public String getBaoQuan() {
        return BaoQuan;
    }

    public void setBaoQuan(String baoQuan) {
        BaoQuan = baoQuan;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getNguonGoc() {
        return NguonGoc;
    }

    public void setNguonGoc(String nguonGoc) {
        NguonGoc = nguonGoc;
    }

    public Integer getSlTon() {
        return SlTon;
    }

    public void setSlTon(Integer slTon) {
        SlTon = slTon;
    }

    private String ChongChiDinh;
    private String BaoQuan;
    private String MoTa;

    public Vaccine(Integer id, String tenVX, String phongBenh, Integer gia) {
        this.id = id;
        TenVX = tenVX;
        PhongBenh = phongBenh;
        Gia = gia;
    }

    private String NguonGoc;
    private Integer SlTon;



//    public Vaccine(Integer Id, String ten, String phongbenh, Integer gia) {
//        this.id=Id;
//        this.TenVX=ten;
//        this.PhongBenh = phongbenh;
//        this.Gia = gia;
//    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenVX() {
        return TenVX;
    }

    public void setTenVX(String tenVX) {
        TenVX = tenVX;
    }

    public String getPhongBenh() {
        return PhongBenh;
    }

    public void setPhongBenh(String phongBenh) {
        PhongBenh = phongBenh;
    }


    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }
}
