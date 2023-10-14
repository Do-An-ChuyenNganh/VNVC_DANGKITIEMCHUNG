package com.example.dangkitiemchung.Models;

import java.util.List;

public class District {
    private int code;

    public int getProvince_code() {
        return province_code;
    }

    public void setProvince_code(int province_code) {
        this.province_code = province_code;
    }

    private int province_code;
    private String name;
    private List<Ward> wards;

    public District(int code,int province_code, String name, List<Ward> wards) {
        this.code = code;
        this.province_code = province_code;
        this.name = name;
        this.wards = wards;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }
}
