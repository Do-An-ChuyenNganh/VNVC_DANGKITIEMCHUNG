package com.example.dangkitiemchung.Models;

public class LichSuTiemChung {
    private int id;
    private String UserName;
    private String TenVX;
    private int MuiSo;
    private String NgayTiem;
    private String PhongBenh;
    private String NoiTiem;

    public LichSuTiemChung(int id, String userName, String tenVX, int muiSo, String ngayTiem, String phongBenh, String noiTiem) {
        this.id = id;
        UserName = userName;
        TenVX = tenVX;
        MuiSo = muiSo;
        NgayTiem = ngayTiem;
        PhongBenh = phongBenh;
        NoiTiem = noiTiem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMuiSo() {
        return MuiSo;
    }

    public void setMuiSo(int muiSo) {
        MuiSo = muiSo;
    }

    public String getNgayTiem() {
        return NgayTiem;
    }

    public void setNgayTiem(String ngayTiem) {
        NgayTiem = ngayTiem;
    }

    public String getPhongBenh() {
        return PhongBenh;
    }

    public void setPhongBenh(String phongBenh) {
        PhongBenh = phongBenh;
    }

    public String getNoiTiem() {
        return NoiTiem;
    }

    public void setNoiTiem(String noiTiem) {
        NoiTiem = noiTiem;
    }
}
