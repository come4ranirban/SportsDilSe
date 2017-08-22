package com.example.asarka1x.sportsdilse;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Stack;

import in.technomenia.user.sportsdilse.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TextView text;
    static ViewPager viewPager;
    private DrawerLayout drawerLayout;
    static MyPagerAdapter pagerAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Handler h;
    private Runnable callback;
    static MainActivity activity;
    private boolean savedpageHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        activity= this;
        Intent intent= new Intent();
        intent.setComponent(new ComponentName(getApplication(), SportsDilSeService.class));
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

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


        //initialize pager adapter class
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        //initialize action bar drawer toggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView= (NavigationView)findViewById(R.id.navigationview);

        //Displaying welcome page
        pagerAdapter.clearList();
        pagerAdapter.addFragment(new Articls(),"Feed");
       // Const.pageHistory.add(pagerAdapter);
        //pagerAdapter.addFragment(new MatchList(),"Matches");
        viewPager.setAdapter(pagerAdapter);
        Const.pageHistory.add(pagerAdapter);
//        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
     //   tabLayout.setupWithViewPager(viewPager);

        drawerLayout.closeDrawers();

        /*
        //items selected in navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        Const.webview_url="https://sportsdilse.com/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.cricket:
                        Const.webview_url="https://sportsdilse.com/category/cricket/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        Toast.makeText(getApplicationContext(),"https://sportsdilse.com/category/cricket/", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.football:
                        Const.webview_url="https://sportsdilse.com/category/football/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.tennis:
                        Const.webview_url="https://sportsdilse.com/category/tennis/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.badminton:
                        Const.webview_url="https://sportsdilse.com/category/badminton/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.formula:
                        Const.webview_url="https://sportsdilse.com/category/formula1/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.hockey:
                        Const.webview_url="https://sportsdilse.com/category/hockey/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.tandf:
                        Const.webview_url="https://sportsdilse.com/category/track-field/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.osports:
                        Const.webview_url="https://sportsdilse.com/category/othersports/";
                        pagerAdapter.clearList();
                        pagerAdapter.addFragment(new WebViewClass(),"Feed");
                        pagerAdapter.addFragment(new MatchList(),"Matches");
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setTabTextColors(Color.parseColor("#18FFFF"), Color.parseColor("#FFFFFF"));
                        tabLayout.setupWithViewPager(viewPager);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });*/
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
               //Toast.makeText(getApplicationContext(), Const.pageHistory.size()+"\nPage->"+Const.pageHistory.get(Const.pageHistory.size()-1).getFraglist(), Toast.LENGTH_SHORT).show();
                viewPager.setAdapter(Const.pageHistory.get(Const.pageHistory.size()-1));
            }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
