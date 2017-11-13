package com.example.gamethetown.gameControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamethetown.R;
import com.example.gamethetown.games.Race;

public class HotspotRace extends AppCompatActivity {

    Race race;
    private Bundle extras;
    private boolean isCorrect = true,answeared = true; //estes vao ser sempre true
    double score;
    //TODO -> BUG, not adding score
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_race);

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
    public void finish(){
        Intent data = new Intent();
        data.putExtra("answeared",answeared);
        data.putExtra("passed",isCorrect);
        data.putExtra("score",score);
        setResult(RESULT_OK,data);
        super.finish();
    }
}
