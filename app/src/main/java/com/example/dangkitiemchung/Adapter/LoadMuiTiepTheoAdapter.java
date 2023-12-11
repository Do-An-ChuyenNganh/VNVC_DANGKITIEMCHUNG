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
import com.example.dangkitiemchung.ChiTietMuiTiepTheo;
import com.example.dangkitiemchung.Models.LichSuTiemChung;
import com.example.dangkitiemchung.Models.MuiTiepTheo;
import com.example.dangkitiemchung.R;

import java.util.ArrayList;

public class LoadMuiTiepTheoAdapter extends RecyclerView.Adapter<LoadMuiTiepTheoAdapter.MyViewHolder>{
    ArrayList<MuiTiepTheo> newLSTC;
    String UserName = "0366850669";
    @NonNull
    @Override
    public LoadMuiTiepTheoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mai_layout_muitieptheo, parent, false);
        return new LoadMuiTiepTheoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadMuiTiepTheoAdapter.MyViewHolder holder, int position) {
        MuiTiepTheo l = newLSTC.get(position);
        holder.muiso.setText("MŨI "+l.getMuiSo());
        holder.tenvx.setText(""+l.getTenVX());
        holder.ngaytiem.setText("Ngày tiêm dự tính: "+l.getNgayTiem());

        holder.tenvx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ChiTietMuiTiepTheo.class);
                intent.putExtra("ten", ""+l.getTenVX());
                intent.putExtra("phongbenh", ""+l.getPhongBenh());
                intent.putExtra("ngaytiem", ""+l.getNgayTiem());
                intent.putExtra("user", ""+l.getUserName());
                view.getContext().startActivity(intent);
            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), ChiTietMuiTiepTheo.class);
                intent.putExtra("ten", ""+l.getTenVX());
                intent.putExtra("phongbenh", ""+l.getPhongBenh());
                intent.putExtra("ngaytiem", ""+l.getNgayTiem());
                intent.putExtra("user", ""+l.getUserName());
                view.getContext().startActivity(intent);
            }
        });
    }


    public LoadMuiTiepTheoAdapter(ArrayList<MuiTiepTheo> newArrayList1, String Ten) {

        newLSTC = newArrayList1;
        this.UserName = Ten;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView muiso, tenvx, ngaytiem, phongbenh;
        ImageView chitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            muiso = itemView.findViewById(R.id.muiso);
            tenvx = itemView.findViewById(R.id.tenvx);
            ngaytiem = itemView.findViewById(R.id.ngaytiem);
            chitiet = itemView.findViewById(R.id.imageView4);

        }
    }

    @Override
    public int getItemCount() {
        return newLSTC.size();
    }

}
