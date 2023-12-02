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

public class SetPasswordActivity extends AppCompatActivity {

    String  mPhoneNumber,mVerificationId;
    EditText  edt_enterPassword, edt_ReEnterPassword;
    Button btn_PressContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        addControl();
        getPhone();

        
        btn_PressContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1= edt_enterPassword.getText().toString().trim();
                String str2= edt_ReEnterPassword.getText().toString().trim();
                if(edt_enterPassword.length() < 6){
                    Toast.makeText(SetPasswordActivity.this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(str1.equals(str2))
                    {
                        // Xác định DatabaseReference cho nút "TaiKhoan"
                        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference().child("TaiKhoan");
                        String usernameCanTimKiem = mPhoneNumber; // Thay thế bằng username bạn đang tìm kiếm

                        taiKhoanRef.orderByChild("UserName").equalTo(usernameCanTimKiem).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Kiểm tra nếu có dữ liệu tại nút tương ứng với username
                                if (dataSnapshot.exists()) {
                                    // Lấy các giá trị từ dataSnapshot
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String taiKhoanKey = snapshot.getKey();

                                        // Cập nhật mật khẩu tại nút tương ứng
                                        taiKhoanRef.child(taiKhoanKey).child("PassWord").setValue(str2);
                                    }
                                } else {
                                    // Không tìm thấy username trong database
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Xử lý khi có lỗi xảy ra
                            }
                        });


                        Intent intent = new Intent(SetPasswordActivity.this, MenuMainActivity.class);
                        intent.putExtra("phone_number",mPhoneNumber);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(SetPasswordActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
    public String getPhone(){
        mPhoneNumber=getIntent().getStringExtra("phone_number");
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