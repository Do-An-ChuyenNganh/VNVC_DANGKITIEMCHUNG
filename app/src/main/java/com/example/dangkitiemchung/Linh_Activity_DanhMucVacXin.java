package com.example.dangkitiemchung;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.Adapter.LinhDMVXAdapter;
import com.example.dangkitiemchung.Adapter.LinhGioHangAdapter;
import com.example.dangkitiemchung.Models.GioHang;
import com.example.dangkitiemchung.Models.VacXin;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Linh_Activity_DanhMucVacXin extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<VacXin> newArrayList = new ArrayList<>();
    private ArrayList<VacXin> newArrayList_loc = new ArrayList<>();
    private ArrayList<GioHang> newArrayList_GioHang = new ArrayList<>();
    private LinhDMVXAdapter adapter;
    private LinhGioHangAdapter adapter_giohang;

    private Button btn_them, btn_mua, btn_loc, btn_loc_dl;
    private CheckBox cbx_cum;

    private ImageView btn_dong, btn_dong_loc;
    private RecyclerView rec_vxdc;

    private ImageView giohang, back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linh_danh_muc_vac_xin);
        getSupportActionBar().hide();
        addControls();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LinhDMVXAdapter(newArrayList);
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
                                if (item.getUsername().trim().compareTo("0366850669") == 0)
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
        DatabaseReference databaseReference = firebaseDatabase.getReference("VacXin");
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


    private void showBottomLoc()
    {
        View v_gh = getLayoutInflater().inflate(R.layout.bottom_sheet_locvx, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(v_gh);
        dialog.show();
        dialog.setCancelable(false);
        cbx_cum = v_gh.findViewById(R.id.chx_cum);
        String dl = (String) cbx_cum.getText();

        btn_loc_dl = v_gh.findViewById(R.id.btn_loc);
        btn_loc_dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbx_cum.isChecked() == true)
                {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Linh_Activity_DanhMucVacXin.this));
                    adapter = new LinhDMVXAdapter(newArrayList);
                    adapter.notifyDataSetChanged();
                    locDL(dl);
                    dialog.dismiss();
                }
            }
        });

        btn_dong_loc = v_gh.findViewById(R.id.imgv_dong_loc);
        btn_dong_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) v_gh.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

    private  void locDL(String dl)
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
                    if (phongBenh.toLowerCase().contains(dl.toLowerCase()))
                    {
                        newArrayList.add(tl);
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

        rec_vxdc.setHasFixedSize(true);
        rec_vxdc.setLayoutManager(new LinearLayoutManager(this));
        adapter_giohang = new LinhGioHangAdapter(newArrayList);
        adapter_giohang.notifyDataSetChanged();
        //LayDT_GioHang();
        LayDT_GioHang_2();
        //layVX_GH();
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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

    }
    public void showDialogSuccess()
    {
        View v = LayoutInflater.from(Linh_Activity_DanhMucVacXin.this).inflate(R.layout.alert_sucess, null);
        Button btnSuccess = v.findViewById(R.id.successDone);
        AlertDialog.Builder builder = new AlertDialog.Builder(Linh_Activity_DanhMucVacXin.this);
        builder.setView(v);
        final AlertDialog alertDialog= builder.create();
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
    }
}
