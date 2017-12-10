package com.example.gamethetown.interfaces;

/**
 * This should be what every controller of the game being played is able to do
 */
public interface GameController {

    /**
     * Starts the game with provided extra information
     * @param o -> extra information provided
     */
    void start(Object o);
}
