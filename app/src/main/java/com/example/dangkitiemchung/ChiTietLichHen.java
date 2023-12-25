package com.example.dangkitiemchung;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangkitiemchung.Models.LaySDT;
import com.example.dangkitiemchung.Models.LichSuTiemChung;
import com.example.dangkitiemchung.Models.MuiTiepTheo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChiTietLichHen extends AppCompatActivity {

    TextView hten, ngaysinh, ngaytiem, tenvx, phongbenh, gia, trungtam, diadiemtiem;
    String id, strNgaydat, strNgaytiem, strNoiTiem, strTenVX;
    String strUser;
    private int strMui2=0;
    private int strMui3=0;
    private String key, keyLS;
    Button btnHuy, btnDaTiem;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("VacXin");

    DatabaseReference myRefLichSuDat = firebaseDatabase.getReference("LichSuDat");
    DatabaseReference myRefLichSuTC = firebaseDatabase.getReference("LichSuTiem");
    DatabaseReference myRefMuiTiepTheo = firebaseDatabase.getReference("MuiTiepTheo");
    DatabaseReference myRefTaiKhoan = firebaseDatabase.getReference("TaiKhoan");

    DatabaseReference myRefDiaDiem = firebaseDatabase.getReference("DiaDiemTiem");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_hen);
        addControls();
        truyenDuLieu();
        trungtam.setText(strNoiTiem);
        ngaytiem.setText(strNgaytiem);
        Data();
        DataTaiKhoan();
        DataDiaChi();
        huyLichTiem();

        daTiem();


    }
    public void truyenDuLieu()
    {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        strUser = intent.getStringExtra("user");
        key = intent.getStringExtra("key");
        strNgaydat =  intent.getStringExtra("ngaydat");
        strNgaytiem =  intent.getStringExtra("ngaytiem");
        strNoiTiem =  intent.getStringExtra("noitiem");

    }
    public void addControls()
    {
        hten = (TextView)findViewById(R.id.hoten);
        ngaysinh = (TextView)findViewById(R.id.ngaysinh);
        ngaytiem = (TextView)findViewById(R.id.tiemngay);
        tenvx = (TextView)findViewById(R.id.tenvx);
        phongbenh = (TextView)findViewById(R.id.phongbenh);
        gia = (TextView)findViewById(R.id.gia);
        diadiemtiem = (TextView)findViewById(R.id.diachi);
        trungtam = (TextView)findViewById(R.id.trungtamtiem);
        btnHuy = (Button) findViewById(R.id.btnhuy);
        btnDaTiem = (Button) findViewById(R.id.btndatiem);


    }
    private void Data() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Integer idVX = dataSnapshot.child("id_VX").getValue(Integer.class);
                    System.out.println("Xuất id coi thử"+ idVX +"này id truyền "+ id);
                    Integer giaVX = dataSnapshot.child("Gia").getValue(Integer.class);
                    String ten = dataSnapshot.child("TenVX").getValue(String.class);
                    String phongbenhVX = dataSnapshot.child("PhongBenh").getValue(String.class);
                    long phacdo =  dataSnapshot.child("PhacDoTiem").getChildrenCount();
                    int Mui2 =0;
                    int Mui3 =0;

                    if (idVX == Integer.parseInt(id)) {
                        {
                            if(phacdo ==2)
                            {
                                Mui2 = dataSnapshot.child("PhacDoTiem").child("Mui 2").getValue(Integer.class);
                            }
                            if(phacdo ==3)
                            {
                                Mui2 = dataSnapshot.child("PhacDoTiem").child("Mui 2").getValue(Integer.class);
                                Mui3 = dataSnapshot.child("PhacDoTiem").child("Mui 3").getValue(Integer.class);
                            }
                            tenvx.setText(ten);
                            phongbenh.setText(phongbenhVX);
                            gia.setText(String.valueOf(giaVX));
                            strMui2 = Mui2;
                            strMui3 = Mui3;
                            System.out.println(strMui2);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void huyLichTiem(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        System.out.println("thời giannnn"+sdf.format(c.getTime()));
        btnHuy.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View view) {
                    if (!ngaytiem.getText().toString().equals(""+sdf.format(c.getTime()))) {
                        new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Bạn có chắc chắn muôn hủy lịch?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myRefLichSuDat.child(String.valueOf(key)).child("tinhTrang").setValue("Đã hủy");
                                Toast.makeText(ChiTietLichHen.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), Mai_LichTiemChung.class);
                                startActivity(intent);

                            }
                        }).setNegativeButton("Cancel", null).show();
                    } else {

                        new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Đã tới ngày tiêm không thể hủy").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.println("...");
                            }

                        }).show();
                    }
                }



        });
    }
    public void daTiem()
    {
        keyLS = myRefLichSuTC.push().getKey();
        String KeyMui2 = myRefMuiTiepTheo.push().getKey();
        String KeyMui3 = myRefMuiTiepTheo.push().getKey();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date dateobj = new Date();
        Calendar c = Calendar.getInstance();
        System.out.println("thời giannnn"+df.format(c.getTime()));
        btnDaTiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ngaytiem.getText().toString().equals(""+df.format(c.getTime()))) {
                    new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Xác nhận đã tiêm chủng?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("Xuất id coi thử key: " + key);
                            LichSuTiemChung ls = new LichSuTiemChung(Integer.parseInt(id), LaySDT.getUser(), tenvx.getText().toString(), 1, ngaytiem.getText().toString(), phongbenh.getText().toString(), strNoiTiem);
                            myRefLichSuTC.child(keyLS).setValue(ls, new DatabaseReference.CompletionListener() {

                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    System.out.println("...");
                                }
                            });
                            if(strMui2 !=0) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String ngayBanDau = ngaytiem.getText().toString();
                                String KetQua = "";
                                try {

                                    // Chuyển đổi chuỗi ngày thành đối tượng Date
                                    Date date = sdf.parse(ngayBanDau);

                                    // Sử dụng Calendar để cộng thêm số tháng
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(date);
                                    calendar.add(Calendar.MONTH, strMui2);

                                    // Lấy ngày sau khi cộng thêm số tháng
                                    Date ngaySauKhiCong = calendar.getTime();

                                    // Chuyển đổi ngày sau khi cộng thành chuỗi
                                    KetQua = sdf.format(ngaySauKhiCong);

                                    System.out.println("Ngày sau khi cộng " + strMui2 + " tháng là: " + KetQua);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                MuiTiepTheo mtt = new MuiTiepTheo(Integer.parseInt(id), strUser, tenvx.getText().toString(), 2, KetQua, phongbenh.getText().toString());
                                myRefMuiTiepTheo.child(KeyMui2).setValue(mtt, new DatabaseReference.CompletionListener() {

                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        System.out.println("...");
                                    }
                                });
                                if (strMui3 != 0) {
                                    try {

                                        // Chuyển đổi chuỗi ngày thành đối tượng Date
                                        Date date = sdf.parse(ngayBanDau);

                                        // Sử dụng Calendar để cộng thêm số tháng
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(date);
                                        calendar.add(Calendar.MONTH, strMui3);

                                        // Lấy ngày sau khi cộng thêm số tháng
                                        Date ngaySauKhiCong = calendar.getTime();

                                        // Chuyển đổi ngày sau khi cộng thành chuỗi
                                        KetQua = sdf.format(ngaySauKhiCong);

                                        System.out.println("Ngày sau khi cộng " + strMui3 + " tháng là: " + KetQua);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    MuiTiepTheo mtt3 = new MuiTiepTheo(Integer.parseInt(id), strUser, tenvx.getText().toString(), 3, KetQua, phongbenh.getText().toString());
                                    myRefMuiTiepTheo.child(KeyMui3).setValue(mtt3, new DatabaseReference.CompletionListener() {

                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            System.out.println("...");
                                        }
                                    });
                                }
                            }

                            myRefLichSuDat.child(String.valueOf(key)).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(ChiTietLichHen.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), Mai_LichSuTiemChung.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", null).show();
                }
                else {

                new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Chưa tới ngày tiêm chủng").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("...");
                    }

                }).show();
                }
            }
        });


    }
    private void DataTaiKhoan() {
        myRefTaiKhoan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String sdt = dataSnapshot.child("UserName").getValue(String.class);
                    String ten = dataSnapshot.child("HoTen").getValue(String.class);
                    String ngayS = dataSnapshot.child("NgaySinh").getValue(String.class);
                    if (LaySDT.getUser().equals(sdt))
                    {
                        hten.setText(ten);
                        ngaysinh.setText(ngayS);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void DataDiaChi()
    {
        myRefDiaDiem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.child("TenDD").getValue(String.class);
                    String name = dataSnapshot.child("DiaChiCuThe").getValue(String.class);
                    if(id.equals(strNoiTiem))
                    {
                        diadiemtiem.setText(name);
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: ");
            }

        });
    }


}