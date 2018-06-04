package com.akexorcist.googledirection.sample;

/**
 * Created by octavian on 5/27/2018.
 */

public class City {
    private double longitude;
    private double latitude;
    private String name;

    public City(double longitude, double latitude,String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name+ "("+latitude+","+longitude+")";
    }
}
