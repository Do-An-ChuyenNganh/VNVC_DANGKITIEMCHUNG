package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangkitiemchung.Adapter.NewsAdapter;
import com.example.dangkitiemchung.Models.News;
import com.example.dangkitiemchung.Models.TaiKhoan;
import com.example.dangkitiemchung.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class MenuMainActivity extends AppCompatActivity {
    RecyclerView recyclerView_news;
    NewsAdapter newsAdapter ;
    String  mPhoneNumber,mVerificationId;
    TextView txt_welcome;
    ImageView imgLichHen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        // Ẩn thanh trạng thái (status bar)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        addControl();
        getPhone();
        getWelcome();
        chuyenLichHen();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_news.setLayoutManager(linearLayoutManager);
        newsAdapter =new NewsAdapter(getListNews());
        recyclerView_news.setAdapter(newsAdapter);
    }
    public List<News> getListNews(){
        List<News> lst = new ArrayList<>();
        lst.add(new News(R.drawable.img_vaccins,"CÁC LOẠI VẮC XIN CHO NGƯỜI LỚN","07/11/2022","22:04"));
        lst.add(new News(R.drawable.imgho,"VẮC XIN NGỪA CÚM CHO NGƯỜI LỚN \n CÓ NÊN TIÊM PHÒNG HẰNG NĂM","06/06/2022","16:03"));
        lst.add(new News(R.drawable.img_bord,"TẠI SAO NGƯỜI LỚN CẦN TIÊM VẮC XIN ?","07/11/2022","22:04"));
        lst.add(new News(R.drawable.img_treat,"CÁC LOẠI VẮC XIN CHO NGƯỜI LỚN","07/11/2022","22:04"));
        lst.add(new News(R.drawable.img_cream,"CÁC LOẠI VẮC XIN CHO NGƯỜI LỚN","07/11/2022","22:04"));
        return lst;
    }
    public static Bitmap decodeSampledBitmapFromResource(Context context, int resId, int inSampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize; // Đặt giảm độ phân giải

        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }
    public void addControl(){
        recyclerView_news=findViewById(R.id.recyclerView_news);
        txt_welcome=(TextView) findViewById(R.id.txt_welcome);
        imgLichHen = (ImageView) findViewById(R.id.imgLichHen);
    }
    public void chuyenLichHen()
    {
        imgLichHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoneNumber = getIntent().getStringExtra("phone_number");
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Mai_LichTiemChung.class);
                intent.putExtra("sdt", ""+mPhoneNumber);
                startActivity(intent);


            }
        });
    }
    public String getPhone(){

        mPhoneNumber=getIntent().getStringExtra("phone_number");
        System.out.println("sdt: menu main  ***************" + mPhoneNumber);
        if (mPhoneNumber.startsWith("+84")) {
            mPhoneNumber = "0" + mPhoneNumber.substring(3);
            System.out.println("sdt: ***************" + mPhoneNumber);
        }
        return mPhoneNumber;
    }

    public void getWelcome(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference taiKhoanRef = database.getReference("TaiKhoan");

// Điều này giả sử "userName" là giá trị bạn muốn tìm
        String userNameToFind = mPhoneNumber;
// Thực hiện truy vấn để lấy thông tin HoTen từ Firebase dựa trên UserName
        Query query = taiKhoanRef.orderByChild("UserName").equalTo(userNameToFind).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);

                        if (taiKhoan != null) {
                            Log.d("Firebase", "HoTen: " + taiKhoan.getHoTen());
                            txt_welcome.setText(taiKhoan.getHoTen());
                        }
                    }
                } else {
                    Log.d("Firebase", "Không tìm thấy người dùng với UserName: " + userNameToFind);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Lỗi khi đọc dữ liệu từ Firebase", databaseError.toException());
            }
        });

    }
}