package com.example.gamethetown.item;

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
    private Itinerary currentItinerary;
    private int currentHotspotIndice;

    private List<Itinerary> completedItineraries;
    private List<Itinerary> createdItineraries;

    public User(){
        currentHotspotIndice = -1; //porque nao temos nenhum ainda
        completedItineraries = new ArrayList<>(); //empty
        createdItineraries = new ArrayList<>();
        level = 1;
    }

    public User(String name,String password){
        this.name = name;
        this.password = password;
        level = 1; //nivel inicial
        currentHotspotIndice = -1;
    }

    public String getName(){return name;}
    public String getPassword(){return password;}
    public int getLevel(){return level;}
    public Itinerary getCurrentItinerary(){return currentItinerary;}
    public List<Itinerary> getCompletedItineraries(){
        return Collections.unmodifiableList(completedItineraries);
    }
    public List<Itinerary> getCreatedItineraries(){
        return Collections.unmodifiableList(createdItineraries);
    }



    public void addCompletedItinerary(Itinerary i){completedItineraries.add(i);}
    public void addCreatedItinerary(Itinerary i){createdItineraries.add(i);}
    public void setCurrentItinerary(Itinerary itinerary){
        currentItinerary = itinerary;
        currentHotspotIndice = 0;
    }
    public void setName(String name){this.name = name;}
    public void setPassword(String password){this.password = password;}
    public void setLevel(int level){this.level = level;}
    public Hotspot getCurrentHotspot(){
        return currentItinerary.getHotSpotList().get(currentHotspotIndice);
    }
    public Hotspot nextHotspot(){
        currentHotspotIndice++;
        if(currentHotspotIndice < currentItinerary.getNumberOfHotspots())
            return currentItinerary.getHotSpotList().get(currentHotspotIndice);
        return null; //caso ja tenha terminado
    }

    //TODO
    public boolean levelUp(double experience){
        return false;
    }


}
