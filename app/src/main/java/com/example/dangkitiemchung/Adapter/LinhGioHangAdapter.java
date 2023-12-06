package com.example.dangkitiemchung.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.Linh_ThongTinVX_;
import com.example.dangkitiemchung.Models.VacXin;
import com.example.dangkitiemchung.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LinhGioHangAdapter extends RecyclerView.Adapter<LinhGioHangAdapter.MyViewHolder>{
    ArrayList<VacXin> newVacXin;

    public LinhGioHangAdapter(ArrayList<VacXin> newVacXin) {
        this.newVacXin = newVacXin;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linh_item_rec_giohang, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VacXin item= newVacXin.get(position);
        holder.tenVX.setText(item.getTenvx());
        holder.phongbenh.setText(item.getPhongbenh());
        holder.gia.setText(""+item.getGia());
        //holder.img_anh.setImageResource(R.drawable.banner1);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference img = storageReference.child("images/"+item.getId_vx()+".jpg");
        System.out.println(img);
        long MAXBYTES = 1024*1024;
        img.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img_anh.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", ""+item.getId_vx());
                intent.setClass(v.getContext(), Linh_ThongTinVX_.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.img_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemFromGioHang(item.getId_vx());
            }
        });
    }
    private void deleteItemFromGioHang(int id_vx) {
        DatabaseReference gioHangRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        gioHangRef = database.getReference().child("GioHang");
        gioHangRef.orderByChild("idvx").equalTo(id_vx).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // Xóa thành công
                                    //Toast.makeText(YourActivity.this, "Đã xóa mục khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                                    System.out.println("xóa thành công");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xóa thất bại
                                    //Toast.makeText(YourActivity.this, "Xóa mục khỏi giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                                    System.out.println("Xóa thất bại");
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi
                //Toast.makeText(YourActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                System.out.println("Lỗi database");
            }
        });

    }


    @Override
    public int getItemCount() {
        return newVacXin.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tenVX, phongbenh, gia;
        ImageView img_anh, img_xoa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenVX = itemView.findViewById(R.id.tv_TenVX);
            phongbenh = itemView.findViewById(R.id.tv_phongbenh);
            gia = itemView.findViewById(R.id.tv_gia);
            img_anh = itemView.findViewById(R.id.img_VX);
            img_xoa = itemView.findViewById(R.id.img_xoavx);
        }
    }
}
