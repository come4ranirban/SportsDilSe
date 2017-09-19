package com.example.asarka1x.sportsdilse;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.Stack;

import in.technomenia.user.sportsdilse.R;

public class MainActivity extends AppCompatActivity {

    static ViewPager viewPager;
    static DrawerLayout drawerLayout;
    static MyPagerAdapter pagerAdapter;
    static MainActivity activity;
    SQLiteDatabase db;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView text;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Handler h;
    private Runnable callback;
    private boolean savedpageHistory;
    private FrameLayout navigationframe;

    public void init(){

        //reset display tempid
        Const.tempid.clear();
        Const.starttempid=true;

        try{
            Cursor cursor= db.rawQuery("select * from sportslist",null);
            if(cursor!=null){
                cursor.moveToFirst();
                if(cursor.getInt(0)==1)
                    Const.cricket=true;
                if(cursor.getInt(1)==1)
                    Const.football=true;
                if(cursor.getInt(2)==1)
                    Const.tennis=true;
                if(cursor.getInt(3)==1)
                    Const.badminton=true;
                if(cursor.getInt(4)==1)
                    Const.formula1=true;
                if(cursor.getInt(5)==1)
                    Const.hockey=true;
                if(cursor.getInt(6)==1)
                    Const.trackfield=true;
                if(cursor.getInt(7)==1)
                    Const.other=true;
                if(cursor.getInt(8)==1)
                    Const.nightmode=true;
            }
        }catch (SQLiteException e){
            db.execSQL("CREATE TABLE IF NOT EXISTS SPORTSLIST(cricket integer default 0, football integer default 0, " +

                    "tennis integer default 0, badminton integer default 0, formula integer default 0," +
                    "hockey integer default 0, track integer default 0, other integer default 0, nightmode integer default 0)");
            db.execSQL("CREATE TABLE IF NOT EXISTS BOOKMARKED(ID number, HEADLINE varchar(200), AUTHOR varchar(30), DATE varchar(18), CONTENT varchar(1500), IMAGE BLOB NOT NULL)");

            ContentValues values= new ContentValues();
            values.put("cricket", 0);
            values.put("football", 0);
            values.put("tennis", 0);
            values.put("badminton", 0);
            values.put("formula", 0);
            values.put("hockey", 0);
            values.put("track", 0);
            values.put("other", 0);
            values.put("nightmode", 0);
            db.insert("sportslist", null, values);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        activity= this;
        Const.serviceflag=0;
        Intent intent= new Intent();
        intent.setComponent(new ComponentName(getApplication(), SportsDilSeService.class));
        startService(intent);

        db= openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists usercredential(status int, username varchar(20), email varchar(40), password varchar(25))");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //text= (TextView) toolbar.findViewById(R.id.toolbartext);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize tab layout,view pager & drawerlayout
        //tabLayout = (TabLayout) findViewById(R.id.tablayout);
        //tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        //tabLayout.setBackgroundColor(Color.BLACK);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawers();
        //initialize pager adapter class
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        //initialize all settings
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //initialize action bar drawer toggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                navigationView= (NavigationView)findViewById(R.id.navigationview);
                navigationframe= (FrameLayout)findViewById(R.id.navigationframe);

                FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.navigationframe, new NavgationViewfragment());
                fragmentTransaction.commit();
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    @Override
    protected void onResume() {
        super.onResume();

        //SET VIEWPAGER AS SOON AS ONRESUME IS CALLED
        if(Const.pageHistory.isEmpty()){

            //Displaying welcome page
            pagerAdapter.clearList();
            pagerAdapter.addFragment(new Articls(),"Feed");
            // Const.pageHistory.add(pagerAdapter);
            //pagerAdapter.addFragment(new MatchList(),"Matches");
            Const.pageHistory.add(pagerAdapter);
            viewPager.setAdapter(pagerAdapter);
           //tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
            //   tabLayout.setupWithViewPager(viewPager);
            try {
                new ReadJson().readNews();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            if(Const.pageHistory.get(Const.pageHistory.size()-1).getPageTitle(0).equals("Feed"))
                getSupportActionBar().show();
            viewPager.setAdapter(Const.pageHistory.get(Const.pageHistory.size()-1));
        }
    }

    @Override
    public void onBackPressed() {
        Const.pageHistory.remove(Const.pageHistory.size()-1);
            if(Const.pageHistory.isEmpty()){
                super.onBackPressed();
            }
            else
            {
                if(Const.pageHistory.get(Const.pageHistory.size()-1).getPageTitle(0).equals("Feed"))
                    getSupportActionBar().show();
                viewPager.setAdapter(Const.pageHistory.get(Const.pageHistory.size()-1));
            }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(GetScore.hitServer.getStatus()!= AsyncTask.Status.FINISHED)
            GetScore.hitServer.cancel(true);
        SportsDilSeService.cd.cancel();
    }
}
