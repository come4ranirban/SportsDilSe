package com.example.asarka1x.sportsdilse;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import in.technomenia.user.sportsdilse.R;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by user on 18-07-2017.
 */

public class Articls extends Fragment{

    public static Bundle savedstate;
    private static RelativeLayout layoutbacktheme;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    Parcelable listState;
    ProgressBar progressBar;
    boolean pvisibility;
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
        View v= inflater.inflate(R.layout.articlelayout, container, false);

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

        if(Const.news!=null)
            try {
                ReadJson.readNews();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        theme();
    }

    @Override
    public void onResume() {
        super.onResume();
        h= new Handler();

        if(pvisibility==true)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                 if(savedstate==null){

                    if(Const.showadapter==true && Const.starttempid==false) {
                        Const.showadapter=false;
                        progressBar.setVisibility(View.GONE);
                        newsRecycler.setAdapter(new NewsAdapter());
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

    @Override
    public void onPause() {
        super.onPause();
        if(MainActivity.mAdView!=null)
            MainActivity.mAdView.setVisibility(View.GONE);

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
                pvisibility=true;
            }
            else{
                listState = savedstate.getParcelable(KEY_RECYCLER_STATE);
                pvisibility=false;
                newsRecycler.getLayoutManager().onRestoreInstanceState(listState);
                newsRecycler.setAdapter(new NewsAdapter());
            }
        }
    }
}

