package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.Models.MuiTiepTheo;
import com.example.dangkitiemchung.Models.Vaccine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChiTietLichHen extends AppCompatActivity {

    TextView hten, ngaysinh, ngaytiem, tenvx, phongbenh, gia, ngaydat, diadiemtiem;
    String id, strNgaydat, strNgaytiem, strNoiTiem;
    String strUser;
    private String key, keyLS;
    Button btnHuy, btnDaTiem;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("VacXin");
    DatabaseReference myRefLichSuDat = firebaseDatabase.getReference("LichSuDat");
    DatabaseReference myRefLichSuTC = firebaseDatabase.getReference("LichSuTiem");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_hen);
        addControls();
        truyenDuLieu();
        ngaydat.setText(strNgaydat);
        diadiemtiem.setText(strNoiTiem);
        ngaytiem.setText(strNgaytiem);
        Data();
        huyLichTiem();


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
        ngaytiem = (TextView)findViewById(R.id.ngaymongmuontiem);
        tenvx = (TextView)findViewById(R.id.tenvx);
        phongbenh = (TextView)findViewById(R.id.phongbenh);
        ngaydat = (TextView)findViewById(R.id.ngaydat);
        gia = (TextView)findViewById(R.id.gia);
        diadiemtiem = (TextView)findViewById(R.id.diadiemtiem);
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
                    if (idVX == Integer.parseInt(id)) {
                        {
                            tenvx.setText(ten);
                            phongbenh.setText(phongbenhVX);
                            gia.setText(String.valueOf(giaVX));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void huyLichTiem()
    {
        keyLS = myRefLichSuTC.push().getKey();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Bạn có chắc chắn muôn hủy lịch?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myRefLichSuTC.child(String.valueOf(keyLS)).child("TinhTrang").setValue("Đã hủy");
                        Toast.makeText(ChiTietLichHen.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Cancel",null).show();
                    }


        });

    }
    public void daTiem()
    {
        btnDaTiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Xác nhận đã tiêm chủng?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Xuất id coi thử key: " + key);
                        myRefLichSuDat.child(String.valueOf(key)).child("TinhTrang").setValue("Đã hủy");
                        Toast.makeText(ChiTietLichHen.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Cancel",null).show();
            }


        });

    }


}