package com.example.gamethetown.gameControllers;

import android.os.Bundle;
import android.widget.TextView;

import com.example.gamethetown.R;
import com.example.gamethetown.games.Race;

public class HotspotRace extends HotspotDoerController {

    Race race;
    private Bundle extras;
    double score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_race);

        setIsCorrect(true);
        setAnsweared(true);

        extras = getIntent().getExtras();
        if(extras != null) {
            race = (Race) extras.get("game");
            race.finishGame();
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            String title = race.getGameName();
            ab.setTitle(title);
            score = race.getScore();
            ((TextView) findViewById(R.id.time)).setText("Time : " + (int)race.getTime());
            ((TextView) findViewById(R.id.score)).setText("Score: " + score);

        }
    }

    @Override
    public void start(Object o) {
        //Do nothing yet
    }
}
