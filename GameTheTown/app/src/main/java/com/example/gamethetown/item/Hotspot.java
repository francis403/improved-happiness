package com.example.gamethetown.item;

import android.location.Location;
import android.util.Log;

import com.example.gamethetown.Database.DBConstants;
import com.example.gamethetown.R;
import com.example.gamethetown.games.CurrentGame;
import com.example.gamethetown.games.ImagePuzzle;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.games.Race;
import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;


public class Hotspot implements Serializable,InTheDatabase{

    private String name; //um nome
    private int imageID; // pode ter uma imagem
    private CurrentGame game; //qualquer hotspot vai ter um jogo qualquer
    private double lt,lg; //Precisamos de ter a posicao em si


    //too define in creation
    public Hotspot(){
        imageID = R.drawable.no_image;
        lt = -1;
        lg = -1;
    }

    public Hotspot(DataSnapshot snap){
        getValueInDatabase(snap,null);
        imageID = R.drawable.no_image;
    }

    public Hotspot(String name, CurrentGame game,LatLng position){

        this.name = name;
        this.imageID = R.drawable.no_image;
        this.game = game;
        lt = position.latitude;
        lg = position.longitude;
    }

    public boolean isComplete(){
        return name != null && !name.equals("") && game != null
                && game.isComplete() && (lt != -1 || lg != -1);
    }

    public String getTitle(){return name;}
    public int getImageID(){return imageID;}
    public CurrentGame getGame(){return game;}
    public LatLng getPosition(){return new LatLng(lt,lg);}
    public Location getLocation(){
        Location l = new Location(name);
        l.setLatitude(lt);
        l.setLongitude(lg);
        return l;
    }

    public void setImageID(int imageID){this.imageID = imageID;}
    public void setName(String name){this.name = name;}
    public void setGame(CurrentGame game){this.game = game;}
    public void setPosition(LatLng position){
        lt = position.latitude;
        lg = position.longitude;
    }

    //TODO
    @Override
    public void setValueInDatabase(DatabaseReference parentRef, Object obj) {
        int i = (int) obj; //todo o itinerario pode ter mais do que um hotspot
        DatabaseReference hotRef = parentRef.child(String.valueOf(i));
        hotRef.child("name").setValue(name);
        //TODO -> falta meter a imagem
        hotRef.child("position").setValue(new LatLng(lt,lg));
        game.setValueInDatabase(hotRef,null);
    }
    //TODO
    @Override
    public void getValueInDatabase(DataSnapshot snap, Object obj) {
        //o hotspot desejado
        //get name
        this.name = snap.child("name").getValue(String.class);
        //TODO -> get game, tenho que passar o type para traz
        DataSnapshot gameSnap = snap.child("game");
        String type = gameSnap.child("type").getValue(String.class);
        //get game
        //TODO -> nao gosto desta maneira, provavelmente consigo usar um factory para fazer isto
        if(type.equals("Quiz"))
            game = new Quiz(snap);
        else if(type.equals("Race"))
            game = new Race(snap);
        else
            game = new ImagePuzzle(); //TODO

        //get position
        DataSnapshot posSnap = snap.child(DBConstants.REFERENCE_POSTION);
        lt = posSnap.child(DBConstants.REFERENCE_POSTION_LATITUDE)
                .getValue(Double.class);
        lg = posSnap.child(DBConstants.REFERENCE_POSTION_LONGITUDE)
                .getValue(Double.class);
    }
}
