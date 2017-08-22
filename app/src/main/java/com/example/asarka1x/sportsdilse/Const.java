package com.example.asarka1x.sportsdilse;

import android.app.Service;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by user on 24-07-2017.
 */

public class Const {

    static int flag=0;//1-achived unique id; 2- achived livescore; 3- to display livescore in recyclerview
    static int newsflag=0;//2-compleated loading all news
    static int newsindex=0;
    static String jsonuid;
    static String jsonMatch;
    static String news;
    static int newsCount=0;
    static ArrayList<String> uid= new ArrayList<>();
    static ArrayList<String> newsid= new ArrayList<>();
    static ArrayList<String> cricketid= new ArrayList<>();
    static ArrayList<String> footballid= new ArrayList<>();
    static ArrayList<String> tennisid= new ArrayList<>();
    static ArrayList<String> badmintonid= new ArrayList<>();
    static ArrayList<String> formula1id= new ArrayList<>();
    static ArrayList<String> hockeyid= new ArrayList<>();
    static ArrayList<String> tracknfieldid= new ArrayList<>();
    static ArrayList<String> otherSportsid= new ArrayList<>();
    static ArrayList<String> matchDetails= new ArrayList<>();
    static ArrayList<Object> newsDetails= new ArrayList<>();
    static ArrayList<MyPagerAdapter> pageHistory= new ArrayList<>();
}

