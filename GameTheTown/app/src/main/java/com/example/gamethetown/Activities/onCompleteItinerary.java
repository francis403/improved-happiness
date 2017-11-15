package com.example.gamethetown.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;

public class onCompleteItinerary extends App_Menu {

    private Itinerary completedItinerary;
    private double score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_complete_itinerary);

        completedItinerary = user.getCurrentItinerary().getItinerary();
        //set view
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(completedItinerary.getTitle());

        ((TextView)findViewById(R.id.nameCreator))
                .setText(completedItinerary.getCreator().getName());
        ((TextView)findViewById(R.id.nameCreator))
                .setText(completedItinerary.getCreator().getName());
        ((TextView)findViewById(R.id.score))
                .setText("Score: " + user.getCurrentItinerary().getScore());
        ((TextView)findViewById(R.id.time))
                .setText("Duration: " + (user.getCurrentItinerary().getDuration()/1000) + "s");

        Spinner spinner = (Spinner) findViewById(R.id.dif);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.iten_dif, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        //complete the itinerary
        user.completeItinerary(score);
    }


    public void finishItinerary(View view){
        user.addCompletedItinerary(completedItinerary);
        finish();

    }

    @Override
    public void finish(){
        Log.e("LISTS","Size of completed: " + user.getCompletedItineraries().size());
        super.finish();
    }
}
