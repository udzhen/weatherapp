package com.example.weatherapp.ui.fragment.historyList;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.models.VisitedPlace;
import com.example.weatherapp.ui.activity.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.dao.VisitedPlaceViewModel;
import com.example.weatherapp.ui.adapter.VisitedPlaceAdapter;
import com.example.weatherapp.ui.fragment.BaseFragmentPositionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryListFragment extends BaseFragmentPositionListener implements HistoryListContract.View{

    public static String FRAGMENT_HISTORY_LIST_TAG = "FRAGMENT_HISTORY_LIST_TAG";

    private HistoryListContract.Presenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private VisitedPlaceAdapter visitedPlaceAdapter;
    private LinearLayoutManager layoutManager;


    public HistoryListFragment(MainActivity.OpenGeopointWeatherInfoListener
                                       openGeopointWeatherInfoListener) {
        super(FRAGMENT_HISTORY_LIST_TAG, openGeopointWeatherInfoListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter = new HistoryListFragmentPresenter(this);
        presenter.onViewCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public Application getApplication() {
        return getActivity().getApplication();
    }

    @Override
    public void initListView(List<VisitedPlace> visitedPlaces) {
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        visitedPlaceAdapter = new VisitedPlaceAdapter(visitedPlaces, visitedPlace ->
                openGeopointWeatherInfoListener.onOpenGeopointWeatherInfo(
                        new LatLng(visitedPlace.latitude, visitedPlace.longitude)));

        recyclerView.setAdapter(visitedPlaceAdapter);

        visitedPlaceAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyListChanged() {
        visitedPlaceAdapter.notifyDataSetChanged();
    }
}
