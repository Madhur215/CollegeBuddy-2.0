package com.example.collegebuddyversion2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.collegebuddyversion2.Fragments.ExploreFragment;
import com.example.collegebuddyversion2.Fragments.HomeFragment;
import com.example.collegebuddyversion2.Fragments.ProfileFragment;
import com.example.collegebuddyversion2.Fragments.QuestionFragment;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.ProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Fragment selectedFragment;
    JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        jsonApiHolder = retrofitInstance.getRetrofitInstance(this).create(JsonApiHolder.class);
        pr = new prefUtils(this);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        if(!pr.isProfileSaved()) {
            profileViewModel.getProfile();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                    new HomeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.question:
                            selectedFragment = new QuestionFragment();
                            break;
//                        case R.id.downloads:
//                            selectedFragment = new DownloadFragment();
//                            break;
                        case R.id.explore:
                            selectedFragment = new ExploreFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        MenuItem homeItem = bottomNavigation.getMenu().getItem(0);

        if(selectedFragment instanceof HomeFragment) {
            super.onBackPressed();
        }
        else{

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                    new HomeFragment()).commit();
            bottomNavigation.setSelectedItemId(homeItem.getItemId());
        }
    }


}
