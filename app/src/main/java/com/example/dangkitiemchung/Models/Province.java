package com.example.dangkitiemchung.Models;

import java.util.List;

public class Province {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;
    private String name;
    private List<District> districts;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public Province(int code, String name, List<District> districts) {
        this.code = code;
        this.name = name;
        this.districts = districts;
    }
}
