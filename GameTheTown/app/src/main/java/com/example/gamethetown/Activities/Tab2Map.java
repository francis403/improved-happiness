package com.example.gamethetown.Activities;

/**
 * Created by franc on 31/10/2017.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamethetown.Catalogs.ItineraryCatalog;
import com.example.gamethetown.Dialogs.AlertDialogs.SelectItineraryDialog;
import com.example.gamethetown.R;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO -> passar por todos os pontos e metelos no mapa
public class Tab2Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Itinerary> itineraries = new ArrayList<>();
    private HashMap<String, Itinerary> mapItineraries = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2map, container, false);

        itineraries = new ItineraryCatalog().getItinerariesFromDatabase();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(Itinerary i : itineraries){
            Hotspot h  = i.getFirstHotspot();
            Marker m = mMap.addMarker(new MarkerOptions().position(h.getPosition()).title(i.getName()));
            mapItineraries.put(m.getId(),i);
        }

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(final Marker marker) {
                final Itinerary iten = mapItineraries.get(marker.getId());
                AlertDialog.Builder aBuilder = new SelectItineraryDialog(getContext(),iten);
                aBuilder.show();
                return true;
            }
        });

        mMap.setMyLocationEnabled(true);

    }
}
