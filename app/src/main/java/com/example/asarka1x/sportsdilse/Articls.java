package com.example.asarka1x.sportsdilse;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import in.technomenia.user.sportsdilse.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 18-07-2017.
 */

public class Articls extends Fragment{

    private RecyclerView newsRecycler;
    private Handler h;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle savedstate;
    Parcelable listState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.articlelayout, container, false);

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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                 if(savedstate==null){

                    if(Const.showadapter==true && Const.starttempid==false) {
                        Const.showadapter=false;
                        newsRecycler.setAdapter(new NewsAdapter());
                        h.removeCallbacks(this);
                    }
                    else
                        h.postDelayed(this,200);
                }
            }
        },200);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        listState = newsRecycler.getLayoutManager().onSaveInstanceState();
        savedstate=outState;
        savedstate.putParcelable(KEY_RECYCLER_STATE, listState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedstate != null) {
            if(Const.setadapter==true){
                Const.setadapter= false;
                savedstate= null;
                onResume();
            }
            else{
                listState = savedstate.getParcelable(KEY_RECYCLER_STATE);
                newsRecycler.getLayoutManager().onRestoreInstanceState(listState);
                newsRecycler.setAdapter(new NewsAdapter());
            }
        }
    }
}

