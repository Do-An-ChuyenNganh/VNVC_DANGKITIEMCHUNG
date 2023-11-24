package com.example.dangkitiemchung.Models;

public class LichTiem {
    private int id;
    private int GiaBan;
    private String UserName;
    private String TenVX;
    private String TinhTrang;
    private String NgayDat;
    private String NgayTiem;
    private String Hinh;
    private String NoiTiem;

    public LichTiem(int id, String userName, String tenVX, String tinhTrang, String ngayDat, String ngayTiem, String noiTiem) {
        this.id = id;
        this.UserName = userName;
        this.TenVX = tenVX;
        this.TinhTrang = tinhTrang;
        this.NgayDat = ngayDat;
        this.NgayTiem = ngayTiem;
        this.NoiTiem = noiTiem;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(int giaBan) {
        GiaBan = giaBan;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTenVX() {
        return TenVX;
    }

    public void setTenVX(String tenVX) {
        TenVX = tenVX;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public void setNgayDat(String ngayDat) {
        NgayDat = ngayDat;
    }

    public String getNgayTiem() {
        return NgayTiem;
    }

    public void setNgayTiem(String ngayTiem) {
        NgayTiem = ngayTiem;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public String getNoiTiem() {
        return NoiTiem;
    }

    public void setNoiTiem(String noiTiem) {
        NoiTiem = noiTiem;
    }

}
