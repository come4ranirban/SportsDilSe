package com.example.asarka1x.sportsdilse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 10/22/2017.
 */

public class AboutUs extends Fragment {

    private static AboutUs aboutUs= new AboutUs();

    public static AboutUs getInstance(){
        return aboutUs;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.aboutus,container,false);
        ImageButton back= (ImageButton)v.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.onBackPressed();
            }
        });
        return v;
    }
}
