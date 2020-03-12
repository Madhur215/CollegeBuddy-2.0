package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.Profile;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileRepository  {

    private JsonApiHolder jsonApiHolder;
    private Application application;
    private CompositeDisposable disposable = new CompositeDisposable();
    private prefUtils pr;

    public ProfileRepository(Application application){
        pr = new prefUtils(application);
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;

    }

    public void getProfile() {
        disposable.add(
                jsonApiHolder.getProfile(prefUtils.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Profile>>(){

                    @Override
                    public void onSuccess(List<Profile> profileEntities) {
                        for(Profile profile: profileEntities){

                            String name = profile.getUser_name();
                            String year = profile.getYear();
                            String branch = profile.getBranch();
                            String college = profile.getCollege();
                            String image_uri = profile.getImageUri();

                            pr.storeProfile(name, college, branch, year, image_uri);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

}
