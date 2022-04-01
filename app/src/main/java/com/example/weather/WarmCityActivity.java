package com.example.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

public class WarmCityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_city);

        String[] cities = new String[] {"Иркутск", "Новосибирск", "Якутск", "Хабаровск", "Омск",
                "Йошкар-Ола", "Калининград", "Рязань", "Ростов", "Южно-Сахалинск"};
        WeatherAsyncTask cityTask;
        WarmCityAdapter adapter;
        ArrayList<WarmCity> warmCity = new ArrayList<>();
        ListView listView = findViewById(R.id.listview_warmcity);

        for (int i = 0; i < cities.length; i++) {
            cityTask = new WeatherAsyncTask();
            cityTask.execute(cities[i]);
            try {
                int temp = cityTask.get();
                warmCity.add(new WarmCity(cities[i], temp));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(warmCity, new CompareTemp());
        adapter = new WarmCityAdapter(this, warmCity);
        listView.setAdapter(adapter);
        }

        private class WeatherAsyncTask extends AsyncTask<String, Void, Integer> {

            @Override
            protected void onPostExecute(Integer gTemp) {
            }

            @Override
            protected Integer doInBackground(String... cityNames) {
                Integer result = -10000;
                String apikey = "2aae981526c60ecfb04f462bc74013c5";
                String urlCoordinate = "https://api.openweathermap.org/data/2.5/weather?q=";
                double kelvin = (double) 273.15;
                try {
                    for (String cityName : cityNames) {
                        URL url = new URL(urlCoordinate + cityName + "&appid=" + apikey);
                        Scanner in = new Scanner((InputStream) url.getContent());
                        String response = in.nextLine();
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject jsonMain = jsonResponse.getJSONObject("main");
                        result = (int) Math.round(jsonMain.getDouble("temp") - kelvin);
                    }
                } catch (Exception e) {
                }
                return result;
            }
        }
}
