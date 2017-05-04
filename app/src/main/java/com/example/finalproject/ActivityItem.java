package com.example.finalproject;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

/**
 * Created by panda_000 on 5/4/2017.
 */

public class ActivityItem {
    private String name;
    private double difficulty;
    private ArrayList<DataPoint> series;

    public ActivityItem(String n, double d){
        name = n;
        difficulty = d;
    }

    public void addPoint(long datetime){
        DataPoint point = new DataPoint(series.size(),1);
        series.add(point);
    }

    public String getName(){
        return name;
    }
}
