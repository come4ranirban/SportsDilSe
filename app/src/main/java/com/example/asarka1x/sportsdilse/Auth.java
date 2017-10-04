package com.example.asarka1x.sportsdilse;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by asarka1x on 9/16/2017.
 */

public class Auth {

    String email,pass,username;
    int flag;
    String readauthstring;
    boolean registration;
    String url;
    StringBuffer buffer;

    Auth(){
    }

    Auth(String username, String email, String password){

        this.email= email;
        this.pass= password;
        this.username= username;
    }


    Auth(String email, String pass){
        this.email=email;
        this.pass=pass;
    }

    //setnonce url
    public void getNonce(){
        url= "https://sportsdilse.com/api/get_nonce/?controller=user&method=generate_auth_cookie";
        flag=1;
        Background background= new Background(url);
        background.execute();
    }

    //get nonce for reg
    public void getNonceReg(){
        url= "https://sportsdilse.com/api/get_nonce/?controller=user&method=register";
        flag=1;
        Background background= new Background(url);
        background.execute();
    }

    //set genrate auth cookey url
    public void genAuthCookey(String nonce){
        StringBuffer buffer= new StringBuffer();
        buffer.append("https://sportsdilse.com/api/user/generate_auth_cookie/?nonce=");
        buffer.append(nonce+"&username="+email+"&password="+pass);
        url= buffer.toString().trim();
        flag=2;
        Background background= new Background(url);
        background.execute();
    }


    //set validation url for login
    public void validate(String cookey){
        StringBuffer buffer= new StringBuffer();
        buffer.append("https://sportsdilse.com/api/user/validate_auth_cookie/?cookie=");
        buffer.append(cookey);
        url= buffer.toString();
        flag=3;
        Background background= new Background(url);
        background.execute();
    }

    //set forgot password url
    public void forgotpassword(String email){
        StringBuffer buffer= new StringBuffer();
        buffer.append("https://sportsdilse.com/api/user/retrieve_password/?user_login="+email);
         url=buffer.toString().trim();
        flag=4;
        Background background= new Background(url);
        background.execute();
    }

    //Set registrtion url
    public void registerurl(String nonce){

        StringBuffer buffer= new StringBuffer();
        buffer.append("https://sportsdilse.com/api/user/register/?username="+username+"&email="+email+
                "&nonce="+nonce+"&display_name="+username+"&user_pass="+pass);
        url=buffer.toString().trim();
        flag=2;
        Background background= new Background(url);
        background.execute();
    }


    //read json responce for forget password
    public void readforgotresponce(String forgotres) throws JSONException {
        buffer.delete(0,buffer.length());
        JSONObject jsonObject= new JSONObject(forgotres);
        if(jsonObject.has("status")){
            LoginPage.forgotflag=1;
            if(jsonObject.getString("status").equalsIgnoreCase("ok")){
                String msg= jsonObject.getString("msg");
                LoginPage.forgotmsg=msg;
            }else {
                LoginPage.forgotmsg="Please try again with valid email id";
            }
        }
    }

    //read json responce for readnonce password
    public void readnonce(String txt) throws JSONException {
        String nonce=null;
        buffer.delete(0,buffer.length());
        JSONObject jsonObject= new JSONObject(txt);
        if(jsonObject.has("nonce"))
            nonce= jsonObject.getString("nonce");

        if(registration==false)
            genAuthCookey(nonce);
        else
            registerurl(nonce);
    }



    //read json responce for readauth
    public void readAuth(String authres) throws JSONException {
        buffer.delete(0,buffer.length());
        String cookey= null;
        JSONObject jsonObject= new JSONObject(authres);
        if(jsonObject.has("cookie"))
                cookey= jsonObject.getString("cookie");
            validate(cookey);
    }

    //read json responce for validation
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

                if(buffer!=null && flag==4){
                    readforgotresponce(buffer.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                LoginPage.forgotflag=1;
                LoginPage.forgotmsg="Please try again with valid email id";
                if(registration==true){
                    SignUp.regresult=true;
                    SignUp.error=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
