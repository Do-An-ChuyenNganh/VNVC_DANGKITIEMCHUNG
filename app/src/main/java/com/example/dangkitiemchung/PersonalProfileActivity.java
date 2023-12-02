package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

public class PersonalProfileActivity extends AppCompatActivity {
    Button btn_update;
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

    List<String> provinceInfoList = new ArrayList<>();
    List<String> provinceInfoListName = new ArrayList<>();

    List<String> districtsInfoList = new ArrayList<>();
    List<String> wardInfoList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        // Ẩn thanh trạng thái (status bar)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        addControl();

        loadSpinerNation();
        loadSpinerNationality();
        String numberPhone=getPhone();
        // Gọi trang kế tiếp
//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String numberPhone=getPhone();
//                loadSpinerNation();
//                showInfomation(numberPhone);
//                register(numberPhone.trim());
//
//
//            }
//        });

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
                        //provinceInfoListName.add(name);
                    }
                    spinnerProvinceAdapter = new SpinnerProvinceAdapter(PersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, provinceInfoList);
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

                            spinnerDistrictAdapter = new SpinnerDistrictAdapter(PersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, districtsInfoList);
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
                            spinnerWardAdapter = new SpinnerWardAdapter(PersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, wardInfoList);
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

      //  showInfomation(numberPhone);
    }
    public void  addControl(){
        btn_update= (Button) findViewById(R.id.btn_update);
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

                Toast.makeText(PersonalProfileActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                // Nếu thành công
                Intent intent = new Intent(PersonalProfileActivity.this, PolicyAndPrivacyActivity.class);
                intent.putExtra("phone_number",mPhoneNumber);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Thêm dữ liệu thất bại
                Toast.makeText(PersonalProfileActivity.this, "Thêm dữ liệu thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public String getPhone(){
      //  mPhoneNumber= getIntent().getStringExtra("phone_number");
        mPhoneNumber= "0342621113";
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
                Toast.makeText(PersonalProfileActivity.this, "Ngày sinh: " + formattedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateInfomation(String numberPhone){
        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference().child("TaiKhoan");
        Query query = taiKhoanRef.orderByChild("UserName").equalTo(numberPhone);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String taiKhoanId = snapshot.getKey();
                    if (taiKhoanId != null) {
                        taiKhoanRef.child(taiKhoanId).child("DanToc").setValue("Kinh");
                        taiKhoanRef.child(taiKhoanId).child("DiaChi").child("Huyen").setValue("Tân Bình");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ
            }
        });

    }


    public void showInfomation(String phoneNumberToQuery) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        // Thực hiện truy vấn
        databaseReference.orderByChild("UserName").equalTo(phoneNumberToQuery).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy thông tin của tài khoản từ dataSnapshot
                    TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);
                    // In thông tin tài khoản ra Logcat (hoặc xử lý theo nhu cầu của bạn)
                    Log.d("FirebaseQuery", "HoTen: " + taiKhoan.getHoTen());
                    String name= taiKhoan.getHoTen();
                    edt_name.setText(name);
                    Log.d("FirebaseQuery", "Dan toc: " + taiKhoan.getDanToc());
                    String nation = taiKhoan.getDanToc(); // Dân tộc
                    String nationally= taiKhoan.getQuocTich(); // Quốc tịch
                    // Dân tộc
                    String[] itemsToAddNation = {"Kinh", "Tày", "Thái ", "Mường", "Khmer","Hoa","Nùng","H Mông","Dao","Gia Rai","Ê Đê","Ba Na","Sán Chay","Chăm"};
                    int viTri = -1;
                    for (int i = 0; i < itemsToAddNation.length; i++) {
                        if (itemsToAddNation[i].equals(nation)) {
                            viTri = i;
                            break;
                        }
                    }
                    // Nếu tìm thấy chuỗi, chọn nó trên Spinner
                    if (viTri != -1) {
                        spinner_nation.setSelection(viTri);
                    }
                    // Quốc tịch
                    String[] itemsToAddNationality = {"Việt Nam","Thái Lan","Campuchia","Malaysia","Hồng Kông"};
                    int viTri2 = -1;
                    for (int i = 0; i < itemsToAddNationality.length; i++) {
                        if (itemsToAddNationality[i].equals(nationally)) {
                            viTri2 = i;
                            break;
                        }
                    }
                    // Nếu tìm thấy chuỗi, chọn nó trên Spinner
                    if (viTri2 != -1) {
                        spinner_nationality.setSelection(viTri2);
                    }
                    Log.d("FirebaseQuery", "Email: " + taiKhoan.getGioiTinh());
                    String sex=taiKhoan.getGioiTinh();
                    if(sex.equals("Nam"))
                    {
                        rad_male.setChecked(true);
                    }
                    else {
                        rad_female.setChecked(true);
                    }

                    Log.d("FirebaseQuery", "Email: " + taiKhoan.getNgaySinh());
                    String birthday= taiKhoan.getNgaySinh();
                    edt_birthday.setText(birthday);
                    Log.d("FirebaseQuery", "Email: " + taiKhoan.getPassWord());
                    Log.d("FirebaseQuery", "Email: " + taiKhoan.getUserName());

                    //+++++++++++++++++++++++++++++++++++++++++++++ lấy tỉnh/ thành phố

                    String province= taiKhoan.getDiaChi().getTinh();
                    int vitri4 = -1;
                    for (int i = 0; i < provinceInfoList.size(); i++) {
                        String _province = provinceInfoList.get(i);
                        String[] partProvince = _province.split(",");
                        String resultpPovince= partProvince[0].trim();
                        if (resultpPovince.equals(province)) {
                            vitri4 = i;
                            break;
                        }
                    }
                    if (vitri4 != -1) {
                        spinner_city.setSelection(vitri4);
                    }


                    ArrayAdapter<String> districtsAdapter = (ArrayAdapter<String>) spinner_district.getAdapter();
                    spinner_district.setAdapter(null);
                    List<String> lstDistricts =  new ArrayList<>( districtsAdapter.getCount());
                    for (int i = 0; i < districtsAdapter.getCount(); i++) {
                        lstDistricts.add(districtsAdapter.getItem(i));
                    }
                    spinnerDistrictAdapter = new SpinnerDistrictAdapter(PersonalProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, lstDistricts);
                    spinnerDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_district.setAdapter(spinnerDistrictAdapter);
                    //++++++++++++++++++++++++++++++++++++++++++++++ lấy huyện

                    ArrayAdapter<String> districtsAdapter1 = (ArrayAdapter<String>) spinner_district.getAdapter();
                    List<String> lstDistricts1 =  new ArrayList<>( districtsAdapter1.getCount());
                    for (int i = 0; i < districtsAdapter1.getCount(); i++) {
                        String _district = districtsAdapter1.getItem(i);
                        String[] partDistrict = _district.split(",");
                        String str= partDistrict[0].trim();
                        lstDistricts1.add(str);
                    }
                    for (String danToc : lstDistricts1) {
                        System.out.println("Huyện: " + danToc);
                    }
                    //
                    String _districts= taiKhoan.getDiaChi().getHuyen();
                    System.out.println("Huyện ở cơ sở dữ liệu: " + _districts);
                    int vitri3 = -1;
                    for (int i = 0; i < lstDistricts1.size(); i++) {
                        if (lstDistricts1.get(i).equals(_districts)) {
                            vitri3 = i;
                            break;
                        }
                    }
                    if (vitri3 != -1) {
                        spinner_district.setSelection(vitri3);
                    }
                    //+++++++++++++++++++++++++++++++++++++++++++++++
                    // Nếu tìm thấy chuỗi, chọn nó trên Spinner

                    //+++++++++++++++++++++++++++++++++++++++++++++ lấy xã
                    Log.d("FirebaseQuery", "Xã: " + taiKhoan.getDiaChi().getXa());
                    String ward= taiKhoan.getDiaChi().getXa();
                    int vitri5 = -1;
                    System.out.println(wardInfoList);
                    for (int i = 0; i < wardInfoList.size(); i++) {
                        if (wardInfoList.get(i).equals(ward)) {
                            vitri5 = i;
                            break;
                        }
                    }

                    // Nếu tìm thấy chuỗi, chọn nó trên Spinner
                    if (vitri5 != -1) {
                        spinner_ward.setSelection(vitri5);
                    }

                    Log.d("FirebaseQuery", "Email: " + taiKhoan.getDiaChi().getSoNha());

                    String address= taiKhoan.getDiaChi().getSoNha();
                    edt_address.setText(address);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseQuery", "Lỗi truy vấn Firebase: " + databaseError.getMessage());
            }
        });
    }
}