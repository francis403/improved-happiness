package com.example.gamethetown.interfaces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * IDEIA -> Todas as classes que queiram ir para a base de dados tem de implementar isto
 * assim chamamos isto e envia para a base de dados
 * a classe chama isto a todos os seus objectos mais complicados
 * e assim simplifica
 */

public interface InTheDatabase {

    /**
     * @param parentRef -> Reference to where the new reference should be made
     * @param obj -> Object passed in case we need something from there
     *               not sure if needed ^
     */
    void setValueInDatabase(DatabaseReference parentRef,Object obj);

    //IDEIA
    //nem sei se possivel
    /**
     * Gets a value in the database
     * @param snap -> o snapshot para irmos buscar os valores
     * @param obj -> extra data if needed
     * @return
     */
    void getValueInDatabase(DataSnapshot snap, Object obj);

    //ideia -> enviar o id especifico dele
}
