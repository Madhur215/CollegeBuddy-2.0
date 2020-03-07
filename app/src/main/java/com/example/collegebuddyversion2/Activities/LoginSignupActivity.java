package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.collegebuddyversion2.Fragments.SignUpFragment;
import com.example.collegebuddyversion2.R;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                new SignUpFragment())
                .commit();
    }
}
