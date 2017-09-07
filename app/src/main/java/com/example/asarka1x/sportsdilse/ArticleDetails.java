package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import in.technomenia.user.sportsdilse.R;

import static android.text.Html.fromHtml;

/**
 * Created by asarka1x on 8/9/2017.
 */

public class ArticleDetails extends Fragment {

    private SimpleDraweeView newsimage;
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
        View v= inflater.inflate(R.layout.articledetails, container, false);
        newsimage= (SimpleDraweeView)v.findViewById(R.id.newsimage);
        newscontent= (TextView)v.findViewById(R.id.newscontent);
        newsAuthor= (TextView)v.findViewById(R.id.newsAuthor);
        dayswitch= (Switch)v.findViewById(R.id.dayswitch);
        daymode= (TextView)v.findViewById(R.id.daymode);
       // newsTime= (TextView)v.findViewById(R.id.newstime);
        newsHeadline= (TextView)v.findViewById(R.id.newsHeadline);
        backButton= (ImageButton) v.findViewById(R.id.back);
        bookmark= (ImageView) v.findViewById(R.id.bookmark);
        articlelayout= (LinearLayout)v.findViewById(R.id.articlelayout);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

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

        String html;
        uri= Uri.parse(Const.newsDetails.get(Const.newsindex+8).toString());
        newsimage.setImageURI(uri);
        html= (String) Const.newsDetails.get(Const.newsindex+5);
        newsAuthor.setText(Const.newsDetails.get(Const.newsindex+7).toString());
        //newsTime.setText(Const.newsDetails.get(Const.newsindex+2).toString());
        newsHeadline.setText(Const.newsDetails.get(Const.newsindex+1).toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           newscontent.setText(fromHtml(html, Html.FROM_HTML_MODE_LEGACY)) ;
        } else {
            newscontent.setText(Html.fromHtml(html));
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.onBackPressed();
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS BOOKMARKED(ID number, HEADLINE varchar(1000), AUTHOR varchar(30), DATE varchar(18), CONTENT varchar(1024), IMAGE BLOB NOT NULL)");

                newsimage.setDrawingCacheEnabled(true);
                newsimage.buildDrawingCache();
                Bitmap bm= newsimage.getDrawingCache();
                //Bitmap bm= ((BitmapDrawable) newsimage.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte imageBuffer[] = outputStream.toByteArray();

                ContentValues values=  new ContentValues();
                values.put("ID", Integer.parseInt(Const.newsDetails.get(Const.newsindex+0).toString()));
                values.put("HEADLINE", newsHeadline.getText().toString());
                values.put("AUTHOR", newsAuthor.getText().toString());
                //values.put("DATE", newsTime.getText().toString());
                values.put("CONTENT", (String) Const.newsDetails.get(Const.newsindex+5));
                values.put("IMAGE", imageBuffer);
                long i= db.insert("BOOKMARKED", null, values);
                Cursor cursor= db.rawQuery("SELECT * FROM BOOKMARKED",null);
                if(i>0)
                    Toast.makeText(getActivity(), "entry\nno->"+cursor.getCount(), Toast.LENGTH_SHORT).show();
                db.close();
            }
        });

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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
