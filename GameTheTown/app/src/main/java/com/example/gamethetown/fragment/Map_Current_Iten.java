package com.example.gamethetown.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.gameControllers.HotspotQuiz;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.Map;

//TODO -> Passar para o fuse
public class Map_Current_Iten extends App_Menu implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {


    private static final float DISTANCE_TO_TARGET = 10000 ; //em metros
    private static final int END_OF_GAME = 1;

    private GoogleMap mMap;
    private Location target;
    private Itinerary currentItinerary;
    private Hotspot hot;
    private Boolean buzzed = false,closeEnough = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__current__iten);

        View completedItin = getView(R.layout.layout_on_completed_itinerary);

        //por agora ainda nao vamos buscar nada (porque nao estamos a meter nada la)
        currentItinerary = user.getCurrentItinerary();
        hot = user.getCurrentHotspot();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
        /**
        for(Hotspot hot: currentItinerary.getHotSpotList()){
            mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
        }
         **/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hot.getPosition()));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(final Marker marker) {
                if(closeEnough)
                    bottomSheetUp(R.layout.layout_go_to_hotspot);
                return true;
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap.setMyLocationEnabled(true);
        // use com.google.android.gms.location.FusedLocationProviderApi
        mMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Caso ja tenhamos terminado
        if(hot == null)
            return;
        closeEnough = location.distanceTo(hot.getLocation()) < DISTANCE_TO_TARGET;
        if(closeEnough && !buzzed) {
            // Vibrate for 500 milliseconds
            buzzed = true;
            v.vibrate(500);
            if(hot.getGame() != null) {
                bottomSheetUp(R.layout.layout_go_to_hotspot);
                //goToGame(this.getCurrentFocus());
            }
        }

    }
    //TODO -> vai ser um start activity for result
    public void goToGame(View v){
        Intent intent = new Intent(this, hot.getGame().getGameClass());
        intent.putExtra("game",hot.getGame());
        startActivityForResult(intent,END_OF_GAME);
        bsd.hide();
    }

    public void cancel(View v){
        mMap.clear();
        hot = user.nextHotspot();
        if (hot != null) {
            mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
        } else {
            View endOfIten = bottomSheetUp(R.layout.layout_on_completed_itinerary);
        }
        bsd.hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        Bundle extras = data.getExtras();
        if (requestCode == END_OF_GAME) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                boolean passed = extras.getBoolean("passed"),answeared = extras.getBoolean("answeared"); //vamos aqui ver se fez bem

                buzzed = false;
                if(answeared) {
                    mMap.clear(); //duvuda
                    hot = user.nextHotspot();
                    if (hot != null) {
                        mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
                    } else {
                        View endOfIten = bottomSheetUp(R.layout.layout_on_completed_itinerary);
                    }
                }
            }
        }
    }


}
