package com.example.dangkitiemchung;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dangkitiemchung.Models.LaySDT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChiTietLichSuTiemChung extends AppCompatActivity {
    TextView hten, ngaysinh, ngaytiem, tenvx, phongbenh, gia, trungtam, diadiemtiem;
    String id, strNgaydat, strNgaytiem, strNoiTiem;
    String strUser = LaySDT.getUser();
    private String key, keyLS;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("VacXin");
    DatabaseReference myRefTaiKhoan = firebaseDatabase.getReference("TaiKhoan");

    DatabaseReference myRefDiaDiem = firebaseDatabase.getReference("DiaDiem");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_su_tiem_chung);
        addControls();
        truyenDuLieu();
        trungtam.setText(strNoiTiem);
        ngaytiem.setText(strNgaytiem);
        Data();
        DataTaiKhoan();
        DataDiaChi();
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
                    if (idVX == Integer.parseInt(id)) {

                        tenvx.setText(ten);
                        phongbenh.setText(phongbenhVX);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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