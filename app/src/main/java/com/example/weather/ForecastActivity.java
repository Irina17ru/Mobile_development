package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        TextView temp, hum, speed, deg, tempDay, tempNight, cityName;
        ListView listView;
        WeatherForecastAdapter adapter;
        int wind_degree;
        double avgTempDay, avgTempNight;
        ArrayList<Weather> weather = new ArrayList<>();

        temp = findViewById(R.id.temp);
        hum = findViewById(R.id.humidity);
        speed = findViewById(R.id.speed);
        tempDay = findViewById(R.id.temp_day);
        tempNight = findViewById(R.id.temp_night);
        listView = findViewById(R.id.listview_forecast);
        cityName = findViewById(R.id.city);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            wind_degree = extras.getInt("deg");
            avgTempDay = extras.getDouble("avg_day");
            avgTempNight = extras.getDouble("avg_night");
            double avgTemp = avgTempDay - avgTempNight;
            String tempCurrent= extras.getString("temp_current") + "°";
            String tempCurrentDay = extras.getString("temp_day") + "°";
            String tempCurrentNight = extras.getString("temp_night") + "°";
            cityName.setText(extras.getString("cityname"));
            temp.setText(tempCurrent);
            tempDay.setText(tempCurrentDay);
            tempNight.setText(tempCurrentNight);
            hum.setText(extras.getInt("humidity") + " %");
            speed.setText(String.valueOf(extras.getDouble("speed")) + " м/с");

            for (int i = 1; i < 8; i++) {
                String date = extras.getString("date" + String.valueOf(i));
                int Day = extras.getInt("day" + String.valueOf(i));
                int Night = extras.getInt("night" + String.valueOf(i));
                String flagD = "";
                String flagN = "";
                int percentage = (int) Math.round(((Math.max(avgTempNight, Day) - Math.min(avgTempNight, Day))/avgTemp)*100);
                if (Day > 0){ flagD = "+";}
                if (Night > 0){ flagN = "+";}
                String tDay = flagD + String.valueOf(Day)  + "°";
                String tNight = flagN + String.valueOf(Night)  + "°";

                weather.add(new Weather(date, tNight, percentage, tDay));
            }
        }

        adapter = new WeatherForecastAdapter(this, weather);
        listView.setAdapter(adapter);
    }


}