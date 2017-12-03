package com.example.gamethetown.Enums;

/**
 * Created by franc on 05/11/2017.
 */

public enum Difficulties {
    JUST_TRAVEL ("Just travel"),
    EASY ("Easy"),
    MEDIUM ("Medium"),
    HARD ("Hard");


    private final String name;

    private Difficulties(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
