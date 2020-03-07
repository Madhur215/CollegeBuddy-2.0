package com.example.collegebuddyversion2.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.SignUpData;
import com.example.collegebuddyversion2.Models.SignUpResponse;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class SignUpDetailsFragment extends Fragment {

    private String branch;
    private String year;
    private String college;
    private String full_name;
    private String password;
    private String mobile_number;
    private Spinner sp_branch;
    private Spinner sp_year;
    private Spinner sp_college;
    private JsonApiHolder jsonApiHolder;
    private static String id;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_details_fragment, container, false);

        jsonApiHolder = retrofitInstance.getRetrofitInstance(getContext()).create(JsonApiHolder.class);
        sp_branch = view.findViewById(R.id.select_branch_drop_down);
        sp_year = view.findViewById(R.id.select_year_drop_down);
        sp_college = view.findViewById(R.id.select_college_drop_down);
        Button sign_up_button = view.findViewById(R.id.sign_up_button_details_layout);
        full_name = SignUpFragment.getFull_name();
        password = SignUpFragment.getPassword();
        mobile_number = SignUpFragment.getPhone();

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branch = sp_branch.getSelectedItem().toString();
                year = sp_year.getSelectedItem().toString();
                college = sp_college.getSelectedItem().toString();
                signUp();
            }
        });

        return view;
    }

    private void signUp() {
        SignUpData sendData = new SignUpData(full_name , college , branch , year , password ,
                mobile_number);

//        Call<signUpResponse> call = jsonApiHolder.signUp(sendData);
//
//        call.enqueue(new Callback<signUpResponse>() {
//            @Override
//            public void onResponse(Call<signUpResponse> call, Response<signUpResponse> response) {
//                signUpResponse signUpResponse = response.body();
//                try {
//                    id = signUpResponse.getID();
//                    getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
//                            new otpFragment()).commit();
//                }
//                catch (NullPointerException e){
//                    Toast.makeText(getContext(), "Error in getting response from the server!",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<signUpResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        disposable.add(
                jsonApiHolder.signUp(sendData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SignUpResponse>(){

                    @Override
                    public void onSuccess(SignUpResponse signUpResponse){
                        id = signUpResponse.getID();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                                new OtpFragment()).commit();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                })

        );

    }

    public static String getUserID(){
        return id;
    }

}
