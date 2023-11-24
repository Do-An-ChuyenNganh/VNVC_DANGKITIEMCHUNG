package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangkitiemchung.Models.MuiTiepTheo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChiTietMuiTiepTheo extends AppCompatActivity {

    TextView hten, sdt, ngaysinh, ngaytiemdutinh, tenvx, phongbenh;
    String strTenvx, strPhongbenh, strNgaytiem;
    String strUser;
    private ArrayList<MuiTiepTheo> newArrayMuiTT = new ArrayList<>();


    DatabaseReference database;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("TaiKhoan");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_chi_tiet_mui_tiep_theo);
        truyenDuLieu();
        addControls();
        tenvx.setText(strTenvx);
        sdt.setText(strUser);
        phongbenh.setText(strPhongbenh);
        ngaytiemdutinh.setText(strNgaytiem);
//        Data();


    }
    public void truyenDuLieu()
    {
        Intent intent = getIntent();
        strTenvx = intent.getStringExtra("ten");
        strPhongbenh = intent.getStringExtra("phongbenh");
        strNgaytiem = intent.getStringExtra("ngaytiem");
        strUser = intent.getStringExtra("user");

    }
    public void addControls()
    {
        hten = (TextView)findViewById(R.id.hoten);
        sdt = (TextView)findViewById(R.id.sdt);
        ngaysinh = (TextView)findViewById(R.id.ngaysinh);
        ngaytiemdutinh = (TextView)findViewById(R.id.tiemngay);
        tenvx = (TextView)findViewById(R.id.tenvx);
        phongbenh = (TextView)findViewById(R.id.phongbenh);


    }
    private void Data() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MuiTiepTheo mtt = dataSnapshot.getValue(MuiTiepTheo.class);
                    if (mtt.getUserName().equals("0366850669")) {

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}