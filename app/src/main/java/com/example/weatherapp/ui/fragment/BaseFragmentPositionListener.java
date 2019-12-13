package com.example.weatherapp.ui.fragment;

import com.example.weatherapp.ui.activity.MainActivity;

public abstract class BaseFragmentPositionListener extends BaseFragment {

    public MainActivity.OpenGeopointWeatherInfoListener openGeopointWeatherInfoListener;

    public BaseFragmentPositionListener() {
        super();
    }

    public BaseFragmentPositionListener(
            String FRAGMENT_TAG,
            MainActivity.OpenGeopointWeatherInfoListener openGeopointWeatherInfoListener) {
        super(FRAGMENT_TAG);
        this.openGeopointWeatherInfoListener = openGeopointWeatherInfoListener;
    }

}