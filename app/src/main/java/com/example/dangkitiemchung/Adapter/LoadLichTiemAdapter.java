package com.example.dangkitiemchung.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.ChiTietLichHen;
import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class LoadLichTiemAdapter extends RecyclerView.Adapter<LoadLichTiemAdapter.MyViewHolder>{

    ArrayList<LichTiem> newLichTiem;
    String UserName;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mai_layout_dalenlich, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        LichTiem l = newLichTiem.get(position);
        holder.tenvx.setText(""+l.getTenVX());
        holder.noitiem.setText(""+l.getNoiTiem());
        holder.ngaytiem.setText("Ngày tiêm: "+l.getNgayTiem());
//        holder.img.setImageResource(Integer.parseInt(taiLieu.getHinh().trim()));

        holder.tenvx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ChiTietLichHen.class);
                intent.putExtra("id", ""+l.getId());
                intent.putExtra("user", ""+l.getUserName());
                intent.putExtra("ngaydat", ""+l.getNgayDat());
                intent.putExtra("ngaytiem",""+l.getNgayTiem());
                intent.putExtra("noitiem", ""+l.getNoiTiem());
                view.getContext().startActivity(intent);
            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ChiTietLichHen.class);
                intent.putExtra("id", ""+l.getId());
                intent.putExtra("user", ""+l.getUserName());
                intent.putExtra("ngaydat", ""+l.getNgayDat());
                intent.putExtra("ngaytiem",""+l.getNgayTiem());
                intent.putExtra("noitiem", ""+l.getNoiTiem());
                view.getContext().startActivity(intent);
            }
        });
    }

    public LoadLichTiemAdapter(ArrayList<LichTiem> newArrayList1, String Ten) {

        newLichTiem = newArrayList1;
        this.UserName = Ten;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tenvx, noitiem, ngaytiem;
        ImageView img, chitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenvx = itemView.findViewById(R.id.tenvx);
            noitiem = itemView.findViewById(R.id.diadiemtiem);
            ngaytiem = itemView.findViewById(R.id.ngaytiem);
            img = itemView.findViewById(R.id.imgSach);
            chitiet = itemView.findViewById(R.id.imageView4);

        }
    }

    @Override
    public int getItemCount() {
        return newLichTiem.size();
    }

}
