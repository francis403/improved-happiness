package com.example.gamethetown.item;

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

    public User(){
        currentHotspotIndice = -1; //porque nao temos nenhum ainda
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

    public void setCurrentItinerary(Itinerary itinerary){
        currentItinerary = itinerary;
        currentHotspotIndice = 0;
    }
    public void incrementIndice(){currentHotspotIndice++;}
    public void setName(String name){this.name = name;}
    public void setPassword(String password){this.password = password;}
    public void setLevel(int level){this.level = level;}

    //TODO
    public boolean levelUp(double experience){
        return false;
    }

}
