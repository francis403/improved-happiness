package com.example.gamethetown.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gamethetown.R;

public class Reg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    public void goToLogin(View view){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

}
