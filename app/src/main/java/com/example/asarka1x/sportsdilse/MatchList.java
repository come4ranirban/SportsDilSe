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

import in.technomenia.user.sportsdilse.R;

/**
 * Created by user on 02-08-2017.
 */

public class MatchList extends Fragment {

    RecyclerView rview;
    GetArticles score;
    Handler h;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.matchlist, container, false);

        rview=(RecyclerView)v.findViewById(R.id.recyclView);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rview.setLayoutManager(layoutManager);
        score= new GetArticles();
        //score.getMatchList();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        h= new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Const.flag==3){
                    //Toast.makeText(getApplicationContext(), "uid->  "+Const.uid.get(1)+"\nmatch details-> "+Const.matchDetails.get(5), Toast.LENGTH_SHORT).show();
                    rview.setAdapter(new ScoreAdapter());
                    h.removeCallbacks(this);
                }
                else{
                    h.postDelayed(this,200);
                }
            }
        },200);
    }
}
