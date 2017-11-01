package com.example.gamethetown.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamethetown.R;
import com.example.gamethetown.item.Itinerary;

import java.util.ArrayList;
import java.util.List;

public class List_Test extends Fragment {

    private List<Itinerary> itenList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list__test, container, false);
        return view;
    }
}
