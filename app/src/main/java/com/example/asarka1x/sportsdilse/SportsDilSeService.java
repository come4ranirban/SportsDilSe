package com.example.asarka1x.sportsdilse;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by asarka1x on 8/21/2017.
 */

public class SportsDilSeService extends Service {

    static Handler h;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final GetScore getScore= new GetScore();
        h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getScore.getNews();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                h.postDelayed(this, 20000);
            }
        },20000);
        return super.onStartCommand(intent, flags, startId);
    }
}
