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

import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;
import com.example.gamethetown.games.ImagePuzzle;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.games.Race;
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
import java.util.List;

//TODO -> passar por todos os pontos e metelos no mapa
public class Tab2Map extends Fragment implements OnMapReadyCallback {

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private List<Itinerary> itineraries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2map, container, false);

       itineraries = getItinerariesFromDatabase();

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

    //TODO -> quando tivermos basedados
    public List<Itinerary> getItinerariesFromDatabase(){
        Hotspot hot1 = new Hotspot(),hot2 = new Hotspot(),
                hot3 = new Hotspot(),hot4 = new Hotspot(),
                hot5 = new Hotspot();
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();
        setQuiz(quiz1,1);
        setQuiz(quiz2,2);
        Race race = new Race();
        ImagePuzzle imgPzl = new ImagePuzzle(R.drawable.background3);
        ImagePuzzle imgPzl2 = new ImagePuzzle(R.drawable.background2);

        hot1.setName("O significado da vida");
        hot1.setPosition(new LatLng(39,-9.134));
        hot1.setGame(quiz1);

        hot2.setName("A Grande Corrida");
        hot2.setPosition(new LatLng(38.7524403,-9.134773));
        hot2.setGame(race);

        hot3.setName("O puzzle pelo trofeu");
        hot3.setPosition(new LatLng(38.7561689,-9.1556714));
        hot3.setGame(imgPzl);

        hot4.setName("Se a minha imagem estivesse completa");
        hot4.setGame(imgPzl2);
        hot4.setPosition(new LatLng(38.74,-9.13));

        hot5.setName("Quem manda no planeta terra?");
        hot5.setGame(quiz2);
        hot5.setPosition(new LatLng(39,-9.134));
        List<Hotspot> list1 = new ArrayList<>();
        list1.add(hot1);list1.add(hot2);
        List<Hotspot> list2 = new ArrayList<>();
        list2.add(hot3);list2.add(hot4);
        List<Hotspot> list3 = new ArrayList<>();
        list3.add(hot2);list3.add(hot1); list3.add(hot3);
        Itinerary iten1 = new Itinerary("Preciso de boleio pelo espaco academico",new Date(),list1, Difficulties.HARD);
        Itinerary iten2 = new Itinerary("O restaurante no fim da fcul",new Date(),list2,Difficulties.EASY);
        Itinerary iten3 = new Itinerary("Adeus e obrigado por todos os exames",new Date(),list3,Difficulties.MEDIUM);
        List<Itinerary> result = new ArrayList<>();
        result.add(iten1);result.add(iten2);result.add(iten3);
        return result;
    }

    private void setQuiz(Quiz quiz,int n){
        quiz.setName("Quiz" + n);
        quiz.setQuestion("Teste do quiz numero " + n);
        quiz.setAsw1("Respota 1");
        quiz.setAsw2("Respota 2");
        quiz.setAsw3("Resposta 3");
        quiz.setAsw4("Resposta 4");
        quiz.setCorrectAsw(2);
    }

}
