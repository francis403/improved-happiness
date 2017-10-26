package com.example.gamethetown;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gamethetown.fragment.Map_Current_Iten;

public class Itenerary extends App_Menu {

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
