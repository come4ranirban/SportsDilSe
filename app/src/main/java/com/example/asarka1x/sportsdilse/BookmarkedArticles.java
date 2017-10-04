package com.example.asarka1x.sportsdilse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 9/6/2017.
 */

public class BookmarkedArticles extends Fragment {

    static FragmentTransaction fragmentTransaction;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ImageButton backpress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.bookmarkedarticles, container, false);
        backpress= (ImageButton)v.findViewById(R.id.back);
        fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.onBackPressed();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        db= MainActivity.activity.openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        try{
            cursor= db.rawQuery("select * from BOOKMARKED", null);
            if(cursor.getCount()>0){
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame, new BookmarkFrame()).commit();
            }else{
                getFragmentManager().beginTransaction().replace(R.id.frame, new NoBookmarks()).commit();
            }
        }catch (SQLiteException e){
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame, new NoBookmarks()).commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cursor.close();
        db.close();
    }
}

