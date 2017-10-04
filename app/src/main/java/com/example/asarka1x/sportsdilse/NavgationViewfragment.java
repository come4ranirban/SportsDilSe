package com.example.asarka1x.sportsdilse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import in.technomenia.user.sportsdilse.R;

import static com.example.asarka1x.sportsdilse.MainActivity.drawerLayout;
import static com.example.asarka1x.sportsdilse.MainActivity.pagerAdapter;
import static com.example.asarka1x.sportsdilse.MainActivity.viewPager;

/**
 * Created by asarka1x on 8/30/2017.
 */

public class NavgationViewfragment extends Fragment {

    private RecyclerView selectedsports;
    private LinearLayout bookmark,sportsPreference;
    private LinearLayout wrt;
    private Switch nightswitch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.navigationfragment, container, false);
        bookmark= (LinearLayout)v.findViewById(R.id.bookmarks);
        wrt= (LinearLayout)v.findViewById(R.id.wrt);
        nightswitch= (Switch)v.findViewById(R.id.nightswitch);
        Const.selectedsportslist.clear();

        if(Const.nightmode==false){
            nightswitch.setChecked(false);
        }else{
            nightswitch.setChecked(true);
        }

        if(Const.allSports==true){
            Const.selectedsportslist.add("Cricket");
            Const.selectedsportslist.add("Football");
            Const.selectedsportslist.add("Tennis");
            Const.selectedsportslist.add("Badminton");
            Const.selectedsportslist.add("Formula 1");
            Const.selectedsportslist.add("Hockey");
            Const.selectedsportslist.add("Track & Field");
            Const.selectedsportslist.add("other");
        }else{
            if(Const.cricket==true)
                Const.selectedsportslist.add("Cricket");

            if(Const.football==true)
                Const.selectedsportslist.add("Football");

            if(Const.tennis)
                Const.selectedsportslist.add("Tennis");

            if(Const.badminton==true)
                Const.selectedsportslist.add("Badminton");

            if(Const.formula1==true)
                Const.selectedsportslist.add("Formula 1");

            if(Const.hockey==true)
                Const.selectedsportslist.add("Hockey");

            if(Const.trackfield==true)
                Const.selectedsportslist.add("Track & Field");

            if(Const.other==true)
                Const.selectedsportslist.add("other");
        }

        sportsPreference= (LinearLayout)v.findViewById(R.id.yourpreference);
        selectedsports= (RecyclerView)v.findViewById(R.id.selectedsportsrecycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        selectedsports.setLayoutManager(layoutManager);
        selectedsports.setHasFixedSize(true);

        //Calling recyclerview for displaying selected sports
        sportsPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerAdapter = new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                pagerAdapter.clearList();
                pagerAdapter.addFragment(new SelectSports(),"Sports Preference");
                Const.pageHistory.add(pagerAdapter);
                viewPager.setAdapter(pagerAdapter);
                drawerLayout.closeDrawers();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        selectedsports.setAdapter(new SelectedSportsAdapter());
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                pagerAdapter.clearList();
                pagerAdapter.addFragment(new BookmarkedArticles(), "BookmarkedArticles");
                Const.pageHistory.add(pagerAdapter);
                viewPager.setAdapter(pagerAdapter);
                drawerLayout.closeDrawers();
            }
        });

        wrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
                Cursor cursor= db.rawQuery("select *from usercredential",null);
                cursor.moveToFirst();
                if(cursor.getCount()>0 ){
                    ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                    pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                    pagerAdapter.clearList();
                    pagerAdapter.addFragment(new WriteArticle(), "WriteArticle");
                    Const.pageHistory.add(pagerAdapter);
                    viewPager.setAdapter(pagerAdapter);
                    drawerLayout.closeDrawers();
                }else{
                    ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                    pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                    pagerAdapter.clearList();
                    pagerAdapter.addFragment(new LoginPage(), "Login");
                    Const.pageHistory.add(pagerAdapter);
                    viewPager.setAdapter(pagerAdapter);
                    drawerLayout.closeDrawers();
                }
            }
        });

        nightswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SQLiteDatabase db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
                if(isChecked)
                {
                    Const.nightmode=true;
                    db.execSQL("UPDATE SPORTSLIST SET nightmode=1");
                    Articls.theme();
                }else {
                    Const.nightmode=false;
                    db.execSQL("UPDATE SPORTSLIST SET nightmode=0");
                    Articls.theme();
                }
            }
        });
    }
}
