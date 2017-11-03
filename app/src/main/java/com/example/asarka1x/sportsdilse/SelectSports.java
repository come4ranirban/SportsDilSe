package com.example.asarka1x.sportsdilse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 8/26/2017.
 */

public class SelectSports extends Fragment {


    private ImageButton back;
    private ImageView cricketicon,footballicon,tennisicon,badmintonicon,formulaicon,hockeyicon;
    private LinearLayout cricket,football,tennis,badminton,formula1,hockey;
    private TextView cricketText, footballText, tennisText, badmintonText, formulaText, hockeyText;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View v= inflater.inflate(R.layout.sportslist, container, false);
        back= (ImageButton)v.findViewById(R.id.back);
        cricket= (LinearLayout)v.findViewById(R.id.cricket);
        football= (LinearLayout)v.findViewById(R.id.football);
        tennis= (LinearLayout)v.findViewById(R.id.tennis);
        badminton= (LinearLayout)v.findViewById(R.id.badminton);
        formula1= (LinearLayout)v.findViewById(R.id.formula1);
        hockey= (LinearLayout)v.findViewById(R.id.hockey);

        cricketicon= (ImageView)v.findViewById(R.id.cricketicon);
        footballicon= (ImageView)v.findViewById(R.id.footballicon);
        tennisicon= (ImageView)v.findViewById(R.id.tennisicon);
        badmintonicon= (ImageView)v.findViewById(R.id.badmintonicon);
        formulaicon= (ImageView)v.findViewById(R.id.formulaicon);
        hockeyicon= (ImageView)v.findViewById(R.id.hockeyicon);

        cricketText= (TextView)v.findViewById(R.id.cricketText);
        footballText= (TextView)v.findViewById(R.id.footballText);
        tennisText= (TextView)v.findViewById(R.id.tennisText);
        badmintonText= (TextView)v.findViewById(R.id.badmintonText);
        formulaText= (TextView)v.findViewById(R.id.formulaText);
        hockeyText= (TextView)v.findViewById(R.id.hockeyText);
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

        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Const.setadapter= true;
                if(Const.cricket==false){
                    cricket.setBackgroundResource(R.drawable.selectedsports);
                    cricketicon.setImageResource(R.drawable.cricketwhite);
                    cricketText.setTextColor(Color.WHITE);
                    Const.cricket=true;
                }else {
                    cricket.setBackgroundResource(R.drawable.rectangleshapewhite);
                    cricketicon.setImageResource(R.drawable.cricketblack);
                    cricketText.setTextColor(Color.BLACK);
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
                    footballText.setTextColor(Color.WHITE);
                    Const.football=true;
                }else {
                    football.setBackgroundResource(R.drawable.rectangleshapewhite);
                    footballicon.setImageResource(R.drawable.footballblack);
                    footballText.setTextColor(Color.BLACK);
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
                    tennisicon.setImageResource(R.drawable.tenniswhite);
                    tennisText.setTextColor(Color.WHITE);
                    Const.tennis=true;
                }else {
                    tennis.setBackgroundResource(R.drawable.rectangleshapewhite);
                    tennisicon.setImageResource(R.drawable.tennisblack);
                    tennisText.setTextColor(Color.BLACK);
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
                    badmintonText.setTextColor(Color.WHITE);
                    Const.badminton=true;
                }else {
                    badminton.setBackgroundResource(R.drawable.rectangleshapewhite);
                    badmintonicon.setImageResource(R.drawable.badmintonblack);
                    badmintonText.setTextColor(Color.BLACK);
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
                    formulaText.setTextColor(Color.WHITE);
                    Const.formula1=true;
                }else {
                    formula1.setBackgroundResource(R.drawable.rectangleshapewhite);
                    formulaicon.setImageResource(R.drawable.formulablack);
                    formulaText.setTextColor(Color.BLACK);
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
                    hockeyText.setTextColor(Color.WHITE);
                    Const.hockey=true;
                }else {
                    hockey.setBackgroundResource(R.drawable.rectangleshapewhite);
                    hockeyicon.setImageResource(R.drawable.hockeyblack);
                    hockeyText.setTextColor(Color.BLACK);
                    Const.hockey= false;
                }
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(Const.cricket== true){
            cricket.setBackgroundResource(R.drawable.selectedsports);
            cricketicon.setImageResource(R.drawable.cricketwhite);
            cricketText.setTextColor(Color.WHITE);
        }else{
            cricket.setBackgroundResource(R.drawable.rectangleshapewhite);
            cricketicon.setImageResource(R.drawable.cricketblack);
            cricketText.setTextColor(Color.BLACK);
        }

        if(Const.football== true){
            football.setBackgroundResource(R.drawable.selectedsports);
            footballicon.setImageResource(R.drawable.footballwhite);
            footballText.setTextColor(Color.WHITE);
        }else{
            football.setBackgroundResource(R.drawable.rectangleshapewhite);
            footballicon.setImageResource(R.drawable.footballblack);
            footballText.setTextColor(Color.BLACK);
        }

        if(Const.tennis== true){
            tennis.setBackgroundResource(R.drawable.selectedsports);
            tennisicon.setImageResource(R.drawable.tenniswhite);
            tennisText.setTextColor(Color.WHITE);
        }else{
            tennis.setBackgroundResource(R.drawable.rectangleshapewhite);
            tennisicon.setImageResource(R.drawable.tennisblack);
            tennisText.setTextColor(Color.BLACK);
        }
        if(Const.badminton== true){
            badminton.setBackgroundResource(R.drawable.selectedsports);
            badmintonicon.setImageResource(R.drawable.badmintonwhite);
            badmintonText.setTextColor(Color.WHITE);
        }else{
            badminton.setBackgroundResource(R.drawable.rectangleshapewhite);
            badmintonicon.setImageResource(R.drawable.badmintonblack);
            badmintonText.setTextColor(Color.BLACK);
        }
        if(Const.formula1== true){
            formula1.setBackgroundResource(R.drawable.selectedsports);
            formulaicon.setImageResource(R.drawable.formulawhite);
            formulaText.setTextColor(Color.WHITE);
        }else{
            formula1.setBackgroundResource(R.drawable.rectangleshapewhite);
            formulaicon.setImageResource(R.drawable.formulablack);
            formulaText.setTextColor(Color.BLACK);
        }
        if(Const.hockey== true){
            hockey.setBackgroundResource(R.drawable.selectedsports);
            hockeyicon.setImageResource(R.drawable.hockeywhite);
            hockeyText.setTextColor(Color.WHITE);
        }else{
            hockey.setBackgroundResource(R.drawable.rectangleshapewhite);
            hockeyicon.setImageResource(R.drawable.hockeyblack);
            hockeyText.setTextColor(Color.BLACK);
        }
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

        db.close();

        if(Const.setadapter==true)
        {
            try {
                Const.starttempid=true;
                Const.tempid.clear();
                ReadJson.readNews();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
