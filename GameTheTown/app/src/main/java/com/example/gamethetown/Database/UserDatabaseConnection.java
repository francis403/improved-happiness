package com.example.gamethetown.Database;


import com.example.gamethetown.item.CurrentItinerary;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.User;
import com.google.firebase.database.DatabaseReference;

public class UserDatabaseConnection extends DatabaseConnection{

    private static String name = "name";
    private static String level = "level";
    private static String exp = "exp";

    //estou a pensar por o email do user porque assim garantimos que nao ha ninguem igual
    private String userID;
    private int lvlHelper;
    private DatabaseReference usersRef;
    private DatabaseReference mUserRef;
    private DatabaseReference lvlReference;

    // oara ouder comecar a fazer a lista de itineraruios
    public UserDatabaseConnection(String userID){
        this.userID = userID;
        usersRef = rootRef.child(DBConstants.REFERENCE_USERS);
        mUserRef = usersRef.child(userID);
        lvlReference = mUserRef.child(level);

    }

    @Override
    public DatabaseReference getReference(){return usersRef;}

    public void updateIndiceOfItinerary(int old_indice){
        mUserRef.child(DBConstants.REFERENCE_CURRENT_ITINERARY)
                .child("indice").setValue(old_indice + 1);
    }

    public void updateScoreOfItinerary(Double newScore,Double oldScore){
        mUserRef.child(DBConstants.REFERENCE_CURRENT_ITINERARY)
                .child("score").setValue(oldScore + newScore);
    }

    public void setUserLvl(int level){
        DatabaseReference lvlReference = mUserRef.child(DBConstants.REFERENCE_LEVEL);
        lvlReference.setValue(level);
    }

    public void setUser(User user){
        user.setValueInDatabase(usersRef,null);
    }

    //teste
    public void setCurrentItinerary(CurrentItinerary currIten){
        DatabaseReference currItenRef = mUserRef.child(DBConstants.REFERENCE_CURRENT_ITINERARY);
        currItenRef.setValue(null); //apagar o que tiver la antes
        //set basic info
        currIten.setValueInDatabase(mUserRef,null);
    }

    public void removeCurrentItinerary(){
        mUserRef.child(DBConstants.REFERENCE_CURRENT_ITINERARY)
                .setValue(null);
    }

    public void setLevelInDatabase(int level){
        mUserRef.child(DBConstants.REFERENCE_LEVEL)
                .setValue(level);
    }

    public void addCreatedIten(Itinerary iten){
        mUserRef.child(DBConstants.REFERENCE_CREATED_ITEN)
                .child(iten.getItenID()).setValue(true); //created
    }

    public void addCompletedIten(Itinerary iten){
        mUserRef.child(DBConstants.REFERENCE_COMPLETED_ITEN)
                .child(iten.getItenID()).setValue(true); //created
    }


    public void setExpInDatabase(double exp) {
        mUserRef.child(DBConstants.REFERENCE_EXP)
                .setValue(exp);
    }
}
