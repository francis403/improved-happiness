package com.example.gamethetown;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gamethetown.fragment.Map_Current_Iten;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

//TODO -> precisa de receber a informacao toda
public class CurrentItenerary extends App_Menu {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itenerary_new);

    }


    public void goToMap(View view){
        Intent intent = new Intent(this, Map_Current_Iten.class);
        startActivity(intent);
    }
}
