package com.example.asarka1x.sportsdilse;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
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

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView text;
    static ViewPager viewPager;
    static DrawerLayout drawerLayout;
    static MyPagerAdapter pagerAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Handler h;
    private Runnable callback;
    static MainActivity activity;
    private boolean savedpageHistory;
    private FrameLayout navigationframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        activity= this;
        Intent intent= new Intent();
        intent.setComponent(new ComponentName(getApplication(), SportsDilSeService.class));
        startService(intent);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        text= (TextView) toolbar.findViewById(R.id.toolbartext);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        //initialize action bar drawer toggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(getApplicationContext(), "open", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "history empty", Toast.LENGTH_SHORT).show();
            //Displaying welcome page
            pagerAdapter.clearList();
            pagerAdapter.addFragment(new Articls(),"Feed");
            // Const.pageHistory.add(pagerAdapter);
            //pagerAdapter.addFragment(new MatchList(),"Matches");
            Const.pageHistory.add(pagerAdapter);
            viewPager.setAdapter(pagerAdapter);
           //tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
            //   tabLayout.setupWithViewPager(viewPager);
            Const.starttempid=true;
            Const.tempid.clear();
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
                Toast.makeText(getApplicationContext(), "PageString", Toast.LENGTH_SHORT).show();
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


}
