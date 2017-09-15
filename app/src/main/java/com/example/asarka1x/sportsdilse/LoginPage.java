package com.example.asarka1x.sportsdilse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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

    static int result,isresultready;
    EditText username,password;
    LinearLayout login,signup,skip;
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.loginscreen, container, false);
        username= (EditText)v.findViewById(R.id.user);
        password= (EditText)v.findViewById(R.id.password);
        login= (LinearLayout)v.findViewById(R.id.login);
        signup= (LinearLayout)v.findViewById(R.id.signin);
        skip= (LinearLayout)v.findViewById(R.id.skip);
        skip.setVisibility(LinearLayout.GONE);

        db= getActivity().openOrCreateDatabase("SPORTSDILSE", Context.MODE_PRIVATE, null);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final Handler h= new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isresultready==1){
                            isresultready=0;
                            if(result==1){
                                result=0;
                                ContentValues values= new ContentValues();
                                values.put("status",1);
                                values.put("firstname","");
                                values.put("lastname","");
                                values.put("email",username.getText().toString());
                                values.put("password",password.getText().toString());
                                values.put("phone","");
                                db.insert("usercredential",null,values);
                                Const.pageHistory.remove(Const.pageHistory.size()-1);
                                pagerAdapter=  new MyPagerAdapter(MainActivity.activity.getSupportFragmentManager());
                                pagerAdapter.clearList();
                                pagerAdapter.addFragment(new WriteArticle(), "WriteArticle");
                                Const.pageHistory.add(pagerAdapter);
                                viewPager.setAdapter(pagerAdapter);
                                drawerLayout.closeDrawers();
                            }else {
                                result=0;
                                Toast.makeText(getActivity(),"Invalid Email id or Password", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            h.postDelayed(this, 20);
                        }
                    }
                },20);

                Auth auth= new Auth(username.getText().toString(), password.getText().toString());
                auth.getNonce();
            }
        });
    }
}


class Auth{

    String email,pass;
    int flag;
    String url;
    StringBuffer buffer;

    Auth(String email, String pass){
        this.email=email;
        this.pass=pass;
    }

    public void getNonce(){
        url= "https://sportsdilse.com/api/get_nonce/?controller=user&method=generate_auth_cookie";
        flag=1;
        Background background= new Background(url);
        background.execute();
    }

    public void genAuthCookey(String nonce){
        StringBuffer buffer= new StringBuffer();
        buffer.append("https://sportsdilse.com/api/user/generate_auth_cookie/?nonce=");
        buffer.append(nonce+"&username="+email+"&password="+pass);
        url= buffer.toString();
        flag=2;
        Background background= new Background(url);
        background.execute();
    }

    public void validate(String cookey){
        StringBuffer buffer= new StringBuffer();
        buffer.append("https://sportsdilse.com/api/user/validate_auth_cookie/?cookie=");
        buffer.append(cookey);
        url= buffer.toString();
        flag=3;
        Background background= new Background(url);
        background.execute();
    }

    public void readnonce(String txt) throws JSONException {
        String nonce=null;
        buffer.delete(0,buffer.length());
        JSONObject jsonObject= new JSONObject(txt);
        if(jsonObject.has("nonce"))
            nonce= jsonObject.getString("nonce");
        genAuthCookey(nonce);
    }

    public void readAuth(String authres) throws JSONException {
        buffer.delete(0,buffer.length());
        String cookey= null;
        JSONObject jsonObject= new JSONObject(authres);
        if(jsonObject.has("cookie"))
            cookey= jsonObject.getString("cookie");
        validate(cookey);
    }

    public void readValidate(String res) throws JSONException {
        String result=null;
        JSONObject jsonObject= new JSONObject(res);
        if(jsonObject.has("valid")){
            result= jsonObject.getString("valid");
            if(result.equals("true"))
                LoginPage.result=1;
            else
                LoginPage.result=0;
            LoginPage.isresultready=1;
        }
    }

    class Background extends AsyncTask {

        String url;
        Background(String url){
            this.url=url;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {

                URL urls = new URL(url);
                InputStream isr = urls.openConnection().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"UTF-8"),8125);
                buffer= new StringBuffer();
                buffer.delete(0,buffer.length());
                String line = null;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if(buffer!=null && flag==1){
                    readnonce(buffer.toString());
                }

                if(buffer!=null && flag==2){
                    readAuth(buffer.toString());
                }

                if(buffer!=null && flag==3){
                    readValidate(buffer.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }
    }
}
