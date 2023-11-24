package com.example.dangkitiemchung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.dangkitiemchung.Fragment.DaHuyFragment;
import com.example.dangkitiemchung.Fragment.DaLenLichFragment;
import com.example.dangkitiemchung.Fragment.LichSuTiemFragment;
import com.example.dangkitiemchung.Fragment.MuiTiepTheoFragment;
import com.example.dangkitiemchung.Models.LichTiem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Mai_LichTiemChung extends AppCompatActivity {

    TabLayout tabLayout;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    String user = "0366850669";
    DatabaseReference database;
    ArrayList<LichTiem> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mai_lich_tiem_chung);
        // Ẩn thanh trạng thái (status bar)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        tabLayout = (TabLayout) findViewById(R.id.tab_lichtiem);
        replaceFrag(new DaLenLichFragment());
//        Intent intent = getIntent();
//        madg = intent.getStringExtra("madg");
////        System.out.println("t mún đi ngủ"+madg);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFrag(new DaLenLichFragment());
                        break;
                    case 1:
                        replaceFrag(new MuiTiepTheoFragment());
                        break;
                    case 2:
                        replaceFrag(new LichSuTiemFragment());
                        break;
                    case 3:
                        replaceFrag(new DaHuyFragment());
                        break;


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });
    }
    public void replaceFrag(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.commit();
    }
}