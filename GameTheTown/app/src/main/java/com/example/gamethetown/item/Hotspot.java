package com.example.gamethetown.item;

import android.location.Location;

import com.example.gamethetown.R;
import com.example.gamethetown.games.CurrentGame;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;


public class Hotspot implements Serializable{

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

}
