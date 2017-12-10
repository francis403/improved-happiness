package com.example.gamethetown.item;

import com.example.gamethetown.Catalogs.UserAuthentication;
import com.example.gamethetown.Database.DBConstants;
import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.Database.UserDatabaseConnection;
import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class User implements InTheDatabase{
    //Conecao com a base de dados
    private UserDatabaseConnection udc;
    private String userID;

    private String name;
    private int imageID;
    private int level;
    private double exp;


    private static CurrentItinerary currentItinerary;

    //ate agora
    private List<String> createdItin;
    private List<String> completedItin;

    /**
     * Used to get the user data from the database
     * @param snap -> location of the data
     */
    public User(DataSnapshot snap){
        UserAuthentication ua = new UserAuthentication();
        udc = new UserDatabaseConnection(ua.getUser().getUid());
        createdItin = new ArrayList<>();
        completedItin = new ArrayList<>();
        //createdItineraries =  new ArrayList<>();
        getValueInDatabase(snap,null);
        imageID = R.drawable.profile;
    }

    /**
     * Used only when registering the user
     * @requires Firebase.getUser() != null
     * @param userID
     */
    public User(String userID,String name){
        UserAuthentication ua = new UserAuthentication();
        udc = new UserDatabaseConnection(ua.getUser().getUid());
        this.name = name;
        this.userID = userID;
        this.level = 1;
        createdItin = new ArrayList<>();
        completedItin = new ArrayList<>();
        //createdItineraries =  new ArrayList<>();
        //this.level = udc.getUserLvl();
        imageID = R.drawable.profile;
    }

    public String getName(){return name;}
    public int getLevel(){return level;}

    public CurrentItinerary getCurrentItinerary(){return currentItinerary;}

    public void completeItinerary(Double score){
        addExperience(score * 100);
        this.currentItinerary = null;
        //this.currentItinerary.setDefault();
        new UserDatabaseConnection(userID).removeCurrentItinerary();
    }
    public void addCompletedItinerary(Itinerary i){
        //completedItineraries.add(i);
        new UserDatabaseConnection(userID).addCompletedIten(i);
    }
    public void addCreatedItinerary(Itinerary i){
        //createdItineraries.add(i);
        createdItin.add(i.getItenID());
        new ItineraryDatabaseConnection().addItinerary(i);
        new UserDatabaseConnection(userID).addCreatedIten(i);

    }

    public void setCurrentItinerary(Itinerary itinerary){

        if(currentItinerary != null)
            this.currentItinerary.setCurrentItinerary(itinerary);
        else
            currentItinerary = new CurrentItinerary(itinerary);
        udc.setCurrentItinerary(currentItinerary); //teste
    }


    public void setName(String name){this.name = name;}
    public Hotspot getCurrentHotspot(){
        if(currentItinerary != null)
            return currentItinerary.getCurrentHotspot();
        return null;
    }
    public void setImageID(int imageID){
        this.imageID = imageID;
    }
    public int getImageID(){return imageID;}
    public String getUserID(){return userID;}
    public Hotspot nextHotspot(){
        udc.updateIndiceOfItinerary(currentItinerary.getIndice());
        return currentItinerary.nextHotspot();
    }

    public void addScoreToItinerary(double score){
        udc.updateScoreOfItinerary(score,currentItinerary.getScore());
        currentItinerary.addScore(score);
    }

    public void addExperience(double exp){
        this.exp += exp;
        if(increasesLevel()){
            this.exp = 0;
            level++;
            //udc.setExpInDatabase(0);
            udc.setLevelInDatabase(level);
        }
    }

    private boolean increasesLevel(){
            return exp > level * 100;
    }

    @Override
    public void setValueInDatabase(DatabaseReference parentRef, Object obj) {
        //deve receber os users aqui
        DatabaseReference usersRef = parentRef.child(userID);
        usersRef.child("name").setValue(name);
        usersRef.child(DBConstants.REFERENCE_LEVEL).setValue(level);
        usersRef.child(DBConstants.REFERENCE_EXP).setValue(exp);
    }

    @Override
    public void getValueInDatabase(DataSnapshot snap, Object obj) {
        //recebemos espaco do user especifico
        this.userID = snap.getKey();
        this.name = snap.child(DBConstants.REFERENCE_NAME).getValue(String.class);
        this.level = snap.child(DBConstants.REFERENCE_LEVEL)
                .getValue(Integer.class);
        this.exp = snap.child(DBConstants.REFERENCE_EXP)
                .getValue(Double.class);

        //buscar o current Itinerary
        DataSnapshot currItenRef = snap.child(DBConstants.REFERENCE_CURRENT_ITINERARY);
        if(currItenRef != null && currItenRef.exists())
            currentItinerary = new CurrentItinerary(currItenRef);

        //buscar a lista de criados
        DataSnapshot crtSnaps = snap.child(DBConstants.REFERENCE_CREATED_ITEN);
        for(DataSnapshot s : crtSnaps.getChildren()){
            createdItin.add(s.getKey());
        }
        //buscar a lista dos completados
        DataSnapshot cmpSnaps = snap.child(DBConstants.REFERENCE_COMPLETED_ITEN);
        for(DataSnapshot s : cmpSnaps.getChildren()){
            completedItin.add(s.getKey());
        }
    }
}
