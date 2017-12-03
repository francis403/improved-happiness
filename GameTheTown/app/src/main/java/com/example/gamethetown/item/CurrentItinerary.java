package com.example.gamethetown.item;

import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class CurrentItinerary implements InTheDatabase{

    private Itinerary currentItinerary;
    private int currentHotspotIndice;
    private double score;
    private long startTime;


    public CurrentItinerary(Itinerary iten){
        currentItinerary = iten; //nao consigo por coisas complexas como estas
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
    public int getIndice(){return currentHotspotIndice;}
    public long getStartTime(){return startTime;}

    @Override
    public void setValueInDatabase(DatabaseReference parentRef,Object obj) {
        DatabaseReference currItenRef = parentRef.child("currIten");
        //set basic info
        currItenRef.child("indice").setValue(currentHotspotIndice);
        currItenRef.child("score").setValue(score);
        currItenRef.child("startTime").setValue(startTime);

        //set harder information
        currItenRef.child("id").setValue(currentItinerary.getItenID());
        //currentItinerary.setValueInDatabase(currItenRef,false);

    }
    //TODO
    @Override
    public void getValueInDatabase(DataSnapshot snap, Object obj) {

    }
}
