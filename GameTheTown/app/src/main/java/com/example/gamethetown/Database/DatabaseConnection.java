package com.example.gamethetown.Database;

import com.example.gamethetown.interfaces.InTheDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DatabaseConnection {

    public DatabaseReference rootRef;

    public DatabaseConnection(){
        rootRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getReference(){return rootRef;}

    //ideia, tirar daqui a DatabaseReference e cada coisa que implementa a interface tem de
    //conseguir dar a sua propria referencia.
    public void setValue(InTheDatabase itD,DatabaseReference currentRef,Object obj){
        //root of the current iten

        itD.setValueInDatabase(currentRef,null);
    }

}
