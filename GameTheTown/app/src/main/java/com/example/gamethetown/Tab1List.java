package com.example.gamethetown;

/**
 * Created by franc on 31/10/2017.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamethetown.adapters.ItineraryAdapter;
import com.example.gamethetown.item.DividerItemDecoration;
import com.example.gamethetown.item.Itinerary;
import com.example.gamethetown.item.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tab1List extends Fragment {

    private List<Itinerary> itenList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItineraryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1list, container, false);
        setData(rootView);
        return rootView;

    }

    public void setData(View view){
        if(view.findViewById(R.id.recycler_view) != null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            mAdapter = new ItineraryAdapter(itenList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

            //TODO -> mandar para o mapa
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(),
                    recyclerView, new ListOfIteneraries.ClickListener() {
                //@Override
                public void onClick(View view, int position) {
                    Itinerary iten = itenList.get(position);
                    Toast.makeText(view.getContext(), iten.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                }

                //@Override
                public void onLongClick(View view, int position) {

                }
            }));
            prepareItenData();
        }
    }

    private void prepareItenData() {
        //se for para ver todas as listas

        Itinerary iten = new Itinerary("Um passeio por Lisboa", new Date(2017, 9, 10));
        itenList.add(iten);

        iten = new Itinerary("Aventura pelo Monteiro Dos Jeronimos", new Date(2017, 9, 11), R.drawable.monteirodojeronimo);
        itenList.add(iten);

        iten = new Itinerary("Vamos conhecer a Fcul!", new Date(2017, 7, 12));
        itenList.add(iten);

        iten = new Itinerary("Vamos Nadar Com os Peixes - Oceanario De Lisboa", new Date(2017, 9, 11));
        itenList.add(iten);

        iten = new Itinerary("Alfama? Mal a Conheco!", new Date(2017, 7, 12));
        itenList.add(iten);

        iten = new Itinerary("Vamos Assaltar Palacio Chiado!", new Date(2017, 7, 12));
        itenList.add(iten);

        iten = new Itinerary("Sera que o Rio Tejo eh mesmo um Rio?", new Date(2017, 7, 12));
        itenList.add(iten);


        mAdapter.notifyDataSetChanged();
    }

}
