package com.example.gamethetown.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by franc on 30/10/2017.
 */

public class AllItinerariesPageAdapter extends FragmentPagerAdapter{

    //assumindo que o numero de tabs que vamos ter
    private static final int NUM_ITEMS = 2;

    public AllItinerariesPageAdapter(FragmentManager fm) {
        super(fm);
    }
    //TODO
    @Override
    public Fragment getItem(int position) {
        //vai ter de ser um fragment especialmente feito para isto
        Fragment frag = new Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count",1);
        return frag;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
