package com.example.weatherapp.dao;

import androidx.lifecycle.AndroidViewModel;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherapp.models.VisitedPlace;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface VisitedPlaceDao{

    @Query("SELECT * FROM visitedPlace order by visitedPlace.last_seen_time desc")
    Flowable<List<VisitedPlace>> getAll();

    @Query("SELECT * FROM visitedPlace WHERE id = :id")
    VisitedPlace getById(long id);

    @Query("SELECT * FROM visitedPlace WHERE latitude = :latitude AND longitude = :longitude")
    VisitedPlace getByLatLon(double latitude, double longitude);

    @Insert
    long insert(VisitedPlace visitedPlace);

    @Update
    void update(VisitedPlace visitedPlace);

    @Delete
    void delete(VisitedPlace visitedPlace);
}
