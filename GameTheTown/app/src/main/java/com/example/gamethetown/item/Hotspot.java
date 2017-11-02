package com.example.gamethetown.item;

import com.example.gamethetown.interfaces.Game;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by franc on 02/11/2017.
 */

public class Hotspot {

    private String name; //um nome
    private int imageID; // pode ter uma imagem
    private Game game; //qualquer hotspot vai ter um jogo qualquer
    private LatLng position; //cada hotspot vai ter uma posicao

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

    public void setImageID(int imageID){this.imageID = imageID;}
    public void setName(String name){this.name = name;}
    public void setGame(Game game){this.game = game;}

}
