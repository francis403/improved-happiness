package com.example.gamethetown.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Storage.StorageDatabase;
import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends App_Menu {

    @InjectView(R.id.name) TextView _nameText;
    @InjectView(R.id.level) TextView _levelText;
    @InjectView(R.id.profilePic) CircleImageView _profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        //String name = new UserAuthentication().getUser().getDisplayName();
        _nameText.setText("Name: " + user.getName());
        _levelText.setText("Level: " + user.getLevel());
        _profileImage.setImageResource(user.getImageID());
    }

    // TODO -> preciso de ir buscar os itinerarios
    public void checkCreatedIten(View view){

        ItineraryDatabaseConnection idc = new ItineraryDatabaseConnection();
        idc.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Itinerary> itens = new ArrayList<>();

                for(String s : user.getCrtItin()){
                    //nao sei porque esta isto a acontecer
                    if(s != null) {
                        DataSnapshot mItin = dataSnapshot.child(s);
                        itens.add(new Itinerary(mItin));
                    }
                }
                user.setCreatedItin(itens);

                Intent intent = new Intent(Profile.this,ListOfCreatedItineraries.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void checkCompletedIten(View view){

        ItineraryDatabaseConnection idc = new ItineraryDatabaseConnection();
        idc.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Itinerary> itens = new ArrayList<>();
                StorageDatabase sd = new StorageDatabase();
                for(String s : user.getCompletedItin()){
                    //nao sei porque esta isto a acontecer
                    if(s != null) {
                        DataSnapshot mItin = dataSnapshot.child(s);
                        Itinerary iten = new Itinerary(mItin);
                        itens.add(iten);
                    }
                }
                user.setCompletedItin(itens);

                Intent intent = new Intent(Profile.this,ListOfCompletedItineraries.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onResume(){
        //caso tenha subido de nivel
        _levelText.setText("Level: " + user.getLevel());
        super.onResume();
    }

}
