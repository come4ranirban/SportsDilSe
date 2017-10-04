
package com.example.asarka1x.sportsdilse;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.technomenia.user.sportsdilse.R;

import static com.example.asarka1x.sportsdilse.MainActivity.pagerAdapter;
import static com.example.asarka1x.sportsdilse.MainActivity.viewPager;

/**
 * Created by asarka1x on 9/15/2017.
 */

public class SignUp extends Fragment {

    static boolean regresult, error;
    EditText username,email,password,cpass;
    TextView register;
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.signupscreen, container, false);
        username= (EditText)v.findViewById(R.id.username);
        email=(EditText)v.findViewById(R.id.email);
        password= (EditText)v.findViewById(R.id.password);
        cpass= (EditText)v.findViewById(R.id.cpass);
        register= (TextView) v.findViewById(R.id.register);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.activity);
                boolean ok=true;
                if(username.getText().toString().isEmpty()) {
                    ok=false;
                    Toast.makeText(getActivity(), "username is empty", Toast.LENGTH_SHORT).show();
                }

                if(email.getText().toString().isEmpty()) {
                    ok=false;
                    Toast.makeText(getActivity(), "email is empty", Toast.LENGTH_SHORT).show();
                }

                if(password.getText().toString().isEmpty()) {
                    ok=false;
                    Toast.makeText(getActivity(), "password is empty", Toast.LENGTH_SHORT).show();
                }

                if(cpass.getText().toString().isEmpty()) {
                    ok=false;
                    Toast.makeText(getActivity(), "confirm password is empty", Toast.LENGTH_SHORT).show();
                }

                if(ok==true && password.getText().toString().equals(cpass.getText().toString())){

                    final ProgressDialog pdialog= new ProgressDialog(getActivity());
                    pdialog.setMessage("Please Wait..");
                    pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pdialog.setIndeterminate(true);
                    pdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    pdialog.show();

                        final Handler h=  new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(regresult==true){
                                    regresult=false;
                                    pdialog.dismiss();
                                    if(error==false){
                                        ContentValues values= new ContentValues();
                                        values.put("status",1);
                                        values.put("username",username.getText().toString());
                                        values.put("email",username.getText().toString());
                                        values.put("password",password.getText().toString());
                                        db.insert("usercredential",null,values);
                                        Const.pageHistory.remove(Const.pageHistory.size()-1);
                                        Const.pageHistory.remove(Const.pageHistory.size()-1);
                                        pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                                        pagerAdapter.clearList();
                                        pagerAdapter.addFragment(new WriteArticle(), "WriteArticle");
                                        Const.pageHistory.add(pagerAdapter);
                                        viewPager.setAdapter(pagerAdapter);
                                    }else {
                                        alert.setMessage("You are already registered.\n Please login..");
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
                                    h.postDelayed(this,20);
                                }
                            }
                        },20);

                        final Auth auth= new Auth(username.getText().toString(),email.getText().toString(),cpass.getText().toString());
                        auth.registration=true;
                        auth.getNonceReg();

                }else{
                    Toast.makeText(getContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                }
                }
            });
        }

    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }
}


