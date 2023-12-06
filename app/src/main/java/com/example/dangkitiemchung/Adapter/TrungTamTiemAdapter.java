package com.example.dangkitiemchung.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dangkitiemchung.DatLichTiemChung;
import com.example.dangkitiemchung.Models.LichTiem;
import com.example.dangkitiemchung.Models.TrungTamTiem;
import com.example.dangkitiemchung.R;

import java.util.ArrayList;

public class TrungTamTiemAdapter extends RecyclerView.Adapter<TrungTamTiemAdapter.MyViewHolder> {
    ArrayList<TrungTamTiem> newLichTiem;
    String UserName;
    @NonNull
    @Override
    public TrungTamTiemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mai_layout_trungtamtiem, parent, false);
        return new TrungTamTiemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrungTamTiemAdapter.MyViewHolder holder, int position) {
        TrungTamTiem l = newLichTiem.get(position);
        holder.noitiem.setText(""+l.getTenDD());
        holder.diachicuthe.setText(""+l.getDiaChiCuThe());
        holder.noitiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), DatLichTiemChung.class);
                intent.putExtra("tendd", ""+l.getTenDD());
                intent.putExtra("diachicuthe", ""+l.getDiaChiCuThe());
                view.getContext().startActivity(intent);
            }
        });
        holder.diachicuthe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), DatLichTiemChung.class);
                intent.putExtra("tendd", ""+l.getTenDD());
                intent.putExtra("diachicuthe", ""+l.getDiaChiCuThe());
                view.getContext().startActivity(intent);
            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), DatLichTiemChung.class);
                intent.putExtra("tendd", ""+l.getTenDD());
                intent.putExtra("diachicuthe", ""+l.getDiaChiCuThe());
                view.getContext().startActivity(intent);
            }
        });
    }
    public TrungTamTiemAdapter(ArrayList<TrungTamTiem> newArrayList1) {

        newLichTiem = newArrayList1;
    }

    @Override
    public int getItemCount() {
        return newLichTiem.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView diachicuthe, noitiem;
        ImageView chitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            noitiem = itemView.findViewById(R.id.tentrungtam);
            diachicuthe = itemView.findViewById(R.id.dchi);
            chitiet = itemView.findViewById(R.id.imageView4);

        }
    }
}
