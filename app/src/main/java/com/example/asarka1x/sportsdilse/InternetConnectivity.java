package com.example.asarka1x.sportsdilse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by user on 14-01-2017.
 */

public class InternetConnectivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if(isConnected != true )
        {
            Const.internet=false;
            Toast.makeText(MainActivity.activity, "You are offline", Toast.LENGTH_SHORT).show();
        }else{
            Const.internet=true;
        }
    }
}

