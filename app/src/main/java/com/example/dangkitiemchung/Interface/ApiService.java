package com.example.dangkitiemchung.Interface;

import com.example.dangkitiemchung.Models.District;
import com.example.dangkitiemchung.Models.Province;
import com.example.dangkitiemchung.Models.Ward;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("p/")
    Call<List<Province>> getProvinces();

    @GET("d/")
    Call<List<District>>  getDistrict();
    //Call<District> getDistrict(@Path("districtId") int districtId);

    @GET("w/")
    Call<List<Ward>> getWard();
}
