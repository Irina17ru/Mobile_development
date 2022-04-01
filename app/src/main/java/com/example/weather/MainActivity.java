package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoWeatherForecast(View view) {
        Intent intentForecastSearch = new Intent(MainActivity.this, ForecastSearch.class);
        startActivity(intentForecastSearch);
    }

    public void gotoWarmCity(View view) {
        Intent intentWarmCity = new Intent(MainActivity.this, WarmCityActivity.class);
        startActivity(intentWarmCity);
    }

    public void gotoFindClosestCity(View view) {
        Intent intentFindClosestCity = new Intent(MainActivity.this, FindClosestCitySearch.class);
        startActivity(intentFindClosestCity);
    }
}
