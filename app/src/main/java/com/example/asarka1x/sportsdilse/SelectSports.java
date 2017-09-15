package com.example.asarka1x.sportsdilse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 8/26/2017.
 */

public class SelectSports extends Fragment {


    private ImageButton back;
    private ImageView cricketicon,footballicon,tennisicon,badmintonicon,formulaicon,hockeyicon,trackfieldicon;
    private LinearLayout allsports,cricket,football,tennis,badminton,formula1,hockey,trackfield,others;
    private SQLiteDatabase db;
    private Handler h;
    private Runnable runnable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View v= inflater.inflate(R.layout.sportslist, container, false);
        back= (ImageButton)v.findViewById(R.id.back);
        allsports= (LinearLayout)v.findViewById(R.id.allSports);
        cricket= (LinearLayout)v.findViewById(R.id.cricket);
        football= (LinearLayout)v.findViewById(R.id.football);
        tennis= (LinearLayout)v.findViewById(R.id.tennis);
        badminton= (LinearLayout)v.findViewById(R.id.badminton);
        formula1= (LinearLayout)v.findViewById(R.id.formula1);
        hockey= (LinearLayout)v.findViewById(R.id.hockey);
        trackfield = (LinearLayout)v.findViewById(R.id.trackfield);
        others= (LinearLayout)v.findViewById(R.id.others);

        cricketicon= (ImageView)v.findViewById(R.id.cricketicon);
        footballicon= (ImageView)v.findViewById(R.id.footballicon);
        tennisicon= (ImageView)v.findViewById(R.id.tennisicon);
        badmintonicon= (ImageView)v.findViewById(R.id.badmintonicon);
        formulaicon= (ImageView)v.findViewById(R.id.formulaicon);
        hockeyicon= (ImageView)v.findViewById(R.id.hockeyicon);
        trackfieldicon= (ImageView)v.findViewById(R.id.trackfieldicon);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.onBackPressed();
            }
        });

        h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Const.cricket==true && Const.football==true && Const.hockey==true && Const.tennis==true && Const.badminton==true
                        && Const.formula1==true && Const.other==true && Const.trackfield==true)
                    allsports.setBackgroundResource(R.drawable.selectedsports);
                else
                    allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                runnable= this;
                h.postDelayed(this, 20);
            }
        },20);

        allsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Const.setadapter= true;

                if(Const.allSports==false){
                    selectAllSports();
                }else {
                    disselectAllSports();
                }
            }
        });

        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Const.setadapter= true;
                if(Const.cricket==false){
                    cricket.setBackgroundResource(R.drawable.selectedsports);
                    cricketicon.setImageResource(R.drawable.cricketwhite);
                    Const.cricket=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    cricket.setBackgroundResource(R.drawable.rectangleshapewhite);
                    cricketicon.setImageResource(R.drawable.cricketblack);
                    Const.cricket= false;
                }
            }
        });

        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.football==false){
                    football.setBackgroundResource(R.drawable.selectedsports);
                    footballicon.setImageResource(R.drawable.footballwhite);
                    Const.football=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    football.setBackgroundResource(R.drawable.rectangleshapewhite);
                    footballicon.setImageResource(R.drawable.footballblack);
                    Const.football= false;
                }
            }
        });

        tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.tennis==false){
                    tennis.setBackgroundResource(R.drawable.selectedsports);
                    Toast.makeText(getActivity(), "tennis", Toast.LENGTH_SHORT).show();
                    tennisicon.setImageResource(R.drawable.tenniswhite);
                    Const.tennis=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    tennis.setBackgroundResource(R.drawable.rectangleshapewhite);
                    tennisicon.setImageResource(R.drawable.tennisblack);
                    Const.tennis= false;
                }
            }
        });

        badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.badminton==false){
                    badminton.setBackgroundResource(R.drawable.selectedsports);
                    badmintonicon.setImageResource(R.drawable.badmintonwhite);
                    Const.badminton=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    badminton.setBackgroundResource(R.drawable.rectangleshapewhite);
                    badmintonicon.setImageResource(R.drawable.badmintonblack);
                    Const.badminton= false;
                }
            }
        });

        formula1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.formula1==false){
                    formula1.setBackgroundResource(R.drawable.selectedsports);
                    formulaicon.setImageResource(R.drawable.formulawhite);
                    Const.formula1=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    formula1.setBackgroundResource(R.drawable.rectangleshapewhite);
                    formulaicon.setImageResource(R.drawable.formulablack);
                    Const.formula1= false;
                }
            }
        });

        hockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.hockey==false){
                    hockey.setBackgroundResource(R.drawable.selectedsports);
                    hockeyicon.setImageResource(R.drawable.hockeywhite);
                    Const.hockey=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    hockey.setBackgroundResource(R.drawable.rectangleshapewhite);
                    hockeyicon.setImageResource(R.drawable.hockeyblack);
                    Const.hockey= false;
                }
            }
        });

        trackfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.trackfield==false){
                    trackfield.setBackgroundResource(R.drawable.selectedsports);
                    trackfieldicon.setImageResource(R.drawable.trackswhite);
                    Const.trackfield=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    trackfield.setBackgroundResource(R.drawable.rectangleshapewhite);
                    trackfieldicon.setImageResource(R.drawable.tracksblack);
                    Const.trackfield= false;
                }
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.setadapter= true;
                if(Const.other==false){
                    others.setBackgroundResource(R.drawable.selectedsports);
                    Const.other=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    others.setBackgroundResource(R.drawable.rectangleshapewhite);
                    Const.other= false;
                }
            }
        });
    }

    public void disselectAllSports(){
        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
        cricket.setBackgroundResource(R.drawable.rectangleshapewhite);
        cricketicon.setImageResource(R.drawable.cricketblack);
        football.setBackgroundResource(R.drawable.rectangleshapewhite);
        footballicon.setImageResource(R.drawable.footballblack);
        tennis.setBackgroundResource(R.drawable.rectangleshapewhite);
        tennisicon.setImageResource(R.drawable.tennisblack);
        badminton.setBackgroundResource(R.drawable.rectangleshapewhite);
        badmintonicon.setImageResource(R.drawable.badmintonblack);
        formula1.setBackgroundResource(R.drawable.rectangleshapewhite);
        formulaicon.setImageResource(R.drawable.formulablack);
        hockey.setBackgroundResource(R.drawable.rectangleshapewhite);
        hockeyicon.setImageResource(R.drawable.hockeyblack);
        trackfield.setBackgroundResource(R.drawable.rectangleshapewhite);
        trackfieldicon.setImageResource(R.drawable.tracksblack);
        others.setBackgroundResource(R.drawable.rectangleshapewhite);
        Const.allSports=false;
        Const.cricket=false;
        Const.football=false;
        Const.tennis=false;
        Const.badminton=false;
        Const.formula1=false;
        Const.hockey=false;
        Const.trackfield=false;
        Const.other=false;
    }

    public void selectAllSports(){
        allsports.setBackgroundResource(R.drawable.selectedsports);
        cricket.setBackgroundResource(R.drawable.selectedsports);
        cricketicon.setImageResource(R.drawable.cricketwhite);
        football.setBackgroundResource(R.drawable.selectedsports);
        footballicon.setImageResource(R.drawable.footballwhite);
        tennis.setBackgroundResource(R.drawable.selectedsports);
        tennisicon.setImageResource(R.drawable.tenniswhite);
        badminton.setBackgroundResource(R.drawable.selectedsports);
        badmintonicon.setImageResource(R.drawable.badmintonwhite);
        formula1.setBackgroundResource(R.drawable.selectedsports);
        formulaicon.setImageResource(R.drawable.formulawhite);
        hockey.setBackgroundResource(R.drawable.selectedsports);
        hockeyicon.setImageResource(R.drawable.hockeywhite);
        trackfield.setBackgroundResource(R.drawable.selectedsports);
        trackfieldicon.setImageResource(R.drawable.trackswhite);
        others.setBackgroundResource(R.drawable.selectedsports);
        Const.allSports=true;
        Const.cricket=true;
        Const.football=true;
        Const.tennis=true;
        Const.badminton=true;
        Const.formula1=true;
        Const.hockey= true;
        Const.trackfield= true;
        Const.other= true;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(Const.allSports== true)
            allsports.setBackgroundResource(R.drawable.selectedsports);
        if(Const.cricket== true){
            cricket.setBackgroundResource(R.drawable.selectedsports);
            cricketicon.setImageResource(R.drawable.cricketblack);
        }else{
            cricket.setBackgroundResource(R.drawable.rectangleshapewhite);
            cricketicon.setImageResource(R.drawable.cricketblack);
        }

        if(Const.football== true){
            football.setBackgroundResource(R.drawable.selectedsports);
            footballicon.setImageResource(R.drawable.footballwhite);
        }else{
            football.setBackgroundResource(R.drawable.rectangleshapewhite);
            footballicon.setImageResource(R.drawable.footballblack);
        }

        if(Const.tennis== true){
            tennis.setBackgroundResource(R.drawable.selectedsports);
            tennisicon.setImageResource(R.drawable.tenniswhite);
        }else{
            tennis.setBackgroundResource(R.drawable.rectangleshapewhite);
            tennisicon.setImageResource(R.drawable.tennisblack);
        }
        if(Const.badminton== true){
            badminton.setBackgroundResource(R.drawable.selectedsports);
            badmintonicon.setImageResource(R.drawable.badmintonwhite);
        }else{
            badminton.setBackgroundResource(R.drawable.rectangleshapewhite);
            badmintonicon.setImageResource(R.drawable.badmintonblack);
        }
        if(Const.formula1== true){
            formula1.setBackgroundResource(R.drawable.selectedsports);
            formulaicon.setImageResource(R.drawable.formulawhite);
        }else{
            formula1.setBackgroundResource(R.drawable.rectangleshapewhite);
            formulaicon.setImageResource(R.drawable.formulablack);
        }
        if(Const.hockey== true){
            hockey.setBackgroundResource(R.drawable.selectedsports);
            hockeyicon.setImageResource(R.drawable.hockeywhite);
        }else{
            hockey.setBackgroundResource(R.drawable.rectangleshapewhite);
            hockeyicon.setImageResource(R.drawable.hockeyblack);
        }
        if(Const.trackfield== true){
            trackfield.setBackgroundResource(R.drawable.selectedsports);
            trackfieldicon.setImageResource(R.drawable.trackswhite);
        }else{
            trackfield.setBackgroundResource(R.drawable.rectangleshapewhite);
            trackfieldicon.setImageResource(R.drawable.tracksblack);
        }
        if(Const.other== true)
            others.setBackgroundResource(R.drawable.selectedsports);
    }

    @Override
    public void onPause() {
        super.onPause();

        db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        if(Const.cricket==true)
            db.execSQL("UPDATE SPORTSLIST SET cricket=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET cricket=0");

        if(Const.football==true)
            db.execSQL("UPDATE SPORTSLIST SET football=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET football=0");

        if(Const.tennis==true)
            db.execSQL("UPDATE SPORTSLIST SET tennis=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET tennis=0");

        if(Const.badminton==true)
            db.execSQL("UPDATE SPORTSLIST SET badminton=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET badminton=0");

        if(Const.formula1==true)
            db.execSQL("UPDATE SPORTSLIST SET formula=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET formula=0");

        if(Const.hockey==true)
            db.execSQL("UPDATE SPORTSLIST SET hockey=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET hockey=0");

        if(Const.trackfield==true)
            db.execSQL("UPDATE SPORTSLIST SET track=1");
        else
            db.execSQL("UPDATE SPORTSLIST SET track=0");


        if(h!=null)
            h.removeCallbacks(runnable);

        if(Const.setadapter==true)
        {
            try {
                Const.starttempid=true;
                Const.tempid.clear();
                new ReadJson().readNews();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
