package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Enter_Password extends AppCompatActivity {

    TextView txt_password;
    Button btn_login;
    String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        txt_password = (TextView) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = getIntent();
             if (intent != null) {
                 String phoneNumber = intent.getStringExtra("phone_number");
                 String password = txt_password.getText().toString().trim();

                 if (phoneNumber != null) {
                     checkPassword(phoneNumber, password);

                 }
             }
         }
     });
    }


    private void checkPassword(String phoneNumber, String enteredPassword1) {
        System.out.println("mat khau :" + enteredPassword1);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        databaseReference.orderByChild("UserName").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            boolean kt = true;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Số điện thoại tồn tại trong cơ sở dữ liệu
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String passwordFromDatabase = snapshot.child("PassWord").getValue(String.class);
                        // Kiểm tra mật khẩu
                        if (enteredPassword1.equals(passwordFromDatabase)) {
                            Intent mainIntent = new Intent(Enter_Password.this, MenuMainActivity.class);
                            System.out.println("Nhap dung:---------: "+ phoneNumber );
                            mainIntent.putExtra("phone_number",phoneNumber);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainIntent);
                            finish();


                        }
                        else {
                            Toast.makeText(Enter_Password.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi truy vấn đến cơ sở dữ liệu
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });

        // Giả định: Trả về false nếu không tìm thấy số điện thoại trong cơ sở dữ liệu

    }

}