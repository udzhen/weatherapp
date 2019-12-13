package com.example.weatherapp.network.api.responce.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDetails {

    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SerializedName("main")
    @Expose
    private Temperature temperature;

    @SerializedName("weather")
    @Expose
    private List<Weather> weathers;

    public WeatherDetails() {
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }
}
