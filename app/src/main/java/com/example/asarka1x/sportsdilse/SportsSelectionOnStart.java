package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 10/24/2017.
 */

public class SportsSelectionOnStart extends AppCompatActivity {

    private ImageView cricketicon,footballicon,tennisicon,badmintonicon,formulaicon,hockeyicon,trackfieldicon,othericon,allsportsicon;
    private LinearLayout allsports,cricket,football,tennis,badminton,formula1,hockey,trackfield,others;
    private SQLiteDatabase db;
    private Handler h;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportsselectfirstscreen);

        allsports= (LinearLayout)findViewById(R.id.allSports);
        cricket= (LinearLayout)findViewById(R.id.cricket);
        football= (LinearLayout)findViewById(R.id.football);
        tennis= (LinearLayout)findViewById(R.id.tennis);
        badminton= (LinearLayout)findViewById(R.id.badminton);
        formula1= (LinearLayout)findViewById(R.id.formula1);
        hockey= (LinearLayout)findViewById(R.id.hockey);
        trackfield = (LinearLayout)findViewById(R.id.trackfield);
        others= (LinearLayout)findViewById(R.id.others);

        cricketicon= (ImageView)findViewById(R.id.cricketicon);
        footballicon= (ImageView)findViewById(R.id.footballicon);
        tennisicon= (ImageView)findViewById(R.id.tennisicon);
        badmintonicon= (ImageView)findViewById(R.id.badmintonicon);
        formulaicon= (ImageView)findViewById(R.id.formulaicon);
        hockeyicon= (ImageView)findViewById(R.id.hockeyicon);
        trackfieldicon= (ImageView)findViewById(R.id.trackfieldicon);
        othericon= (ImageView)findViewById(R.id.othericon);
        allsportsicon= (ImageView)findViewById(R.id.allSportsicon);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Const.allSports== true)
        {
            allsports.setBackgroundResource(R.drawable.selectedsports);
            allsportsicon.setImageResource(R.drawable.allsportswhite);
        }else {
            allsportsicon.setImageResource(R.drawable.allsportsblack);
            allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
        }

        if(Const.cricket== true){
            cricket.setBackgroundResource(R.drawable.selectedsports);
            cricketicon.setImageResource(R.drawable.cricketwhite);
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
        {
            others.setBackgroundResource(R.drawable.selectedsports);
            othericon.setImageResource(R.drawable.climbingwhite);
        }else {
            others.setBackgroundResource(R.drawable.rectangleshapewhite);
            othericon.setImageResource(R.drawable.climbingblack);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Const.cricket==true && Const.football==true && Const.hockey==true && Const.tennis==true && Const.badminton==true
                        && Const.formula1==true && Const.other==true && Const.trackfield==true)
                {
                    allsports.setBackgroundResource(R.drawable.selectedsports);
                    allsportsicon.setImageResource(R.drawable.allsportswhite);
                }
                else
                {
                    allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                    allsportsicon.setImageResource(R.drawable.allsportsblack);
                }
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
                    othericon.setImageResource(R.drawable.climbingwhite);
                    Const.other=true;
                }else {
                    if(Const.allSports==true){
                        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
                        Const.allSports=false;
                    }
                    others.setBackgroundResource(R.drawable.rectangleshapewhite);
                    othericon.setImageResource(R.drawable.climbingblack);
                    Const.other= false;
                }
            }
        });
    }


    public void disselectAllSports(){
        allsports.setBackgroundResource(R.drawable.rectangleshapewhite);
        allsportsicon.setImageResource(R.drawable.allsportsblack);
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
        othericon.setImageResource(R.drawable.climbingblack);
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
        allsportsicon.setImageResource(R.drawable.allsportswhite);
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
        othericon.setImageResource(R.drawable.climbingwhite);
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

        if(Const.trackfield==true)
            values.put("track", 1);

        if(Const.other==true)
            values.put("other", 1);
        values.put("nightmode", 0);
        db.insert("sportslist", null, values);
        db.close();

        if(h!=null)
            h.removeCallbacks(runnable);

        if(Const.cricket || Const.formula1 || Const.football || Const.badminton ||
                Const.trackfield || Const.tennis || Const.hockey || Const.other)
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
