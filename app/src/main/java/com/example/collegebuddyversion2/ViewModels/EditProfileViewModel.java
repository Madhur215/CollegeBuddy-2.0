package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegebuddyversion2.Models.EditDetails;
import com.example.collegebuddyversion2.Repository.EditProfileRepository;

public class EditProfileViewModel extends AndroidViewModel {

    private EditProfileRepository editDetailsRepository;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        editDetailsRepository = new EditProfileRepository(application);
    }

    public void editProfile(EditDetails data){
        editDetailsRepository.editProfile(data);
    }
}
