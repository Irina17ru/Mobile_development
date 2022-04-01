package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.Locale;

public class ForecastSearch extends AppCompatActivity {
    EditText city;
    String res, latitude, longitude;
    double kelvin = (double) 273.15;
    double wind_speed, avgTempNight = 100, avgTempDay = -100;
    long temperatureCurrent;
    long temperatureDay;
    long temperatureNight;
    int humidity;
    int windDegree;
    private String apikey = "2aae981526c60ecfb04f462bc74013c5";
    private String urlCoordinate = "https://api.openweathermap.org/data/2.5/weather?q=";
    private String urlDaily = "https://api.openweathermap.org/data/2.5/onecall?";
    private String exclude = "&exclude=minutely,hourly,current";

    /*
    https://api.openweathermap.org/data/2.5/onecall?lat=52.2978&lon=-104.2964&exclude=minutely,hourly,current&appid=2aae981526c60ecfb04f462bc74013c5
    https://api.openweathermap.org/data/2.5/weather?q=иркутск&appid=2aae981526c60ecfb04f462bc74013c5
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_search);
        city = findViewById(R.id.forecast_input_city);
    }

    public void getWeatherForecast(View view) {
        final Intent intentForecast = new Intent(ForecastSearch.this, ForecastActivity.class);
        String cityName = city.getText().toString().trim();
        intentForecast.putExtra("cityname",cityName);
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        if (!cityName.isEmpty()) {
            String urlGetCityCoordinate = urlCoordinate + cityName + "&appid=" + apikey;
            StringRequest currentRequest = new StringRequest(Request.Method.POST, urlGetCityCoordinate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONObject jsonMain = jsonResponse.getJSONObject("main");
                        JSONObject jsonCoord = jsonResponse.getJSONObject("coord");
                        longitude = jsonCoord.getString("lon");
                        latitude = jsonCoord .getString("lat");
                        humidity = jsonMain.getInt("humidity");
                        temperatureCurrent = Math.round(jsonMain.getDouble("temp") - kelvin);
                        String flagCurrent = temperatureCurrent > 0 ? "+" : "";
                        wind_speed = (int) Math.round(jsonResponse.getJSONObject("wind").getDouble("speed"));
                        windDegree = jsonResponse.getJSONObject("wind").getInt("deg");

                        intentForecast.putExtra("temp_current",flagCurrent + String.valueOf(temperatureCurrent));
                        intentForecast.putExtra("humidity",humidity);
                        intentForecast.putExtra("speed",wind_speed);
                        intentForecast.putExtra("deg",windDegree);

                        String urlGetDaily = urlDaily + "lat=" + latitude + "&lon=" + longitude + exclude + "&appid=" + apikey;
                        StringRequest dailyRequest = new StringRequest(Request.Method.POST, urlGetDaily, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONArray jsonDaily = jsonResponse.getJSONArray("daily");
                                    temperatureDay = Math.round(jsonDaily.getJSONObject(0).getJSONObject("temp").getDouble("day") - kelvin);
                                    temperatureNight = Math.round(jsonDaily.getJSONObject(0).getJSONObject("temp").getDouble("night") - kelvin);
                                    String flagDayCurrent = temperatureDay > 0 ? "+" : "";
                                    String flagNightCurrent = temperatureNight > 0 ? "+" : "";

                                    for (int i = 1; i < jsonDaily.length(); i++) {
                                        JSONObject tempData = jsonDaily.getJSONObject(i);
                                        JSONObject tempDaily = tempData.getJSONObject("temp");
                                        long date = tempData.getLong("dt");
                                        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC);
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EE", new Locale("ru"));
                                        String formattedDate = dateTime.format(formatter);

                                        int ejectDay = (int) Math.round(tempDaily.getDouble("day") - kelvin);
                                        if (ejectDay > avgTempDay) {
                                            avgTempDay = ejectDay;
                                        }
                                        int ejectNight = (int) Math.round(tempDaily.getDouble("night") - kelvin);
                                        if (ejectNight < avgTempNight){
                                            avgTempNight = ejectNight;
                                        }
                                        intentForecast.putExtra("date" + String.valueOf(i),formattedDate);
                                        intentForecast.putExtra("day" + String.valueOf(i),ejectDay);
                                        intentForecast.putExtra("night" + String.valueOf(i),ejectNight);
                                    }
                                    intentForecast.putExtra("temp_day", flagDayCurrent + String.valueOf(temperatureDay));
                                    intentForecast.putExtra("temp_night", flagNightCurrent + String.valueOf(temperatureNight));
                                    intentForecast.putExtra("avg_day", avgTempDay);
                                    intentForecast.putExtra("avg_night", avgTempNight);
                                    startActivity(intentForecast);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        requestQueue.add(dailyRequest);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            requestQueue.add(currentRequest);
        }
    }
}

