package com.example.dangkitiemchung.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.ChiTietDaHuy;
import com.example.dangkitiemchung.ChiTietLichSuTiemChung;
import com.example.dangkitiemchung.Models.LichSuTiemChung;
import com.example.dangkitiemchung.R;

import java.util.ArrayList;

public class LoadLichSuTiemChungAdapter extends RecyclerView.Adapter<LoadLichSuTiemChungAdapter.MyViewHolder>{
    ArrayList<LichSuTiemChung> newLSTC;
    String UserName;
    @NonNull
    @Override
    public LoadLichSuTiemChungAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mai_layout_lichsutiemchung, parent, false);
        return new LoadLichSuTiemChungAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadLichSuTiemChungAdapter.MyViewHolder holder, int position) {
        LichSuTiemChung l = newLSTC.get(position);
        holder.muiso.setText("MŨI "+l.getMuiSo());
        holder.tenvx.setText(""+l.getTenVX());
        holder.noitiem.setText(""+l.getNoiTiem());
        holder.ngaytiem.setText(""+l.getNgayTiem());
        holder.phongbenh.setText("Phòng bệnh: "+ l.getPhongBenh());

        holder.tenvx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ChiTietLichSuTiemChung.class);
                intent.putExtra("id", ""+l.getId());
                intent.putExtra("key", ""+l.getKey());
                intent.putExtra("user", ""+l.getUserName());
                intent.putExtra("ngaytiem",""+l.getNgayTiem());
                intent.putExtra("noitiem", ""+l.getNoiTiem());
                view.getContext().startActivity(intent);
            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ChiTietLichSuTiemChung.class);
                intent.putExtra("id", ""+l.getId());
                intent.putExtra("key", ""+l.getKey());
                intent.putExtra("user", ""+l.getUserName());
                intent.putExtra("ngaytiem",""+l.getNgayTiem());
                intent.putExtra("noitiem", ""+l.getNoiTiem());
                view.getContext().startActivity(intent);
            }
        });
    }


    public LoadLichSuTiemChungAdapter(ArrayList<LichSuTiemChung> newArrayList1, String Ten) {

        newLSTC = newArrayList1;
        this.UserName = Ten;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView muiso, tenvx, noitiem, ngaytiem, phongbenh;
        ImageView chitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            muiso = itemView.findViewById(R.id.muiso);
            tenvx = itemView.findViewById(R.id.tenvx);
            noitiem = itemView.findViewById(R.id.diadiemtiem);
            ngaytiem = itemView.findViewById(R.id.ngaytiem);
            phongbenh = itemView.findViewById(R.id.phongbenh);
            chitiet = itemView.findViewById(R.id.imageView4);

        }
    }

    @Override
    public int getItemCount() {
        return newLSTC.size();
    }

}
