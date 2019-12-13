package com.example.weatherapp.ui.fragment.historyList;

import android.util.Log;

import com.example.weatherapp.dao.VisitedPlaceViewModel;
import com.example.weatherapp.models.VisitedPlace;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryListFragmentPresenter implements HistoryListContract.Presenter {

    private HistoryListContract.View view;

    private CompositeDisposable compositeDisposable = null;

    private List<VisitedPlace> visitedPlaces = new ArrayList<>();

    public HistoryListFragmentPresenter(HistoryListContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewCreated() {
        view.initListView(visitedPlaces);

        VisitedPlaceViewModel visitedPlaceViewModel = new VisitedPlaceViewModel(view.getApplication());

        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }

        Disposable disposable = visitedPlaceViewModel
                .getAll()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        visitedPlaces -> onVisitedPlaceListChanged(visitedPlaces),
                        e -> Log.e("RoomWithRx", e.getMessage()));

        compositeDisposable.add(disposable);
    }

    private void onVisitedPlaceListChanged(List<VisitedPlace> visitedPlaces){
        this.visitedPlaces.clear();
        this.visitedPlaces.addAll(visitedPlaces);
        view.notifyListChanged();
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }

}
