package com.example.weatherapp.ui.fragment.map;

import com.example.weatherapp.network.NetworkClient;
import com.example.weatherapp.network.api.ApiWeather;
import com.example.weatherapp.network.api.responce.LatitudeLongitude;
import com.example.weatherapp.network.api.responce.region.PositionDescription;
import com.example.weatherapp.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragmentPresenter implements MapFragmentContract.Presenter {

    private MapFragmentContract.View view;

    private Call<List<LatitudeLongitude>> getPositionsApiCall;

    public MapFragmentPresenter(MapFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void onMapReady() {
        List<LatLng> locations = generateRandomPositions(50);
        view.addLocations(locations);

        view.hideLoading();
    }

    @Override
    public void onRefreshLocationsBtnClick(boolean checked) {
        if (checked){
            getRandomLocationsFromServer(50);
        }else {
            view.showLoading();
            List<LatLng> locations = generateRandomPositions(50);
            view.clearMap();
            view.addLocations(locations);
            view.hideLoading();
        }
    }

    private void getRandomLocationsFromServer(int quantity){
        Map<String, String> params = createGetPositionDescriptionRequestParams(quantity);

        if (getPositionsApiCall != null){
            getPositionsApiCall.cancel();
        }

        getPositionsApiCall = NetworkClient
                .getRetrofit(Constants.HTTP_URL_RANDOMLOCATION)
                .create(ApiWeather.class)
                .getRandomPositions(params);

        view.showLoading();

        getPositionsApiCall.enqueue(new Callback<List<LatitudeLongitude>>() {
            @Override
            public void onResponse(Call<List<LatitudeLongitude>> call, Response<List<LatitudeLongitude>> response) {
                if (response.body() != null){
                    onRandomLocationsFromServerReceived(response.body());
                }else {
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<List<LatitudeLongitude>> call, Throwable t) {
                view.hideLoading();
            }
        });
    }

    private void onRandomLocationsFromServerReceived(List<LatitudeLongitude> locationsFromServer){
        List<LatLng> locations = new ArrayList<>(locationsFromServer.size());

        for (LatitudeLongitude latitudeLongitude : locationsFromServer) {
            locations.add(new LatLng(
                    latitudeLongitude.getLatitude(),
                    latitudeLongitude.getLongitude()));
        }
        view.clearMap();
        view.addLocations(locations);

        view.hideLoading();
    }

    private Map<String, String> createGetPositionDescriptionRequestParams(int quantity){
        Map<String, String> params = new HashMap<>();

        params.put("quantity", String.valueOf(quantity));

        return params;
    }

    private List<LatLng> generateRandomPositions(int quantity){
        List<LatLng> locations = new ArrayList<LatLng>(quantity);

        for (int i = 0; i < quantity; i++){
            LatLng latLng = new LatLng(
                    getRandomDoubleInRange(Constants.LATITUDE_MIN, Constants.LATITUDE_MAX),
                    getRandomDoubleInRange(Constants.LONGITUDE_MIN, Constants.LONGITUDE_MAX)
            );
            locations.add(latLng);
        }

        return locations;
    }

    private double getRandomDoubleInRange(double min, double max){
        Random r = new Random();

        double randomValue = min + (max - min) * r.nextDouble();

        double truncatedDouble = BigDecimal.valueOf(randomValue)
                .setScale(6, RoundingMode.HALF_UP)
                .doubleValue();

        return truncatedDouble;
    }
}
