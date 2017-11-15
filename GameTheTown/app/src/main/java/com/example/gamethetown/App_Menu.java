package com.example.gamethetown;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gamethetown.Activities.CreateItenerary;
import com.example.gamethetown.Activities.CurrentItenerary;
import com.example.gamethetown.Activities.Login;
import com.example.gamethetown.Activities.Profile;
import com.example.gamethetown.Activities.SearchAllItineraries;
import com.example.gamethetown.games.ImagePuzzle;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.games.Race;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by franc on 21/10/2017.
 * An activity that will use our menu has to extend this class
 */

public class App_Menu extends AppCompatActivity {

    //ALL CODES CAN GO IN HERE
    protected static final int SELECT_PICTURE = 0;
    //ja fizemos o login por isso precisamos do user
    // O user vai ser sempre o mesmo para a aplicacao toda
    protected static User user = new User("Francis", "password1234");
    protected BottomSheetDialog bsd;

    //DEFAULT VALUES (TODO -> REMOVE WHEN DATABASE IS DONE
    protected List<Itinerary> itineraries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        bsd = new BottomSheetDialog(this);
        //TODO -> get logined user
        //find user
        //setDefaultUser();
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


    //TODO -> delete after database is made
    public void setDefaultUser(){

        Itinerary iten = new Itinerary("Aventura pelo Monteiro Dos Jeronimos",new Date());
        User creator = new User("Joao Leal","123321");

        Hotspot hot = new Hotspot();
        LatLng position = new LatLng(38.7561689,-9.1556714);
        Quiz quiz = new Quiz();
        Race race = new Race();
        ImagePuzzle imgPzl = new ImagePuzzle(R.drawable.background3);
        ImagePuzzle imgPzl2 = new ImagePuzzle(R.drawable.background2);

        setQuiz(quiz,1);
        hot.setPosition(position);
        hot.setName("Teste do fazer");
        hot.setGame(quiz);
        iten.addHotspot(hot);

        Hotspot hot2 = new Hotspot();
        hot2.setName("100 Montaditos");
        hot2.setPosition(new LatLng(38.7524403,-9.134773));
        hot2.setGame(imgPzl);
        iten.addHotspot(hot2);
        iten.setCreator(creator);

        Hotspot hot3 = new Hotspot();
        hot3.setName("Novo sitio qualquer");
        hot3.setPosition(new LatLng(38.7561689,-9.1556714));
        hot3.setGame(imgPzl2);
        //iten.addHotspot(hot3);

        iten.setCreator(creator);
        user.setCurrentItinerary(iten);
    }

    private void setQuiz(Quiz quiz,int n){
        quiz.setName("Quiz" + n);
        quiz.setQuestion("Teste do quiz numero " + n);
        quiz.setAsw1("Respota 1");
        quiz.setAsw2("Respota 2");
        quiz.setAsw3("Resposta 3");
        quiz.setAsw4("Resposta 4");
        quiz.setCorrectAsw(2);
    }

    //copia de um metodo que ja tinhamos antes, a ver se mudamos de sitio
    public View bottomSheetUp(int layoutID){
        View sView = getLayoutInflater().inflate(layoutID,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics()));
        bsd.show();
        return sView;
    }

    //retorna simplesmente a view daquele id
    public View getView(int layoutID){
        return getLayoutInflater().inflate(layoutID,null);
    }

}
