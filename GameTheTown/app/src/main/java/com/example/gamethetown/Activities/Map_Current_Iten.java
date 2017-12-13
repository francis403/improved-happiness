package com.example.gamethetown.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Database.UserDatabaseConnection;
import com.example.gamethetown.R;
import com.example.gamethetown.item.Hotspot;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

//TODO -> Passar para o fuse
public class Map_Current_Iten extends App_Menu implements OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener {


    private static final float DISTANCE_TO_TARGET = 10000 ; //em metros
    private static final int END_OF_GAME = 1;
    private static final int REQUEST_CHECK_SETTINGS = 0;

    private GoogleMap mMap;
    private Hotspot hot;
    private Boolean buzzed = false,closeEnough = false;

    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__current__iten);
        //setDefaultUser();
        View completedItin = getView(R.layout.layout_no_itinerary);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        hot = user.getCurrentHotspot();
        //nao temos nenhum intenerario entao
        if(hot == null)
            bottomSheetUp(R.layout.layout_no_itinerary);
        else
            hot.getGame().startGame();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
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
        boolean gps_enabled;
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {
            gps_enabled = false;
        }

        if(!gps_enabled){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().
                    getString(R.string.open_location_settings)
                    , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Map_Current_Iten.this.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                }
            });
            dialog.show();
        }

        //caso nao tenhmamos nada para fazer
        if(hot != null) {
            mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hot.getPosition()));
        }
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
            return;
        }
        mMap.setMyLocationEnabled(true);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mMap.moveCamera(CameraUpdateFactory.
                                    newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));
                        }
                    }
                });


        // use com.google.android.gms.location.FusedLocationProviderApi
        mMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Caso nao tenhamos nada para fazer
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

    public void goToGame(View v){
        Intent intent = new Intent(this, hot.getGame().getGameClass());
        intent.putExtra("game", hot.getGame());
        startActivityForResult(intent, END_OF_GAME);
        bsd.hide();
    }

    public void completeIten(){
        Intent intent = new Intent(this, onCompleteItinerary.class);
        startActivity(intent);
        finish();
    }

    public void cancel(View v){
        mMap.clear();
        hot = user.nextHotspot();
        if (hot != null) {
            mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
            hot.getGame().startGame();
        } else {
            completeIten();
        }
        bsd.hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Finished game","Finished game");
        // Check which request we're responding to
        Bundle extras = data.getExtras();
        if (requestCode == END_OF_GAME) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.e("Finished game","Everything went ok");
                boolean passed = extras.getBoolean("passed"),
                        answeared = extras.getBoolean("answeared");
                Log.e("answeard game","Answeard = " + answeared);
                buzzed = false;
                if(answeared) {
                    if(passed) {
                        if(extras.containsKey("score"))
                            user.addScoreToItinerary(extras.getDouble("score"));
                        else
                            user.addScoreToItinerary(hot.getGame().getScore());
                    }
                    mMap.clear();
                    hot = user.nextHotspot();
                    if (hot != null) {
                        mMap.addMarker(new MarkerOptions().position(hot.getPosition()).title(hot.getTitle()));
                        hot.getGame().startGame();

                    } else
                        completeIten();
                }
            }
        }
    }


}
