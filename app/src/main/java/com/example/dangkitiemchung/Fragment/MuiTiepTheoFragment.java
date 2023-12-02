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
import com.example.dangkitiemchung.Adapter.LoadMuiTiepTheoAdapter;
import com.example.dangkitiemchung.Models.LichSuTiemChung;
import com.example.dangkitiemchung.Models.MuiTiepTheo;
import com.example.dangkitiemchung.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MuiTiepTheoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MuiTiepTheoFragment extends Fragment {

    String UserName;
    private ArrayList<MuiTiepTheo> newArrayList = new ArrayList<>();
    private RecyclerView recycleView;
    private LoadMuiTiepTheoAdapter adapter;
    private LinearLayoutManager layout;

    public MuiTiepTheoFragment() {
        // Required empty public constructor
    }
    public MuiTiepTheoFragment(String userName) {
        this.UserName = userName;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MuiTiepTheoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MuiTiepTheoFragment newInstance(String param1, String param2) {
        MuiTiepTheoFragment fragment = new MuiTiepTheoFragment();
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
        return inflater.inflate(R.layout.fragment_mui_tiep_theo, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = new LinearLayoutManager(getContext());
        recycleView = view.findViewById(R.id.rec_dalenlich);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(layout);
        adapter = new LoadMuiTiepTheoAdapter(newArrayList,UserName);
        adapter.notifyDataSetChanged();
        Data();
    }

    private void Data() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("MuiTiepTheo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(newArrayList != null)
                {
                    newArrayList.clear();
                }
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {

                    Integer id = dataSnapshot.child("id").getValue(Integer.class);
                    Integer muiSo = dataSnapshot.child("muiSo").getValue(Integer.class);
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    String tenVX = dataSnapshot.child("tenVX").getValue(String.class);
                    String phongBenh = dataSnapshot.child("phongBenh").getValue(String.class);
                    String ngayTiem = dataSnapshot.child("ngayTiem").getValue(String.class);
                    MuiTiepTheo tl = new MuiTiepTheo( id, userName, tenVX, muiSo, ngayTiem, phongBenh);
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