package com.example.asarka1x.sportsdilse;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONException;

import java.net.MalformedURLException;

/**
 * Created by asarka1x on 8/21/2017.
 */

public class SportsDilSeService extends Service {

    private CountDownTimer cd;
    private Handler h;

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

        final GetArticles getArticles= new GetArticles();

        cd= new CountDownTimer(30*60*1000, 2*60*1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    getArticles.getNews();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                cd.start();
            }
        }.start();

        h= new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                // clear both memory and disk caches
                Fresco.getImagePipeline().clearCaches();
                System.gc();
                h.postDelayed(this,1000*60*4);
            }
        },1000*60*4);

        return super.onStartCommand(intent, flags, startId);
    }
}
