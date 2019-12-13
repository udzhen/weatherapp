package com.example.weatherapp.network.api.responce.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PositionDescriptionResult {

    @SerializedName("formatted")
    @Expose
    private String regionName;


    public PositionDescriptionResult() {
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
