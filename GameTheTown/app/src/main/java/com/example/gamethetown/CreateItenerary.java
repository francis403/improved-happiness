package com.example.gamethetown;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.gamethetown.fragment.SetTitle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.gamethetown.fragment.AddPhotoFragment;
import com.example.gamethetown.fragment.DescriptionFragment;
import com.example.gamethetown.fragment.DifficultyFragment;


public class CreateItenerary extends App_Menu implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                onCreateInitialLocation(view);
            }
        });
    }
    //TODO -> Mudar para um create
    public void onCreateInitialLocation(View view){
        BottomSheetDialog bsd = new BottomSheetDialog(this);
        View sView = getLayoutInflater().inflate(R.layout.layout_create_itinerary,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
        bsd.show();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("New Marker");
                mMap.addMarker(marker);
                System.out.println(point.latitude + "---" + point.longitude);
            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap.setMyLocationEnabled(true);

    }
    /*
    //set title
    public void setTitle(View view){
        SetTitle st = new SetTitle();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.add_func,st);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setDescription(View view){
        DescriptionFragment dFrag = new DescriptionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.add_func,dFrag);

        transaction.addToBackStack(null);
        transaction.commit();
    }
    */
    public void setDifficulty(View view){
        DifficultyFragment dFrag = new DifficultyFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map,dFrag);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setPhoto(View view){
        AddPhotoFragment dFrag = new AddPhotoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map,dFrag);

        transaction.addToBackStack(null);
        transaction.commit();
    }

}
