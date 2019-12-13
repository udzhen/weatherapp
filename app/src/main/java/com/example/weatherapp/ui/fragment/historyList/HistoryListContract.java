package com.example.weatherapp.ui.fragment.historyList;

import android.app.Application;

import com.example.weatherapp.models.VisitedPlace;

import java.util.List;

public interface HistoryListContract {

    interface View{
        Application getApplication();
        void initListView(List<VisitedPlace> visitedPlaces);
        void notifyListChanged();
    }

    interface Presenter{
        void onViewCreated();
        void onDestroyView();
    }

}
