package com.example.weatherapp.network.api.responce.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("temp")
    @Expose
    private double temperature; //Kelvin

    @SerializedName("pressure")
    @Expose
    private double pressure; //hPa


    public Temperature() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }


}
