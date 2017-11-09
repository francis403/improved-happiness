package com.example.gamethetown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Start user query
     * if query is successfull login
     * else no login
     * @param view
     */
    public void onLoginPress(View view){

        //Check if everything is fine

        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

    public void GoToRegister(View view){
        Intent intent = new Intent(this,Reg.class);
        startActivity(intent);
    }

}
