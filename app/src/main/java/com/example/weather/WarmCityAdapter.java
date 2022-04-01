package com.example.weather;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WarmCityAdapter extends BaseAdapter {
    Context ctx; ArrayList<WarmCity> warmCity;

    public WarmCityAdapter(Context ctx, ArrayList<WarmCity> warmCity) {
        this.ctx = ctx;
        this.warmCity = warmCity;
    }

    @Override
    public int getCount() {
        return warmCity.size();
    }

    @Override
    public Object getItem(int position) {
        return warmCity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WarmCity wc = warmCity.get(position);
        convertView = LayoutInflater.from(ctx).
                inflate(R.layout.listview_warmcity, parent, false);
        TextView name = convertView.findViewById(R.id.wcname);
        TextView temp = convertView.findViewById(R.id.wctemp);
        String flag = wc.temp > 0 ? "+" : "";
        String tempText = flag + String.valueOf(wc.temp) + "°";
        name.setText(wc.name);
        temp.setText(tempText);
        return convertView;
    }
}
