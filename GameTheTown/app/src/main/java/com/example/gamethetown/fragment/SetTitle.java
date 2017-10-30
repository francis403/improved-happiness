package com.example.gamethetown.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gamethetown.R;

public class SetTitle extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_title, container, false);

        /*
        Button button = (Button) view.findViewById(R.id.setDesc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set(v);
            }
        });
        */
        return view;
    }


}
