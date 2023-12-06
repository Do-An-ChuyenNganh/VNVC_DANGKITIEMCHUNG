package com.example.dangkitiemchung.Models;

public class TrungTamTiem {
    public TrungTamTiem(String tenDD, String diaChiCuThe) {
        TenDD = tenDD;
        DiaChiCuThe = diaChiCuThe;
    }

    public String getTenDD() {
        return TenDD;
    }

    public void setTenDD(String tenDD) {
        TenDD = tenDD;
    }

    public String getDiaChiCuThe() {
        return DiaChiCuThe;
    }

    public void setDiaChiCuThe(String diaChiCuThe) {
        DiaChiCuThe = diaChiCuThe;
    }

    private String TenDD;
    private String DiaChiCuThe;

}
