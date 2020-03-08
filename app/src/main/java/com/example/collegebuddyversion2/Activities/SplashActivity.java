package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.collegebuddyversion2.Models.LoginData;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.prefUtils;

public class SplashActivity extends AppCompatActivity {

    prefUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = new prefUtils(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){

                if(pref.isLoggedIn()){
                    Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this , LoginSignupActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },1500);
    }
}
