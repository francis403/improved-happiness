package com.example.gamethetown.Catalogs;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Dialogs.AlertDialogs.SelectItineraryDialog;
import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;
import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.fragment.FilterFragment;
import com.example.gamethetown.fragment.List_Test;
import com.example.gamethetown.games.ImagePuzzle;
import com.example.gamethetown.games.Quiz;
import com.example.gamethetown.games.Race;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Hotspot;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;
import com.example.gamethetown.item.User;
import com.google.android.gms.maps.model.LatLng;
//import com.example.gamethetown.item.RecyclerTouchListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by franc on 22/10/2017.
 */

/**
 * IDEIA -> Isto vai ser a classe onde vamos buscar qualquer tipo de lista ha base de dados
 * TODO -> retirar as coisas da ideia antiga
 */
public class ItineraryCatalog extends App_Menu {

    //neste caso vai representar a base de dados
    private static List<Itinerary> itenList = new ArrayList<>();

    public ItineraryCatalog(){
        if(itenList.size() == 0)
            setDefaultItineraries();
    }

    //tODO -> retirar daqui
    public RecyclerView.OnItemTouchListener getTouchListener(
            RecyclerView recyclerView,final Context c){
        RecyclerTouchListener result = new RecyclerTouchListener(c,
                recyclerView, new ClickListener() {
            //@Override
            public void onClick(View view, int position) {

                Itinerary iten = itenList.get(position);
                Toast.makeText(c, iten.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            //@Override
            public void onLongClick(View view, int position) {
                final Itinerary iten = itenList.get(position);
                AlertDialog.Builder aBuilder = new SelectItineraryDialog(c,iten);
                aBuilder.show();
            }
        });
        return result;
    }


    public List<Itinerary> getItinerariesFromDatabase(){
        return itenList;
    }

    public List<Itinerary> filterItinerarieList(String searchString, Difficulties dif){

        itenList.clear();
        if(searchString == null || searchString.equals("")) {
            setDefaultItineraries();
            return itenList;
        }

        for(Itinerary i : itenList){
            if(i.getTitle().contains(searchString) ||
                    i.getCreator().getName().contains(searchString)){
                itenList.add(i);
            }
        }
        return itenList;
    }

    public void addItineraryToDatabase(Itinerary iten){
        itenList.add(iten);
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    //tODO -> Retirar quando tivermos a informacao na base de dados
    private void setDefaultItineraries(){
        User creator = new User("Joao Leal","123321");
        creator.setImageID(R.drawable.creatorprofileimage);
        Hotspot hot1 = new Hotspot(),hot2 = new Hotspot(),
                hot3 = new Hotspot(),hot4 = new Hotspot(),
                hot5 = new Hotspot();
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();
        setQuiz(quiz1,1);
        setQuiz(quiz2,2);
        Race race = new Race();
        ImagePuzzle imgPzl = new ImagePuzzle(R.drawable.background3);
        ImagePuzzle imgPzl2 = new ImagePuzzle(R.drawable.background2);

        hot1.setName("O significado da vida");
        hot1.setPosition(new LatLng(39,-9.134));
        hot1.setGame(quiz1);

        hot2.setName("A Grande Corrida");
        hot2.setPosition(new LatLng(38.7524403,-9.134773));
        hot2.setGame(race);

        hot3.setName("O puzzle pelo trofeu");
        hot3.setPosition(new LatLng(38.7561689,-9.1556714));
        hot3.setGame(imgPzl);

        hot4.setName("Se a minha imagem estivesse completa");
        hot4.setGame(imgPzl2);
        hot4.setPosition(new LatLng(38.74,-9.13));

        hot5.setName("Quem manda no planeta terra?");
        hot5.setGame(quiz2);
        hot5.setPosition(new LatLng(39,-9.134));
        List<Hotspot> list1 = new ArrayList<>();
        list1.add(hot1);list1.add(hot2);
        List<Hotspot> list2 = new ArrayList<>();
        list2.add(hot3);
        List<Hotspot> list3 = new ArrayList<>();
        list3.add(hot2);list3.add(hot1); list3.add(hot3);
        Itinerary iten1 = new Itinerary("Preciso de boleio pelo espaco academico",new Date(),list1, Difficulties.HARD);
        iten1.setCreator(creator);
        iten1.setDescription("TEste da descricao");
        Itinerary iten2 = new Itinerary("O restaurante no fim da fcul",new Date(),list2,Difficulties.MEDIUM);
        iten2.setCreator(creator);
        iten2.setImageID(R.drawable.cantinavelha);
        iten2.setDescription("Uma visita pelos restaurantes todos da cidade Universitaria. Termina no mini-campus para conseguirem descansar");
        Itinerary iten3 = new Itinerary("Adeus e obrigado por todos os exames",new Date(),list3,Difficulties.EASY);
        iten3.setCreator(creator);
        iten3.setDescription("TEste da descricao");

        itenList.add(iten1);itenList.add(iten2);itenList.add(iten3);
    }

}