package com.example.asarka1x.sportsdilse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragmentList = new ArrayList<>();
    List<String> mFragmentTitleList = new ArrayList<>();


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void clearList(){
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }

    public void addFragment(Fragment fragment, String string){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(string);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitleList.get(position);
    }

}
