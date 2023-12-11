package com.example.dangkitiemchung.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dangkitiemchung.Adapter.LoadLichSuTiemChungAdapter;
import com.example.dangkitiemchung.Adapter.LoadLichTiemAdapter;
import com.example.dangkitiemchung.Models.LaySDT;
import com.example.dangkitiemchung.Models.LichSuTiemChung;
import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.Models.MangDatLich;
import com.example.dangkitiemchung.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichSuTiemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichSuTiemFragment extends Fragment {

    String UserName;
    private ArrayList<LichSuTiemChung> newArrayList = new ArrayList<>();
    private RecyclerView recycleView;
    private LoadLichSuTiemChungAdapter adapter;
    private LinearLayoutManager layout;

    public LichSuTiemFragment() {
        // Required empty public constructor
    }
    public LichSuTiemFragment(String userName) {
        this.UserName = userName;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichSuTiemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichSuTiemFragment newInstance(String param1, String param2) {
        LichSuTiemFragment fragment = new LichSuTiemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lich_su_tiem, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = new LinearLayoutManager(getContext());
        recycleView = view.findViewById(R.id.rec_dalenlich);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(layout);
        adapter = new LoadLichSuTiemChungAdapter(newArrayList,UserName);
        adapter.notifyDataSetChanged();
        Data();
    }

    private void Data() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("LichSuTiem");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(newArrayList != null)
                {
                    newArrayList.clear();
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    String key = dataSnapshot.getKey();
                    Integer id = dataSnapshot.child("id").getValue(Integer.class);
                    Integer muiSo = dataSnapshot.child("muiSo").getValue(Integer.class);
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    String tenVX = dataSnapshot.child("tenVX").getValue(String.class);
                    String phongBenh = dataSnapshot.child("phongBenh").getValue(String.class);
                    String ngayTiem = dataSnapshot.child("ngayTiem").getValue(String.class);
                    String noiTiem = dataSnapshot.child("noiTiem").getValue(String.class);
                    LichSuTiemChung tl = new LichSuTiemChung(key, id, userName, tenVX, muiSo, ngayTiem, phongBenh, noiTiem);
                    if(LaySDT.user.equals(userName)) {
                        newArrayList.add(tl);
                    }

                }
                recycleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "lỗi rồi máaaa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}