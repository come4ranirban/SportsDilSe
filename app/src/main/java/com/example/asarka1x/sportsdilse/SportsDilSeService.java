package com.example.asarka1x.sportsdilse;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.net.MalformedURLException;

/**
 * Created by asarka1x on 8/21/2017.
 */

public class SportsDilSeService extends Service {

    static CountDownTimer cd;

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
    public int onStartCommand(Intent intent, final int flags, int startId) {

        try {
            new ReadJson().readNews();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        cd= new CountDownTimer(30*60*1000, 20*1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    new GetArticles().getNews();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                cd.start();
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }
}
