package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Mai_LichTiemChung extends AppCompatActivity {

    TabLayout tabLayout;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    //    String tendg = "";
//    String madg = "";
//    DatabaseReference database;
//    ArrayList<DocGia> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mai_lich_tiem_chung);
    }
}