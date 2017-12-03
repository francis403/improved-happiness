package com.example.gamethetown.item;

import android.content.SyncStatusObserver;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.gamethetown.Catalogs.ItineraryCatalog;
import com.example.gamethetown.Catalogs.UserAuthentication;
import com.example.gamethetown.Database.DBConstants;
import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.Database.UserDatabaseConnection;
import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class User implements InTheDatabase{
    //Conecao com a base de dados
    private UserDatabaseConnection udc;
    private String userID;


    private String name;
    private int imageID;
    private int level;
    private double exp;

    //TODO
    //ideia passar estes 3 para string do id do itinerario
    //e quando necessario vamos buscar tudo
    private static CurrentItinerary currentItinerary;
    private List<Itinerary> completedItineraries =  new ArrayList<>();
    private List<Itinerary> createdItineraries;

    //ate agora
    private List<String> createdItin;
    private List<String> completedItin;

    public List<String> getCrtItin(){return Collections.unmodifiableList(createdItin);}
    public List<String> getCompletedItin(){return Collections.unmodifiableList(completedItin);}
    //posso retirar isto daqui
    public User(String name,String password){
        UserAuthentication ua = new UserAuthentication();
        udc = new UserDatabaseConnection(ua.getUser().getUid());
        this.name = name;
        level = 1; //nivel inicial
        imageID = R.drawable.profile;
        createdItin = new ArrayList<>();
        completedItin = new ArrayList<>();
        createdItineraries =  new ArrayList<>();
    }

    public User(DataSnapshot snap){
        UserAuthentication ua = new UserAuthentication();
        udc = new UserDatabaseConnection(ua.getUser().getUid());
        createdItin = new ArrayList<>();
        completedItin = new ArrayList<>();
        createdItineraries =  new ArrayList<>();
        getValueInDatabase(snap,null);
        imageID = R.drawable.profile;
    }

    // TODO -> retirar
    /**
     * @requires Firebase.getUser() != null
     * @param userID
     */
    //TODO -> IDEIA
    public User(String userID){
        UserAuthentication ua = new UserAuthentication();
        udc = new UserDatabaseConnection(ua.getUser().getUid());
        this.name = ua.getUser().getDisplayName();
        this.userID = userID;
        //ha uma maneira mais simples! podemos chamar o child no dataSnapshot
        //TODO -> mudar isto depois
        this.level = 1;
        createdItin = new ArrayList<>();
        completedItin = new ArrayList<>();
        createdItineraries =  new ArrayList<>();
        //this.level = udc.getUserLvl();
        imageID = R.drawable.profile; //DEFAULt VALUE //TODO -> buscar o valor
    }

    public String getName(){return name;}
    public int getLevel(){return level;}

    //passar isto para uma base de dados?
    public CurrentItinerary getCurrentItinerary(){return currentItinerary;}
    public List<Itinerary> getCompletedItineraries(){
        return Collections.unmodifiableList(completedItineraries);
    }
    public List<Itinerary> getCreatedItineraries(){
        return Collections.unmodifiableList(createdItineraries);
    }

    /**
     * Quando completamos um itinerario
     * @param score -> Score a adicionar ao exp do utilizador
     * @results currentItinerary = null && currentHotspotIndice = -1
     * CurrentItinerary passa a null e eh adicionado a score ao utilizador
     */
    public void completeItinerary(Double score){
        addExperience(score * 100);
        this.currentItinerary = null;
    }
    public void addCompletedItinerary(Itinerary i){
        completedItineraries.add(i);
        new UserDatabaseConnection(userID).addCompletedIten(i);
    }
    public void addCreatedItinerary(Itinerary i){
        createdItineraries.add(i);
        createdItin.add(i.getItenID());
        new ItineraryDatabaseConnection().addItinerary(i);
        new UserDatabaseConnection(userID).addCreatedIten(i);

        //prov posso tirar isto daqui
        new ItineraryCatalog().addItineraryToDatabase(i);
    }
    //TODO -> o que estamos a fazer
    public void setCurrentItinerary(Itinerary itinerary){
        this.currentItinerary = new CurrentItinerary(itinerary);
        udc.setCurrentItinerary(currentItinerary); //teste
    }


    public void setCreatedItin(List<Itinerary> itens){
        this.createdItineraries = itens;
    }
    public void setCompletedItin(List<Itinerary> itens){this.completedItineraries = itens;}
    public void setName(String name){this.name = name;}
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
    public String getUserID(){return userID;}
    public Hotspot nextHotspot(){
        return currentItinerary.nextHotspot();
    }

    public void addScoreToItinerary(double score){currentItinerary.addScore(score);}

    public void addExperience(double exp){
        this.exp += exp;
        if(increasesLevel()){
            this.exp = 0;
            level++;
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
        //falta meter o resto -> TODO
    }

    //TODO
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

        //buscar a lista de criados
        DataSnapshot crtSnaps = snap.child(DBConstants.REFERENCE_CREATED_ITEN);
        for(DataSnapshot s : crtSnaps.getChildren()){
            createdItin.add(s.getKey());
        }

        DataSnapshot cmpSnaps = snap.child(DBConstants.REFERENCE_COMPLETED_ITEN);
        for(DataSnapshot s : cmpSnaps.getChildren()){
            completedItin.add(s.getKey());
        }
    }
}
