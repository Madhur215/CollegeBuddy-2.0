package com.example.collegebuddyversion2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.collegebuddyversion2.Models.Members;
import com.example.collegebuddyversion2.Repository.ExploreRepository;

import java.util.ArrayList;

public class ExploreViewModel extends AndroidViewModel {

    private ArrayList<Members> membersArrayList;

    public ExploreViewModel(@NonNull Application application) {
        super(application);
        ExploreRepository exploreRepository = new ExploreRepository(application);
        membersArrayList = exploreRepository.getMembersList();
    }

    public ArrayList<Members> getMembers(){
        return membersArrayList;
    }
}
