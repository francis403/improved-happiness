package com.example.gamethetown.Enums;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.gamethetown.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by franc on 05/11/2017.
 */

public enum Difficulties {
    ALL ("ALL"),
    JUST_TRAVEL ("JUST_TRAVEL"),
    EASY ("EASY"),
    MEDIUM ("MEDIUM"),
    HARD ("HARD");


    private final String name;

    private Difficulties(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Gets the id of the difficulty
     * Default value is Just Travel
     * @param dif -> difficulty we want the id of
     * @return the id of dif
     */
    public static int getID(Difficulties dif){
        switch (dif){
            case JUST_TRAVEL: return R.id.justTravel;
            case EASY: return R.id.easy;
            case MEDIUM: return R.id.medium;
            case HARD: return R.id.hard;
            default: return R.id.justTravel;
        }
    }

    public static Difficulties getDifFromID(int id){
        switch (id){
            case R.id.justTravel: return Difficulties.JUST_TRAVEL;
            case R.id.easy: return Difficulties.EASY;
            case R.id.medium: return Difficulties.MEDIUM;
            case R.id.hard: return Difficulties.HARD;
            default: return Difficulties.JUST_TRAVEL;
        }
    }

}
