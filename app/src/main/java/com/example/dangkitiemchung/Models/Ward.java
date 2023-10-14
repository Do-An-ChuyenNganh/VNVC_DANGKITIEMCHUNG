package com.example.dangkitiemchung.Models;

public class Ward {
    private int code,district_code;
    private String name;

    public Ward(int code, int district_code, String name) {
        this.code = code;
        this.district_code = district_code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(int district_code) {
        this.district_code = district_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

