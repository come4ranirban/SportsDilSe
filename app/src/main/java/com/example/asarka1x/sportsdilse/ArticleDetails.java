package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.net.URL;

import in.technomenia.user.sportsdilse.R;

import static android.text.Html.fromHtml;

/**
 * Created by asarka1x on 8/9/2017.
 */

public class ArticleDetails extends Fragment {

    static SQLiteDatabase db;
    ImageView bookmark;
    private ValueEventListener dataentry,viewdisplay;
    private SimpleDraweeView newsimage;
    private DatabaseReference postId;
    private FloatingActionButton share;
    private TextView newscontent, newsAuthor, newsTime, newsHeadline, viewtext, views;
    private ImageButton backButton;
    private LinearLayout articlelayout;
    private Uri uri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.articledetails, container, false);
        newsimage= (SimpleDraweeView)v.findViewById(R.id.newsimage);
        newscontent= (TextView)v.findViewById(R.id.newscontent);
        newsAuthor= (TextView)v.findViewById(R.id.newsAuthor);
        views= (TextView)v.findViewById(R.id.views);
        viewtext= (TextView)v.findViewById(R.id.viewtext);
        newsTime= (TextView)v.findViewById(R.id.date);
        newsHeadline= (TextView)v.findViewById(R.id.newsHeadline);
        backButton= (ImageButton) v.findViewById(R.id.back);
        bookmark= (ImageView) v.findViewById(R.id.bookmark);
        articlelayout= (LinearLayout)v.findViewById(R.id.articlelayout);
        share= (FloatingActionButton)v.findViewById(R.id.sharefab);
        db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);


        AdRequest adRequest = new AdRequest.Builder().addTestDevice("5158C1BB1B9FA97D34B9DBA57C39BEA9").build();

        AdView mAdView;
        mAdView = (AdView)v.findViewById(R.id.fragadd);
        mAdView.loadAd(adRequest);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(Const.nightmode==true)
            darktheme();
        else
            lighttheme();

        final TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        postId= FirebaseDatabase.getInstance().getReference("Postid");
        dataentry= postId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Const.newsDetails.get(Const.newsindex).toString())){
                    if(Const.phone_state_permission){
                        if(!dataSnapshot.child(Const.newsDetails.get(Const.newsindex).toString()).hasChild(telephonyManager.getDeviceId()))
                            dataSnapshot.child(Const.newsDetails.get(Const.newsindex).toString()).getRef().child(telephonyManager.getDeviceId()).setValue(telephonyManager.getDeviceId());
                    }
                }else{
                    if(Const.phone_state_permission){
                        if(!dataSnapshot.child(Const.newsDetails.get(Const.newsindex).toString()).hasChild(telephonyManager.getDeviceId()))
                            dataSnapshot.child(Const.newsDetails.get(Const.newsindex).toString()).getRef().child(telephonyManager.getDeviceId()).setValue(telephonyManager.getDeviceId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        viewdisplay= postId.child(Const.newsDetails.get(Const.newsindex).toString()).getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if((int)dataSnapshot.getChildrenCount() <= 1)
                    viewtext.setText("View");
                else
                    viewtext.setText("Views");
                views.setText(""+(int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        String html,date;
        uri= Uri.parse(Const.newsDetails.get(Const.newsindex+8).toString());
        newsimage.setImageURI(uri);
        html= (String) Const.newsDetails.get(Const.newsindex+5);

        StringBuffer buffer= new StringBuffer();

        date= Const.newsDetails.get(Const.newsindex+2).toString();

        for (int i=0;i<10;i++)
            buffer.append(date.charAt(i));

        newsAuthor.setText(Const.newsDetails.get(Const.newsindex+7).toString());
        newsTime.setText(buffer.toString());
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



        boolean bookstatus= getBookmarkStatus();
        if(bookstatus)
            bookmark.setImageResource(R.drawable.bookmarkadded);
        else
            bookmark.setImageResource(R.drawable.makebookmark);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bookstatus= getBookmarkStatus();

                if(bookstatus==false){
                    bookmark.setImageResource(R.drawable.bookmarkadded);
                    newsimage.setDrawingCacheEnabled(true);
                    newsimage.buildDrawingCache();
                    Bitmap bm= newsimage.getDrawingCache();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    byte imageBuffer[] = outputStream.toByteArray();

                    ContentValues values=  new ContentValues();
                    values.put("ID", Integer.parseInt(Const.newsDetails.get(Const.newsindex+0).toString()));
                    values.put("HEADLINE", newsHeadline.getText().toString());
                    values.put("AUTHOR", newsAuthor.getText().toString());
                    values.put("DATE", newsTime.getText().toString());
                    values.put("CONTENT", (String) Const.newsDetails.get(Const.newsindex+5));
                    values.put("IMAGE", imageBuffer);
                    db.insert("BOOKMARKED", null, values);
                }else{
                    db.execSQL("delete from BOOKMARKED where id="+Const.newsDetails.get(Const.newsindex+0));
                    bookmark.setImageResource(R.drawable.makebookmark);
                }
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent= new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, Const.newsDetails.get(Const.newsindex+4).toString());
                startActivity(Intent.createChooser(shareIntent, "choose"));
            }
        });
    }

    public void darktheme(){
        articlelayout.setBackgroundColor(Color.parseColor("#616161"));
        newsAuthor.setTextColor(Color.WHITE);
        newsHeadline.setTextColor(Color.WHITE);
        newsTime.setTextColor(Color.WHITE);
        viewtext.setTextColor(Color.WHITE);
        views.setTextColor(Color.WHITE);
        newscontent.setTextColor(Color.WHITE);
    }

    public void lighttheme(){
        articlelayout.setBackgroundColor(Color.WHITE);
        newsAuthor.setTextColor(Color.BLACK);
        newsHeadline.setTextColor(Color.BLACK);
        newsTime.setTextColor(Color.DKGRAY);
        viewtext.setTextColor(Color.BLUE);
        views.setTextColor(Color.BLUE);
        newscontent.setTextColor(Color.DKGRAY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public boolean getBookmarkStatus(){
        boolean temp=false;
        Cursor cursor = db.rawQuery("select * from BOOKMARKED", null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                if(cursor.getString(0).equals(Const.newsDetails.get(Const.newsindex+0).toString())){
                    temp=true;
                    break;
                }else{
                    temp=false;
                }
            }while(cursor.moveToNext());
        }else
            temp=false;

        return temp;
    }

    @Override
    public void onPause() {
        super.onPause();
        postId.removeEventListener(viewdisplay);
        postId.removeEventListener(dataentry);
    }
}
