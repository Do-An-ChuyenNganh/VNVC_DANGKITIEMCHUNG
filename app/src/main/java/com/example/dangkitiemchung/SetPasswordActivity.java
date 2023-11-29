package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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