package com.example.gamethetown.Activities;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;
import com.example.gamethetown.gameControllers.HotspotImagePuzzle;
import com.example.gamethetown.gameControllers.HotspotImagePuzzleCreator;
import com.example.gamethetown.gameControllers.Hotspot_Quiz_Creator;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.games.Race;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CreateItenerary extends App_Menu implements OnMapReadyCallback {

    private static final int GET_GAME_CODE = 1;
    private static final int PICK_FROM_FILE = 2 ;

    private Itinerary createdIten;
    private Bundle extras;
    private List<Hotspot> receivedHotspots;
    //os hotspots antes de serem confirmados
    private HashMap<String,Hotspot> preHotspots = new HashMap<>();
    private GoogleMap mMap;
    private FloatingActionButton fab_rem; //prov posso tirar isto daqui nao?
    private FusedLocationProviderClient mFusedLocationClient;

    private ImagePicker imgPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        createdIten = new Itinerary();
        createdIten.setDate(new Date());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        imgPicker = ImagePicker.create(this).returnAfterFirst(true)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")
                .enableLog(false);

        extras = getIntent().getExtras();
        if(extras != null && !extras.isEmpty()){
            //get data from arleady existing itinerary and list of preHotspots
            Itinerary iten = (Itinerary) extras.get("hotList");
            receivedHotspots = iten.getHotSpotList();
            //tenho que meter os dados do itinerario
        }

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
                RadioGroup rg = (RadioGroup) sView.findViewById(R.id.dif);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId){
                            case R.id.justTravel:
                                createdIten.setDifficulty(Difficulties.JUST_TRAVEL);
                                break;
                            case R.id.easy:
                                createdIten.setDifficulty(Difficulties.EASY);
                                break;
                            case R.id.medium:
                                createdIten.setDifficulty(Difficulties.MEDIUM);
                                break;
                            case R.id.hard:
                                createdIten.setDifficulty(Difficulties.HARD);
                                break;
                        }
                    }
                });
                String dif = createdIten.getDifficulty();
                if(dif != null) {
                    if(dif.equals(Difficulties.JUST_TRAVEL.toString()))
                        rg.check(R.id.justTravel);
                    else if(dif.equals(Difficulties.EASY.toString()))
                        rg.check(R.id.easy);
                    else if(dif.equals(Difficulties.MEDIUM.toString()))
                        rg.check(R.id.medium);
                    else if(dif.equals(Difficulties.HARD.toString()))
                        rg.check(R.id.hard);
                }
                itenData(sView);

            }
        });
        //confirmed until now
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
                        if(createdIten.preCompleted(preHotspots.values())){
                            //se ja estiver tudo bem e completo
                            Intent intent = new Intent(v.getContext(),
                                    ConfirmHotspots.class);
                            intent.putExtra("itinerary",createdIten);
                            intent.putExtra("preList",preHotspots);
                            startActivity(intent);
                        }
                        else{
                            bottomSheetUp(R.layout.layout_error_creating_itinerary);
                        }
                    }
                });
                itenData(sView);
            }
        });

    }

    public void OnMarkerAddition(final Marker marker){
        Hotspot hot = new Hotspot();
        View sView = bottomSheetUp(R.layout.layout_set_hotspot_info);
        hot.setPosition(marker.getPosition());
        preHotspots.put(marker.getId(),hot);
        setHotspotInfoWindow(marker,sView);
    }

    //TODO -> falta meter a foto
    private void setHotspotInfoWindow(final Marker marker, View sView){
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

        //TODO -> Vou ter de por o metodo para ele ir buscar os valores
        Spinner spinner = (Spinner) sView.findViewById(R.id.type_of_game);
        //TODO -> add the too spines
        Hotspot h = preHotspots.get(marker.getId());
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.games, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        //TODO mudar, nao gosto disto assim
        if(h.getGame() != null){
            if(h.getGame().getGameName().equals("Quiz"))
                spinner.setSelection(0);
            else if(h.getGame().getGameName().equals("Race"))
                spinner.setSelection(1);
            else
                spinner.setSelection(2);
        }

        hotspotInfoConfirm(marker,sView,spinner);
    }

    private void hotspotInfoConfirm(final Marker marker,View v,final Spinner spinner){
        FloatingActionButton fab = (FloatingActionButton)
                v.findViewById(R.id.confirm);
        final Hotspot hot = preHotspots.get(marker.getId());
        final TextView title = (TextView) v.findViewById(R.id.title);
        String name = hot.getTitle();
        if(hot.getGame() != null && hot.getGame().getGameName() != null) {
            if (hot.getGame().getGameName().equals("ImagePuzzle"))
                spinner.setSelection(2);
        }
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                if(t != null){
                    hot.setName(t);
                }
                //torna o marker verde caso ja esteja completo
                if(hot.isComplete()) {
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
                else{
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }

                bsd.hide();
            }

        });
        //ao voltar
        if(name != null && !name.equals(""))
            title.setText(name);
        //meter o butao para ir ver o jogo
        fab = (FloatingActionButton)
                v.findViewById(R.id.marker_info);
        //TODO -> here is where we add the game
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent;
                if(spinner.getSelectedItem().toString().equals("Quiz")) {
                    intent = new Intent(v.getContext(), Hotspot_Quiz_Creator.class);
                    intent.putExtra("currentGame",hot.getGame());
                    intent.putExtra("markerID",marker.getId());
                    startActivityForResult(intent,GET_GAME_CODE);
                }
                if(spinner.getSelectedItem().toString().equals("Race")){
                    Race game = new Race();
                    hot.setGame(game);
                    Toast.makeText(getBaseContext(), "Race added", Toast.LENGTH_SHORT).show();
                }
                if(spinner.getSelectedItem().toString().equals("ImagePuzzle")) {
                    intent = new Intent(v.getContext(), HotspotImagePuzzleCreator.class);
                    intent.putExtra("currentGame",hot.getGame());
                    intent.putExtra("markerID",marker.getId());
                    startActivityForResult(intent,GET_GAME_CODE);
                }

            }

        });

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

                Marker m = mMap.addMarker(marker);
                OnMarkerAddition(m);
            }
        });
        if(receivedHotspots != null) {
            for (Hotspot h : receivedHotspots) {
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(h.getLocation().getLatitude(),
                                h.getLocation().getLongitude()))
                        .title("New Marker");
                Marker m = mMap.addMarker(marker);
                m.setIcon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                preHotspots.put(m.getId(),h);
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(final Marker marker) {

                View sView = bottomSheetUp(R.layout.layout_set_hotspot_info);
                setHotspotInfoWindow(marker,sView);
                return true;
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mMap.moveCamera(CameraUpdateFactory.
                                    newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
                        }
                    }
                });

        mMap.setMyLocationEnabled(true);
    }

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

    private void itenData(View sView){
        TextView title = (TextView) sView.findViewById(R.id.title);
        TextView description = (TextView) sView.findViewById(R.id.description);
        String t = createdIten.getTitle(),d = createdIten.getDescription();
        if(t != null && !title.equals(""))
            title.setText(t);
        if(d != null && !description.equals(""))
            description.setText(d);
    }

    public void getImage(View view){
        imgPicker.start(PICK_FROM_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK)
            return;

        // Check which request we're responding to
        if (requestCode == GET_GAME_CODE) {
            // Make sure the request was successful

            CurrentGame game = (CurrentGame) data.getExtras().get("game");
            String markerID = (String) data.getExtras().get("markerID");
            Hotspot hot = preHotspots.get(markerID);
            hot.setGame(game);
            return;
        }
        if(requestCode == PICK_FROM_FILE){
            Bitmap bitmap;
            String path = "";
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            Image image = images.get(0);
            path = image.getPath();
            //vamos sempre precisar de guardar o path tambem
            createdIten.setImagePath(path);
        }
    }

}
