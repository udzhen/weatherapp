package com.example.weatherapp.ui.fragment.geoPointInfo;

import com.example.weatherapp.dao.VisitedPlaceViewModel;
import com.example.weatherapp.models.VisitedPlace;
import com.example.weatherapp.network.NetworkClient;
import com.example.weatherapp.network.api.ApiWeather;
import com.example.weatherapp.network.api.responce.region.PositionDescription;
import com.example.weatherapp.network.api.responce.weather.WeatherDetails;
import com.example.weatherapp.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeoPointInfoFragmentPresenter implements GeoPointInfoContract.Presenter {

    private GeoPointInfoContract.View view;
    private LatLng position;

    private Call<WeatherDetails> getWeatherDetailsApiCall;
    private Call<PositionDescription> getPositionDescriptionApiCall;

    public GeoPointInfoFragmentPresenter(GeoPointInfoContract.View view, LatLng position) {
        this.view = view;
        this.position = position;
    }

    @Override
    public void onViewCreated() {
        getPositionDescription(position);
        getWeatherDetails(position);
    }

    private void getPositionDescription(LatLng position){
        Map<String, String> params = createGetPositionDescriptionRequestParams(position);

        if (getPositionDescriptionApiCall != null){
            getPositionDescriptionApiCall.cancel();
        }

        getPositionDescriptionApiCall = NetworkClient
                .getRetrofit(Constants.HTTP_URL_OPENCAGEDATA)
                .create(ApiWeather.class)
                .getPositionDescription(params);

        view.showPositionDescriptionLoading();
        getPositionDescriptionApiCall.enqueue(new Callback<PositionDescription>() {
            @Override
            public void onResponse(Call<PositionDescription> call, Response<PositionDescription> response) {
                PositionDescription positionDescription = response.body();
                if (positionDescription != null){
                    view.showPositionDescription(positionDescription);
                    saveOrUpdateVisitedPlace(positionDescription, position);
                }else {
                    view.showPositionDescriptionError();
                }
            }

            @Override
            public void onFailure(Call<PositionDescription> call, Throwable t) {
                view.showPositionDescriptionError();
            }
        });
    }

    private void saveOrUpdateVisitedPlace(PositionDescription positionDescription, LatLng position){
        VisitedPlaceViewModel visitedPlaceViewModel = new VisitedPlaceViewModel(view.getApplication());

        new Thread(() -> {
            VisitedPlace visitedPlace =
                    visitedPlaceViewModel.getByLatLon(position.latitude, position.longitude);

            if (visitedPlace != null){
                visitedPlace.lastSeenTime = System.currentTimeMillis()/1000;

                visitedPlaceViewModel.updateVisitedPlace(visitedPlace);
            }else {
                visitedPlace = new VisitedPlace();

                visitedPlace.lastSeenTime = System.currentTimeMillis()/1000;
                visitedPlace.latitude = position.latitude;
                visitedPlace.longitude = position.longitude;
                visitedPlace.placeName = positionDescription.getResults().get(0).getRegionName();

                visitedPlaceViewModel.addVisitedPlace(visitedPlace);
            }

        }).start();
    }

    private void getWeatherDetails(LatLng position){
        Map<String, String> params = createShortWeatherRequestParams(position);

        if (getWeatherDetailsApiCall != null){
            getWeatherDetailsApiCall.cancel();
        }

        getWeatherDetailsApiCall = NetworkClient
                .getRetrofit(Constants.HTTP_URL_OPENWEATHERMAP)
                .create(ApiWeather.class)
                .getWeatherDetails(params);

        view.showWeatherDetailLoading();
        getWeatherDetailsApiCall.enqueue(new Callback<WeatherDetails>() {
            @Override
            public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {
                WeatherDetails weatherDetails = response.body();
                if (weatherDetails != null){
                    view.showWeatherDetails(weatherDetails);
                }else {
                    view.showWeatherDetailsError();
                }
            }

            @Override
            public void onFailure(Call<WeatherDetails> call, Throwable t) {
                view.showWeatherDetailsError();
            }
        });
    }

    private Map<String, String> createGetPositionDescriptionRequestParams(LatLng position){
        Map<String, String> params = new HashMap<>();

        params.put("key", Constants.API_KEY_OPENCAGEDATA);
        params.put("q", new StringBuilder()
                .append(position.latitude)
                .append(", ")
                .append(position.longitude)
                .toString());

        return params;
    }

    private Map<String, String> createShortWeatherRequestParams(LatLng position){
        Map<String, String> params = new HashMap<>();

        params.put("lat", String.valueOf(position.latitude));
        params.put("lon", String.valueOf(position.longitude));
        params.put("APPID", Constants.API_KEY_OPENWEATHERMAP);

        return params;
    }

}
