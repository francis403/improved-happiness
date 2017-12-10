package com.example.gamethetown.Enums;

import android.os.Build;
import android.support.annotation.RequiresApi;

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

    public static List<String> getNames() {
        List<String> res = new ArrayList<>();
        for(Difficulties d : Difficulties.values())
            res.add(d.name);
        return res;
    }
    @Override
    public String toString() {
        return this.name;
    }

}
