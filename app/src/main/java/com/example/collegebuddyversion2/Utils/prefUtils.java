package com.example.collegebuddyversion2.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.example.collegebuddyversion2.Activities.LoginSignupActivity;

import java.util.HashMap;

public class prefUtils {

    private static SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor profile_editor;
    private Context ctx;

    private static final String PREF_NAME = "login";

    private static final String IS_LOGIN = "isLoggedIn";
    private static final String IS_PROFILE_SAVED = "profileRequired";

    public static final String KEY_TOKEN = "token";
    private static final String NAME = "name";
    private static final String COLLEGE = "college";
    private static final String BRANCH = "branch";
    private static final String YEAR = "year";
    private static final String IMAGE = "image";

    public prefUtils(Context context){
        ctx = context;
        sp = ctx.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        editor = sp.edit();
        profile_editor = sp.edit();
    }

    public void createLogin(String token){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public void storeProfile(String name, String college, String branch, String year, String image) {
        profile_editor.putString(NAME, name);
        profile_editor.putString(COLLEGE, college);
        profile_editor.putString(BRANCH, branch);
        profile_editor.putString(YEAR, year);
        profile_editor.putString(IMAGE, image);
        profile_editor.putBoolean(IS_PROFILE_SAVED, true);
        profile_editor.commit();
    }

    public boolean isProfileSaved(){
        return sp.getBoolean(IS_PROFILE_SAVED, false);
    }

    public static String getNAME(){
        return sp.getString(NAME, null);
    }

    public static String getCOLLEGE() {
        return sp.getString(COLLEGE, null);
    }

    public static String getBRANCH() {
        return sp.getString(BRANCH, null);
    }

    public static String getYEAR() {
        return sp.getString(YEAR, null);
    }

    public static String getIMAGE() {
        return sp.getString(IMAGE, null);
    }

    public boolean isLoggedIn(){
        return sp.getBoolean(IS_LOGIN, false);
    }

    public static String getToken() {
        return sp.getString(KEY_TOKEN, null);
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();
        profile_editor.clear();
        profile_editor.commit();
        Intent i = new Intent(ctx, LoginSignupActivity.class);
        ctx.startActivity(i);

    }

}
