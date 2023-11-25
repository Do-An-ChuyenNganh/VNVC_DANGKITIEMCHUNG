package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangkitiemchung.Adapter.SpinnerDistrictAdapter;
import com.example.dangkitiemchung.Adapter.SpinnerProvinceAdapter;
import com.example.dangkitiemchung.Adapter.SpinnerWardAdapter;
import com.example.dangkitiemchung.Handle.ColorCharacters;
import com.example.dangkitiemchung.Interface.ApiService;
import com.example.dangkitiemchung.Models.DiaChi;
import com.example.dangkitiemchung.Models.District;
import com.example.dangkitiemchung.Models.Province;
import com.example.dangkitiemchung.Models.TaiKhoan;
import com.example.dangkitiemchung.Models.Ward;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPersonalProfileActivity extends AppCompatActivity {
    Button btn_register;
    Spinner spinner_nationality, spinner_nation,spinner_city,spinner_district,spinner_ward;
    TextView txt_nationality, txt_ward,txt_nation,txt_provinces,txt_district, txt_name, txt_address, txt_sex,txt_birthday;
    EditText edt_name, edt_address, edt_birthday;
    RadioGroup radioGroup;

    RadioButton rad_male, rad_female;
    private ApiService apiService;
    private List<Province> provinces;
    private List<District> districts;
    private List<Ward> wards;
    private  int ID_PROVINCE , ID_DISTRICT;
    private SpinnerProvinceAdapter spinnerProvinceAdapter;
    private SpinnerDistrictAdapter spinnerDistrictAdapter;
    private SpinnerWardAdapter spinnerWardAdapter;
    String  mPhoneNumber,mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_personal_profile);
        // Ẩn thanh trạng thái (status bar)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        addControl();
        loadSpinerNation();
        loadSpinerNationality();



        // Gọi trang kế tiếp
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberPhone=getPhone();
                loadSpinerNation();
                register(numberPhone.trim());


            }
        });

        edt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender();
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
        txt_address = (TextView)findViewById(R.id.txt_address);
        edt_address= (EditText) findViewById(R.id.edt_address);
        edt_name = (EditText) findViewById(R.id.edt_name);
        txt_ward=(TextView)findViewById(R.id.txt_ward);
        txt_name= (TextView)findViewById(R.id.txt_name);

        txt_sex= (TextView) findViewById(R.id.txt_sex);

        txt_birthday=(TextView)findViewById(R.id.txt_birthday);
        edt_birthday= (EditText)findViewById(R.id.edt_birthday);
        rad_male= (RadioButton) findViewById(R.id.rad_male);
        rad_female= (RadioButton) findViewById(R.id.rad_female);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);


    }
    public void handleBackgroud(){
        ColorCharacters colorCharacters = new ColorCharacters();
        colorCharacters.colorRed("Quốc tịch *",txt_nationality);
        colorCharacters.colorRed("Dân tộc *",txt_nation);
        colorCharacters.colorRed("Tỉnh / Thành phố *",txt_provinces);
        colorCharacters.colorRed("Quận / Huyện *",txt_district);
        colorCharacters.colorRed("Phường / Xã *",txt_ward);
        colorCharacters.colorRed("Địa chỉ *",txt_address);
        colorCharacters.colorRed("Họ tên *",txt_name);
        colorCharacters.colorRed("Giới tính *",txt_sex);
        colorCharacters.colorRed("Ngày sinh *",txt_birthday);

    }
    public  void set_value_initial(){
        spinner_district.setEnabled(false);
        spinner_ward.setEnabled(false);
    }


    public void register(String pPhone){
        String phone=pPhone;
        String nation= spinner_nation.getSelectedItem().toString(); // dan toc
        String nationality= spinner_nationality.getSelectedItem().toString();

        String city= spinner_city.getSelectedItem().toString();
        String district= spinner_district.getSelectedItem().toString();

        String[] parts1 = city.split(",");
        String resultCity= parts1[0].trim();
        String[] parts2 = district.split(",");
        String resultDistrict= parts2[0].trim();


        String birthday= edt_birthday.getText().toString().trim();
        String sex="";
        if(rad_male.isChecked())
            sex=rad_male.getText().toString().trim();
        else
            sex=rad_female.getText().toString().trim();

        String ward= spinner_ward.getSelectedItem().toString();
        String address= edt_address.getText().toString().trim();
        String name= edt_name.getText().toString();
        // Xử lý thêm
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan");

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setDanToc(nation);
        taiKhoan.setDiaChi(new DiaChi(resultDistrict,address ,resultCity ,ward));
        taiKhoan.setEmail("");
        taiKhoan.setGioiTinh(sex);
        taiKhoan.setHoTen(name);
        taiKhoan.setNgaySinh(birthday);
        taiKhoan.setPassWord("");
        taiKhoan.setQuocTich(nationality);
        taiKhoan.setUserName(phone);

        // Thêm dữ liệu vào Firebase
       //myRef.child("1").setValue(taiKhoan);
        Task<Void> task = myRef.push().setValue(taiKhoan);

        // Đăng ký lắng nghe sự kiện thành công hoặc thất bại của Task
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Thêm dữ liệu thành công

                Toast.makeText(RegisterPersonalProfileActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                // Nếu thành công
                Intent intent = new Intent(RegisterPersonalProfileActivity.this, PolicyAndPrivacyActivity.class);
                intent.putExtra("phone_number",mPhoneNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Thêm dữ liệu thất bại
                Toast.makeText(RegisterPersonalProfileActivity.this, "Thêm dữ liệu thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public String getPhone(){
        mPhoneNumber=getIntent().getStringExtra("phone_number");
        mVerificationId=getIntent().getStringExtra("verification_id");
        System.out.println("sdt: ***************" + mPhoneNumber);
        if (mPhoneNumber.startsWith("+84")) {
            mPhoneNumber = "0" + mPhoneNumber.substring(3);
            System.out.println("sdt: ***************" + mPhoneNumber);
        }
        return mPhoneNumber;
    }
    public void loadSpinerNation(){  // dan toc
        List<String> dataList = new ArrayList<>();
        String[] itemsToAdd = {"Kinh", "Tày", "Thái ", "Mường", "Khmer","Hoa","Nùng","H Mông","Dao","Gia Rai","Ê Đê","Ba Na","Sán Chay","Chăm"};
        dataList.addAll(Arrays.asList(itemsToAdd));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_nation.setAdapter(adapter);
    }
    public void loadSpinerNationality(){ // quoc tich
        List<String> dataList = new ArrayList<>();
        String[] itemsToAdd = {"Việt Nam","Thái Lan","Campuchia","Malaysia","Hồng Kông"};
        dataList.addAll(Arrays.asList(itemsToAdd));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_nationality.setAdapter(adapter);
    }
    public void showCalender()
    {
        Calendar calendar = Calendar.getInstance();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        // Tạo MaterialDatePicker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(today)
                .setTitleText("Chọn ngày")
                .build();

        // Hiển thị MaterialDatePicker
        datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

        // Xử lý khi người dùng chọn ngày
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Xử lý khi ngày thay đổi
                Date selectedDate = new Date(selection);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(selectedDate);

                edt_birthday.setText(formattedDate);
                Toast.makeText(RegisterPersonalProfileActivity.this, "Ngày sinh: " + formattedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

}