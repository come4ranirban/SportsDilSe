package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import in.technomenia.user.sportsdilse.R;

/**
 * Created by asarka1x on 9/11/2017.
 */

public class WriteArticle extends Fragment {

    private Spinner sports;
    private EditText  content, title;
    private ImageButton  send, filesaved, retrive, back;
    private StringBuffer buffer;
    private String catagory;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.writearticle, container, false);
        sports= (Spinner)v.findViewById(R.id.sportsspinner);
        title= (EditText)v.findViewById(R.id.title);
        content= (EditText)v.findViewById(R.id.content);
        send= (ImageButton)v.findViewById(R.id.send);
        filesaved= (ImageButton)v.findViewById(R.id.savedimg);
        retrive= (ImageButton)v.findViewById(R.id.retrive);
        back= (ImageButton)v.findViewById(R.id.back);

        db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);
        try{
            cursor= db.rawQuery("select * from writearticle",null);

        }catch (SQLiteException e){
            db.execSQL("CREATE TABLE IF NOT EXISTS writearticle(id int, catagory varchar(20), title varchar(200), content varchar(1500))");
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        sports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0:
                        catagory=null;
                        break;

                    case 1:
                        catagory= "cricket";
                        break;

                    case 2:
                        catagory= "hockey";
                        break;

                    case 3:
                        catagory= "badminton";
                        break;

                    case 4:
                        catagory= "formula 1";
                        break;

                    case 5:
                        catagory= "hockey";
                        break;

                    case 6:
                        catagory= "track & field";
                        break;

                    case 7:
                        catagory= "other";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                catagory= null;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(catagory==null)
                    Toast.makeText(getActivity(), "Select Sports Catagory", Toast.LENGTH_SHORT).show();

                if(title.getText().toString()==null || title.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Content Title Empty", Toast.LENGTH_SHORT).show();

                if(content.getText().toString()==null || content.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Content Empty", Toast.LENGTH_SHORT).show();

                if(!title.getText().toString().isEmpty() && !content.getText().toString().isEmpty() && catagory!=null)
                {
                    buffer= new StringBuffer();
                    buffer.append("Catagory:-"+catagory);
                    buffer.append("\nTitle:-"+title.getText().toString());
                    buffer.append("\nContent:-"+content.getText().toString());

                    SendMail sm= new SendMail(getActivity(), "anirbansrkr007@gmail.com", "test", buffer.toString());
                    sm.execute();
                }
            }
        });

        filesaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(catagory==null)
                    Toast.makeText(getActivity(), "Select Sports Catagory", Toast.LENGTH_SHORT).show();

                if(title.getText().toString()==null || title.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Content Title Empty", Toast.LENGTH_SHORT).show();

                if(content.getText().toString()==null || content.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Content Empty", Toast.LENGTH_SHORT).show();

                if(!title.getText().toString().isEmpty() && !content.getText().toString().isEmpty() && catagory!=null)
                {
                    db.execSQL("delete from writearticle");
                    ContentValues values= new ContentValues();
                    values.put("id",1);
                    values.put("catagory", catagory);
                    values.put("title", title.getText().toString());
                    values.put("content", content.getText().toString());
                    db.insert("writearticle",null,values);

                    cursor= db.rawQuery("select * from writearticle",null);
                    if(cursor.getCount()>0)
                        Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor= db.rawQuery("select * from writearticle",null);
                cursor.moveToFirst();
                if(cursor.getCount()>0){
                    for(int i=0; i<sports.getAdapter().getCount(); i++){
                        if(sports.getItemAtPosition(i).toString().equalsIgnoreCase(cursor.getString(1))){
                            sports.setSelection(i);
                            break;
                        }
                    }

                    title.setText(cursor.getString(2));
                    content.setText(cursor.getString(3));

                }else{
                    Toast.makeText(getActivity(), "No Items Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.activity.onBackPressed();
            }
        });
    }
}
