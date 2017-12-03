package com.example.gamethetown.Database;

import com.example.gamethetown.item.Itinerary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by franc on 28/11/2017.
 */

public class ItineraryDatabaseConnection extends DatabaseConnection {
    private DatabaseReference itensRef;

    public ItineraryDatabaseConnection(){
        itensRef = rootRef.child(DBConstants.REFERENCE_ITINERARIES);
    }
    @Override
    public DatabaseReference getReference(){return itensRef;}

    public void addItinerary(Itinerary iten){
        //Todo -> aqui tinha de enviar o numero que queria que isto tivesse como id
        //true para dizer que ele nao vai precisar de uma pasta so para ele
        //enviar por aqui o ID
        iten.setValueInDatabase(itensRef,true);
    }

    public Itinerary getItineraryByID(DataSnapshot itens, String itenID){
        DataSnapshot mItenSnap = itens.child(itenID);
        return new Itinerary(mItenSnap);
    }

}
