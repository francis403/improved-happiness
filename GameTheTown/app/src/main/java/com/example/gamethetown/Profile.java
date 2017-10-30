package com.example.gamethetown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Profile extends App_Menu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);
    }

    public void checkCreatedIten(View view){
        Intent intent = new Intent(this,ListOfCreatedItineraries.class);
        startActivity(intent);
    }

    public void checkCompletedIten(View view){
        Intent intent = new Intent(this,ListOfCompletedItineraries.class);
        startActivity(intent);
    }
}
