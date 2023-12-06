package com.example.dangkitiemchung;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.dangkitiemchung.Adapter.TrungTamTiemAdapter;
import com.example.dangkitiemchung.Models.TrungTamTiem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LayDiaChi extends AppCompatActivity {

    TrungTamTiemAdapter adapter;
    ArrayList<TrungTamTiem> list = new ArrayList<>();
    RecyclerView recycler_view;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay_dia_chi);
        recycler_view = findViewById(R.id.rectrungtam);
        layoutRecyclerView();
        adapter.notifyDataSetChanged();
        database = FirebaseDatabase.getInstance().getReference().child("DiaDiemTiem");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list != null) {
                    list.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.child("TenDD").getValue(String.class);
                    String name = dataSnapshot.child("DiaChiCuThe").getValue(String.class);
                    TrungTamTiem d = new TrungTamTiem(id, name);
                    System.out.println("Ten trung tam"+id);
                    list.add(d);

                }
                recycler_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: ");
            }

        });

    }
    private void layoutRecyclerView()
    {
        recycler_view.addItemDecoration(new DividerItemDecoration(LayDiaChi.this, LinearLayoutManager.VERTICAL));
        adapter = new TrungTamTiemAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }
}