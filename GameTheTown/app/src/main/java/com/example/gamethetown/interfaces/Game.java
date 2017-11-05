package com.example.gamethetown.interfaces;

import java.io.Serializable;

/**
 * Created by franc on 02/11/2017.
 */
//TODO
public interface Game {

    String getGameName();

    //nem sei se vamos precisar deste
    boolean isFinished();
    boolean isCorrect();

    /**
     * Funciona para ver se o jogo ja esta completamente feito, assim vemos
     * se ja podemos confirmar a criacao do jogo
     * @return
     */
    boolean isComplete();
}
