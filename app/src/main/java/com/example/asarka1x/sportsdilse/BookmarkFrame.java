package com.example.asarka1x.sportsdilse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import in.technomenia.user.sportsdilse.R;

import static com.example.asarka1x.sportsdilse.MainActivity.drawerLayout;
import static com.example.asarka1x.sportsdilse.MainActivity.pagerAdapter;
import static com.example.asarka1x.sportsdilse.MainActivity.viewPager;

public class BookmarkFrame extends Fragment {

    static int id;
    RecyclerView bookmarks;
    Bitmap bit;
    byte[] image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.bookmarkrecyclerframe, container, false);
        bookmarks= (RecyclerView)v.findViewById(R.id.bookmarkrecycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookmarks.setLayoutManager(layoutManager);
        bookmarks.setHasFixedSize(true);
        bookmarks.setNestedScrollingEnabled(false);
        bookmarks.setItemViewCacheSize(20);
        bookmarks.setAdapter(new BookmarkAdapter());
        LinearLayout bookmarkback= (LinearLayout)v.findViewById(R.id.bookmarkback);

        if(Const.nightmode)
            bookmarkback.setBackgroundColor(Color.parseColor("#212121"));
        else
            bookmarkback.setBackgroundColor(Color.parseColor("#FFFFFF"));
        return v;
    }


    class BookmarkAdapter extends RecyclerView.Adapter<BookmarkHolder>{

        SQLiteDatabase db;
        Cursor cursor;

        @Override
        public void onViewRecycled(BookmarkHolder holder) {
            super.onViewRecycled(holder);
        }

        @Override
        public BookmarkHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater= LayoutInflater.from(MainActivity.activity);
            View v= inflater.inflate(R.layout.bookmarklayout, parent, false);
            BookmarkHolder holder= new BookmarkHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(BookmarkHolder holder, final int position) {
            db= MainActivity.activity.openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
            cursor= db.rawQuery("select * from BOOKMARKED", null);
            cursor.move(position+1);
            final int deleteid= cursor.getInt(0);
            holder.itemhead.setText(cursor.getString(1));
            image= cursor.getBlob(5);
            Glide.with(getContext()).asBitmap().load(image).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(holder.image);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cursor.close();
                    db.execSQL("delete from BOOKMARKED where id="+deleteid);
                    db.close();
                    bookmarks.setAdapter(new BookmarkAdapter());
                }
            });

            holder.read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cursor= db.rawQuery("select * from BOOKMARKED", null);
                    cursor.move(position+1);
                    id= cursor.getInt(0);
                    cursor.close();
                    db.close();
                    pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                    pagerAdapter.clearList();
                    pagerAdapter.addFragment(new BookedArticleDetails(), "BookedArticleDetails");
                    Const.pageHistory.add(pagerAdapter);
                    viewPager.setAdapter(pagerAdapter);
                    drawerLayout.closeDrawers();
                }
            });
        }

        @Override
        public int getItemCount() {
            db= MainActivity.activity.openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
            cursor= db.rawQuery("select * from BOOKMARKED", null);
            return cursor.getCount();
        }
    }

    class BookmarkHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView itemhead;
        ImageView delete;
        LinearLayout read;
        public BookmarkHolder(View itemView) {
            super(itemView);
            image= (ImageView)itemView.findViewById(R.id.itemimage);
            itemhead= (TextView)itemView.findViewById(R.id.itemhead);
            delete= (ImageView)itemView.findViewById(R.id.delete);
            read= (LinearLayout)itemView.findViewById(R.id.read);
        }
    }
}


