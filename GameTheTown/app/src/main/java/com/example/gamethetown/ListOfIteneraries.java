package com.example.gamethetown;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by franc on 22/10/2017.
 */

public class ListOfIteneraries extends App_Menu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_itenerary);

        Spinner spinnerDif = (Spinner) findViewById(R.id.spinner_dif);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.iten_dif, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDif.setAdapter(adapter);

        Spinner spinnerProx = (Spinner) findViewById(R.id.spinner_prox);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.iten_prox, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerProx.setAdapter(adapter);

    }
}
