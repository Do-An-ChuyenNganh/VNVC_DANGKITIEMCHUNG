package com.example.dangkitiemchung;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CaNhan extends AppCompatActivity {


    LinearLayout layout_dangxuat,layout_edit, layout_doimatkhau;
    ImageButton icon_back;
    String  mPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_nhan);
        Window window = getWindow();
        // Ẩn thanh trạng thái (status bar)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getPhone();

        layout_dangxuat =(LinearLayout) findViewById(R.id.layout_dangxuat);
        icon_back= (ImageButton)findViewById(R.id.icon_back);
        layout_edit =(LinearLayout) findViewById(R.id.layout_edit);
        layout_doimatkhau =(LinearLayout) findViewById(R.id.layout_doimatkhau);
        layout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(CaNhan.this, PersonalProfileActivity.class);
                mainIntent.putExtra("phone_number",mPhoneNumber);
                startActivity(mainIntent);
            }
        });
        layout_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(CaNhan.this, ChangePassword.class);
                mainIntent.putExtra("phone_number",mPhoneNumber);
                startActivity(mainIntent);
            }
        });

        layout_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   finishAffinity();
                showExitConfirmationDialog();

            }
        });

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("có click");
                Intent mainIntent = new Intent(CaNhan.this, MenuMainActivity.class);
                mainIntent.putExtra("phone_number",mPhoneNumber);
                startActivity(mainIntent);
                finish();
            }
        });

    }
    public void  getPhone(){
        mPhoneNumber= getIntent().getStringExtra("phone_number");
        //mPhoneNumber= "0366850669";
        System.out.println("sdt: ***************" + mPhoneNumber);
        if (mPhoneNumber.startsWith("+84")) {
            mPhoneNumber = "0" + mPhoneNumber.substring(3);
            System.out.println("sdt: ***************" + mPhoneNumber);
        }

    }
    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CaNhan.this);
        builder.setTitle("Xác nhận thoát");
        builder.setMessage("Bạn có chắc chắn muốn thoát không?");

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kết thúc tất cả các activity và thoát ứng dụng
                finishAffinity();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog nếu người dùng chọn hủy
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}