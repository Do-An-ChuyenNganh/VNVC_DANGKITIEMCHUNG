package com.example.dangkitiemchung;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangkitiemchung.Models.LaySDT;
import com.example.dangkitiemchung.Models.MuiTiepTheo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietMuiTiepTheo extends AppCompatActivity {

    TextView hten, ngaysinh, ngaytiem, tenvx, phongbenh, gia, trungtam, diadiemtiem;
    String id, strNgaydat, strNgaytiem;
    String strUser = LaySDT.getUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("VacXin");
    DatabaseReference myRefTaiKhoan = firebaseDatabase.getReference("TaiKhoan");

    DatabaseReference myRefDiaDiem = firebaseDatabase.getReference("DiaDiem");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mui_tiep_theo);
        addControls();
        truyenDuLieu();
        ngaytiem.setText(strNgaytiem);
        Data();
        DataTaiKhoan();


    }
    public void truyenDuLieu()
    {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        strUser = intent.getStringExtra("user");
        strNgaydat =  intent.getStringExtra("ngaydat");
        strNgaytiem =  intent.getStringExtra("ngaytiem");

    }
    public void addControls()
    {
        hten = (TextView)findViewById(R.id.hoten);
        ngaysinh = (TextView)findViewById(R.id.ngaysinh);
        ngaytiem = (TextView)findViewById(R.id.tiemngay);
        tenvx = (TextView)findViewById(R.id.tenvx);
        phongbenh = (TextView)findViewById(R.id.phongbenh);
        gia = (TextView)findViewById(R.id.gia);


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
  }