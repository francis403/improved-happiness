package com.example.gamethetown.item;

import android.widget.ImageView;

import com.example.gamethetown.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by franc on 29/10/2017.
 */

public class Itinerary {

    private static final int DEFAULT_IMAGE_ID = R.drawable.no_image;

    private String name;
    private int imageID;
    private Date creatDate;
    private String description;
    //Array de todos os hostpots possiveis
    //o hotspot vai ter a posicao
    //O primeiro hotspot vai ser o base ( o que aparece no mapa)
    private List<Hotspot> hotspots;

    //TODO
    //vai ter um LatLng para a imagem base,
    //vai ter uma list de LatLng para todos os hotspots possiveis

    public Itinerary(){
        imageID = DEFAULT_IMAGE_ID;
    }


    public Itinerary(String name,Date creatDate){
        hotspots = new ArrayList<>();
        this.name = name;
        this.creatDate = creatDate;
        imageID = DEFAULT_IMAGE_ID;
    }

    public Itinerary(String name,Date creatDate,int imageID){
        hotspots = new ArrayList<>();
        this.name = name;
        this.imageID = imageID;
        this.creatDate = creatDate;
    }

    public Itinerary(String name,Date creatDate,int imageID,List<Hotspot> list){
        hotspots = new ArrayList<>();
        this.name = name;
        this.imageID = imageID;
        this.creatDate = creatDate;
        hotspots = list;
    }

    public boolean hasImage(){
        return imageID != DEFAULT_IMAGE_ID;
    }

    public String getTitle(){return name;}
    public Date getCreationDate(){return creatDate;}
    public int getImageID(){return imageID;}
    public int getNumberOfHotspots(){return hotspots.size();}
    public Hotspot getFirstHotspot(){return hotspots.get(0);}
    public List<Hotspot> getHotSpotList(){
        return Collections.unmodifiableList(hotspots);
    }
    public String getDescription(){return description;}

    public void setTitle(String title){this.name = title;}
    public void setDescription(String description){this.description = description;}
    public void setDate(Date date){this.creatDate = date;}
    public void addHotspot(Hotspot hotspot){hotspots.add(hotspot);}


}
