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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gamethetown.games.Hotspot_Quiz_Creator;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.HashMap;

//TODO -> implemt fuse
public class CreateItenerary extends App_Menu implements OnMapReadyCallback {

    private Itinerary createdIten;

    //os hotspots antes de serem confirmados
    private HashMap<String,Hotspot> preHotspots = new HashMap<>();
    private GoogleMap mMap;
    private FloatingActionButton fab_rem; //prov posso tirar isto daqui nao?
    private BottomSheetDialog bsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        createdIten = new Itinerary();
        bsd = new BottomSheetDialog(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View sView = bottomSheetUp(R.layout.layout_create_itinerary);
                FloatingActionButton confirm = (FloatingActionButton)
                        sView.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        closeBottomView(sView);
                    }
                });
                itenData(sView);

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_confirm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View sView = bottomSheetUp(R.layout.layout_confirm_creation);
                FloatingActionButton confirm = (FloatingActionButton)
                        sView.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //do something //TODO
                        //verificar se esta tubo bem
                    }
                });
                itenData(sView);
            }
        });

    }

    public View bottomSheetUp(int layoutID){
        View sView = getLayoutInflater().inflate(layoutID,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics()));
        bsd.show();
        return sView;
    }

    //TODO
    public void OnMarkerAddition(final Marker marker){
        Hotspot hot = new Hotspot();
        View sView = bottomSheetUp(R.layout.layout_set_hotspot_info);
        preHotspots.put(marker.getId(),hot);
        fabRemove(marker,sView);
        hotspotInfoConfirm(marker,sView);
        Spinner spinner = (Spinner) sView.findViewById(R.id.type_of_game);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.games, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    //TODO
    private void fabRemove(final Marker marker, View sView){
        fab_rem = (FloatingActionButton) sView.findViewById(R.id.remove);
        //haver se mudo isto de sitio so para fazer isto uma vez TODO
        fab_rem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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
                        if(!marker.isVisible()) {
                            preHotspots.remove(marker.getId());
                            marker.remove();
                            bsd.hide();
                        }
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        //do nothing?
                    }
                });
                snackbar.show();
            }
        });
    }

    private void hotspotInfoConfirm(final Marker marker,View v){
        FloatingActionButton fabConfirm = (FloatingActionButton)
                v.findViewById(R.id.confirm);
        final Hotspot hot = preHotspots.get(marker.getId());
        final TextView title = (TextView) v.findViewById(R.id.title);
        String name = hot.getTitle();

        fabConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                if(t != null){
                    hot.setName(t);
                }
                bsd.hide();
            }

        });
        if(name != null && !name.equals(""))
            title.setText(name);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //TODO -> verificar se o ponto nao esta a mais de uma distancia pre-defenida

                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(point.latitude, point.longitude))
                        .title("New Marker");

                Marker m = mMap.addMarker(marker);
                OnMarkerAddition(m);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(final Marker marker) {

                View sView = bottomSheetUp(R.layout.layout_set_hotspot_info);
                fabRemove(marker,sView);
                hotspotInfoConfirm(marker,sView);
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

    public void fabInform(View v) {
        Intent intent = new Intent(v.getContext(),Hotspot_Quiz_Creator.class);
        startActivity(intent);
    }
    //TODO -> Only do if valid input
    //ERRO -> esta a receber o titule em branco
    public void closeBottomView(View v){
        String title = ((EditText) v.findViewById(R.id.title)).getText()
                .toString();
        if(title != null)
            createdIten.setTitle(title);
        String description = ((EditText) v.findViewById(R.id.description)).getText()
                .toString();
        if(description != null)
            createdIten.setDescription(description);

        bsd.hide(); //close window
    }

    //TODO -> mete os dados todos para nos vermos e confirmarmos
    private void itenData(View sView){
        TextView title = (TextView) sView.findViewById(R.id.title);
        TextView description = (TextView) sView.findViewById(R.id.description);
        String t = createdIten.getTitle(),d = createdIten.getDescription();
        if(t != null && !title.equals(""))
            title.setText(t);
        if(d != null && !description.equals("")){
            description.setText(d);
        }

    }

}
