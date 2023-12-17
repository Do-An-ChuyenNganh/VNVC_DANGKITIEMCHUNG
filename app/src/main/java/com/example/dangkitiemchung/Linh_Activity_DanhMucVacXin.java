package com.example.dangkitiemchung;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
//<<<<<<< Updated upstream
import android.content.Context;
import android.content.DialogInterface;
//=======
///>>>>>>> Stashed changes
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.Adapter.LinhDMVXAdapter;
import com.example.dangkitiemchung.Adapter.LinhGioHangAdapter;
import com.example.dangkitiemchung.Models.GioHang;
import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.Models.MangDatLich;
import com.example.dangkitiemchung.Models.VacXin;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Linh_Activity_DanhMucVacXin extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<VacXin> newArrayList = new ArrayList<>();
    private ArrayList<VacXin> newArrayList_loc = new ArrayList<>();
    private ArrayList<GioHang> newArrayList_GioHang = new ArrayList<>();
    private LinhDMVXAdapter adapter;
    private LinhGioHangAdapter adapter_giohang;

    private Button btn_them, btn_mua, btn_loc, btn_loc_dl;
    private CheckBox cbx_cum, cbx_bachhau, cbx_bailiet, cbx_dai, cbx_hoga, cbx_lao;
    private CheckBox cbx_my, cbx_canada, cbx_vietnam, cbx_hanquoc, cbx_phap, cbx_ando;
    private CheckBox cbx_gia1, cbx_gia2, cbx_gia3, cbx_gia4;
    AppCompatButton btnDK;
    TextView tv_gia;

    private ImageView btn_dong, btn_dong_loc, img_gia;
    private RecyclerView rec_vxdc;

    private ImageView giohang, back;
    private int clickCount = 0;
    private String user = "0343080814";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linh_danh_muc_vac_xin);
        getSupportActionBar().hide();
        addControls();
        Intent intent = getIntent();
        user = intent.getStringExtra("sdt");
        System.out.println("USER NÈ: " + user);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adapter = new LinhDMVXAdapter(newArrayList, user);
        adapter = new LinhDMVXAdapter(newArrayList, this, user, new LinhDMVXAdapter.ButtonClickListener() {
            @Override
            public void onButtonClick(String item) {
                showMessageDialog(item);
            }
        });
        adapter.notifyDataSetChanged();
        LayDL();

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //showBottomGioHang();
                showBottomLoc();
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomGioHang();

            }
        });

        tv_gia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickCount++;

                
                switch (clickCount) {
                    case 1:
                        img_gia.setImageResource(R.drawable.gia_up);
                        layDL_giatang_2();
                        break;
                    case 2:
                        img_gia.setImageResource(R.drawable.gia_down);
                        layDL_giagiam_2();
                        break;
                    case 3:
                        img_gia.setImageResource(R.drawable.gia);
                        // Reset biến đếm khi đạt đến lần nhấn thứ ba
                        LayDL();
                        clickCount = 0;
                        break;
                    default:
                        img_gia.setImageResource(R.drawable.gia_up);
                        break;
                }




            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), MenuMainActivity.class);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MenuMainActivity.class);
                intent.putExtra("phone_number",user);
                startActivity(intent);
                //finish();

            }
        });

    }

    public void searchList(String text)
    {
        ArrayList<VacXin> searchVX = new ArrayList<>();
        for (VacXin vx : newArrayList)
        {
            if (vx.getTenvx().toLowerCase().contains(text.toLowerCase()))
            {
                searchVX.add(vx);

            }

        }
        if (searchVX.isEmpty())
        {
            Toast.makeText(this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapter.setSearchList(searchVX);
        }
    }

    public void layDL_giatang_2()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("VacXin");
        myRef.child("VacXin").orderByChild("Gia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ArrayList<VacXin> vacXinList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VacXin vacXin = snapshot.getValue(VacXin.class);
                    if (vacXin != null) {
                        newArrayList.add(vacXin);
                    }
                }

                // Gọi hàm hiển thị RecyclerView ở đây
                SX_GiaTang(newArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

    public void layDL_giagiam_2()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("VacXin");
        myRef.child("VacXin").orderByChild("Gia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ArrayList<VacXin> vacXinList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VacXin vacXin = snapshot.getValue(VacXin.class);
                    if (vacXin != null) {
                        newArrayList.add(vacXin);
                    }
                }

                // Gọi hàm hiển thị RecyclerView ở đây
                SX_GiaGiam(newArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

    // Hàm sắp xếp giá tăng dần
    private void SX_GiaTang(ArrayList<VacXin> vacXinList) {
        // Sắp xếp danh sách theo giá tăng dần
        Collections.sort(vacXinList, new Comparator<VacXin>() {
            @Override
            public int compare(VacXin vacXin1, VacXin vacXin2) {
                return Integer.compare(vacXin1.getGia(), vacXin2.getGia());
            }
        });
        //LinhDMVXAdapter adapter = new LinhDMVXAdapter(vacXinList);
        adapter = new LinhDMVXAdapter(vacXinList, this, user, new LinhDMVXAdapter.ButtonClickListener() {
            @Override
            public void onButtonClick(String item) {
                showMessageDialog(item);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void SX_GiaGiam(ArrayList<VacXin> vacXinList) {
        // Sắp xếp danh sách theo giá giảm dần
        Collections.sort(vacXinList, new Comparator<VacXin>() {
            @Override
            public int compare(VacXin vacXin1, VacXin vacXin2) {
                return Integer.compare(vacXin2.getGia(), vacXin1.getGia());
            }
        });
        //LinhDMVXAdapter adapter = new LinhDMVXAdapter(vacXinList);
        adapter = new LinhDMVXAdapter(vacXinList, this, user, new LinhDMVXAdapter.ButtonClickListener() {
            @Override
            public void onButtonClick(String item) {
                showMessageDialog(item);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void LayDL() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("VacXin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(newArrayList != null)
                {
                    newArrayList.clear();
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    String baoquan = dataSnapshot.child("BaoQuan").getValue(String.class);
                    String chongcd = dataSnapshot.child("ChongChiDinh").getValue(String.class);
                    Integer gia = dataSnapshot.child("Gia").getValue(Integer.class);
                    String mota = dataSnapshot.child("MoTa").getValue(String.class);
                    String nguongoc = dataSnapshot.child("NguonGoc").getValue(String.class);
                    String phongBenh = dataSnapshot.child("PhongBenh").getValue(String.class);
                    Integer slton = dataSnapshot.child("SLTon").getValue(Integer.class);
                    String tenVX = dataSnapshot.child("TenVX").getValue(String.class);
                    String hinh = "1";
                    int id_VX = dataSnapshot.child("id_VX").getValue(Integer.class);
                    VacXin tl = new VacXin(id_VX, hinh,gia , slton, baoquan, chongcd, mota, nguongoc, phongBenh, tenVX);
                    newArrayList.add(tl);

                }
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Linh_Activity_DanhMucVacXin.this, "Loi", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void DuyetGH(ArrayList<GioHang> list)
    {
            Intent intent = getIntent();
            String username = intent.getStringExtra("sdt");
            System.out.println("USER trong duyetGH: " + username);
            //LinhDMVXAdapter adapter_us = new LinhDMVXAdapter();
            //adapter_us.setUser(username);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("VacXin");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(newArrayList != null)
                    {
                        newArrayList.clear();
                    }
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        String baoquan = dataSnapshot.child("BaoQuan").getValue(String.class);
                        String chongcd = dataSnapshot.child("ChongChiDinh").getValue(String.class);
                        Integer gia = dataSnapshot.child("Gia").getValue(Integer.class);
                        String mota = dataSnapshot.child("MoTa").getValue(String.class);
                        String nguongoc = dataSnapshot.child("NguonGoc").getValue(String.class);
                        String hinh = "";
                        String phongBenh = dataSnapshot.child("PhongBenh").getValue(String.class);
                        Integer slton = dataSnapshot.child("SLTon").getValue(Integer.class);
                        String tenVX = dataSnapshot.child("TenVX").getValue(String.class);
                        int id_VX = dataSnapshot.child("id_VX").getValue(Integer.class);
                        VacXin tl = new VacXin(id_VX, hinh,gia , slton, baoquan, chongcd, mota, nguongoc, phongBenh, tenVX);
                        for (GioHang item: list)
                        {

                            if (id_VX == item.getIdvx())
                            {


                                if (username.equalsIgnoreCase(item.getUsername()))
                                {
                                    newArrayList.add(tl);
                                }

                            }
                        }
                    }

                    rec_vxdc.setAdapter(adapter_giohang);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Linh_Activity_DanhMucVacXin.this, "Loi", Toast.LENGTH_SHORT).show();
                }
            });

        }

    private void LayDT_GioHang_2() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference_GH = firebaseDatabase.getReference("GioHang");
        databaseReference_GH.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(newArrayList_GioHang != null)
                {
                    newArrayList_GioHang.clear();
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    int id_VX_gh = dataSnapshot.child("idvx").getValue(Integer.class);
                    String username = dataSnapshot.child("username").getValue(String.class);
                    GioHang gh = new GioHang(id_VX_gh, username);
                    newArrayList_GioHang.add(gh);
                    DuyetGH(newArrayList_GioHang);

                }




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void processCheckBoxes()  {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        CheckBox[] checkBoxes_PB = new CheckBox[]{cbx_cum, cbx_lao, cbx_hoga, cbx_dai, cbx_bailiet, cbx_bachhau};
        CheckBox[]  checkBoxes_Xuatxu = new CheckBox[]{cbx_hanquoc, cbx_my, cbx_phap, cbx_ando, cbx_vietnam, cbx_canada};
        CheckBox[] checkBoxes_gia = new CheckBox[]{cbx_gia1, cbx_gia2,cbx_gia3,cbx_gia4};
        ArrayList<String> listcheck_pb = new ArrayList<>();
        ArrayList<String> listcheck_xx = new ArrayList<>();
        ArrayList<String> listcheck_gia = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes_PB) {
            // Kiểm tra nếu CheckBox có giá trị là true
            if (checkBox.isChecked()) {

                 //Lấy text của CheckBox và xử lý theo nhu cầu của bạn (ở đây, in ra Log)
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Linh_Activity_DanhMucVacXin.this));

                    adapter = new LinhDMVXAdapter(newArrayList);
                    adapter.notifyDataSetChanged();
                    String checkBoxText = checkBox.getText().toString();
                    if (!listcheck_pb.contains(checkBoxText)) {
                        listcheck_pb.add(checkBoxText);
                    }


            }
        }
        for (CheckBox checkBox : checkBoxes_Xuatxu) {
            // Kiểm tra nếu CheckBox có giá trị là true
            if (checkBox.isChecked()) {
                // Lấy text của CheckBox và xử lý theo nhu cầu của bạn (ở đây, in ra Log)
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Linh_Activity_DanhMucVacXin.this));
                adapter = new LinhDMVXAdapter(newArrayList);
                adapter.notifyDataSetChanged();
                String checkBoxText = checkBox.getText().toString();
                if (!listcheck_xx.contains(checkBoxText)) {
                    listcheck_xx.add(checkBoxText);
                }
            }
        }
        for (CheckBox checkBox : checkBoxes_gia) {
            // Kiểm tra nếu CheckBox có giá trị là true
            if (checkBox.isChecked()) {
                // Lấy text của CheckBox và xử lý theo nhu cầu của bạn (ở đây, in ra Log)
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Linh_Activity_DanhMucVacXin.this));
                adapter = new LinhDMVXAdapter(newArrayList);
                adapter.notifyDataSetChanged();
                String checkBoxText = checkBox.getText().toString();
                if (!listcheck_gia.contains(checkBoxText)) {
                    listcheck_gia.add(checkBoxText);
                }
            }
        }

        locDL_VX(listcheck_pb, listcheck_xx, listcheck_gia);


    }


    private void showBottomLoc()
    {
        View v_gh = getLayoutInflater().inflate(R.layout.bottom_sheet_locvx, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(v_gh);
        dialog.show();
        dialog.setCancelable(false);
        cbx_cum = v_gh.findViewById(R.id.chx_cum);
        cbx_bachhau = v_gh.findViewById(R.id.chx_bachhau);
        cbx_bailiet = v_gh.findViewById(R.id.chx_bailiet);
        cbx_dai = v_gh.findViewById(R.id.chx_dai);
        cbx_hoga = v_gh.findViewById(R.id.chx_hoga);
        cbx_lao = v_gh.findViewById(R.id.chx_lao);
        ////////
        cbx_my = v_gh.findViewById(R.id.chx_My);
        cbx_vietnam = v_gh.findViewById(R.id.chx_VN);
        cbx_canada = v_gh.findViewById(R.id.chx_My);
        cbx_hanquoc = v_gh.findViewById(R.id.chx_HQ);
        cbx_ando = v_gh.findViewById(R.id.chx_Ando);
        cbx_phap = v_gh.findViewById(R.id.chx_Phap);
        /////////
        cbx_gia1 = v_gh.findViewById(R.id.chx_gia1);
        cbx_gia2 = v_gh.findViewById(R.id.chx_gia2);
        cbx_gia3 = v_gh.findViewById(R.id.chx_gia3);
        cbx_gia4 = v_gh.findViewById(R.id.chx_gia4);
        /////////////
        btn_loc_dl = v_gh.findViewById(R.id.btn_loc);


//        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();

        // Lưu trạng thái của CheckBox vào SharedPreferences
//        editor.putBoolean("checkbox1", cbx_cum.isChecked());
//        editor.putBoolean("checkbox2", cbx_bachhau.isChecked());
//        editor.apply();


        btn_loc_dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    processCheckBoxes();
                    dialog.dismiss();
            }
        });

        btn_dong_loc = v_gh.findViewById(R.id.imgv_dong_loc);
        btn_dong_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //adapter = new LinhDMVXAdapter(newArrayList, user);
                adapter.notifyDataSetChanged();
                LayDL();
            }
        });
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) v_gh.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

    private  void locDL_VX(ArrayList<String> list_pb, ArrayList<String> list_xx, ArrayList<String> list_gia)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("VacXin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(newArrayList != null)
                {
                    newArrayList.clear();
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    String baoquan = dataSnapshot.child("BaoQuan").getValue(String.class);
                    String chongcd = dataSnapshot.child("ChongChiDinh").getValue(String.class);
                    Integer gia = dataSnapshot.child("Gia").getValue(Integer.class);
                    String mota = dataSnapshot.child("MoTa").getValue(String.class);
                    String nguongoc = dataSnapshot.child("NguonGoc").getValue(String.class);
                    String phongBenh = dataSnapshot.child("PhongBenh").getValue(String.class);
                    Integer slton = dataSnapshot.child("SLTon").getValue(Integer.class);
                    String tenVX = dataSnapshot.child("TenVX").getValue(String.class);
                    String hinh = "1";
                    int id_VX = dataSnapshot.child("id_VX").getValue(Integer.class);
                    VacXin tl = new VacXin(id_VX, hinh,gia , slton, baoquan, chongcd, mota, nguongoc, phongBenh, tenVX);
                    for (String l : list_pb) {
                        if (phongBenh.toLowerCase().contains(l.toLowerCase()))
                        {
                            newArrayList.add(tl);
                        }

                    }
                    for (String l : list_xx) {
                        if (nguongoc.toLowerCase().contains(l.toLowerCase()))
                        {
                            newArrayList.add(tl);
                        }

                    }
                    for (String l : list_gia) {
                        if ("Dưới 5 triệu".compareTo(l) == 0)
                        {
                            if (gia <= 5000000)
                            {
                                newArrayList.add(tl);
                            }

                        }
                        if ("5.000.000 - 7.000.000".compareTo(l) == 0)
                        {
                            if (gia >= 5000000 && gia <= 7000000)
                            {
                                newArrayList.add(tl);
                            }

                        }

                    }

                }
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Linh_Activity_DanhMucVacXin.this, "Loi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBottomGioHang()
    {
        View v_gh = getLayoutInflater().inflate(R.layout.bottom_sheet_giohang, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(v_gh);
        dialog.show();
        dialog.setCancelable(false);

        btn_dong = v_gh.findViewById(R.id.imgv_dong);
        rec_vxdc = v_gh.findViewById(R.id.rec_dmvx_dachon);
        btnDK = v_gh.findViewById(R.id.btn_dangkymt);

        rec_vxdc.setHasFixedSize(true);
        rec_vxdc.setLayoutManager(new LinearLayoutManager(this));
        //btn_dkvx = v_gh.findViewById(R.id.btn_dangkymt);
        adapter_giohang = new LinhGioHangAdapter(newArrayList, user);
        adapter_giohang.notifyDataSetChanged();
        //LayDT_GioHang();
        LayDT_GioHang_2();
        //layVX_GH();
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                LayDL();
            }
        });



        // Cua mai mai
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Bạn có chắc chắn muốn đặt những Vaccine trên?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MangDatLich.mangmuon.size()<=3) {

                            Intent intent = new Intent(Linh_Activity_DanhMucVacXin.this, DatLichTiemChung.class);
                            startActivity(intent);
                        }
                        else
                        {
                            new AlertDialog.Builder(view.getContext()).setTitle("Thông báo").setMessage("Không được tiêm cùng lúc trên 3 vaccine").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }

                    }
                }).setNegativeButton("Cancel",null).show();

            }

        });
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) v_gh.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }

    public void addControls() {
        recyclerView = findViewById(R.id.rec_dmvx);
        searchView = findViewById(R.id.searchView);
        btn_loc = findViewById(R.id.btn_loc);
        giohang = findViewById(R.id.right_ic_dm);
        back = findViewById(R.id.left_ic_dm);
        tv_gia = findViewById(R.id.tv_gia);
        img_gia = findViewById(R.id.img_gia);

    }

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click if needed
                    }
                })
                .show();
    }



}
