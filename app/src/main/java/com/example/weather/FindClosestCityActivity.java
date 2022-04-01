package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FindClosestCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_closest_city);
        CloseCityAdapter adapter;
        ArrayList<ClosestCity> closestCity = new ArrayList<>();
        ListView listView = findViewById(R.id.listview_closest_city);
        Intent intent = getIntent();
        closestCity = (ArrayList<ClosestCity>) intent.getSerializableExtra("closestCity");
        adapter = new CloseCityAdapter(this, closestCity);
        listView.setAdapter(adapter);
    }
}