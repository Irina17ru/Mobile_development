package com.example.weather;

public class WarmCity {
    String name;
    int temp;

    public WarmCity(String name, int temp) {
        this.name = name;
        this.temp = temp;
    }

    public String getCityName() {
        return name;
    }

    public int getTemp() {
        return temp;
    }

}