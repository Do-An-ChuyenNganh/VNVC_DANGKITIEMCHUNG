package com.example.dangkitiemchung.Models;

public class LichSuTiemChung {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    private int id;
    private String UserName;
    private String TenVX;
    private int MuiSo;
    private String NgayTiem;
    private String PhongBenh;
    private String NoiTiem;

    public LichSuTiemChung(String Key, int Id_VX, String UserName, String TenVX, int MuiSo, String NgayTiem, String PhongBenh, String NoiTiem) {
        this.id = Id_VX;
        this.UserName = UserName;
        this.TenVX = TenVX;
        this.MuiSo = MuiSo;
        this.NgayTiem = NgayTiem;
        this.PhongBenh = PhongBenh;
        this.NoiTiem = NoiTiem;
        this.key = Key;
    }
    public LichSuTiemChung( int Id_VX, String UserName, String TenVX, int MuiSo, String NgayTiem, String PhongBenh, String NoiTiem) {
        this.id = Id_VX;
        this.UserName = UserName;
        this.TenVX = TenVX;
        this.MuiSo = MuiSo;
        this.NgayTiem = NgayTiem;
        this.PhongBenh = PhongBenh;
        this.NoiTiem = NoiTiem;
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
