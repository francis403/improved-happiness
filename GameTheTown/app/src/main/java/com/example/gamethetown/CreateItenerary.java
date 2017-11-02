package com.example.gamethetown;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.gamethetown.games.Hotspot_Quiz_Creator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.gamethetown.fragment.AddPhotoFragment;
import com.example.gamethetown.fragment.DifficultyFragment;

//TODO -> implemt fuse
public class CreateItenerary extends App_Menu implements OnMapReadyCallback {

    //private Animation show_fab_1 = AnimationUtils.loadAnimation
    //       (getApplication(), R.anim.fab1_show);

    private GoogleMap mMap;
    private FloatingActionButton fab_rem;
    private FloatingActionButton fab_mark_inform;
    private View marker_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab_rem = (FloatingActionButton) findViewById(R.id.remove);
        fab_mark_inform = (FloatingActionButton) findViewById(R.id.marker_information);
        marker_info = findViewById(R.id.marker_info);
        //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab_mark_inform.getLayoutParams();
        //layoutParams.leftMargin += (int) (fab_mark_inform.getWidth() * 1.7);
        //layoutParams.bottomMargin += (int) (fab_mark_inform.getHeight() * 0.25);
        //fab_mark_inform.setLayoutParams(layoutParams);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                onCreateInitialLocation(view);
                //setFab1();
            }
        });
    }

    public void onCreateInitialLocation(View view){
        BottomSheetDialog bsd = new BottomSheetDialog(this);
        View sView = getLayoutInflater().inflate(R.layout.layout_create_itinerary,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics()));
        bsd.show();
    }

    //TODO
    public void OnMarkerAddition(){
        BottomSheetDialog bsd = new BottomSheetDialog(this);
        View sView = getLayoutInflater().inflate(R.layout.layout_set_hotspot_info,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics()));
        bsd.show();

        Spinner spinner = (Spinner) sView.findViewById(R.id.type_of_game);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.games, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        if(spinner != null)
            spinner.setAdapter(adapter);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //TODO -> verificar se o ponto nao esta a mais de uma distancia pre-defenida
                //fab_mark_inform.setVisibility(View.VISIBLE);
                //fab_rem.setVisibility(View.INVISIBLE);
                marker_info.setVisibility(View.INVISIBLE);
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("New Marker");

                mMap.addMarker(marker);
                OnMarkerAddition();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(final Marker marker) {
                //fab_rem.setVisibility(View.VISIBLE);
                boolean pressed = false;
                marker_info.setVisibility(View.VISIBLE);
                final String id = marker.getId();
                fab_rem.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //marker.remove();
                        marker.setVisible(false);
                        Snackbar snackbar = Snackbar.make(v,"Deleted marker.", Snackbar.LENGTH_SHORT).
                                setAction("Undo", new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        marker.setVisible(true);
                                    }
                                });
                        snackbar.addCallback(new Snackbar.Callback() {

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                //see Snackbar.Callback docs for event details
                                if(!marker.isVisible())
                                    marker.remove();
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                                //do nothing?
                            }
                        });
                        snackbar.show();
                        //fab_rem.setVisibility(View.INVISIBLE);
                        marker_info.setVisibility(View.INVISIBLE);
                    }
                });

                fab_mark_inform.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //fab_rem.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(v.getContext(),Hotspot_Quiz_Creator.class);
                        startActivity(intent);
                        //marker_info.setVisibility(View.INVISIBLE);
                    }
                });

                return true;
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
