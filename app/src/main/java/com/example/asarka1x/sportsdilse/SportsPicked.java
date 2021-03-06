package com.example.asarka1x.sportsdilse;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 10/18/2017.
 */

public class SportsPicked extends Fragment {


    public static Bundle savedstate;
    private static RelativeLayout layoutbacktheme;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    Parcelable listState;
    ProgressBar progressBar;
    boolean pvisibility;
    private boolean showSports=false;
    private RecyclerView newsRecycler;
    private Handler h;
    private Runnable run;

    public static void theme(){
        if(Const.nightmode)
            layoutbacktheme.setBackgroundColor(Color.parseColor("#212121"));
        else
            layoutbacktheme.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v;
        Const.sportsdisplayid.clear();

        for(int i=0; i<Const.newsDetails.size(); i=i+9){
            if(Const.sportsSelected.toString().equals(Const.newsDetails.get(i+6).toString())){
                Const.sportsdisplayid.add(Const.newsDetails.get(i).toString());
            }
        }

        if(Const.sportsdisplayid.size()==0)
        {
            showSports=false;
            v= inflater.inflate(R.layout.noitemlayout,container,false);
        }
        else
        {
            showSports=true;
            v= inflater.inflate(R.layout.articlelayout, container, false);
        }

        if(showSports){
            layoutbacktheme= (RelativeLayout)v.findViewById(R.id.articlelayout);
            progressBar= (ProgressBar)v.findViewById(R.id.progressbar);
            pvisibility=true;
            newsRecycler= (RecyclerView) v.findViewById(R.id.newsRecycler);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            newsRecycler.setLayoutManager(layoutManager);
            newsRecycler.setHasFixedSize(true);
            newsRecycler.setNestedScrollingEnabled(false);
            newsRecycler.setItemViewCacheSize(20);
            newsRecycler.setDrawingCacheEnabled(true);
            newsRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            newsRecycler.getRecycledViewPool().clear();
            newsRecycler.removeAllViews();
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        if(showSports)
            theme();
    }


    @Override
    public void onResume() {
        super.onResume();

        if(MainActivity.mAdView!=null)
            MainActivity.mAdView.setVisibility(View.VISIBLE);

        if(showSports){
            h= new Handler();

            if(pvisibility==true)
                progressBar.setVisibility(View.VISIBLE);
            else
                progressBar.setVisibility(View.GONE);

            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(savedstate==null){

                        if(Const.sportsdisplayid.size()>0) {
                            progressBar.setVisibility(View.GONE);
                            newsRecycler.setAdapter(new SportsPickedDisplayAdapter());
                            run=this;
                            h.removeCallbacks(this);
                        }
                        else{
                            h.postDelayed(this,20);
                        }
                    }
                }
            },20);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(MainActivity.mAdView!=null)
            MainActivity.mAdView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(showSports){
            listState = newsRecycler.getLayoutManager().onSaveInstanceState();
            savedstate=outState;
            savedstate.putParcelable(KEY_RECYCLER_STATE, listState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(showSports){
            if (savedstate != null) {

                if(Const.setadapter==true){
                    Const.setadapter= false;
                    savedstate= null;
                    pvisibility=true;
                }
                else{
                    listState = savedstate.getParcelable(KEY_RECYCLER_STATE);
                    pvisibility=false;
                    newsRecycler.getLayoutManager().onRestoreInstanceState(listState);
                    newsRecycler.setAdapter(new SportsPickedDisplayAdapter());
                }
            }
        }
    }
}
