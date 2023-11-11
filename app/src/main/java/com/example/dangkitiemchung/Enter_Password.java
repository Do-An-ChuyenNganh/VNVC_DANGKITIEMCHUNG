package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Enter_Password extends AppCompatActivity {

    TextView txt_password;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);
        txt_password =(TextView) findViewById(R.id.txt_password);
        btn_login=(Button)findViewById(R.id.btn_login);

    }
}