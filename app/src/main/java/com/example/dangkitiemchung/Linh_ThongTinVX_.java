package com.example.dangkitiemchung;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dangkitiemchung.Adapter.LinhDMVXAdapter;
import com.example.dangkitiemchung.Models.VacXin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Linh_ThongTinVX_ extends AppCompatActivity {
    ImageView image_VX;
    TextView tv_Tenvx, tv_sl, tv_phongBenh, tv_nguonGoc, tv_moTa, tv_gia, tv_chongcd, tv_baoquan, tv_pdt_m1, tv_pdt_m2, tv_pdt_m3;
    String id_vx;
    ImageView left_ic;

    private ArrayList<VacXin> newArrayList = new ArrayList<>();
    private LinhDMVXAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linh_thong_tin_vx);
        getSupportActionBar().hide();
        addControls();
        Intent intent = getIntent();
        id_vx = intent.getStringExtra("id");

        getData();
        left_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), Linh_Activity_DanhMucVacXin.class);
                v.getContext().startActivity(intent);
            }
        });

    }

    public void vd()
    {
        // Giả sử bạn có một tham chiếu Firebase trỏ đến nút "VacXin"
        DatabaseReference vacXinRef = FirebaseDatabase.getInstance().getReference().child("VacXin");

        // Bạn có thể sử dụng addListenerForSingleValueEvent để lấy dữ liệu một lần
        vacXinRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Duyệt qua từng child của nút "VacXin"
                    for (DataSnapshot vacXinSnapshot : dataSnapshot.getChildren()) {
                        // Lấy giá trị của "PhacDoTiem" từ mỗi child
                        DataSnapshot phacDoTiemSnapshot = vacXinSnapshot.child("PhacDoTiem");

                        // Lấy giá trị của "Mui 1", "Mui 2", "Mui 3" từ "PhacDoTiem"
                        Integer mui1 = phacDoTiemSnapshot.child("Mui 1").getValue(Integer.class);
                        Integer mui2 = phacDoTiemSnapshot.child("Mui 2").getValue(Integer.class);
                        Integer mui3 = phacDoTiemSnapshot.child("Mui 3").getValue(Integer.class);
                        System.out.println("phac do tiem ne  "+ mui1 + " " + mui2 + " " + mui3);
                        // Kiểm tra xem có giá trị hay không và sử dụng giá trị nếu cần
                        if (mui1 != null) {
                            Log.d("Firebase", "Mui 1: " + mui1);
                        }

                        if (mui2 != null) {
                            Log.d("Firebase", "Mui 2: " + mui2);
                        }

                        if (mui3 != null) {
                            Log.d("Firebase", "Mui 3: " + mui3);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }

    public void layDT_PDT(int id_vx)
    {
        DatabaseReference vacXinRef = FirebaseDatabase.getInstance().getReference().child("VacXin");

        // Bạn có thể sử dụng addListenerForSingleValueEvent để lấy dữ liệu một lần
        vacXinRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Duyệt qua từng child của nút "VacXin"
                    for (DataSnapshot vacXinSnapshot : dataSnapshot.getChildren()) {
                        // Lấy giá trị của "PhacDoTiem" từ mỗi child
                        String key = vacXinSnapshot.getKey();
                        if (id_vx == Integer.parseInt(key))
                        {
                            DataSnapshot phacDoTiemSnapshot = vacXinSnapshot.child("PhacDoTiem");

                            // Lấy giá trị của "Mui 1", "Mui 2", "Mui 3" từ "PhacDoTiem"
                            Integer mui1 = phacDoTiemSnapshot.child("Mui 1").getValue(Integer.class);
                            Integer mui2 = phacDoTiemSnapshot.child("Mui 2").getValue(Integer.class);
                            Integer mui3 = phacDoTiemSnapshot.child("Mui 3").getValue(Integer.class);
                            System.out.println("phac do tiem ne  "+ mui1 + " " + mui2 + " " + mui3);
                            // Kiểm tra xem có giá trị hay không và sử dụng giá trị nếu cần
                            if (mui1 != null) {

                                tv_pdt_m1.setText(" + Mui 1: " + mui1 +" tháng");
                            }

                            if (mui2 != null) {
                                tv_pdt_m2.setText(" + Mui 2: " + mui2 +" tháng");
                            }

                            if (mui3 != null) {
                                tv_pdt_m3.setText(" + Mui 3: " + mui3 +" tháng");
                            }
                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });

    }

    private void getData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("VacXin");
        DatabaseReference myRef_PDT = firebaseDatabase.getReference("VacXin").child("PhacDoTiem");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                    int id_VX = dataSnapshot.child("id_VX").getValue(Integer.class);
                    String baoquan = dataSnapshot.child("BaoQuan").getValue(String.class);
                    String chongcd = dataSnapshot.child("ChongChiDinh").getValue(String.class);
                    Integer gia = dataSnapshot.child("Gia").getValue(Integer.class);
                    String mota = dataSnapshot.child("MoTa").getValue(String.class);
                    String nguongoc = dataSnapshot.child("NguonGoc").getValue(String.class);
                    String phongBenh = dataSnapshot.child("PhongBenh").getValue(String.class);
                    Integer slton = dataSnapshot.child("SLTon").getValue(Integer.class);
                    String tenVX = dataSnapshot.child("TenVX").getValue(String.class);

                    if (id_VX == Integer.parseInt(id_vx))
                    {
                        tv_Tenvx.setText(tenVX);
                        tv_nguonGoc.setText("Nguồn gốc: "+nguongoc);
                        tv_phongBenh.setText("Phòng bệnh: "+phongBenh);
                        tv_gia.setText("Giá" + String.valueOf(gia));
                        tv_moTa.setText(mota);
                        tv_chongcd.setText(chongcd);
                        tv_baoquan.setText(baoquan);
                        img_vx(id_VX);
                        layDT_PDT(id_VX);



                    }



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Linh_ThongTinVX_.this, "Loi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void img_vx(int id_vx)
    {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference img = storageReference.child("images/"+id_vx+".jpg");
        System.out.println(img);
        long MAXBYTES = 1024*1024;
        img.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image_VX.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void addControls()
    {
        image_VX = findViewById(R.id.img_VX);
        tv_Tenvx = findViewById(R.id.tv_TenVX);
        tv_baoquan = findViewById(R.id.tv_baoquan);
        tv_chongcd = findViewById(R.id.tv_chongchidinh);
        tv_moTa = findViewById(R.id.tv_moTa);
        tv_nguonGoc = findViewById(R.id.tv_nguon_goc);
        tv_phongBenh = findViewById(R.id.tv_phong_benh);
        tv_gia = findViewById(R.id.tv_gia);
        left_ic = findViewById(R.id.left_ic);
        tv_pdt_m1 = findViewById(R.id.tv_phacdotiem_m1);
        tv_pdt_m2 = findViewById(R.id.tv_phacdotiem_m2);
        tv_pdt_m3 = findViewById(R.id.tv_phacdotiem_m3);
    }
}