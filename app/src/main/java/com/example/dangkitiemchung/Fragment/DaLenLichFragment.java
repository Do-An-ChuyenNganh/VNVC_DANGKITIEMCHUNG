package com.example.dangkitiemchung.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dangkitiemchung.Adapter.LoadLichTiemAdapter;
import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaLenLichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaLenLichFragment extends Fragment {

    String UserName;
    private ArrayList<LichTiem> newArrayList = new ArrayList<>();
    private RecyclerView recycleView;
    private LoadLichTiemAdapter adapter;
    private LinearLayoutManager layout;

    public DaLenLichFragment(String userName) {
        // Required empty public constructor
        this.UserName = userName;
    }
    public DaLenLichFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DaLenLichFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaLenLichFragment newInstance(String param1, String param2) {
        DaLenLichFragment fragment = new DaLenLichFragment();
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
        return inflater.inflate(R.layout.fragment_da_len_lich, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = new LinearLayoutManager(getContext());
        recycleView = view.findViewById(R.id.rec_dalenlich);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(layout);
        adapter = new LoadLichTiemAdapter(newArrayList,UserName);
        adapter.notifyDataSetChanged();
        Data();



    }

    private void Data() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("LichSuDat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(newArrayList != null)
                {
                    newArrayList.clear();
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    String tinhTrang = dataSnapshot.child("TinhTrang").getValue(String.class);
                    Integer id = dataSnapshot.child("id_VX").getValue(Integer.class);
                    Integer giaBan = dataSnapshot.child("GiaBan").getValue(Integer.class);
                    String userName = dataSnapshot.child("UserName").getValue(String.class);
                    String tenVX = dataSnapshot.child("TenVX").getValue(String.class);
                    String ngayDat = dataSnapshot.child("NgayDat").getValue(String.class);
                    String ngayTiem = dataSnapshot.child("NgayTiem").getValue(String.class);
                    String noiTiem = dataSnapshot.child("NoiTiem").getValue(String.class);
//                    String tenHinh = dataSnapshot.child("Hinh").getValue(String.class);
//                    Integer hinh = getResources().getIdentifier(tenHinh,"drawable", getActivity().getPackageName());
//                    String ha = ""+hinh;
//                    if(tinhTrang == "Đã lên lịch") {
                        LichTiem tl = new LichTiem(id, giaBan, userName, tenVX, tinhTrang,  ngayDat, ngayTiem, noiTiem);
                        newArrayList.add(tl);

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