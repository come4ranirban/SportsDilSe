package com.example.asarka1x.sportsdilse;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import in.technomenia.user.sportsdilse.R;

import static android.text.Html.fromHtml;

/**
 * Created by asarka1x on 8/9/2017.
 */

public class NewsDetails extends Fragment {

    private SimpleDraweeView newsimage;
    private TextView newscontent, newsAuthor, newsTime, newsHeadline;
    private ImageButton backButton;
    ImageView bookmark;
    //private Picasso picasso;
    private Uri uri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.articledetails, container, false);
        newsimage= (SimpleDraweeView)v.findViewById(R.id.newsimage);
        newscontent= (TextView)v.findViewById(R.id.newscontent);
        newsAuthor= (TextView)v.findViewById(R.id.newsAuthor);
        newsTime= (TextView)v.findViewById(R.id.newstime);
        newsHeadline= (TextView)v.findViewById(R.id.newsHeadline);
        backButton= (ImageButton) v.findViewById(R.id.back);
        bookmark= (ImageView) v.findViewById(R.id.bookmark);
        /*picasso = new Picasso.Builder(MainActivity.activity)
                .memoryCache(new LruCache(204800))
                .build();*/
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        String html;
        //picasso.with(MainActivity.activity).load((String) Const.newsDetails.get(Const.newsindex+8)).fit().into(newsimage);

        uri= Uri.parse(Const.newsDetails.get(Const.newsindex+8).toString());
        newsimage.setImageURI(uri);
        html= (String) Const.newsDetails.get(Const.newsindex+5);
        newsAuthor.setText(Const.newsDetails.get(Const.newsindex+7).toString());
        newsTime.setText(Const.newsDetails.get(Const.newsindex+2).toString());
        newsHeadline.setText(Const.newsDetails.get(Const.newsindex+1).toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           newscontent.setText(fromHtml(html, Html.FROM_HTML_MODE_LEGACY)) ;
        } else {
            newscontent.setText(Html.fromHtml(html));
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                MainActivity.activity.onBackPressed();
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmark.setImageResource(R.drawable.bookmarkadded);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
