package com.example.weather;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherForecastAdapter extends BaseAdapter {
    Context ctx; ArrayList<Weather> weather;

    public WeatherForecastAdapter(Context ctx, ArrayList<Weather> weather) {
        this.ctx = ctx;
        this.weather = weather;
    }

    @Override
    public int getCount() {
        return weather.size();
    }

    @Override
    public Object getItem(int position) {
        return weather.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Weather w = weather.get(position);
        convertView = LayoutInflater.from(ctx).
                inflate(R.layout.listview_forecast, parent, false);
        TextView date = convertView.findViewById(R.id.lvdate);
        TextView tempNight = convertView.findViewById(R.id.lvtempnight);
        ProgressBar progress = convertView.findViewById(R.id.lvprogress);
        TextView tempDay = convertView.findViewById(R.id.lvtempday);
        date.setText(w.date);
        tempNight.setText(w.tempNight);
        progress.setProgress(w.progressBar);
        tempDay.setText(w.tempDay);
        return convertView;
    }
}
