package com.example.dangkitiemchung.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.Linh_Activity_DanhMucVacXin;
import com.example.dangkitiemchung.Linh_ThongTinVX_;
import com.example.dangkitiemchung.Models.GioHang;
import com.example.dangkitiemchung.Models.VacXin;
import com.example.dangkitiemchung.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Map;

public class LinhDMVXAdapter extends RecyclerView.Adapter<LinhDMVXAdapter.MyViewHolder>  {

    ArrayList<VacXin> newVacXin;
    private RecyclerViewClickListener clickListener;
    Context context ;
    ArrayList<GioHang> newGioHang;
    private ArrayList<GioHang> newArrayList_GioHang = new ArrayList<>();


    public  LinhDMVXAdapter(ArrayList<VacXin> dataList)
    {

        this.newVacXin = dataList;

    }
    public  LinhDMVXAdapter(ArrayList<VacXin> dataList, RecyclerViewClickListener clickListener)
    {

        this.newVacXin = dataList;
        this.clickListener = clickListener;

    }



    public void setSearchList(ArrayList<VacXin> dataSearch)
    {
        this.newVacXin = dataSearch;
        notifyDataSetChanged();
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linh_item_recyc_dmvc, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

        VacXin item= newVacXin.get(position);
        holder.tenVX.setText(item.getTenvx());
        holder.phongbenh.setText("Phòng bệnh: "+item.getPhongbenh());
        holder.gia.setText("Giá: "+item.getGia());
        //holder.img_anh.setImageResource(R.drawable.banner1);

        //holder.img_anh.setImageBitmap();


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
         holder.btn_themgh.setOnClickListener(new View.OnClickListener() {
             @SuppressLint("SetTextI18n")
             @Override
             public void onClick(View view) {


                 FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                 DatabaseReference databaseReference = firebaseDatabase.getReference().child("GioHang");
                 Integer id_Vx = item.getId_vx();
                 String user = "0366850669";

                 //GioHang gioHang= new GioHang(id_Vx, user);
                 //databaseReference.push().setValue(gioHang);
                 //System.out.println("Thành công");
                 KT_VXGH(id_Vx, user);

             }
         });

    }

    private void KT_VXGH(final int idvx, final String username) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference_GH = firebaseDatabase.getReference().child("GioHang");
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("GioHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Lặp qua tất cả các mục trong giỏ hàng
                for (DataSnapshot gioHangItemSnapshot : dataSnapshot.getChildren()) {
                    // Lấy giá trị của mỗi mục trong giỏ hàng
                    Map<String, Object> gioHangItem = (Map<String, Object>) gioHangItemSnapshot.getValue();

                    if (gioHangItem != null) {
                        // Lấy giá trị của idvx và username từ mỗi mục trong giỏ hàng
                        int gioHangItemIDVX = Integer.parseInt(gioHangItem.get("idvx").toString());
                        String gioHangItemUsername = gioHangItem.get("username").toString();

                        // Kiểm tra xem idvx và username có trùng khớp không
                        if (gioHangItemIDVX == idvx && gioHangItemUsername.equals(username)) {
                            // Nếu trùng khớp, thông báo rằng đã tồn tại trong giỏ hàng
                            System.out.println("Đã tồn tại");
                            //Toast.makeText((Linh_Activity_DanhMucVacXin )context, "Đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                // Nếu không tìm thấy, idvx và username chưa tồn tại trong giỏ hàng
                GioHang gioHang= new GioHang(idvx, username);
                databaseReference_GH.push().setValue(gioHang);
                //Toast.makeText(YourActivity.this, "Chưa tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                System.out.println("Không tồn tại");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                //Toast.makeText(YourActivity.this, "Đã xảy ra lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {

        return newVacXin.size();

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tenVX, phongbenh, gia;
        ImageView img_anh;
        Button btn_themgh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenVX = itemView.findViewById(R.id.tv_TenVX);
            phongbenh = itemView.findViewById(R.id.tv_phongbenh);
            gia = itemView.findViewById(R.id.tv_gia);
            img_anh = itemView.findViewById(R.id.img_VX);
            btn_themgh = itemView.findViewById(R.id.btn_themgiohang);
        }
    }

    public interface RecyclerViewClickListener {
        void onButtonClick(int position);
    }






}

