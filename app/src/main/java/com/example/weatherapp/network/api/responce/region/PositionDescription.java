package com.example.weatherapp.network.api.responce.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PositionDescription {

    @SerializedName("results")
    @Expose
    private List<PositionDescriptionResult> results;


    public PositionDescription() {
    }

    public List<PositionDescriptionResult> getResults() {
        return results;
    }

    public void setResults(List<PositionDescriptionResult> results) {
        this.results = results;
    }
}
