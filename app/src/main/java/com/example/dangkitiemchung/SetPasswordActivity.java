package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SetPasswordActivity extends AppCompatActivity {
Button btn_resetpassword;
    String  mPhoneNumber,mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getPhone();
        btn_resetpassword= (Button) findViewById(R.id.btn_resetpassword);

        btn_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetPasswordActivity.this, MenuMainActivity.class);
                intent.putExtra("phone_number",mPhoneNumber);
                startActivity(intent);
            }
        });

    }
    public String getPhone(){
        mPhoneNumber=getIntent().getStringExtra("phone_number");
        mVerificationId=getIntent().getStringExtra("verification_id");
        System.out.println("sdt: ***************" + mPhoneNumber);
        if (mPhoneNumber.startsWith("+84")) {
            mPhoneNumber = "0" + mPhoneNumber.substring(3);
            System.out.println("sdt: ***************" + mPhoneNumber);
        }
        return mPhoneNumber;
    }
}