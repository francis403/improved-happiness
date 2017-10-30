package com.example.gamethetown;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.gamethetown.fragment.SetTitle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.gamethetown.fragment.AddPhotoFragment;
import com.example.gamethetown.fragment.DescriptionFragment;
import com.example.gamethetown.fragment.DifficultyFragment;


public class CreateItenerary extends App_Menu implements OnMapReadyCallback {

    private MapFragment map;
    private Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        if(findViewById(R.id.map) != null){
            //map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            //map.getMapAsync(this);
            frag =  (Fragment) getFragmentManager().findFragmentById(R.id.map);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(map != null){
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.addMarker(new MarkerOptions()
                   .position(new LatLng(0, 0))
                   .title("Marker"));
        }
    }

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
        transaction.replace(R.id.add_func,dFrag);

        transaction.addToBackStack(null);
        transaction.commit();
    }

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
