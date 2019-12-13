package com.example.weatherapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "visitedPlace")
public class VisitedPlace {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "place_name")
    @NonNull
    public String placeName;

    @ColumnInfo(name = "latitude")
    @NonNull
    public double latitude;

    @ColumnInfo(name = "longitude")
    @NonNull
    public double longitude;

    @ColumnInfo(name = "last_seen_time")
    @NonNull
    public long lastSeenTime;
}
