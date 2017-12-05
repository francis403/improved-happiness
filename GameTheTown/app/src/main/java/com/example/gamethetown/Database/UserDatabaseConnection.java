package com.example.gamethetown.Database;


import android.util.Log;

import com.example.gamethetown.item.CurrentItinerary;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
    //TODO -> Vou ter de mudar isto para estar numa colecao chamada Users
    // oara ouder comecar a fazer a lista de itineraruios
    public UserDatabaseConnection(String userID){
        this.userID = userID;
        usersRef = rootRef.child(DBConstants.REFERENCE_USERS);
        mUserRef = usersRef.child(userID);
        lvlReference = mUserRef.child(level);

        setListeners();

    }

    @Override
    public DatabaseReference getReference(){return usersRef;}

    //TODO -> posso apagar
    public void setListeners(){
        Log.e("Entra aqui","Entra no getUserLvl");
        lvlReference = mUserRef.child(level);
        lvlReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    lvlHelper = dataSnapshot.getValue(Integer.class);
                } else {
                    lvlHelper = 1;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //do nothing
                Log.e("DAtabase Error: " ,databaseError.getMessage());
            }
        });
    }

    //nao vejo outra maneira de buscar os valores
    public DatabaseReference getLvlRef(){return mUserRef.child(DBConstants.REFERENCE_LEVEL);}
    //TODO
    public DatabaseReference getExpRef(){return mUserRef.child(DBConstants.REFERENCE_EXP);}

    public void setUserLvl(int level){
        DatabaseReference lvlReference = mUserRef.child(DBConstants.REFERENCE_LEVEL);
        lvlReference.setValue(level);
    }
    /**
    public void setUserDefaultInfo(){
        setUserLvl(1);
    }
     **/

    public void setUser(User user){
        user.setValueInDatabase(usersRef,null);
    }

    //teste
    public void setCurrentItinerary(CurrentItinerary currIten){
        //root of the current iten
        Log.e("POS","No setCurrentItinerary");
        if(currIten == null)
            Log.e("POS","CurrentItinerary == null");
        else
            Log.e("POS","CurrentItinerary != null");
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


}
