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
    static String news=null;
    static boolean nightmode,starttempid, internet;
    static int newsCount=0;
    static StringBuffer sportsSelected= new StringBuffer();//sports selected on navigation drawer
    static boolean phone_state_permission;
    static boolean cricket,football,tennis,badminton,formula1,hockey,setadapter,showadapter;
    static ArrayList<String> newsid= new ArrayList<>();
    static ArrayList<String> tempid= new ArrayList<>();
    static ArrayList<String> sportsdisplayid= new ArrayList<>();
    static ArrayList<Object> newsDetails= new ArrayList<>();
    static ArrayList<MyPagerAdapter> pageHistory= new ArrayList<>();
    static ArrayList<String> selectedsportslist= new ArrayList<>();
}

