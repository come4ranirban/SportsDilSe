package com.example.asarka1x.sportsdilse;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 10/19/2017.
 */

public class SportsPickedDisplayAdapter extends RecyclerView.Adapter<NewsHolder> {
    private int index;
    private Uri uri;
    private MyPagerAdapter pagerAdapter;
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(MainActivity.activity);

        View v= inflater.inflate(R.layout.indiarticle, parent, false);
        NewsHolder newsHolder = new NewsHolder(v);
        return (newsHolder);
    }

    @Override
    public void onBindViewHolder(final NewsHolder holder, final int position) {
        index= Const.newsDetails.indexOf(Const.sportsdisplayid.get(position));
        holder.catagory.setText((String) Const.newsDetails.get(index+6));
        holder.title.setText((String) Const.newsDetails.get(index+1));
//        holder.datetime.setText((String)Const.newsDetails.get(index+2));
        uri= Uri.parse(Const.newsDetails.get(index+8).toString());
        holder.imageNews.setImageURI(uri);

        holder.newscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index= Const.newsDetails.indexOf(Const.sportsdisplayid.get(position));
                Const.newsindex= index;
                pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                pagerAdapter.clearList();
                pagerAdapter.addFragment(new ArticleDetails(), "ArticleDetails");
                MainActivity.viewPager.setAdapter(pagerAdapter);
                Const.pageHistory.add(pagerAdapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Const.tempid.size()-1;
    }
}


