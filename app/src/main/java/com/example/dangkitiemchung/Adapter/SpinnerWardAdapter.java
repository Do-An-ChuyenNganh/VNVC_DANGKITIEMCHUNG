package com.example.dangkitiemchung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dangkitiemchung.R;

import java.util.List;

public class SpinnerWardAdapter extends ArrayAdapter<String> {
    private List<String> items;
    private LayoutInflater inflater;

    public SpinnerWardAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.custom_spinner, null);
        TextView txt_name = view.findViewById(R.id.txt_name);
        TextView txt_code = view.findViewById(R.id.txt_code);
        String item = items.get(position);
        txt_name.setText(item);
        txt_code.setVisibility(View.INVISIBLE);
        txt_code.setVisibility(View.GONE);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}