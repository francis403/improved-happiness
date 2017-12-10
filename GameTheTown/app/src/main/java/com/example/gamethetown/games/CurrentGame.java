package com.example.gamethetown.games;

import com.example.gamethetown.R;
import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public abstract class CurrentGame implements Serializable,InTheDatabase {

    protected static final int DEFAULT_IMAGE_ID = R.drawable.no_image;
    private String name;

    public CurrentGame(DataSnapshot snap,String name){
        getValueInDatabase(snap,null );
        this.name = name;
    }

    public CurrentGame(String name){this.name = name;}

    /**
     * Sets the photo of the specific game
     * @param itenID
     * @param i
     */
    public abstract void setPhoto(String itenID, int i);

    public String getGameName() {
        return name;
    }
    public void setName(String name){this.name = name;}

    public abstract void startGame();
    public abstract Class getGameClass();
    public abstract boolean isComplete();
    public abstract Double getScore();

}
