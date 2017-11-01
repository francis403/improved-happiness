package com.example.gamethetown.item;

import android.widget.ImageView;

import com.example.gamethetown.R;

import java.util.Date;

/**
 * Created by franc on 29/10/2017.
 */

public class Itinerary {

    private String name;
    private int imageID;
    private Date creatDate;
    private String Description;

    //TODO
    //vai ter um LatLng para a imagem base,
    //vai ter uma list de LatLng para todos os hotspots possiveis

    public Itinerary(){}


    public Itinerary(String name,Date creatDate){
        this.name = name;
        this.creatDate = creatDate;
        imageID = R.drawable.no_image;
    }

    public Itinerary(String name,Date creatDate,int imageID){
        this.name = name;
        this.imageID = imageID;
        this.creatDate = creatDate;
    }

    public boolean hasImage(){
        return imageID != R.drawable.no_image;
    }

    public String getTitle(){return name;}
    public Date getCreationDate(){return creatDate;}
    public int getImageID(){return imageID;}

}
