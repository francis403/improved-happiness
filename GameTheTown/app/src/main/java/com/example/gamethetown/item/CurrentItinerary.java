package com.example.gamethetown.item;

/**
 * Created by franc on 10/11/2017.
 */

public class CurrentItinerary {

    private Itinerary currentItinerary;
    private int currentHotspotIndice;
    private double score;
    private long startTime;


    public CurrentItinerary(Itinerary iten){
        currentItinerary = iten;
        currentHotspotIndice = 0;
        score = 0;
        startTime = System.currentTimeMillis(); //isto so vai dar se for sempre no mesmo dispositivo
    }

    public Itinerary getCurrentItinerary(){return currentItinerary;}
    public Hotspot getCurrentHotspot(){
        if(currentItinerary != null && currentHotspotIndice > -1)
            return currentItinerary.getHotSpotList().get(currentHotspotIndice);
        return null;
    }
    public Hotspot nextHotspot(){
        currentHotspotIndice++;
        if(currentHotspotIndice < currentItinerary.getNumberOfHotspots())
            return currentItinerary.getHotSpotList().get(currentHotspotIndice);
        return null;
    }

    //uma ideia para o complete (mas duvido que va funcionar)
    public void complete(){
        currentHotspotIndice = -1;
    }

    public long getDuration(){return System.currentTimeMillis() - startTime;}
    public void addScore(double score){this.score += score;}


    public Itinerary getItinerary(){return currentItinerary;}
    public double getScore(){return score;}

}
