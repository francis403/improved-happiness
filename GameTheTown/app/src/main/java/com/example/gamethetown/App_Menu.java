package com.example.gamethetown;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.gamethetown.item.User;

/**
 * Created by franc on 21/10/2017.
 * An activity that will use our menu has to extend this class
 */

public class App_Menu extends AppCompatActivity {

    //ja fizemos o login por isso precisamos do user
    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //TODO -> get logined user
        //find user
    }

    public User getUser(){return user;}

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                goToProfile(item.getActionView());
                return true;
            case R.id.action_create_itenerary:
                createIten(item.getActionView());
                return true;
            case R.id.action_itenerary:
                currentIten(item.getActionView());
                return true;
            case R.id.action_select_itenerary:
                selectItenerary(item.getActionView());
                return true;
            case R.id.action_log_out:
                log_out(item.getActionView());
                return true;
            case R.id.action_settings:
                settings(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToProfile(View view) {
        //verificar se ja nao estamos no login
        if(findViewById(R.id.profile) == null){
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
        }
    }

    //Vai para a pagina dos settings
    public void settings(View view){
        //do something
    }

    public void createIten(View view){
        if(findViewById(R.id.create_iten) == null) {
            Intent intent = new Intent(this, CreateItenerary.class);
            startActivity(intent);
        }
    }

    public void currentIten(View view){
        if(findViewById(R.id.current_iten) == null) {
            Intent intent = new Intent(this, CurrentItenerary.class);
            startActivity(intent);
        }
    }

    public void log_out(View view){
        if(findViewById(R.id.login) == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

    public void selectItenerary(View view){
        if(findViewById(R.id.list_itenerary) == null) {
            Intent intent = new Intent(this, SearchAllItineraries.class);
            startActivity(intent);
        }
    }

    public void selectTest(View view){
        Intent intent = new Intent(this,SearchAllItineraries.class);
        startActivity(intent);
    }

}
