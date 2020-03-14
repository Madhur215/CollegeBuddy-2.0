package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;


import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.Members;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ExploreRepository {

    private JsonApiHolder jsonApiHolder;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ArrayList<Members> membersArrayList = new ArrayList<>();
    private Application application;

    public ExploreRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
        getMembersArrayList();

    }

    private void getMembersArrayList(){
        disposable.add(
                jsonApiHolder.getMembers(prefUtils.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Members>>(){

                    @Override
                    public void onSuccess(ArrayList<Members> members) {
                        for(Members members1 : members){
                            String name = members1.getMember_name();
                            String year = members1.getMember_year();
                            String branch = members1.getMember_branch();

                            Members addMember = new Members(name, branch, year);
                            membersArrayList.add(addMember);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                })
        );

    }

    public ArrayList<Members> getMembersList(){
        return membersArrayList;
    }


}
