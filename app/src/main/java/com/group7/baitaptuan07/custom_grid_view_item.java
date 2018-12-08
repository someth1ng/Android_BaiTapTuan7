package com.group7.baitaptuan07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class custom_grid_view_item extends BaseAdapter {
    Context context;
    Integer[] logos;

    public custom_grid_view_item(Context context,  Integer[] logos) {
        this.context = context;
        this.logos = logos;
    }

    @Override
    public int getCount() {
        return logos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            gridView = inflater.inflate(R.layout.custom_grid_view_item, null);

            ImageView imgView = (ImageView) gridView.findViewById(R.id.logo);
            imgView.setImageResource(logos[position]);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
