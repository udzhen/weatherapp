package com.example.weatherapp.ui.fragment.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapFragmentContract {
    interface View{
        void addLocations(List<LatLng> locations);

        void showLoading();
        void hideLoading();

        void clearMap();
    }

    interface Presenter{
        void onMapReady();
        void onRefreshLocationsBtnClick(boolean checked);
    }
}
