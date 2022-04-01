package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

//Волли я тебя ненавижу!!!
//Это нерабочая версия активности, тут все плохо

public class WarmCityActivityFailed extends AppCompatActivity {
    final double kelvin = (double) 273.15;
    String apikey = "2aae981526c60ecfb04f462bc74013c5";
    String urlCoordinate = "https://api.openweathermap.org/data/2.5/weather?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_city);
        final String[] cities = new String[] {"Иркутск", "Новосибирск", "Якутск", "Хабаровск", "Омск",
                "Йошкар-Ола", "Москва", "Санкт-Петербург", "Ростов", "Южно-Сахалинск"};
        final int[] requestCounter = {cities.length};
        WarmCityAdapter adapter;
        final ArrayList<WarmCity> warmCity = new ArrayList<>();
        ListView listView = findViewById(R.id.listview_warmcity);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        for (int i = 0; i < cities.length; i++){
            String urlGetCityCoordinate = urlCoordinate + cities[i] + "&appid=" + apikey;
            final int finalI = i;
            StringRequest currentRequest = new StringRequest(Request.Method.POST, urlGetCityCoordinate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject jsonMain = jsonResponse.getJSONObject("main");
                        int temp = (int) Math.round(jsonMain.getDouble("temp") - kelvin);
                        warmCity.add(new WarmCity(cities[finalI], temp));
                        Log.d("wc1", String.valueOf(temp) + " " + cities[finalI]);
                        requestCounter[0] -= 1;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            requestCounter[0] -= 1;
                        }
                    });
            requestQueue.add(currentRequest);

        }
        Collections.sort(warmCity, new CompareTemp());
        adapter = new WarmCityAdapter(this, warmCity);
        listView.setAdapter(adapter);
        Log.d("wc2", String.valueOf(warmCity));
        Log.d("wc2", "I was there");

    }
}
