package com.example.weather;

import android.util.Log;

import java.util.Comparator;

public class CompareTemp implements Comparator<WarmCity> {
    public int compare(WarmCity a, WarmCity b) {
        int cmp = a.getTemp() > b.getTemp() ? +1 : a.getTemp() < b.getTemp() ? -1 : 0;
        return cmp;
    }
}
