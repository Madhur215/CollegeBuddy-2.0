package com.example.collegebuddyversion2.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class OtpFragment extends Fragment {
    private JsonApiHolder jsonApiHolder;
    private EditText otp_edit_text;
    private TextView timer_text_view;
    private CountDownTimer countDownTimer;
    private long timeLeft = 120000;
    private boolean timerRunning = false;
    private TextView resend_otp_text;
    Button verify_phone_button;
    Button resend_otp_button;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.otp_fragment, container, false);

        jsonApiHolder = retrofitInstance.getRetrofitInstance(getContext()).create(JsonApiHolder.class);
        verify_phone_button = view.findViewById(R.id.verify_otp_button);
        otp_edit_text = view.findViewById(R.id.enter_otp_edit_text);
        TextView  user_name_text_view = view.findViewById(R.id.user_name_otp);
        timer_text_view = view.findViewById(R.id.timer_text_view);
        resend_otp_text = view.findViewById(R.id.resend_otp_text);
        resend_otp_button = view.findViewById(R.id.resend_otp_button);
        resend_otp_button.setVisibility(View.INVISIBLE);
        startTimer();
        user_name_text_view.setText(SignUpFragment.getFull_name());
        verify_phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOtp()) {
                    String otp = otp_edit_text.getText().toString().trim();
                    stopTimer();
                    timer_text_view.setVisibility(View.INVISIBLE);
                    resend_otp_text.setVisibility(View.INVISIBLE);

                    verifyPhone(otp);

                }
                else{
                    Toast.makeText(getContext(), "Enter OTP First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resend_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }

        });

        return view;

    }

    private void resendOtp() {

//        Call<ResponseBody> call = jsonApiHolder.resendOTP(signUpDetailsFragment.id );
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()){
//                    resend_otp_button.setVisibility(View.INVISIBLE);
//                    resend_otp_text.setVisibility(View.VISIBLE);
//                    timer_text_view.setVisibility(View.VISIBLE);
//                    timeLeft = 120000;
//                    startTimer();
//                }
//                else{
//                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

        disposable.add(
                jsonApiHolder.resendOTP(SignUpDetailsFragment.getUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponseBody>(){

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        resend_otp_button.setVisibility(View.INVISIBLE);
                        resend_otp_text.setVisibility(View.VISIBLE);
                        timer_text_view.setVisibility(View.VISIBLE);
                        timeLeft = 120000;
                        startTimer();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                })

        );

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;

    }

    private void updateTimer() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeLeftText  = "";

        timeLeftText += "" + minutes;
        timeLeftText += ":";
        if(seconds < 10 ){
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        timer_text_view.setText(timeLeftText);
        if(timeLeftText.equals("0:00")){
            timer_text_view.setVisibility(View.INVISIBLE);
            resend_otp_text.setVisibility(View.INVISIBLE);
            resend_otp_button.setVisibility(View.VISIBLE);
        }

    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private boolean checkOtp(){
        String otp = otp_edit_text.getText().toString().trim();
        return otp.length() != 0;
    }

    private void verifyPhone(String otp) {
//        Call<String> call = jsonApiHolder.verifyPhone(SignUpDetailsFragment.getUserID() , otp);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response.isSuccessful()) {
//                    String message = response.body();
//                    if(message.equals("Verified")) {
//                        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
//                                new LoginFragment()).commit();
//                    }
//                    else{
//                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
////                        verify_phone_button.setText(R.string.resend_otp);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        disposable.add(
                jsonApiHolder.verifyPhone(SignUpDetailsFragment.getUserID(), otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){

                    @Override
                    public void onSuccess(String s) {

                    if(s.equals("Verified")) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                                new LoginFragment()).commit();
                    }
                    else{
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
//                        verify_phone_button.setText(R.string.resend_otp);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                                new SignUpFragment()).commit();
                    }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}

