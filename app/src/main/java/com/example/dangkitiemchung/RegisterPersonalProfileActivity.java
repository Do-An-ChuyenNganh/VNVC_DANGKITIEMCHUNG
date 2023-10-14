package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dangkitiemchung.Adapter.SpinnerDistrictAdapter;
import com.example.dangkitiemchung.Adapter.SpinnerProvinceAdapter;
import com.example.dangkitiemchung.Adapter.SpinnerWardAdapter;
import com.example.dangkitiemchung.Handle.ColorCharacters;
import com.example.dangkitiemchung.Interface.ApiService;
import com.example.dangkitiemchung.Models.District;
import com.example.dangkitiemchung.Models.Province;
import com.example.dangkitiemchung.Models.Ward;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPersonalProfileActivity extends AppCompatActivity {
    Button btn_register;
    Spinner spinner_nationality, spinner_nation,spinner_city,spinner_district,spinner_ward;
    TextView txt_nationality, txt_nation,txt_provinces,txt_district;

    private ApiService apiService;
    private List<Province> provinces;
    private List<District> districts;
    private List<Ward> wards;
    private  int ID_PROVINCE , ID_DISTRICT;
    private SpinnerProvinceAdapter spinnerProvinceAdapter;
    private SpinnerDistrictAdapter spinnerDistrictAdapter;
    private SpinnerWardAdapter spinnerWardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal_profile);
        // Ẩn thanh trạng thái (status bar)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        addControl();

        // Gọi trang kế tiếp
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPersonalProfileActivity.this, PolicyAndPrivacyActivity.class);
                startActivity(intent);
            }
        });
        // Xử lí giao diện
        handleBackgroud();
        //
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://provinces.open-api.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Gọi API để lấy danh sách tỉnh thành
        Call<List<Province>> provinceCall = apiService.getProvinces();
        provinceCall.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (response != null && response.body() != null) {
                    List<Province> provinces = response.body();
                    List<String> provinceInfoList = new ArrayList<>();
                    for (Province province : provinces) {
                        String name = province.getName();
                        int code = province.getCode();
                        String strcode= String.valueOf(code);
                        String common= name+","+strcode;
                        provinceInfoList.add(common);
                    }
                    spinnerProvinceAdapter = new SpinnerProvinceAdapter(RegisterPersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, provinceInfoList);
//                  ArrayAdapter<String> adapterProvince = new ArrayAdapter<>(RegisterPersonalProfile.this, android.R.layout.simple_spinner_dropdown_item, provinceInfoList);
                    spinnerProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_city.setAdapter(spinnerProvinceAdapter);

                } else {
                    System.out.println("Không có dữ liệu");
                }
            }
            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                // Xử lý lỗi khi gọi API
            }
        });
        // Xử lí sự kiện khi chọn item trên spinner tỉnh thành
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinner_district.setEnabled(true);
                String selectedValue = parentView.getItemAtPosition(position).toString();
                System.out.println( "index trung ========================================"+ selectedValue );
                String[] parts = selectedValue.split(",");
                int _id=Integer.parseInt(parts[1].trim().toString());
                ID_PROVINCE=_id;

                // Gọi API quận huyện
                Call<List<District>> districtCall = apiService.getDistrict();
                districtCall.enqueue(new Callback<List<District>>() {
                    @Override
                    public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                        if (response != null && response.body() != null) {

                            List<District> districts = response.body();
                            List<String> districtsInfoList = new ArrayList<>();
                            String name="";
                            String common="";
                            int district_code=1, province_code=1 ;

                            for (District district : districts) {
                                   name = district.getName();
                                   district_code = district.getCode();
                                   ID_DISTRICT= district_code;
                                   common = name+ ", "+ ID_DISTRICT;
                                   province_code = district.getProvince_code();
                                if(ID_PROVINCE ==  province_code){
                                    districtsInfoList.add(common);
                                }

                            }

                            spinnerDistrictAdapter = new SpinnerDistrictAdapter(RegisterPersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, districtsInfoList);
                          //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterPersonalProfile.this, android.R.layout.simple_spinner_dropdown_item, districtsInfoList);
                            spinnerDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_district.setAdapter(spinnerDistrictAdapter);

                        } else {
                            System.out.println("Không có dữ liệu");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<District>> call, Throwable t) {
                        // Xử lý lỗi khi gọi API
                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn (nếu cần).
            }
        });
        //Xử lí sự kiện khi chọn item trên spinner quận huyện
        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinner_ward.setEnabled(true);
                String selectedValue = parentView.getItemAtPosition(position).toString();
                System.out.println( "index trung ========================================"+ selectedValue );
                String[] parts = selectedValue.split(",");
                int _id=Integer.parseInt(parts[1].trim().toString());

                ID_DISTRICT=_id;
                // Gọi API Xã Phường
                Call<List<Ward>> wardtCall = apiService.getWard();
                wardtCall.enqueue(new Callback<List<Ward>>() {
                    @Override
                    public void onResponse(Call<List<Ward>> call, Response<List<Ward>> response) {
                        if (response != null && response.body() != null) {
                            List<Ward> wards = response.body();
                            List<String> wardInfoList = new ArrayList<>();
                            String name="";
                            int district_code ;
                            for (Ward ward : wards) {
                                district_code = ward.getDistrict_code();
                                if (ID_DISTRICT == district_code) {
                                    name = ward.getName();
                                    wardInfoList.add(name);
                                }
                            }
                            spinnerWardAdapter = new SpinnerWardAdapter(RegisterPersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, wardInfoList);
                            spinnerWardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_ward.setAdapter(spinnerWardAdapter);
                        } else {
                            System.out.println("Không có dữ liệu");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Ward>> call, Throwable t) {
                        // Xử lý lỗi khi gọi API
                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn (nếu cần).
            }
        });




    }
    public void  addControl(){
        btn_register= (Button) findViewById(R.id.btn_register);
        spinner_nationality = (Spinner)findViewById(R.id.spinner_nationality);
        spinner_nation = (Spinner)findViewById(R.id.spinner_nation);
        spinner_city = (Spinner)findViewById(R.id.spinner_provinces);
        spinner_district = (Spinner)findViewById(R.id.spinner_district);
        spinner_ward = (Spinner)findViewById(R.id.spinner_ward);
        txt_nationality = (TextView)findViewById(R.id.txt_nationality);
        txt_nation = (TextView)findViewById(R.id.txt_nation);
        txt_provinces = (TextView)findViewById(R.id.txt_provinces);
        txt_district = (TextView)findViewById(R.id.txt_district);

    }
    public void handleBackgroud(){
        ColorCharacters colorCharacters = new ColorCharacters();
        colorCharacters.colorRed("Quốc tịch *",txt_nationality);
        colorCharacters.colorRed("Dân tộc *",txt_nation);
        colorCharacters.colorRed("Tỉnh / Thành phố *",txt_provinces);
        colorCharacters.colorRed("Quận / Huyện *",txt_district);
    }
    public  void set_value_initial(){
        spinner_district.setEnabled(false);
        spinner_ward.setEnabled(false);

    }
}