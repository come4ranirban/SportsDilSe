package com.example.asarka1x.sportsdilse;



import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Switch;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.CountedCompleter;

class GetScore {

    HitServer hitServer;
    private String urls;
    private URL url;
    private StringBuffer buffer;

    /*public void getMatchList() {
        hitServer = new HitServer();
        urls = "http://cricapi.com/api/matches?apikey=BIZNbcuBvafNee4yIF8NepNhtLE2";
        Const.flag=1;
        hitServer.execute();
    }*/

    /*public void getLiveScore(String uniqueId) {
        hitServer = new HitServer();
        urls = "http://cricapi.com/api/cricketScore?apikey=BIZNbcuBvafNee4yIF8NepNhtLE2&unique_id="+uniqueId;//http://cricapi.com/api/matches?apikey=BIZNbcuBvafNee4yIF8NepNhtLE2
        Const.flag=2;
        hitServer.execute();
    }*/

    public void getNews() throws MalformedURLException {
        hitServer= new HitServer();
        urls="https://sportsdilse.com/?json=get_recent_posts&count=100";
        url = new URL(urls);
        if(buffer==null)
            buffer = new StringBuffer();
        Const.newsflag=1;
        hitServer.execute();
    }



    class HitServer extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            try {

                InputStream isr = url.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr));

                buffer.delete(0, buffer.length());
                String line = null;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

       /*         if(buffer!=null && Const.flag==2)
                {
                    Const.jsonMatch=buffer.toString();
                    new ReadJson().readLiveScore();
                }*/


                if(buffer!=null && Const.newsflag == 1){
                    Const.news= buffer.toString();

                    if(Const.news!=null){
                        try {
                            Const.flag=1;
                            new ReadJson().readNews();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        /*
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (Const.flag == 1) {
                Const.jsonuid = buffer.toString();
                if(buffer!=null)
                    buffer.delete(0,buffer.length());
                //check flag set for achived uid
                if (Const.jsonuid != null) {
                    try {
                        new ReadJson().readUniqueId();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/
    }
}


class ReadJson {

    GetScore getScore;

    /*
    public void readUniqueId() throws JSONException {

        while (Const.jsonuid != null) {
            getScore= new GetScore();
            JSONObject jsonObject = new JSONObject(Const.jsonuid);
            JSONArray routes = jsonObject.getJSONArray("matches");
            for (int i = 0; i < routes.length(); i++) {
                JSONObject route = routes.getJSONObject(i);
                //if(route.has("matchStarted") && route.getBoolean("matchStarted")== true)
                    Const.uid.add(route.getString("unique_id"));
            }
            break;
        }

        if (Const.uid.size() > 0){
            sendUid();
        }
    }

    public void sendUid(){
        getScore= new GetScore();
        if(Const.index < Const.uid.size() && Const.index<5){
            Const.matchDetails.add(Const.uid.get(Const.index));

            getScore.getLiveScore(Const.uid.get(Const.index));
        }else{
            Const.flag=3;
        }
    }

    public void readLiveScore() throws JSONException{
        JSONObject jsonObject = new JSONObject(Const.jsonMatch);

        if(jsonObject.has("stat"))
            Const.matchDetails.add(jsonObject.getString("stat"));
        else
            Const.matchDetails.add("NA");

        if(jsonObject.has("score"))
            Const.matchDetails.add(jsonObject.getString("score"));
        else
            Const.matchDetails.add("NA");

        if(jsonObject.has("team-1"))
            Const.matchDetails.add(jsonObject.getString("team-1"));
        else
            Const.matchDetails.add("NA");

        if(jsonObject.has("team-2"))
            Const.matchDetails.add(jsonObject.getString("team-2"));
        else
            Const.matchDetails.add("NA");

        Const.index++;
        sendUid();
    }*/

    JSONObject jsonObject;
    JSONArray posts;

    public void readNews() throws JSONException{

        jsonObject = new JSONObject(Const.news);
       /* Picasso picasso = new Picasso.Builder(MainActivity.activity)
                .memoryCache(new LruCache(204800))
                .build();*/

        if (jsonObject.has("count_total")) {
            Const.flag = 4;
            Const.newsCount = jsonObject.getInt("count_total");
        }

        posts = jsonObject.getJSONArray("posts");
        for (int i = 0; i < posts.length(); i++) {
            JSONObject post = posts.getJSONObject(i);
            if (post.has("id"))
                Const.newsid.add(post.getString("id"));
        }

        for(int i=0; i<posts.length(); i++){
            JSONObject post= posts.getJSONObject(i);
            if(post.has("id"))
                Const.newsDetails.add(post.getString("id"));
            if(post.has("title"))
                Const.newsDetails.add(post.getString("title"));
            if(post.has("date"))
                Const.newsDetails.add(post.getString("date"));
            if(post.has("modified"))
                Const.newsDetails.add(post.getString("modified"));
            if(post.has("url"))
                Const.newsDetails.add(post.getString("url"));
            if(post.has("content"))
                Const.newsDetails.add(post.getString("content"));
            if(post.has("categories")){
                JSONArray catagories= post.getJSONArray("categories");
                Const.newsDetails.add(catagories.getJSONObject(0).getString("title"));
                switch(catagories.getJSONObject(0).getString("title")) {

                    case "Formula 1":
                        Const.formula1id.add(post.getString("id"));
                        break;

                    case "Cricket":
                        Const.cricketid.add(post.getString("id"));
                        break;

                    case "Football":
                        Const.footballid.add(post.getString("id"));
                        break;

                    case "Tennis":
                        Const.tennisid.add(post.getString("id"));
                        break;

                    case "Track &amp; Field":
                        Const.tracknfieldid.add(post.getString("id"));
                        break;

                    case "Hockey":
                        Const.hockeyid.add(post.getString("id"));
                        break;

                    case "Badminton":
                        Const.badmintonid.add(post.getString("id"));
                        break;

                    default:
                        Const.otherSportsid.add(post.getString("id"));
                        break;
                }
            }
            if (post.has("author"))
                Const.newsDetails.add(post.getJSONObject("author").getString("nickname"));
            if(post.has("thumbnail_images")){
                JSONObject thumbnail= post.getJSONObject("thumbnail_images");
                //Const.newsDetails.add(picasso.with(MainActivity.activity).load(thumbnail.getJSONObject("medium_large").getString("url")).fit().centerCrop());
                Const.newsDetails.add(thumbnail.getJSONObject("medium_large").getString("url"));
            }
        }

        Const.newsflag=2;
    }
}


