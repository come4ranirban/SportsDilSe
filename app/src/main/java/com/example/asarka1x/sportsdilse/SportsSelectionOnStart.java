package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 10/24/2017.
 */

public class SportsSelectionOnStart extends AppCompatActivity {

    private ImageView cricketicon,footballicon,tennisicon,badmintonicon,formulaicon,hockeyicon;
    private LinearLayout cricket,football,tennis,badminton,formula1,hockey;
    private TextView cricketText, footballText, tennisText, badmintonText, formulaText, hockeyText;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportsselectfirstscreen);

        cricketText= (TextView)findViewById(R.id.cricketText);
        footballText= (TextView)findViewById(R.id.footballText);
        tennisText= (TextView)findViewById(R.id.tennisText);
        badmintonText= (TextView)findViewById(R.id.badmintonText);
        formulaText= (TextView)findViewById(R.id.formulaText);
        hockeyText= (TextView)findViewById(R.id.hockeyText);

        cricket= (LinearLayout)findViewById(R.id.cricket);
        football= (LinearLayout)findViewById(R.id.football);
        tennis= (LinearLayout)findViewById(R.id.tennis);
        badminton= (LinearLayout)findViewById(R.id.badminton);
        formula1= (LinearLayout)findViewById(R.id.formula1);
        hockey= (LinearLayout)findViewById(R.id.hockey);

        cricketicon= (ImageView)findViewById(R.id.cricketicon);
        footballicon= (ImageView)findViewById(R.id.footballicon);
        tennisicon= (ImageView)findViewById(R.id.tennisicon);
        badmintonicon= (ImageView)findViewById(R.id.badmintonicon);
        formulaicon= (ImageView)findViewById(R.id.formulaicon);
        hockeyicon= (ImageView)findViewById(R.id.hockeyicon);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
    protected void onResume() {
        super.onResume();

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


    public void next(View v){


        db= MainActivity.activity.openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        ContentValues values= new ContentValues();
        if(Const.cricket==true)
            values.put("cricket", 1);

        if(Const.football==true)
            values.put("football", 1);

        if(Const.tennis==true)
            values.put("tennis", 1);

        if(Const.badminton==true)
            values.put("badminton", 1);

        if(Const.formula1==true)
            values.put("formula", 1);

        if(Const.hockey==true)
            values.put("hockey", 1);
        else
            db.execSQL("UPDATE SPORTSLIST SET hockey=0");

        values.put("nightmode", 0);
        db.insert("sportslist", null, values);
        db.close();

        if(Const.cricket || Const.formula1 || Const.football || Const.badminton ||
                 Const.tennis || Const.hockey)
        {
            setResult(101);
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Please Select Your Sports", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.activity.finish();
        this.finish();
    }
}
