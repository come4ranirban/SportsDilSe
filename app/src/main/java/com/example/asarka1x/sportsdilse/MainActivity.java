package com.example.asarka1x.sportsdilse;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.Stack;

import in.technomenia.user.sportsdilse.Manifest;
import in.technomenia.user.sportsdilse.R;

public class MainActivity extends AppCompatActivity {

    static ViewPager viewPager;
    static DrawerLayout drawerLayout;
    static MyPagerAdapter pagerAdapter;
    static MainActivity activity;
    static AdView mAdView;
    static Handler connecth,gch;
    static Runnable connectrun,gcrun;
    SQLiteDatabase db;
    private CountDownTimer countDownTimer;
    private BroadcastReceiver connectivity;
    private boolean viewFlag;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private FrameLayout navigationframe;

    public void init() throws MalformedURLException, JSONException {

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
                cursor.close();
            }
        }catch (SQLiteException e){
            db.execSQL("CREATE TABLE IF NOT EXISTS SPORTSLIST(cricket integer default 0, football integer default 0, " +
                    "tennis integer default 0, badminton integer default 0, formula integer default 0," +
                    "hockey integer default 0, track integer default 0, other integer default 0, nightmode integer default 0)");

            db.execSQL("CREATE TABLE IF NOT EXISTS BOOKMARKED(ID number, HEADLINE varchar(200), AUTHOR varchar(30), DATE varchar(18), CONTENT varchar(1500), IMAGE BLOB NOT NULL)");

            //create unique id for user in firebase database
            db.execSQL("CREATE TABLE IF NOT EXISTS VIEWS(UID number, POSTID number)");

            ContentValues values= new ContentValues();
            values.put("cricket", 1);
            values.put("football", 1);
            values.put("tennis", 1);
            values.put("badminton", 1);
            values.put("formula", 1);
            values.put("hockey", 1);
            values.put("track", 1);
            values.put("other", 1);
            values.put("nightmode", 0);
            db.insert("sportslist", null, values);
            init();
        }


        //reset display tempid
        Const.tempid.clear();
        Const.starttempid=true;

        connecth=new Handler();
        connecth.postDelayed(new Runnable() {
            @Override
            public void run() {
                connectrun=this;
                if(Const.internet){
                    try {
                        ReadJson.readNews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }else{
                    connecth.postDelayed(this, 200);
                }
            }
        },200);

        db.execSQL("create table if not exists usercredential(status int, username varchar(20), email varchar(40), password varchar(25))");

        countDownTimer= new CountDownTimer(2 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                setContentView(R.layout.welcomscreen);
            }

            @Override
            public void onFinish() {
                if(Const.phone_state_permission){
                    viewFlag=true;
                    setContentView(R.layout.activity_main);
                    countDownTimer.cancel();
                    onStart();
                }else
                    countDownTimer.start();
            }
        }.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            Const.phone_state_permission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        activity= this;

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3940256099942544~3347511713");

        //register Broadcast receiver for internet connnectivity
        IntentFilter filter = new IntentFilter();
        connectivity = new InternetConnectivity();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivity, filter);


        db= openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE},1);
            }else{
                Const.phone_state_permission=true;
            }
        }else {
            Const.phone_state_permission=true;
        }


        //initialize all settings
        try {
            init();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(viewFlag){

            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("2697A290015D3B37B97F4E74B4C0517E").build();
            mAdView.loadAd(adRequest);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            //initialize tab layout,view pager & drawerlayout
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            drawerLayout.closeDrawers();
            //initialize pager adapter class
            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

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
            onResume();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(viewFlag==true){

            //SET VIEWPAGER AS SOON AS ONRESUME IS CALLED
            if(Const.pageHistory.isEmpty()){
                //Displaying welcome page
                pagerAdapter.clearList();
                pagerAdapter.addFragment(new Articls(),"Feed");
                Const.pageHistory.add(pagerAdapter);
                viewPager.setAdapter(pagerAdapter);
            }else{
                if(Const.pageHistory.get(Const.pageHistory.size()-1).getPageTitle(0).equals("Feed"))
                {
                    getSupportActionBar().show();
                }
                viewPager.setAdapter(Const.pageHistory.get(Const.pageHistory.size()-1));
            }

        }
    }

    @Override
    public void onBackPressed() {

        if(viewFlag){
            Const.pageHistory.remove(Const.pageHistory.size()-1);
            if(Const.pageHistory.isEmpty()){
                InterstitialAd mInterstitialAd = new InterstitialAd(this);
                mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                //2697A290015D3B37B97F4E74B4C0517E
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("5158C1BB1B9FA97D34B9DBA57C39BEA9").build());
                super.onBackPressed();
            }
            else
            {
                if(Const.pageHistory.get(Const.pageHistory.size()-1).getPageTitle(0).equals("Feed"))
                {
                    getSupportActionBar().show();
                    Const.sportsSelected.delete(0,Const.sportsSelected.length());
                    if(mAdView!=null)
                        mAdView.setVisibility(View.VISIBLE);
                }

                viewPager.setAdapter(Const.pageHistory.get(Const.pageHistory.size()-1));
            }
            clearGarbage();
        }
    }

    private void clearGarbage(){
        gch=new Handler();
        gch.postDelayed(new Runnable() {
            @Override
            public void run() {
                gcrun=this;
                // clear both memory and disk caches
                Fresco.getImagePipeline().clearCaches();
                Runtime.getRuntime().gc();
                System.gc();
                gch.postDelayed(this,1000*60*4);
            }
        },1000*60*4);

    }

    @Override
    protected void onPause() {
        super.onPause();

        viewFlag=false;
        if(gch!=null){
            gch.removeCallbacks(gcrun);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        if(connecth!=null)
        {
            connecth.removeCallbacks(connectrun);
            connecth=null;
        }

        unregisterReceiver(connectivity);

        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                mAdView.destroy();
            }
        });

        Articls.savedstate=null;
        MainActivity.activity.finish();
    }

    public void writearticle(View v){
        SQLiteDatabase db= openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
        Cursor cursor= db.rawQuery("select *from usercredential",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0 ){
            cursor.close();
            getSupportActionBar().hide();
            pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
            pagerAdapter.clearList();
            pagerAdapter.addFragment(new WriteArticle(), "WriteArticle");
            Const.pageHistory.add(pagerAdapter);
            viewPager.setAdapter(pagerAdapter);
            drawerLayout.closeDrawers();
        }else{
            cursor.close();
            getSupportActionBar().hide();
            pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
            pagerAdapter.clearList();
            pagerAdapter.addFragment(new LoginPage(), "Login");
            Const.pageHistory.add(pagerAdapter);
            viewPager.setAdapter(pagerAdapter);
            drawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        viewFlag=true;
    }
}
