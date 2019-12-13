package com.example.weatherapp.ui.fragment.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Switch;

import androidx.cardview.widget.CardView;

import com.example.weatherapp.ui.activity.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.ui.fragment.BaseFragmentPositionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends BaseFragmentPositionListener implements MapFragmentContract.View {

    public static final String FRAGMENT_MAP_TAG = "FRAGMENT_MAP_TAG";

    private MapView mapView;
    private GoogleMap googleMap;

    @BindView(R.id.btn_refresh_locations)
    FloatingActionButton refreshLocationsBtn;

    @BindView(R.id.card_refresh_locations)
    CardView refreshLocationsCard;

    @BindView(R.id.pb_refresh_locations)
    ProgressBar  refreshLocationsPb;

    @BindView(R.id.switch_locations_source)
    Switch locationsSourceSwitch;


    private MapFragmentContract.Presenter presenter;

    public MapFragment(MainActivity.OpenGeopointWeatherInfoListener
                               openGeopointWeatherInfoListener) {
        super(FRAGMENT_MAP_TAG, openGeopointWeatherInfoListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(onMapReadyCallback);
        }

        presenter = new MapFragmentPresenter(MapFragment.this);

        initViews();
    }

    private void initViews(){
        refreshLocationsBtn.setOnClickListener(v ->
                presenter.onRefreshLocationsBtnClick(locationsSourceSwitch.isChecked()));
    }

    private OnMapReadyCallback onMapReadyCallback = googleMap -> {
        this.googleMap = googleMap;

        MapsInitializer.initialize(getActivity());

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(true);

        presenter.onMapReady();

        googleMap.setOnMarkerClickListener(marker -> {
            openGeopointWeatherInfoListener.onOpenGeopointWeatherInfo(marker.getPosition());
            return true;
        });
    };

    @Override
    public void addLocations(List<LatLng> locations) {
        for (LatLng latLng: locations) {
            googleMap.addMarker(new MarkerOptions().position(latLng));
        }
    }

    @Override
    public void showLoading() {
        refreshLocationsCard.setVisibility(View.VISIBLE);
        refreshLocationsPb.setIndeterminate(true);
    }

    @Override
    public void hideLoading() {
        refreshLocationsCard.setVisibility(View.GONE);
        refreshLocationsPb.setIndeterminate(false);
    }

    @Override
    public void clearMap() {
        googleMap.clear();
    }

}