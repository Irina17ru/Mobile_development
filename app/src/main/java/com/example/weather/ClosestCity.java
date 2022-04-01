package com.example.weather;
import java.io.Serializable;

public class ClosestCity implements Serializable {
    String name;
    String distance;

    public ClosestCity(String name, String distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getCityName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

}