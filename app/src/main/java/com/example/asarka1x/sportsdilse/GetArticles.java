package com.example.asarka1x.sportsdilse;



import android.os.AsyncTask;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


class GetArticles {

    static int totalArticle;
    HitServer hitServer;
    private String urls;
    private URL url;
    private StringBuffer buffer;

    public void getNews() throws MalformedURLException {

        if(MainActivity.connecth!=null)
        {
            MainActivity.connecth.removeCallbacks(MainActivity.connectrun);
            MainActivity.connecth=null;
        }

        int count=10;
        if(totalArticle>0)
            count=totalArticle;

        hitServer= new HitServer();
        urls="https://sportsdilse.com/?json=get_recent_posts&count="+count;
        if(buffer==null)
            buffer = new StringBuffer();
        Const.newsflag=1;
        hitServer.execute();
    }


    class HitServer extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                url = new URL(urls);
                InputStream isr = url.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"UTF-8"),8125);

                String line = null;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } catch (MalformedURLException e) {
                try {
                    getNews();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } catch (IOException e) {
                try {
                    getNews();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(buffer!=null && Const.newsflag == 1){
                Const.news= buffer.toString();

                if(Const.news!=null){
                    try {
                        Const.flag=1;
                        ReadJson.readNews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


class ReadJson {

    private static JSONObject jsonObject;
    private static JSONArray posts;

    public static void readNews() throws JSONException, MalformedURLException {

        if(Const.news==null) {
            new GetArticles().getNews();
        }else{

            jsonObject = new JSONObject(Const.news);
            if (jsonObject.has("count_total")) {
                Const.flag = 4;
                Const.newsCount = jsonObject.getInt("count_total");
            }

            if(jsonObject.has("count")){
                if(Const.newsCount!=jsonObject.getInt("count")) {
                    GetArticles.totalArticle=Const.newsCount;
                    new GetArticles().getNews();
                }else{

                    Const.newsid.clear();
                    Const.newsDetails.clear();


                    posts = jsonObject.getJSONArray("posts");
                    for (int i = 0; i < posts.length(); i++) {
                        final JSONObject post = posts.getJSONObject(i);
                        if (post.has("id")) {
                            Const.newsid.add(post.getString("id"));
                        }
                    }

                    for(int i=0; i<posts.length(); i++){
                        JSONObject post= posts.getJSONObject(i);
                        if(post.has("id"))
                            Const.newsDetails.add(post.getString("id"));
                        else
                            Const.newsDetails.add("null");

                        if(post.has("title"))
                            Const.newsDetails.add(post.getString("title"));
                        else
                            Const.newsDetails.add("null");

                        if(post.has("date"))
                            Const.newsDetails.add(post.getString("date"));
                        else
                            Const.newsDetails.add("null");

                        if(post.has("modified"))
                            Const.newsDetails.add(post.getString("modified"));
                        else
                            Const.newsDetails.add("null");

                        if(post.has("url"))
                            Const.newsDetails.add(post.getString("url"));
                        else
                            Const.newsDetails.add("null");
                        if(post.has("content"))
                            Const.newsDetails.add(post.getString("content"));
                        else
                            Const.newsDetails.add("null");

                        if(post.has("categories")){
                            JSONArray catagories= post.getJSONArray("categories");

                            if(catagories.length()>0){
                                Const.newsDetails.add(catagories.getJSONObject(0).getString("title"));
                                if(Const.starttempid == true){

                                    switch (catagories.getJSONObject(0).getString("title")) {
                                        case "Cricket":
                                            if (Const.cricket == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        case "Football":
                                            if (Const.football == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        case "Tennis":
                                            if (Const.tennis == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        case "Badminton":
                                            if (Const.badminton == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        case "Formula 1":
                                            if (Const.formula1 == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        case "Hockey":
                                            if (Const.hockey == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        case "Track & Field":
                                            if (Const.trackfield == true)
                                                Const.tempid.add(post.getString("id"));
                                            break;

                                        default:
                                            if (Const.other == true) {
                                                Const.tempid.add(post.getString("id"));
                                            }
                                            break;
                                    }
                                }
                            }
                            else
                                Const.newsDetails.add("null");
                        }
                        if (post.has("author"))
                            Const.newsDetails.add(post.getJSONObject("author").getString("nickname"));
                        else
                            Const.newsDetails.add("null");

                        if(post.has("thumbnail_images")){
                            JSONObject thumbnail= post.getJSONObject("thumbnail_images");
                            Const.newsDetails.add(thumbnail.getJSONObject("medium_large").getString("url"));
                        }
                        else
                            Const.newsDetails.add("null");
                    }


                    if(Const.starttempid==true){
                        Const.starttempid= false;
                        Const.showadapter=true;
                    }
                }
            }
        }
    }
}


