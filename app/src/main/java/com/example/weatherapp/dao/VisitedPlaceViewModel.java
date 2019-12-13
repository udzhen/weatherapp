package com.example.weatherapp.dao;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.weatherapp.models.VisitedPlace;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class VisitedPlaceViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public VisitedPlaceViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
    }

    public VisitedPlace getByLatLon(double latitude, double longitude) {
        return appDatabase.employeeDao().getByLatLon(latitude, longitude);
    }

    public Flowable<List<VisitedPlace>> getAll() {
        return appDatabase.employeeDao().getAll();
    }

    public long addVisitedPlace(VisitedPlace visitedPlace) {
        return appDatabase.employeeDao().insert(visitedPlace);
    }

    public void updateVisitedPlace(VisitedPlace visitedPlace) {
        appDatabase.employeeDao().update(visitedPlace);
    }
}