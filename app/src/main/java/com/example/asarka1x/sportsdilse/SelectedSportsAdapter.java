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

import static com.example.asarka1x.sportsdilse.MainActivity.drawerLayout;
import static com.example.asarka1x.sportsdilse.MainActivity.pagerAdapter;
import static com.example.asarka1x.sportsdilse.MainActivity.viewPager;

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

                if(Const.sportsSelected!=null)
                    Const.sportsSelected.delete(0,Const.sportsSelected.length());

                if(Color.parseColor("#18FFFF")==holder.sportsname.getCurrentTextColor()){
                    holder.sportsname.setTextColor(Color.WHITE);
                }else {
                    holder.sportsname.setTextColor(Color.parseColor("#18FFFF"));
                }

                if(holder.sportsname.getText().equals("Cricket"))
                    Const.sportsSelected.append("Cricket");

                if(holder.sportsname.getText().equals("Football"))
                    Const.sportsSelected.append("Football");

                if(holder.sportsname.getText().equals("Tennis"))
                    Const.sportsSelected.append("Tennis");

                if(holder.sportsname.getText().equals("Badminton"))
                    Const.sportsSelected.append("Badminton");

                if(holder.sportsname.getText().equals("Formula 1"))
                    Const.sportsSelected.append("Formula 1");

                if(holder.sportsname.getText().equals("Hockey"))
                    Const.sportsSelected.append("Hockey");

                if(holder.sportsname.getText().equals("Track & Field"))
                    Const.sportsSelected.append("Track & Field");

                if(holder.sportsname.getText().equals("other"))
                    Const.sportsSelected.append("other");

                for(int i=0;i<Const.pageHistory.size();i++){
                    if(Const.pageHistory.get(i).getPageTitle(0).equals("SportsPickedforDisplay"))
                        Const.pageHistory.remove(i);
                }

                pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                pagerAdapter.clearList();
                pagerAdapter.addFragment(new SportsPicked(),"SportsPickedforDisplay");
                Const.pageHistory.add(pagerAdapter);
                viewPager.setAdapter(pagerAdapter);
                drawerLayout.closeDrawers();
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
