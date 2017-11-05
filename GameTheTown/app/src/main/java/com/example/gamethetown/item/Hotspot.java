package com.example.gamethetown.item;

import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.Game;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;


public class Hotspot implements Serializable{

    private String name; //um nome
    private int imageID; // pode ter uma imagem
    private Game game; //qualquer hotspot vai ter um jogo qualquer
    private LatLng position; //cada hotspot vai ter uma posicao

    //nao sei se vamos precisar
    private boolean isConfirmed = false; //here mearly to help while creating an itinerary

    //too define in creation
    public Hotspot(){}

    public Hotspot(String name, Game game,LatLng position){
        this.name = name;
        this.imageID = R.drawable.no_image;
        this.game = game;
        this.position = position;
    }

    public Hotspot(String name, int imageID, Game game,LatLng position){
        this.name = name;
        this.imageID = imageID;
        this.game = game;
        this.position = position;
    }

    public String getTitle(){return name;}
    public int getImageID(){return imageID;}
    public Game getGame(){return game;}
    public LatLng getPosition(){return position;}
    public boolean isConfirmed(){return isConfirmed;}

    public void setConfirmed(Boolean confirmed){isConfirmed = confirmed;}
    public void setImageID(int imageID){this.imageID = imageID;}
    public void setName(String name){this.name = name;}
    public void setGame(Game game){this.game = game;}

}
