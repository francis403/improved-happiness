package com.example.gamethetown.item;

import com.example.gamethetown.R;
import com.example.gamethetown.games.CurrentGame;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;


public class Hotspot implements Serializable{

    private String name; //um nome
    private int imageID; // pode ter uma imagem
    private CurrentGame game; //qualquer hotspot vai ter um jogo qualquer
    private LatLng position; //cada hotspot vai ter uma posicao


    //too define in creation
    public Hotspot(){}

    public Hotspot(String name, CurrentGame game,LatLng position){
        this.name = name;
        this.imageID = R.drawable.no_image;
        this.game = game;
        this.position = position;
    }

    public Hotspot(String name, int imageID, CurrentGame game,LatLng position){
        this.name = name;
        this.imageID = imageID;
        this.game = game;
        this.position = position;
    }

    public boolean isComplete(){
        return name != null && !name.equals("") && game != null
                && game.isComplete() && position != null;
    }

    public String getTitle(){return name;}
    public int getImageID(){return imageID;}
    public CurrentGame getGame(){return game;}
    public LatLng getPosition(){return position;}

    public void setImageID(int imageID){this.imageID = imageID;}
    public void setName(String name){this.name = name;}
    public void setGame(CurrentGame game){this.game = game;}
    public void setPosition(LatLng position){this.position = position;}

}
