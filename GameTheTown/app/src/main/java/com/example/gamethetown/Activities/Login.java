package com.example.gamethetown.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.example.gamethetown.R;

public class Login extends AppCompatActivity {

    private PopupWindow popwindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * When the register button is pressed
     */
    /**
    public void onRegisterPress(View view) {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popregister,null);
        //size
        popwindow = new PopupWindow(container,height/2,width/2,true);
        //position
        popwindow.showAtLocation((ConstraintLayout)findViewById(R.id.login),
                Gravity.NO_GRAVITY,0,0);

        //startActivity(new Intent(this,Pop.class));
    }
    **/
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
