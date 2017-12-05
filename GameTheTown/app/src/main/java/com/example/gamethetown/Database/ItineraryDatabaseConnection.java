package com.example.gamethetown.Database;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.gamethetown.Storage.ImageItineraryGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    public void allItineraries(final ItineraryAdapter ia){
        itensRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Itinerary> itens = new ArrayList<>();

               for(DataSnapshot s : dataSnapshot.getChildren())
                   itens.add(new Itinerary(s));

                //vamos passar por cada itinerario e esperar para ver se tem foto
                for(final Itinerary i : itens) {
                    StorageDatabase sb = new StorageDatabase();
                    sb.getItenPhoto(i.getItenID()).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ImageItineraryGetter ig = new ImageItineraryGetter(i,ia);
                            try {
                                ig.execute(uri.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //do nothing
                        }
                    });
                }
                ia.setList(itens);
                ia.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });
    }

    public void getHotspotImage(ImageView imageView,String itenID,int i){
        StorageDatabase sb = new StorageDatabase();
        sb.getHotspotPhoto(itenID,i).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

            }
        });
    }

}
