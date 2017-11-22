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
import android.widget.Spinner;

import com.example.gamethetown.App_Menu;
import com.example.gamethetown.Catalogs.ItineraryCatalog;
import com.example.gamethetown.R;

public class SearchAllItineraries extends App_Menu {

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
        final BottomSheetDialog bsd = new BottomSheetDialog(this);
        final View sView = getLayoutInflater().inflate(R.layout.layout_filter,null);
        bsd.setContentView(sView);
        BottomSheetBehavior bsb = BottomSheetBehavior.from((View) sView.getParent());
        bsb.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
        bsd.show();

        Spinner spinner = (Spinner) sView.findViewById(R.id.spinner_dif);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.iten_dif, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner = (Spinner) sView.findViewById(R.id.spinner_prox);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.iten_prox, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        Button confirmSearch = (Button) sView.findViewById(R.id.search);
        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = ((EditText) sView.findViewById(R.id.paramFilter))
                        .getText().toString();
                new ItineraryCatalog().filterItinerarieList(search,null);
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
                    Tab1List tab1 = new Tab1List();
                    return tab1;
                case 1:
                    Tab2Map tab2 = new Tab2Map();
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
