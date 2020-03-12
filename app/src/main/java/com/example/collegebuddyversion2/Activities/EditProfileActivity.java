package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.collegebuddyversion2.Models.EditDetails;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.ViewModels.EditProfileViewModel;
import com.example.collegebuddyversion2.ViewModels.ProfileViewModel;

public class EditProfileActivity extends AppCompatActivity {

    private String toastMessage;
    private EditDetails editDetails;
    Spinner branch_spinner;
    Spinner year_spinner;
    EditText changed_password_edit_text;
    EditText old_password_edit_text;
    private String p;
    private EditProfileViewModel editProfileViewModel;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        changed_password_edit_text = findViewById(R.id.new_password_edit_text);
        old_password_edit_text = findViewById(R.id.old_password_edit_text);
        Button save_details_button = findViewById(R.id.save_details_button);
        branch_spinner = findViewById(R.id.select_branch_drop_down_edit_profile);
        year_spinner = findViewById(R.id.select_year_drop_down_edit_profile);
        ImageView edit_profile_back_button = findViewById(R.id.edit_profile_back_button);
        edit_profile_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        save_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkOldPassword() && checkNewPassword()){
                    p = old_password_edit_text.getText().toString().trim();
                    String new_password = changed_password_edit_text.getText().toString().trim();
                    String b = branch_spinner.getSelectedItem().toString();
                    String y = year_spinner.getSelectedItem().toString();
                    editDetails = new EditDetails(b , y , new_password , p);
                    closeKeyboard();
                    editProfile();
                }
                else {
                    Toast.makeText(EditProfileActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void editProfile() {
        int result = editProfileViewModel.editProfile(editDetails);
        if(result == 100){
            profileViewModel.getProfile();
        }
        finish();
    }

    private boolean checkOldPassword() {
        String p = old_password_edit_text.getText().toString().trim();
        if(p.length() == 0){
            toastMessage = "Invalid Old Password!";
            return false;
        }
        return true;
    }

    private boolean checkNewPassword(){
        String p = changed_password_edit_text.getText().toString().trim();
        if(p.length() == 0){
            toastMessage = "Invalid New Password!";
            return false;
        }
        else if(p.length() < 6){
            toastMessage = "New Password Should Have At Least 6 Characters!";
        }
        return true;
    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }
}
