package com.example.weatherapp.network.api.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatitudeLongitude {

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;


    public LatitudeLongitude() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
