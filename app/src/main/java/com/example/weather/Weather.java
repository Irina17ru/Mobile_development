package com.example.weather;

public class Weather {
    String date, tempNight, tempDay;
    int progressBar;

    public Weather(String date, String tempNight, int progressBar, String tempDay) {
        this.date = date;
        this.tempNight = tempNight;
        this.progressBar = progressBar;
        this.tempDay = tempDay;
    }
}
