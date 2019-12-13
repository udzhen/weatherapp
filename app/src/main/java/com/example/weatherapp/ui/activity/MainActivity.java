package com.example.weatherapp.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.weatherapp.R;
import com.example.weatherapp.dao.AppDatabase;
import com.example.weatherapp.ui.fragment.BaseFragment;
import com.example.weatherapp.ui.fragment.geoPointInfo.GeoPointInfoFragment;
import com.example.weatherapp.ui.fragment.historyList.HistoryListFragment;
import com.example.weatherapp.ui.fragment.map.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_map)
    Button btnMap;

    @BindView(R.id.btn_history_list)
    Button btnHistoryList;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private OpenGeopointWeatherInfoListener openGeopointWeatherInfoListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();

        showMapFragment();
    }

    private void initViews(){
        btnMap.setOnClickListener(v -> showMapFragment());

        btnHistoryList.setOnClickListener(v -> showHistoryListFragment());

        openGeopointWeatherInfoListener = position -> showGeoPointInfoFragment(position);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            System.out.println();

            switch (item.getItemId()){
                case R.id.action_map:
                    showMapFragment();
                    return true;
                case R.id.action_history:
                    showHistoryListFragment();
                    return true;
            }

            return false;
        });

        initBackStackListen();
    }

    private void showMapFragment(){
        showFragment(new MapFragment(openGeopointWeatherInfoListener));
    }

    private void showHistoryListFragment(){
        showFragment(new HistoryListFragment(openGeopointWeatherInfoListener));
    }

    private void showGeoPointInfoFragment(LatLng position){
        showFragment(new GeoPointInfoFragment(position));
    }

    private void showFragment(BaseFragment baseFragment){
        hideFragments();

        Fragment fr = getSupportFragmentManager().findFragmentByTag(baseFragment.FRAGMENT_TAG);
        if (fr == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_fragments, baseFragment, baseFragment.FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .show(fr)
                    .commit();
        }
    }

    private void hideFragments(){
        for (Fragment fragment : getSupportFragmentManager().getFragments()){
            getSupportFragmentManager().beginTransaction()
                    .hide(fragment)
                    .commit();
        }
    }

    private void initBackStackListen(){
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getSupportFragmentManager().getFragments().size();
            Fragment fragment = getSupportFragmentManager().getFragments().get(count - 1);

            if (fragment != null){
                getSupportFragmentManager().beginTransaction()
                        .show(fragment)
                        .commit();
            }

        });
    }


    public interface OpenGeopointWeatherInfoListener {
        void onOpenGeopointWeatherInfo(LatLng position);
    }
}
