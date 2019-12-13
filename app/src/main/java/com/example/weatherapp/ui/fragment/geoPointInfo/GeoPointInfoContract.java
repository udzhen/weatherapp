package com.example.weatherapp.ui.fragment.geoPointInfo;

import android.app.Application;

import com.example.weatherapp.network.api.responce.region.PositionDescription;
import com.example.weatherapp.network.api.responce.weather.WeatherDetails;

public interface GeoPointInfoContract {
    interface View{
        void showWeatherDetails(WeatherDetails weatherDetails);
        void showWeatherDetailLoading();
        void showWeatherDetailsError();

        void showPositionDescription(PositionDescription positionDescription);
        void showPositionDescriptionLoading();
        void showPositionDescriptionError();

        Application getApplication();
    }

    interface Presenter{
        void onViewCreated();
    }
}
