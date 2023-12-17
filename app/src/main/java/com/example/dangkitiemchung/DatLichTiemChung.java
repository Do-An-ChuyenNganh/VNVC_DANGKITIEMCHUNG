package com.example.dangkitiemchung;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangkitiemchung.Adapter.LinhGioHangAdapter;
import com.example.dangkitiemchung.Adapter.LoadDatLichAdapter;
import com.example.dangkitiemchung.Adapter.TrungTamTiemAdapter;
import com.example.dangkitiemchung.Models.LaySDT;
import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.Models.MangDatLich;
import com.example.dangkitiemchung.Models.TrungTamTiem;
import com.example.dangkitiemchung.Models.VacXin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatLichTiemChung extends AppCompatActivity {

    LoadDatLichAdapter adapter;
    ArrayList<VacXin> list = new ArrayList<>();
    RecyclerView recycler_view;
    String strTenDD, strDiaChiCuThe, strUserName;
    Button btn_DatLich;
    TextView DiaChi, NgayTiem;
    TextView btnDChi, btnLich;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRefLichSuDat = firebaseDatabase.getReference("LichSuDat");
    DatabaseReference myRef = firebaseDatabase.getReference("VacXin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich_tiem_chung);
        addControls();
        recycler_view = findViewById(R.id.rec_vaccine);
        layoutRecyclerView();
        adapter.notifyDataSetChanged();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list != null) {
                    list.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String baoquan = dataSnapshot.child("BaoQuan").getValue(String.class);
                    String chongcd = dataSnapshot.child("ChongChiDinh").getValue(String.class);
                    Integer gia = dataSnapshot.child("Gia").getValue(Integer.class);
                    String mota = dataSnapshot.child("MoTa").getValue(String.class);
                    String nguongoc = dataSnapshot.child("NguonGoc").getValue(String.class);
                    String phongBenh = dataSnapshot.child("PhongBenh").getValue(String.class);
                    String tenVX = dataSnapshot.child("TenVX").getValue(String.class);
                    String hinh = "1";
                    int id_VX = dataSnapshot.child("id_VX").getValue(Integer.class);
                    VacXin tl = new VacXin(id_VX, hinh,gia , 1, baoquan, chongcd, mota, nguongoc, phongBenh, tenVX);
                    for(VacXin v : MangDatLich.mangmuon)
                    {
                        if(id_VX == v.getId_vx()) {
                            list.add(tl);
                        }
                    }


                }
                recycler_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: ");
            }

        });
        btnLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });
        btnDChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatLichTiemChung.this, LayDiaChi.class);
                startActivity(intent);


            }
        });
        truyenDuLieu();

        datLichTiem();


    }
    public void addControls()
    {
        btn_DatLich = (Button)findViewById(R.id.button2);
        btnDChi = (TextView) findViewById(R.id.icondiadiem);
        DiaChi = (TextView)findViewById(R.id.trungtam);
        NgayTiem= (TextView)findViewById(R.id.ngaytiem);
        btnLich = (TextView)findViewById(R.id.iconlich);
    }
    public void datLichTiem()
    {
        strUserName = LaySDT.user;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(c.getTime());
        btn_DatLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Bạn có chắc chắn muốn đặt lịch?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!NgayTiem.getText().equals("") && !DiaChi.getText().equals("")) {

                            for (VacXin g : MangDatLich.mangmuon) {
                                String Key = myRefLichSuDat.push().getKey();
                                LichTiem l = new LichTiem(g.getId_vx(), strUserName, g.getTenvx(), "Đã lên lịch", strDate, NgayTiem.getText().toString(), strTenDD);
                                myRefLichSuDat.child(Key).setValue(l, new DatabaseReference.CompletionListener() {

                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            System.out.println("Thành công");

                                    }
                                });

                            }
                            Intent intent = new Intent(DatLichTiemChung.this, Mai_LichTiemChung.class);
                            startActivity(intent);
                        }
                        else
                        {
                            new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Không được bỏ trống địa điểm tiêm hoặc ngày tiêm mong muốn").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }

                    }
                }).setNegativeButton("Cancel",null).show();
            }
        });
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
                        LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);

                        // Lấy ngày hiện tại, thiết lập giờ, phút, giây thành 0
                        LocalDate currentDate = LocalDate.now().withDayOfMonth(1); // Ngày hiện tại, đặt ngày là 1 để không ảnh hưởng đến tháng sau
                        // Thêm 2 tháng vào ngày hiện tại
                        LocalDate maxAllowedDate = currentDate.plusMonths(2);

                        // Kiểm tra ngày đã chọn có nằm trong khoảng cho phép hay không
                        if (selectedDate.isBefore(currentDate) || selectedDate.isAfter(maxAllowedDate)) {

                            Toast.makeText(DatLichTiemChung.this, "Ngày không hợp lệ. Vui lòng chọn từ ngày hiện tại đến 2 tháng sau.", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("Dô tới đây thì nói chuyện với tao");
                            // Handle the case where the selected date is allowed
                            if (selectedCalendar.get(Calendar.YEAR) == currentYear
                                    && selectedCalendar.get(Calendar.MONTH) == currentMonth
                                    && selectedCalendar.get(Calendar.DAY_OF_MONTH) == currentDay) {
                                // Hiển thị thông báo hoặc thực hiện các hành động khác
                                Toast.makeText(DatLichTiemChung.this, "Không thể chọn ngày hiện tại", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String formattedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                NgayTiem.setText(formattedDate);
                            }
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
        adapter = new LoadDatLichAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onBackPressed() {
        // Xử lý sự kiện khi người dùng nhấn nút "Back"

        // Gọi super để giữ nguyên chức năng mặc định của nút "Back" (ví dụ: thoát khỏi Activity)
        super.onBackPressed();
    }

}