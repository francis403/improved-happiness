package com.example.gamethetown.Activities;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.Dialogs.AlertDialogs.SelectItineraryDialog;
import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class Tab2Map extends Fragment implements OnMapReadyCallback {

    private ItineraryDatabaseConnection idc = new ItineraryDatabaseConnection();
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private GoogleMap mMap;
    private HashMap<String, Itinerary> mapItineraries = new HashMap<>();
    private Location lastKnownLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2map, container, false);
        Log.e("Created view","Creates map");
        //TODO -> to test
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations())
                    lastKnownLocation = location;
                Log.e("Location changed","Location changed");
            }
        };
        mFusedLocationClient = LocationServices.
                getFusedLocationProviderClient(Tab2Map.this.getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;

    }

    public Location getLocation(){return lastKnownLocation;}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        idc.allItinerariesIntoMap(mMap,mapItineraries);

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lastKnownLocation = location;
                            mMap.moveCamera(CameraUpdateFactory.
                                    newLatLngZoom(new LatLng(
                                            location.getLatitude(),
                                            location.getLongitude()), 14));
                        }
                    }
                });

    }

    public GoogleMap getMap(){return mMap;}
    public Map getMapList(){return mapItineraries;}

}
