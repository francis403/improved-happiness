package com.example.gamethetown.Activities;

/**
 * Created by franc on 31/10/2017.
 */

import android.Manifest;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamethetown.Catalogs.ItineraryCatalog;
import com.example.gamethetown.Catalogs.UserCatalog;
import com.example.gamethetown.Dialogs.AlertDialogs.SelectItineraryDialog;
import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;
import com.example.gamethetown.games.ImagePuzzle;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.games.Race;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;
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
            Marker m = mMap.addMarker(new MarkerOptions().position(h.getPosition()).title(i.getTitle()));
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
