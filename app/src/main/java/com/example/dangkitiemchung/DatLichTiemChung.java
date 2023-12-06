package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangkitiemchung.Adapter.TrungTamTiemAdapter;
import com.example.dangkitiemchung.Models.TrungTamTiem;

import java.util.ArrayList;
import java.util.Calendar;

public class DatLichTiemChung extends AppCompatActivity {

    TrungTamTiemAdapter adapter;
    ArrayList<TrungTamTiem> list = new ArrayList<>();
    RecyclerView recycler_view;
    String strTenDD, strDiaChiCuThe;
    TextView DiaChi, NgayTiem;
    TextView btnDChi, btnLich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich_tiem_chung);
        addControls();
        btnLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });
        truyenDuLieu();


    }
    public void addControls()
    {
        DiaChi = (TextView)findViewById(R.id.trungtam);
        NgayTiem= (TextView)findViewById(R.id.ngaytiem);
        btnLich = (TextView)findViewById(R.id.iconlich);


    }
    public void truyenDuLieu()
    {
        Intent intent = getIntent();
        strTenDD = intent.getStringExtra("tendd");
        strDiaChiCuThe = intent.getStringExtra("diachicuthe");
        DiaChi.setText(strTenDD);

    }
    public void showDatePickerDialog(View view) {
        // Lấy ngày tháng năm hiện tại
        final Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, monthOfYear, dayOfMonth);

                        // Kiểm tra xem ngày đã chọn có phải là ngày hiện tại không
                        if (selectedCalendar.get(Calendar.YEAR) == currentYear
                                && selectedCalendar.get(Calendar.MONTH) == currentMonth
                                && selectedCalendar.get(Calendar.DAY_OF_MONTH) == currentDay) {
                            // Hiển thị thông báo hoặc thực hiện các hành động khác
                            Toast.makeText(DatLichTiemChung.this, "Không thể chọn ngày hiện tại", Toast.LENGTH_SHORT).show();
                        } else {
                            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            NgayTiem.setText(selectedDate);

                        }



                    }
                },
                currentYear, currentMonth, currentDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
    private void layoutRecyclerView()
    {
        recycler_view.addItemDecoration(new DividerItemDecoration(DatLichTiemChung.this, LinearLayoutManager.VERTICAL));
        adapter = new TrungTamTiemAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }
}