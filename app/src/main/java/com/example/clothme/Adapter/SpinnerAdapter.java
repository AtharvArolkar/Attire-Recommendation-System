package com.example.clothme.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.clothme.Color.ColorDetector;
import com.example.clothme.R;

import java.util.ArrayList;
import java.util.Map;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    String []colors;
    Map<String,Integer[]> color;
    public SpinnerAdapter(Context context, String []colors){
        this.context=context;
        this.colors=colors;
        ColorDetector cd=new ColorDetector();
        color=cd.colorDict;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lf=LayoutInflater.from(context);
        convertView=lf.inflate(android.R.layout.simple_dropdown_item_1line,null);
        TextView tv=(TextView)convertView.findViewById(android.R.id.text1);
        Color c=new Color();
        String colorText=colors[position];
        Integer [] rgb=color.get(colorText);
        int r=rgb[0];
        int g=rgb[1];
        int b=rgb[2];
        String ResId ="R.color."+colorText;
//        tv.setBackgroundColor((int)Integer.parseInt(ResId)) ;
//
         tv.setBackgroundColor(Color.rgb(r,g,b)) ;
        if(colorText=="NULL") {
            tv.setTextColor(Color.rgb(0,0,0));
        }

        tv.setText(colorText);
        return convertView;
    }
}
