package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegebuddyversion2.Repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profile;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profile = new ProfileRepository(application);

    }

    public void getProfile(){
        profile.getProfile();
    }
}
