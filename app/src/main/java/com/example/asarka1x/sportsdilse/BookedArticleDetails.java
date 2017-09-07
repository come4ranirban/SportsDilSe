package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;

import in.technomenia.user.sportsdilse.R;

import static android.text.Html.fromHtml;

/**
 * Created by asarka1x on 9/7/2017.
 */

public class BookedArticleDetails extends Fragment {

    private ImageView newsimage;
    private TextView newscontent, newsAuthor, newsTime, newsHeadline,daymode;
    private ImageButton backButton;
    private LinearLayout articlelayout;
    ImageView bookmark;
    private Switch dayswitch;
    static SQLiteDatabase db;
    private Uri uri;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.bookarticledetails, container, false);
        newsimage= (ImageView)v.findViewById(R.id.newsimage);
        newscontent= (TextView)v.findViewById(R.id.newscontent);
        newsAuthor= (TextView)v.findViewById(R.id.newsAuthor);
        dayswitch= (Switch)v.findViewById(R.id.dayswitch);
        daymode= (TextView)v.findViewById(R.id.daymode);
        // newsTime= (TextView)v.findViewById(R.id.newstime);
        newsHeadline= (TextView)v.findViewById(R.id.newsHeadline);
        articlelayout= (LinearLayout)v.findViewById(R.id.articlelayout);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        if(Const.daymode==false){
            dayswitch.setChecked(false);
            darktheme();
        }else{
            dayswitch.setChecked(true);
            lighttheme();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        db= MainActivity.activity.openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
        Cursor cursor= db.rawQuery("select * from BOOKMARKED where id="+BookmarkFrame.id, null);
        String html;
        cursor.moveToFirst();
        Toast.makeText(getActivity(), "count ->"+cursor.getInt(0), Toast.LENGTH_SHORT).show();

        byte image[]= cursor.getBlob(5);
        Bitmap bit= BitmapFactory.decodeByteArray(image, 0, image.length);
        newsimage.setImageBitmap(bit);

        html= cursor.getString(4);
        newsAuthor.setText(cursor.getString(2));
        //newsTime.setText(Const.newsDetails.get(Const.newsindex+2).toString());
        newsHeadline.setText(cursor.getString(1));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newscontent.setText(fromHtml(html, Html.FROM_HTML_MODE_LEGACY)) ;
        } else {
            newscontent.setText(Html.fromHtml(html));
        }

        dayswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    lighttheme();
                    Const.daymode=true;
                }else {
                    darktheme();
                    Const.daymode=false;
                }
            }
        });
    }

    public void darktheme(){
        articlelayout.setBackgroundColor(Color.BLACK);
        newsAuthor.setTextColor(Color.WHITE);
        newsHeadline.setTextColor(Color.WHITE);
        daymode.setTextColor(Color.WHITE);
        newscontent.setTextColor(Color.WHITE);
    }

    public void lighttheme(){
        articlelayout.setBackgroundColor(Color.WHITE);
        newsAuthor.setTextColor(Color.BLACK);
        newsHeadline.setTextColor(Color.BLACK);
        daymode.setTextColor(Color.BLUE);
        newscontent.setTextColor(Color.DKGRAY);
    }
}
