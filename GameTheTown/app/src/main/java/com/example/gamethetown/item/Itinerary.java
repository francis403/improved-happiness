package com.example.gamethetown.item;

import android.util.Log;
import android.widget.ImageView;

import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by franc on 29/10/2017.
 */

public class Itinerary implements Serializable{

    private static final int DEFAULT_IMAGE_ID = R.drawable.no_image;

    private String name;
    private int imageID;
    private Date creatDate;
    private String description;
    private Enum<Difficulties> dif;
    private User creator;
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

    //facilita os testes
    public Itinerary(String name,Date creatDate,int imageID,List<Hotspot> list){
        hotspots = new ArrayList<>();
        this.name = name;
        this.imageID = imageID;
        this.creatDate = creatDate;
        hotspots = list;
    }

    /**
     * Verifica se funciona com a lista de preHotspots
     * @param preHotspots -> hotspots ainda nao confirmados
     * @return
     */
    public boolean preCompleted(Collection<Hotspot> preHotspots){
        for(Hotspot h : preHotspots)
            if(!h.isComplete())
                return false;
        Log.d("teste","todos os hotspots estao completos");
        return preHotspots.size() >= 1 && name != null
                && description != null && creatDate != null && dif != null;
    }

    //tem de haver pelo menos um hotspot no itinerario
    public boolean isComplete(){
        for(Hotspot h : hotspots)
            if(!h.isComplete())
                return false;
        return name != null && !name.equals("") && creatDate != null &&
                dif != null && description != null && !description.equals("")
                && hotspots.size() >=1;
    }
    public boolean hasImage(){
        return imageID != DEFAULT_IMAGE_ID;
    }
    public User getCreator(){return creator;}
    public String getTitle(){return name;}
    public Date getCreationDate(){return creatDate;}
    public int getImageID(){return imageID;}
    public int getNumberOfHotspots(){return hotspots.size();}
    public Hotspot getFirstHotspot(){return hotspots.get(0);}
    public List<Hotspot> getHotSpotList(){
        return Collections.unmodifiableList(hotspots);
    }
    public String getDescription(){return description;}
    public String getDifficulty(){
        if(dif == null)
            return null;
        return dif.toString();
    }
    public int numberOfHotspot(){return hotspots.size();}

    public void setCreator(User creator){this.creator = creator;}
    public void setDifficulty(Difficulties dif){this.dif = dif;}
    public void setTitle(String title){this.name = title;}
    public void setDescription(String description){this.description = description;}
    public void setDate(Date date){this.creatDate = date;}
    public void addHotspot(Hotspot hotspot){hotspots.add(hotspot);}



}
