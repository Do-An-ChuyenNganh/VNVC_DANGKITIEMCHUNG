package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangkitiemchung.Models.LichSuTiemChung;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChiTietLichHen extends AppCompatActivity {

    TextView hten, ngaysinh, ngaytiem, tenvx, phongbenh, gia, ngaydat, diadiemtiem;
    String id, strNgaydat, strNgaytiem, strNoiTiem, strTenVX;
    String strUser, strMui2, strMui3;
    private String key, keyLS;
    Button btnHuy, btnDaTiem;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("VacXin");
    DatabaseReference myRefLichSuDat = firebaseDatabase.getReference("LichSuDat");
    DatabaseReference myRefLichSuTC = firebaseDatabase.getReference("LichSuTiem");
    DatabaseReference myRefMuiTiepTheo = firebaseDatabase.getReference("MuiTiepTheo");
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
        strUser ="0366850669";
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
                                myRefLichSuDat.child(String.valueOf(key)).child("TinhTrang").setValue("Đã hủy");
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
                            LichSuTiemChung ls = new LichSuTiemChung(Integer.parseInt(id), strUser, tenvx.getText().toString(), 1, ngaytiem.getText().toString(), phongbenh.getText().toString(), strNoiTiem);
                            myRefLichSuTC.child(keyLS).setValue(ls, new DatabaseReference.CompletionListener() {

                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    System.out.println("...");
                                }
                            });

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


}