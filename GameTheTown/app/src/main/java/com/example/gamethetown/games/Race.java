package com.example.gamethetown.games;

import com.example.gamethetown.gameControllers.HotspotRace;
import com.example.gamethetown.item.Hotspot;

import java.text.DecimalFormat;

/**
 * Created by franc on 11/11/2017.
 */

public class Race extends CurrentGame {

    private long startTime;
    private long endTime;
    public static final long MINUTE = 60 * 1000;
    public static final long HOUR = 60 * MINUTE;
    private static final long DURATION_INTERVAL = 10 * MINUTE; //10 minutes

    public Race(){
        super("Race");
    }

    @Override
    public void startGame() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public Class getGameClass() {
        return HotspotRace.class;
    }

    //race is always complete
    @Override
    public boolean isComplete() {
        return true;
    }

    public void finishGame(){
        endTime = System.currentTimeMillis();
    }

    public long getTime(){
        return (endTime - startTime)/1000;
    }
    @Override
    public Double getScore() {
        return Double.parseDouble(new DecimalFormat("##.##").format(1000/(getTime() + 1)));
        //every second is a point

    }
}
