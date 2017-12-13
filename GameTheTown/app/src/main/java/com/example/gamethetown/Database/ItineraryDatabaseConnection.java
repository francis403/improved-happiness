package com.example.gamethetown.Database;

import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.Storage.ImageItineraryGetter;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItineraryDatabaseConnection extends DatabaseConnection {

    /**
     * Reference to where all the Itineraries are being stored
     */
    private DatabaseReference itensRef;

    /**
     * List with of all Itineraries in a local format
     * Used to simplify searches and help filter
     */
    private static List<Itinerary> localList;

    public ItineraryDatabaseConnection(){
        if(localList == null)
            localList = new ArrayList<>();
        itensRef = rootRef.child(DBConstants.REFERENCE_ITINERARIES);
    }

    @Override
    public DatabaseReference getReference(){return itensRef;}

    public void addItinerary(Itinerary iten){
        //true para dizer que ele nao vai precisar de uma pasta so para ele
        iten.setValueInDatabase(itensRef,true);
    }

    public void allItinerariesIntoMap(@NonNull final GoogleMap mMap,@ NonNull final Map mapItineraries){

        itensRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot s : dataSnapshot.getChildren()) {
                    Itinerary iten = new Itinerary(s);
                    Hotspot h  = iten.getFirstHotspot();
                    Marker m = mMap.addMarker(new MarkerOptions().
                            position(h.getPosition()).title(iten.getName()));
                    mapItineraries.put(m.getId(),iten);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });
    }

    public void allItineraries(final ItineraryAdapter ia){
        localList.clear();
        itensRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot s : dataSnapshot.getChildren()) {
                    final Itinerary iten = new Itinerary(s);
                    getImage(iten,ia);
                    //adicionamos ha lista local
                    localList.add(iten);
                }
                ia.setList(localList);
                ia.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });
    }


    public void filterItineraries(
            ItineraryAdapter ia,GoogleMap mMap,final Map mapItineraries,
            String searchString, Difficulties selDif, int maxDist,
            Location userLocation){

        mMap.clear(); //delete all markers from the map
        mapItineraries.clear();

        List<Itinerary> filtered = new ArrayList<>();
        for(Itinerary i : localList){
            if(i.getName().toLowerCase().contains(searchString.toLowerCase())) {
                if(selDif.equalsName("ALL") ||
                        i.getDifficulty().equals(selDif.name())) {
                    boolean closeEnough = userLocation.distanceTo(
                            i.getFirstHotspot().getLocation()) < maxDist * 1000;
                    if(maxDist == 0 || closeEnough) {
                        Hotspot h = i.getFirstHotspot();
                        Marker m = mMap.addMarker(new MarkerOptions().
                                position(h.getPosition()).title(i.getName()));
                        mapItineraries.put(m.getId(),i);
                        filtered.add(i);
                    }
                }
            }
        }
        ia.setList(filtered);
        ia.notifyDataSetChanged();
    }

    /**
     *
     * @param ia -> Adapter a meter a lista
     * @param userID -> O Id do user que queremos ir buscar
     * @param constant -> onde informarmos aquilo que queremos isr buscar (created, completed,...)
     */
    public void allUserItineraries(final ItineraryAdapter ia, final String userID,
                                   final String constant){
        itensRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot itenSnapshot) {
                final List<Itinerary> itens = new ArrayList<>();
                UserDatabaseConnection udc = new UserDatabaseConnection(userID);
                DatabaseReference dbRef = udc.getReference().child(userID).child(constant);
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot userSnapshot) {
                        for(DataSnapshot itenIDRef : userSnapshot.getChildren()){
                            String itenID = itenIDRef.getKey();
                            Itinerary iten = new Itinerary(itenSnapshot.child(itenID));
                            getImage(iten,ia);
                            itens.add(iten);
                        }
                        ia.setList(itens);
                        ia.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Changes the rating in the database of the itinerary
     * @param rating
     * @param iten
     */
    public void setRating(Itinerary iten,double rating){
        DatabaseReference mItenRef = itensRef.child(iten.getItenID());
        int nr_compl = iten.getTimesCompleted() + 1;
        double newRating = (rating + iten.getRating())/nr_compl;
        mItenRef.child("completed").setValue(nr_compl);
        mItenRef.child("rating").setValue(newRating);
    }

    private void getImage(final Itinerary iten,final ItineraryAdapter ia){
        StorageDatabase sb = new StorageDatabase();
        sb.getItenPhoto(iten.getItenID()).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageItineraryGetter ig = new ImageItineraryGetter(iten, ia);
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
                iten.setHasPhoto(false);
            }
        });
    }

}
