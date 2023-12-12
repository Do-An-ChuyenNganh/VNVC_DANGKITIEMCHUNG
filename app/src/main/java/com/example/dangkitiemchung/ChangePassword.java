package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    String  mPhoneNumber,mVerificationId;
    EditText  edt_enterPassword, edt_ReEnterPassword;
    Button btn_PressContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        addControl();
        getPhone();

      btn_PressContinue.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              changePassword(mPhoneNumber);
          }
      });



    }
    public void changePassword( String userNameToFind)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot taiKhoanSnapshot : dataSnapshot.child("TaiKhoan").getChildren()) {
                    String currentUserName = taiKhoanSnapshot.child("UserName").getValue(String.class);
                    if (userNameToFind.equals(currentUserName)) {
                        String password = taiKhoanSnapshot.child("PassWord").getValue(String.class);
                        taiKhoanSnapshot.child("PassWord").getRef().setValue("matkhaumoi");
                        Toast.makeText(getApplicationContext(), "Đã cập nhật mật khẩu mới cho " + password, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Toast.makeText(getApplicationContext(), "Lỗi khi đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public String getPhone(){
      //  mPhoneNumber=getIntent().getStringExtra("phone_number");
        mPhoneNumber="0342621113";
        mVerificationId=getIntent().getStringExtra("verification_id");
        System.out.println("sdt: trang set password  ***************" + mPhoneNumber);
        if (mPhoneNumber.startsWith("+84")) {
            mPhoneNumber = "0" + mPhoneNumber.substring(3);
            System.out.println("sdt:trang set password  ***************" + mPhoneNumber);
        }
        return mPhoneNumber;
    }

    public void addControl(){
        edt_enterPassword=(EditText)findViewById(R.id.edt_enterPassword);
        edt_ReEnterPassword=(EditText)findViewById(R.id.edt_ReEnterPassword);
        btn_PressContinue=(Button) findViewById(R.id.btn_PressContinue);
    }
}