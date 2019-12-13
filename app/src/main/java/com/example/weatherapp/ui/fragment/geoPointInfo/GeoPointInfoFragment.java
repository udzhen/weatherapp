package com.example.weatherapp.ui.fragment.geoPointInfo;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.network.api.responce.region.PositionDescription;
import com.example.weatherapp.network.api.responce.weather.Temperature;
import com.example.weatherapp.network.api.responce.weather.Weather;
import com.example.weatherapp.network.api.responce.weather.WeatherDetails;
import com.example.weatherapp.ui.fragment.BaseFragment;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeoPointInfoFragment extends BaseFragment implements GeoPointInfoContract.View {

    public static String FRAGMENT_GEOPOINT_INFO_TAG = "FRAGMENT_GEOPOINT_INFO_TAG";

    private GeoPointInfoContract.Presenter presenter;

    private LatLng position;


    @BindView(R.id.tv_region_name)
    TextView regionName;

    @BindView(R.id.pb_region_name_loading)
    ProgressBar regionNameLoadingPb;

    @BindView(R.id.pb_region_loading_container)
    LinearLayout regionNameLoadingLL;


    @BindView(R.id.tv_temperature)
    TextView temperatureTV;

    @BindView(R.id.tv_weather_condition_main)
    TextView weatherConditionMainTV;

    @BindView(R.id.tv_weather_condition_description)
    TextView weatherConditionDescriptionTV;

    @BindView(R.id.tv_presure)
    TextView presureTV;

    @BindView(R.id.tv_wind_speed)
    TextView windSpeedTV;

    @BindView(R.id.pb_weather_loading)
    ProgressBar weatherLoadingPb;

    @BindView(R.id.pb_weather_loading_container)
    LinearLayout weatherLoadingLL;

    @BindView(R.id.weather_container_ll)
    LinearLayout weatherContainerLL;


    public GeoPointInfoFragment(LatLng position) {
        super(new StringBuilder()
                .append(FRAGMENT_GEOPOINT_INFO_TAG)
                .append(position.latitude)
                .append(position.longitude)
                .toString());

        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geopoint_info, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter = new GeoPointInfoFragmentPresenter(GeoPointInfoFragment.this, position);
        presenter.onViewCreated();
    }

    @Override
    public void showWeatherDetails(WeatherDetails weatherDetails) {
        double temperature = BigDecimal
                .valueOf(weatherDetails.getTemperature().getTemperature() - 273.15)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        temperatureTV.setText(
                new StringBuilder()
                .append("Temperature: ")
                .append(temperature)
                .append("°C")
                .toString());

        weatherConditionMainTV.setText(weatherDetails.getWeathers().get(0).getMain());
        weatherConditionDescriptionTV.setText(weatherDetails.getWeathers().get(0).getDescription());

        presureTV.setText(new StringBuilder()
                .append("Presure: ")
                .append(weatherDetails.getTemperature().getPressure())
                .append(" hPa")
                .toString());

        windSpeedTV.setText(new StringBuilder()
                .append("Wind speed: ")
                .append(weatherDetails.getWind().getSpeed())
                .append(" meter/sec"));

        hideWeatherDetailLoading();

        weatherContainerLL.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWeatherDetailLoading() {
        weatherContainerLL.setVisibility(View.GONE);
        weatherLoadingPb.setIndeterminate(true);
        weatherLoadingLL.setVisibility(View.VISIBLE);
    }

    public void hideWeatherDetailLoading() {
        weatherLoadingPb.setIndeterminate(false);
        weatherLoadingLL.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherDetailsError() {
        hideWeatherDetailLoading();
    }

    @Override
    public void showPositionDescription(PositionDescription positionDescription) {
        regionName.setText(positionDescription.getResults().get(0).getRegionName());
        regionName.setVisibility(View.VISIBLE);
        hidePositionDescriptionLoading();
    }

    @Override
    public void showPositionDescriptionLoading() {
        regionNameLoadingPb.setIndeterminate(true);
        regionNameLoadingLL.setVisibility(View.VISIBLE);
    }

    public void hidePositionDescriptionLoading() {
        regionNameLoadingPb.setIndeterminate(false);
        regionNameLoadingLL.setVisibility(View.GONE);
    }

    @Override
    public void showPositionDescriptionError() {
        hidePositionDescriptionLoading();
        regionName.setText(getResources().getString(R.string.error_getting_region));
    }

    @Override
    public Application getApplication() {
        return getActivity().getApplication();
    }

}
