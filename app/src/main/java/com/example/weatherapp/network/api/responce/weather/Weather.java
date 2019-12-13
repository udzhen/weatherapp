package com.example.weatherapp.network.api.responce.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    @Expose
    private String main;

    @SerializedName("description")
    @Expose
    private String description;


    public Weather() {
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
