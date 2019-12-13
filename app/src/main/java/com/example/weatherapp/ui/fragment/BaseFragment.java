package com.example.weatherapp.ui.fragment;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public String FRAGMENT_TAG;

    public BaseFragment() {
        super();
    }

    public BaseFragment(String FRAGMENT_TAG) {
        super();
        this.FRAGMENT_TAG = FRAGMENT_TAG;
    }

}
