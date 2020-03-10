package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.AskQuestionViewModel;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskQuestionActivity extends AppCompatActivity {

    private String question;
    private String ask_anonymous = "false";
    EditText ask_question_edit_text;
    private AskQuestionViewModel askQuestionViewModel;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        if(!isConnected(this)){
            buildDialog(this).show();
        }

        askQuestionViewModel = ViewModelProviders.of(AskQuestionActivity.this)
                .get(AskQuestionViewModel.class);

        ask_question_edit_text = findViewById(R.id.enter_question_edit_text);
        Button ask_button = findViewById(R.id.ask_question_button);
        Toolbar toolbar = findViewById(R.id.toolbar_ask_question_activity);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView back_button = findViewById(R.id.ask_question_activity_back_image);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkQuestion()) {
                    closeKeyboard();
                    question = ask_question_edit_text.getText().toString().trim();
                    askQuestionViewModel.PostQuestion(question);
                    finish();
                }
            }
        });

    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

    public void askAnonymously(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            ask_anonymous = "true";
        }
    }

    private boolean checkQuestion(){

        String question = ask_question_edit_text.getText().toString().trim();
        if(question.isEmpty()){
            Toast.makeText(this, "Invalid Question!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Dismiss.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                dialog.cancel();
            }
        });

        return builder;
    }
}
