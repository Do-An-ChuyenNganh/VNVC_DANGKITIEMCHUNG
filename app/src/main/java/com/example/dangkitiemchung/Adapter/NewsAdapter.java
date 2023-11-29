package com.example.dangkitiemchung.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dangkitiemchung.Models.News;
import com.example.dangkitiemchung.R;
import com.squareup.picasso.Picasso;

import  java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private  List<News> mlistNews;
    private static final int TARGET_WIDTH = 70; // Kích thước mục tiêu theo chiều rộng
    private static final int TARGET_HEIGHT = 60; // Kích thước mục tiêu theo chiều cao

    public NewsAdapter(List<News> mlistNews) {
        this.mlistNews = mlistNews;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
                News newsModel =mlistNews.get(position);
                if(newsModel == null)
                    return;


                //holder.resourseId.setImageResource(newsModel.getResourceId());
                Picasso.get()
                .load(newsModel.getResourceId())  // Assume getResId trả về URL hoặc đường dẫn hình ảnh
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .onlyScaleDown()
                .into(holder.resourseId);
                holder.tilte.setText(newsModel.getTitle());
                holder.date.setText(newsModel.getDate());
                holder.hours.setText(newsModel.getDate());
    }
    @Override
    public int getItemCount() {
        if(mlistNews !=null)
            return mlistNews.size();
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
       private  ImageView resourseId;
       private  TextView tilte, date, hours;
       public NewsViewHolder(@NonNull View itemView) {
           super(itemView);
           resourseId =itemView.findViewById(R.id.img);
           tilte =itemView.findViewById(R.id.txt_title);
           date =itemView.findViewById(R.id.txt_date);
           hours =itemView.findViewById(R.id.txt_hours);

       }
   }
}

