package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.EditDetails;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class EditProfileRepository {

    private JsonApiHolder jsonApiHolder;
    private Application application;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int result = 0;

    public EditProfileRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
    }

    public int editProfile(EditDetails data){
        disposable.add(
                jsonApiHolder.editProfile(prefUtils.getToken(), data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){

                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(application, s, Toast.LENGTH_SHORT).show();
                        result = 100;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(application, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                })
        );

        return result;
    }

}
