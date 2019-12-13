package com.example.weatherapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.VisitedPlace;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VisitedPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VISITED_PLACE_TYPE = 6548;
    private List<VisitedPlace> visitedPlaces;
    private OnItemVisitedPlaceClickListener onItemVisitedPlaceClickListener;

    public VisitedPlaceAdapter(List<VisitedPlace> visitedPlaces,
                               OnItemVisitedPlaceClickListener onItemVisitedPlaceClickListener) {
        this.visitedPlaces = visitedPlaces;
        this.onItemVisitedPlaceClickListener = onItemVisitedPlaceClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return VISITED_PLACE_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_visited_place, parent, false);

        return new VisitedPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VisitedPlaceViewHolder) {
            VisitedPlaceViewHolder visitedPlaceViewHolder = (VisitedPlaceViewHolder) holder;
            VisitedPlace visitedPlace = visitedPlaces.get(position);
            visitedPlaceViewHolder.bindView(visitedPlace);
        }
    }

    @Override
    public int getItemCount() {
        return visitedPlaces.size();
    }


    private class VisitedPlaceViewHolder extends RecyclerView.ViewHolder {
        public TextView latitudeTV;
        public TextView longitudeTV;
        public TextView placeNameTV;
        public LinearLayout cardVisitedPlaceRootLL;

        public VisitedPlaceViewHolder(View itemView) {
            super(itemView);

            latitudeTV = (TextView) itemView.findViewById(R.id.tv_place_latitude);
            longitudeTV = (TextView) itemView.findViewById(R.id.tv_place_longitude);
            placeNameTV = (TextView) itemView.findViewById(R.id.tv_place_name);
            cardVisitedPlaceRootLL = (LinearLayout) itemView.findViewById(R.id.card_visited_place_root);
        }

        public void bindView(VisitedPlace visitedPlace) {
            cardVisitedPlaceRootLL.setOnClickListener(v ->
                    onItemVisitedPlaceClickListener.onItemVisitedPlaceClick(visitedPlace));

            placeNameTV.setText(visitedPlace.placeName);

            latitudeTV.setText(new StringBuilder()
                    .append("Latitude: ")
                    .append(visitedPlace.latitude));

            longitudeTV.setText(new StringBuilder()
                    .append("Longitude: ")
                    .append(visitedPlace.longitude));
        }

    }

    public interface OnItemVisitedPlaceClickListener{
        void onItemVisitedPlaceClick(VisitedPlace visitedPlace);
    }

}