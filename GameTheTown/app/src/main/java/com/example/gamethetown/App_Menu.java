package com.example.gamethetown;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.example.gamethetown.Activities.CreateItenerary;
import com.example.gamethetown.Activities.CurrentItenerary;
import com.example.gamethetown.Activities.Login;
import com.example.gamethetown.Activities.Profile;
import com.example.gamethetown.Activities.SearchAllItineraries;
import com.example.gamethetown.Database.UserAuthentication;
import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.Dialogs.AlertDialogs.LoadingDialog;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * An activity that will use our menu has to extend this class
 */

public class App_Menu extends AppCompatActivity {


    //TODO -> tirar todos os new e passar para um lado onde estao sempre

    protected static UserAuthentication ua = new UserAuthentication();
    protected static ItineraryDatabaseConnection idc =
            new ItineraryDatabaseConnection();
    protected static User user = ua.getCurrentUser();
    protected BottomSheetDialog bsd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        bsd = new BottomSheetDialog(this);
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
        if(user.getCurrentItinerary() == null){
            Intent intent = new Intent(App_Menu.this, CurrentItenerary.class);
            startActivity(intent);
            return;
        }
        final Itinerary iten = user.getCurrentItinerary().getCurrentItinerary();
        final String itenID = user.getCurrentItinerary().getCurrentItineraryID();
        if(findViewById(R.id.current_iten) == null) {
            //caso nao tenhamos nada
            if(itenID == null){
                Log.e("itenID","iten == null");
                Intent intent = new Intent(App_Menu.this, CurrentItenerary.class);
                startActivity(intent);
                return;
            }
            //caso ja o tenhamos nao vale a pena ir buscar novamente
            if(iten != null){
                Log.e("iten","iten != null");
                Intent intent = new Intent(App_Menu.this, CurrentItenerary.class);
                startActivity(intent);
                //setUserInItinerary(iten);
                return;
            }

            final LoadingDialog ld = new LoadingDialog(
                    App_Menu.this,"Loading Current Itinerary");

            //ir bucasr caso tenhamos mas nao o tenhamos escolhido
            ItineraryDatabaseConnection idc = new ItineraryDatabaseConnection();
            idc.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("estamos a buscar iten","antes de o criar");
                    Itinerary iten = new Itinerary(dataSnapshot.child(itenID));
                    Log.e("ChegaAqui","Chega aqui!");
                    setUserInItinerary(iten);
                    ld.hide();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    ld.hide();
                }
            });
            //setUserInItinerary(iten);

        }
    }
    //TODO -> acho que nao preciso disto
    //meto apenas a fotos
    private void setUserInItinerary(final Itinerary iten){
        //caso tenhamos sido nos nao vale a pena ir buscar novamente
        user.setCurrentItinerary(iten);
        Intent intent = new Intent(App_Menu.this, CurrentItenerary.class);
        startActivity(intent);
    }

    public void log_out(View view){
        new UserAuthentication().log_out();

        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void selectItenerary(View view){

        Intent intent = new Intent(this, SearchAllItineraries.class);
        startActivity(intent);

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
