package com.example.dangkitiemchung.Models;

import android.graphics.Bitmap;

public class News {
    int resourceId;
String title, date, hours;

    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getHours() {
        return hours;
    }

    public News(int resourceId, String title, String date, String hours) {
        this.resourceId = resourceId;
        this.title = title;
        this.date = date;
        this.hours = hours;
    }
}
