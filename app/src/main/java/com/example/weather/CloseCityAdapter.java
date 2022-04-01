package com.example.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CloseCityAdapter extends BaseAdapter {
    Context ctx; ArrayList<ClosestCity> closestCity;

    public CloseCityAdapter(Context ctx, ArrayList<ClosestCity> closestCity) {
        this.ctx = ctx;
        this.closestCity = closestCity;
    }

    @Override
    public int getCount() {
        return closestCity.size();
    }

    @Override
    public Object getItem(int position) {
        return closestCity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClosestCity cc = closestCity.get(position);
        convertView = LayoutInflater.from(ctx).
                inflate(R.layout.listview_closest_city, parent, false);
        TextView name = convertView.findViewById(R.id.ccname);
        TextView temp = convertView.findViewById(R.id.ccdist);
        name.setText(cc.name);
        temp.setText(cc.distance);
        return convertView;
    }
}