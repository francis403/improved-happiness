package com.example.gamethetown.Activities;

/**
 * Created by franc on 31/10/2017.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamethetown.R;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

//TODO -> passar por todos os pontos e metelos no mapa
public class Tab2Map extends Fragment implements OnMapReadyCallback {

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private ArrayList<Itinerary> itineraries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2map, container, false);

        Itinerary itinerary = new Itinerary("Hotspot test",new Date(13,10,10),R.drawable.no_image);
        itinerary.addHotspot(new Hotspot("teste",null,new LatLng(38.757145, -9.157641)));
        itineraries.add(itinerary);
        itinerary = new Itinerary("Hotspot test2",new Date(13,10,10),R.drawable.no_image);
        itinerary.addHotspot(new Hotspot("teste2",null,new LatLng(40, -9.157641)));

        itineraries.add(itinerary);

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
            mMap.addMarker(new MarkerOptions().position(h.getPosition()).title(i.getTitle()));
        }

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        mMap.setMyLocationEnabled(true);
        //Location myLoc = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLoc.getLatitude(),myLoc.getLongitude())));

    }

}
