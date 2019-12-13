package com.example.weatherapp.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherapp.models.VisitedPlace;

@Database(entities = {VisitedPlace.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "database").build();
        }
        return instance;
    }

    public abstract VisitedPlaceDao employeeDao();

}
