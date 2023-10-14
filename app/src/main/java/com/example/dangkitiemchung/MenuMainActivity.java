package com.example.dangkitiemchung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.dangkitiemchung.Adapter.NewsAdapter;
import com.example.dangkitiemchung.Models.News;
import com.example.dangkitiemchung.R;

import java.util.ArrayList;
import java.util.List;

public class MenuMainActivity extends AppCompatActivity {
    RecyclerView recyclerView_news;
    NewsAdapter newsAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        // Ẩn thanh trạng thái (status bar)
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        addControl();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_news.setLayoutManager(linearLayoutManager);
        newsAdapter =new NewsAdapter(getListNews());
        recyclerView_news.setAdapter(newsAdapter);
    }
    public List<News> getListNews(){
        List<News> lst = new ArrayList<>();
        lst.add(new News(R.drawable.img_vaccins,"CÁC LOẠI VẮC XIN CHO NGƯỜI LỚN","07/11/2022","22:04"));
        lst.add(new News(R.drawable.imgho,"VẮC XIN NGỪA CÚM CHO NGƯỜI LỚN \n CÓ NÊN TIÊM PHÒNG HẰNG NĂM","06/06/2022","16:03"));
        lst.add(new News(R.drawable.img_bord,"TẠI SAO NGƯỜI LỚN CẦN TIÊM VẮC XIN ?","07/11/2022","22:04"));
        lst.add(new News(R.drawable.img_treat,"CÁC LOẠI VẮC XIN CHO NGƯỜI LỚN","07/11/2022","22:04"));
        lst.add(new News(R.drawable.img_cream,"CÁC LOẠI VẮC XIN CHO NGƯỜI LỚN","07/11/2022","22:04"));
        return lst;
    }
    public void addControl(){
        recyclerView_news=findViewById(R.id.recyclerView_news);
    }
}