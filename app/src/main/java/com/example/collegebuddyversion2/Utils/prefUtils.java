package com.example.collegebuddyversion2.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.example.collegebuddyversion2.Activities.LoginSignupActivity;

import java.util.HashMap;

public class prefUtils {

    private static SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context ctx;

    private static final String PREF_NAME = "login";

    private static final String IS_LOGIN = "isLoggedIn";

    public static final String KEY_TOKEN = "token";

    public prefUtils(Context context){
        ctx = context;
        sp = ctx.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void createLogin(String token){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sp.getBoolean(IS_LOGIN, false);
    }

    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_TOKEN, sp.getString(KEY_TOKEN, null));
        return user;
    }

    public static String getToken() {
        return sp.getString(KEY_TOKEN, null);
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();

        Intent i = new Intent(ctx, LoginSignupActivity.class);
        ctx.startActivity(i);

    }

}
