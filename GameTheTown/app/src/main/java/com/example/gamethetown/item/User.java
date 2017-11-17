package com.example.gamethetown.item;

import android.util.Log;

import com.example.gamethetown.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by franc on 07/11/2017.
 */

public class User {

    private String name;
    private String password;
    private int imageID;
    private int level;
    private CurrentItinerary currentItinerary;

    private List<Itinerary> completedItineraries =  new ArrayList<>();
    private List<Itinerary> createdItineraries =  new ArrayList<>();

    public User(String name,String password){
        this.name = name;
        this.password = password;
        level = 1; //nivel inicial
        imageID = R.drawable.profile;
    }

    public String getName(){return name;}
    public String getPassword(){return password;}
    public int getLevel(){return level;}
    public CurrentItinerary getCurrentItinerary(){return currentItinerary;}
    public List<Itinerary> getCompletedItineraries(){
        return Collections.unmodifiableList(completedItineraries);
    }
    public List<Itinerary> getCreatedItineraries(){
        return Collections.unmodifiableList(createdItineraries);
    }

    //TODO
    /**
     * Quando completamos um itinerario
     * @param score -> Score a adicionar ao exp do utilizador
     * @results currentItinerary = null && currentHotspotIndice = -1
     * CurrentItinerary passa a null e eh adicionado a score ao utilizador
     */
    public void completeItinerary(Double score){
        this.currentItinerary = null;
    }
    public void addCompletedItinerary(Itinerary i){completedItineraries.add(i);}
    public void addCreatedItinerary(Itinerary i){createdItineraries.add(i);}
    public void setCurrentItinerary(Itinerary itinerary){
        currentItinerary = new CurrentItinerary(itinerary);
    }
    public void setName(String name){this.name = name;}
    public void setPassword(String password){this.password = password;}
    public void setLevel(int level){this.level = level;}
    public Hotspot getCurrentHotspot(){
        if(currentItinerary != null)
            return currentItinerary.getCurrentHotspot();
        return null;
    }
    public void setImageID(int imageID){
        this.imageID = imageID;
    }
    public int getImageID(){return imageID;}
    public Hotspot nextHotspot(){
        return currentItinerary.nextHotspot();
    }

    public void addScoreToItinerary(double score){currentItinerary.addScore(score);}

    //TODO
    public boolean levelUp(double experience){
        return false;
    }
}
