package com.example.asarka1x.sportsdilse;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import in.technomenia.user.sportsdilse.R;

import static com.example.asarka1x.sportsdilse.MainActivity.drawerLayout;
import static com.example.asarka1x.sportsdilse.MainActivity.pagerAdapter;
import static com.example.asarka1x.sportsdilse.MainActivity.viewPager;

/**
 * Created by asarka1x on 9/14/2017.
 */

public class LoginPage extends Fragment {

    static int result,isresultready,forgotflag;
    static String forgotmsg;
    EditText username,password;
    TextView emailtext,passtext;
    LinearLayout login,signup,skip,forgot;

    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.loginscreen, container, false);
        username= (EditText)v.findViewById(R.id.user);
        password= (EditText)v.findViewById(R.id.password);
        login= (LinearLayout) v.findViewById(R.id.login);
        signup= (LinearLayout) v.findViewById(R.id.signin);
        forgot= (LinearLayout) v.findViewById(R.id.forgot);
        skip= (LinearLayout) v.findViewById(R.id.skip);
        skip.setVisibility(LinearLayout.GONE);

        db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        final ProgressDialog pdialog= new ProgressDialog(getActivity());
        pdialog.setMessage("Authenticating..");
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setIndeterminate(true);
        pdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pdialog.show();
                final AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.activity);
                final Handler h= new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isresultready==1){
                            pdialog.dismiss();
                            isresultready=0;
                            if(result==1){
                                result=0;
                                ContentValues values= new ContentValues();
                                values.put("status",1);
                                values.put("username","");
                                values.put("email",username.getText().toString());
                                values.put("password",password.getText().toString());
                                db.insert("usercredential",null,values);
                                Const.pageHistory.remove(Const.pageHistory.size()-1);
                                pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                                pagerAdapter.clearList();
                                pagerAdapter.addFragment(new WriteArticle(), "WriteArticle");
                                Const.pageHistory.add(pagerAdapter);
                                viewPager.setAdapter(pagerAdapter);
                            }else {
                                result=0;
                                alert.setMessage("invalid email_id or password");
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }

                            h.removeCallbacks(this);
                        }else {
                            h.postDelayed(this, 20);
                        }
                    }
                },20);

                Auth auth= new Auth(username.getText().toString(), password.getText().toString());
                auth.getNonce();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                pagerAdapter.clearList();
                pagerAdapter.addFragment(new SignUp(), "SignUp");
                Const.pageHistory.add(pagerAdapter);
                viewPager.setAdapter(pagerAdapter);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().isEmpty()){
                   Toast.makeText(getActivity(), "Email Id empty", Toast.LENGTH_SHORT).show();
                }else {

                    final ProgressDialog pdialog= new ProgressDialog(getActivity());
                    pdialog.setMessage("please wait..");
                    pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pdialog.setIndeterminate(true);
                    pdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    pdialog.show();

                    final AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.activity);
                    final Handler h= new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(forgotflag==1){
                                forgotflag=0;
                                pdialog.dismiss();
                                alert.setMessage(forgotmsg);
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                h.removeCallbacks(this);
                            }else {
                                h.postDelayed(this,20);
                            }

                        }
                    },20);
                    Auth auth= new Auth();
                    auth.forgotpassword(username.getText().toString());
                }
            }
        });
    }
}

