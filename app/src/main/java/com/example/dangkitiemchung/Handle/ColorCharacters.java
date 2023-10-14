package com.example.dangkitiemchung.Handle;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class ColorCharacters {
    public void colorRed(String text, TextView textView)
    {
        SpannableString spannableString = new SpannableString(text);

        // Lấy chiều dài của đoạn văn bản
        int textLength = spannableString.length();
        // Màu sắc bạn muốn áp dụng (ví dụ: màu đỏ)
        int colorRed = Color.RED;
        // Tạo một ForegroundColorSpan để đổi màu cho ký tự cuối cùng
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(colorRed);
        // Áp dụng ForegroundColorSpan cho ký tự cuối cùng
        spannableString.setSpan(colorSpan, textLength - 1, textLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }
}
