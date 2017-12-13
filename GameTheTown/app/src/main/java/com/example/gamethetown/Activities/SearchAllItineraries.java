package com.example.gamethetown.Activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Database.ItineraryDatabaseConnection;
import com.example.gamethetown.Enums.Difficulties;
import com.example.gamethetown.R;

public class SearchAllItineraries extends App_Menu {

    private static Tab1List tab1;
    private static Tab2Map tab2;
    private int maxDistance;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all_itineraries);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilter(view);
            }
        });

    }

    public void onFilter(View view){
        maxDistance = 0;
        final BottomSheetDialog bsd = new BottomSheetDialog(this);
        final View sView = getLayoutInflater().inflate(R.layout.layout_filter,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        //BottomSheetBehavior bsb = BottomSheetBehavior.from(new Filter().getView());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
        bsd.show();

        final Spinner spinnerDif = (Spinner) sView.findViewById(R.id.spinner_dif);
        final TextView filterText = (TextView) sView.findViewById(R.id.textProx);
        SeekBar seekBar = (SeekBar) sView.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxDistance = progress;
                if(maxDistance == 0)
                    filterText.setText("Max distance: No Max");
                else
                    filterText.setText("Max distance: " + maxDistance + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter<Difficulties>(this,
                android.R.layout.simple_list_item_1,Difficulties.values());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDif.setAdapter(adapter);

        Button confirmSearch = (Button) sView.findViewById(R.id.search);
        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = ((EditText) sView.findViewById(R.id.paramFilter))
                        .getText().toString();
                Difficulties selectedDif = (Difficulties) spinnerDif.getSelectedItem();
                if(tab1 != null && tab2 != null){
                    ItineraryDatabaseConnection idc = new ItineraryDatabaseConnection();
                    idc.filterItineraries(tab1.getmAdapter(),tab2.getMap(),
                            tab2.getMapList(),search,selectedDif,
                            maxDistance,tab2.getLocation());
                }
                bsd.hide();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    tab1 = new Tab1List();
                    return tab1;
                case 1:
                    tab2 = new Tab2Map();
                    return tab2;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "List";
                case 1:
                    return "Map";
            }
            return null;
        }


    }
}
