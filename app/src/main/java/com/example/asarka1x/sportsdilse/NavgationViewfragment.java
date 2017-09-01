package com.example.asarka1x.sportsdilse;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import in.technomenia.user.sportsdilse.R;

import static com.example.asarka1x.sportsdilse.MainActivity.drawerLayout;
import static com.example.asarka1x.sportsdilse.MainActivity.pagerAdapter;
import static com.example.asarka1x.sportsdilse.MainActivity.viewPager;

/**
 * Created by asarka1x on 8/30/2017.
 */

public class NavgationViewfragment extends Fragment {

    private ImageView sportsPreference;
    private RecyclerView selectedsports;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.navigationfragment, container, false);

        Const.selectedsportslist.clear();

        if(Const.allSports==true){
            Const.selectedsportslist.add("Cricket");
            Const.selectedsportslist.add("Football");
            Const.selectedsportslist.add("Tennis");
            Const.selectedsportslist.add("Badminton");
            Const.selectedsportslist.add("Formula 1");
            Const.selectedsportslist.add("Hockey");
            Const.selectedsportslist.add("Track & Field");
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
        }

        Toast.makeText(getActivity(), "sports list->"+Const.selectedsportslist.size(), Toast.LENGTH_SHORT).show();
        sportsPreference= (ImageView)v.findViewById(R.id.yourpreference);
        selectedsports= (RecyclerView)v.findViewById(R.id.selectedsportsrecycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        selectedsports.setLayoutManager(layoutManager);
        selectedsports.setHasFixedSize(true);

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

    }
}
