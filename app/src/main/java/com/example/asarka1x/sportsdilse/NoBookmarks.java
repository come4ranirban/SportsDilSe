package com.example.asarka1x.sportsdilse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 9/6/2017.
 */

public class NoBookmarks extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.nobookmarks, container, false);
        return v;
    }
}
