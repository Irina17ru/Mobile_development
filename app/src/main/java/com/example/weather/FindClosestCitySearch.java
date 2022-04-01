package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindClosestCitySearch extends AppCompatActivity {
    int cityCurrentPosition;
    int counter = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_closest_city_search);
        Spinner citySpinner = findViewById(R.id.city_spinner);
        List<String> citySpinnerList = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.city_list_min);
        String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
        try {
            JSONArray jsonCity = new JSONArray(jsonString);
            for(int i = 0; i < counter; i++){
                String jsonTemp = jsonCity.getJSONObject(i).getString("name");
                citySpinnerList.add(jsonTemp);
                }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_simple, citySpinnerList);
            citySpinner.setAdapter(adapter);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cityCurrentPosition = position;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getClosestCity(View view) throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.city_list_min);
        String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
        ArrayList<ClosestCity> closestCity = new ArrayList<>();
        JSONArray jsonCity = new JSONArray(jsonString);
        EditText inputDistance = findViewById(R.id.distance);
        Intent intentFindClosestCity = new Intent(FindClosestCitySearch.this, FindClosestCityActivity.class);
        float[] tempDistance = {(float) 0};
        double cityLongitude = jsonCity.getJSONObject(cityCurrentPosition).getJSONObject("coord").getDouble("lon");
        double cityLatitude = jsonCity.getJSONObject(cityCurrentPosition).getJSONObject("coord").getDouble("lat");
        int cmp = Integer.parseInt(inputDistance.getText().toString().replaceAll("[\\D]", ""));

        for (int i = 0; i < counter; i++) {
            if (i != cityCurrentPosition) {
                String tempCityName = jsonCity.getJSONObject(i).getString("name");
                double tempLongitude = jsonCity.getJSONObject(i).getJSONObject("coord").getDouble("lon");
                double tempLatitude = jsonCity.getJSONObject(i).getJSONObject("coord").getDouble("lat");
                Location.distanceBetween(cityLatitude, cityLongitude, tempLatitude, tempLongitude, tempDistance);
                int distance = Math.round(tempDistance[0] / 1000);
                if (cmp > distance) {
                    Log.d("CMP", String.valueOf(cmp) + " " + distance);
                    closestCity.add(new ClosestCity(tempCityName, String.valueOf(distance)));
                }
            }
        }
        intentFindClosestCity.putExtra("closestCity", closestCity);
        startActivity(intentFindClosestCity);
    }
}