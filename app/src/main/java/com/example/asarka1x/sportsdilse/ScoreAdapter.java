package com.example.asarka1x.sportsdilse;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.technomenia.user.sportsdilse.R;

/**
 * Created by user on 22-07-2017.
 */
public class ScoreAdapter extends RecyclerView.Adapter<MyHolder> {

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(MainActivity.activity);
        View v= inflater.inflate(R.layout.scoreboard, parent, false);
        MyHolder holder= new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        int index= Const.matchDetails.indexOf(Const.uid.get(position));
//        holder.ll.setBackgroundResource(R.drawable.greenimage);
//        holder.uid.setText(Const.matchDetails.get(index));
        holder.stat.setText(Const.matchDetails.get(index+1));
        holder.score.setText(Const.matchDetails.get(index+2));
        holder.team1.setText(Const.matchDetails.get(index+3));
        holder.team2.setText(Const.matchDetails.get(index+4));
    }

    @Override
    public int getItemCount() {
        return Const.jsonuid.length();
    }
}

class MyHolder extends RecyclerView.ViewHolder{

    TextView stat,uid,score,team1,team2;
    LinearLayout ll;
    public MyHolder(View itemView) {
        super(itemView);
//        ll= (LinearLayout) itemView.findViewById(R.id.ll);
        stat= (TextView)itemView.findViewById(R.id.status);
        //uid= (TextView)itemView.findViewById(R.id.uid);
        score= (TextView)itemView.findViewById(R.id.score);
        team1= (TextView)itemView.findViewById(R.id.team1);
        team2= (TextView)itemView.findViewById(R.id.team2);
    }
}