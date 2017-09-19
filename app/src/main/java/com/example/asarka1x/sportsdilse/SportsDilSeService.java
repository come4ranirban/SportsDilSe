package com.example.asarka1x.sportsdilse;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;

/**
 * Created by asarka1x on 8/21/2017.
 */

public class SportsDilSeService extends Service {

    static Handler h;
    static Runnable run;
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

        Const.serviceflag=0;

        cd= new CountDownTimer(30*60*1000, 20*1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    if(Const.serviceflag<=2){
                        new ReadJson().readNews();
                    }else {
                        new GetScore().getNews();
                    }
                    Const.serviceflag++;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {

            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }
}
