package com.example.weatherapp.network.api;

import com.example.weatherapp.network.api.responce.LatitudeLongitude;
import com.example.weatherapp.network.api.responce.region.PositionDescription;
import com.example.weatherapp.network.api.responce.weather.WeatherDetails;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiWeather {

    @GET("/geocode/v1/json")
    Call<PositionDescription> getPositionDescription(@QueryMap Map<String, String> params);

    @GET("/data/2.5/weather")
    Call<WeatherDetails> getWeatherDetails(@QueryMap Map<String, String> params);

    @GET("/generateRandomPositions")
    Call<List<LatitudeLongitude>> getRandomPositions(@QueryMap Map<String, String> params);
}
