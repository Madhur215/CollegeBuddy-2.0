package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegebuddyversion2.Repository.ImageUploadRepository;

public class UploadImageViewModel extends AndroidViewModel {

    private ImageUploadRepository imageUploadRepository;

    public UploadImageViewModel(@NonNull Application application) {
        super(application);
        imageUploadRepository = new ImageUploadRepository(application);
    }

    public int uploadImage(String filePath){
        return imageUploadRepository.uploadImage(filePath);
    }
}
