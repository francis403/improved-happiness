package com.example.gamethetown.item;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Itinerary implements Serializable,InTheDatabase{

    private static final int DEFAULT_IMAGE_ID = R.drawable.no_image;
    //preciso de guardar o id de quem criou
    private String userID;
    //id do itinerario em si
    private String itenID;
    private String name;
    private float rating;
    private int times_completed; //default value
    private Bitmap image;
    private int imageID;
    private String imagePath; // por agora temos o image path so para caso

    private Date creatDate;
    private String description;
    private Enum<Difficulties> dif;
    private User creator;

    private boolean hasPhoto = true;
    private List<Hotspot> hotspots = new ArrayList<>();

    //acho que posso tirar daqui
    public Itinerary(){
        imageID = DEFAULT_IMAGE_ID;
    }
    //usado para ir buscar os valores da base de dados
    public Itinerary(DataSnapshot snap){
        getValueInDatabase(snap,null);
        imageID = DEFAULT_IMAGE_ID;
    }

    public Itinerary(String name,Date creatDate){
        hotspots = new ArrayList<>();
        this.name = name;
        this.creatDate = creatDate;
        imageID = DEFAULT_IMAGE_ID;
    }

    public Itinerary(String name,Date creatDate,List<Hotspot> list,Difficulties dif){
        this.name = name;
        this.creatDate = creatDate;
        hotspots = list;
        this.dif = dif;
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

    public float getRating(){return rating;}
    public int getTimesCompleted(){return times_completed;}

    public boolean hasPhoto(){return hasPhoto;}
    public void setHasPhoto(boolean hasPhoto){this.hasPhoto = hasPhoto;}
    /**
     * Verifica se funciona com a lista de preHotspots
     * @param preHotspots -> hotspots ainda nao confirmados
     * @return
     */
    public boolean preCompleted(Collection<Hotspot> preHotspots){
        for(Hotspot h : preHotspots)
            if(!h.isComplete())
                return false;
        return preHotspots.size() >= 1 && name != null
                && description != null && creatDate != null && dif != null;
    }

    public String getItenID(){
        return itenID;
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
    public String getName(){return name;}
    public Date getCreationDate(){return creatDate;}
    public int getImageID(){return imageID;}
    public int getNumberOfHotspots(){return hotspots.size();}
    public Hotspot getFirstHotspot(){return hotspots.get(0);}
    public String getImagePath(){return imagePath;}
    public List<Hotspot> getHotSpotList(){
        return Collections.unmodifiableList(hotspots);
    }
    public String getDescription(){return description;}
    public Bitmap getImageBitmap(){return image;}
    public String getDifficulty(){
        if(dif == null)
            return null;
        return dif.toString();
    }
    public int numberOfHotspot(){return hotspots.size();}

    public void setImagePath(String imagePath){this.imagePath = imagePath;}
    public void setImageBitmap(Bitmap image){this.image = image;}
    public void setCreator(User creator){this.creator = creator;}
    public void setDifficulty(Difficulties dif){this.dif = dif;}
    public void setTitle(String title){this.name = title;}
    public void setDescription(String description){this.description = description;}
    public void setDate(Date date){this.creatDate = date;}
    public void addHotspot(Hotspot hotspot){hotspots.add(hotspot);}
    public void setImageID(int imageID){this.imageID = imageID;}

    /**
     *
     * @param parentRef -> Reference to where the new reference should be made
     * @param obj
     *
     */
    @Override
    public void setValueInDatabase(DatabaseReference parentRef, Object obj) {
        itenID = parentRef.push().getKey();

        DatabaseReference itenRef = parentRef.child(itenID);

        itenRef.child("name").setValue(name);
        itenRef.child("description").setValue(description);
        itenRef.child("date").setValue(creatDate);
        itenRef.child("dif").setValue(dif);

        if(userID != null && !userID.equals("")) //aqui so por causa dos que ja estao criados
            itenRef.child("creatorID").setValue(userID);

        DatabaseReference hotspotRef = itenRef.child("hotspots");
        int i = 1;
        for(Hotspot h : getHotSpotList()){
            h.setValueInDatabase(hotspotRef,i);
            i++;
        }

        itenRef.child("rating").setValue(rating);
        itenRef.child("completed").setValue(times_completed);

        StorageDatabase sb = new StorageDatabase();
        //Bitmap bit = BitmapFactory.decodeFile(imagePath);
        if(image != null)
            sb.setItenPhoto(itenID,image);

    }

    @Override
    public void getValueInDatabase(DataSnapshot snap, Object obj) {
        //get basic values
        this.itenID = snap.getKey();
        this.userID = snap.child("creatorID").getValue(String.class);
        this.name = snap.child("name").getValue(String.class);
        this.description = snap.child("description").getValue(String.class);

        this.dif = Difficulties.valueOf(snap.child("dif").getValue(String.class));

        this.creatDate = snap.child("date").getValue(Date.class);

        //get hotspots
        DataSnapshot hotRef = snap.child("hotspots");
        for(DataSnapshot s : hotRef.getChildren())
            hotspots.add(new Hotspot(s));

        //Rating
        DataSnapshot ratRef = snap.child("rating");
        if(ratRef.exists())
            this.rating = ratRef.getValue(Float.class);
        else
            this.rating = 0;
        this.times_completed = snap.child("completed").getValue(Integer.class);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
