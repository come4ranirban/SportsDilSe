package com.example.asarka1x.sportsdilse;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 9/1/2017.
 */

public class SelectedSportsAdapter extends RecyclerView.Adapter<SportsHolder> {

    LinearLayout sportsclick;

    @Override
    public SportsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(MainActivity.activity);
        View v= inflater.inflate(R.layout.selectedsports, parent, false);
        sportsclick= (LinearLayout)v.findViewById(R.id.sportsclick);
        SportsHolder sportsHolder= new SportsHolder(v);
        return sportsHolder;
    }


    @Override
    public void onBindViewHolder(final SportsHolder holder, int position) {
        holder.sportsname.setText(Const.selectedsportslist.get(position));

        switch (Const.selectedsportslist.get(position)){

            case "Cricket":
                holder.sportsimage.setImageResource(R.drawable.cricketball);
                break;

            case "Football":
                holder.sportsimage.setImageResource(R.drawable.soccerball);
                break;

            case "Tennis":
                holder.sportsimage.setImageResource(R.drawable.tennisball);
                break;

            case "Badminton":
                holder.sportsimage.setImageResource(R.drawable.badmintoncock);
                break;

            case "Formula 1":
                holder.sportsimage.setImageResource(R.drawable.formulacar);
                break;

            case "Hockey":
                holder.sportsimage.setImageResource(R.drawable.hockeyball24);
                break;

            case "Track & Field":
                holder.sportsimage.setImageResource(R.drawable.racetrack24);
                break;

            case "other":
                holder.sportsimage.setImageResource(R.drawable.other24);

        }

        sportsclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Color.parseColor("#18FFFF")==holder.sportsname.getCurrentTextColor()){
                    holder.sportsname.setTextColor(Color.WHITE);
                 
                }else {
                    holder.sportsname.setTextColor(Color.parseColor("#18FFFF"));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return Const.selectedsportslist.size();
    }
}

class SportsHolder extends RecyclerView.ViewHolder{

    TextView sportsname;
    ImageView sportsimage;
    public SportsHolder(View itemView) {
        super(itemView);
        sportsimage= (ImageView)itemView.findViewById(R.id.sportsimage);
        sportsname= (TextView)itemView.findViewById(R.id.sportsname);
    }
}
