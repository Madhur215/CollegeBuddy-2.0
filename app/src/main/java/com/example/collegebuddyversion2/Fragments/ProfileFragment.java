package com.example.collegebuddyversion2.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegebuddyversion2.Activities.EditProfileActivity;
import com.example.collegebuddyversion2.Activities.UserQuestionsActivity;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.UploadImageViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {


    private ImageView user_image_view;
    private Uri selectedImage;
    private prefUtils pr;
    private int GALLERY_REQUEST_CODE = 10;
    private int STORAGE_PERMISSION_CODE = 20;
    private UploadImageViewModel uploadImageViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView user_name_text_view = view.findViewById(R.id.user_name_profile);
        TextView year_text_view = view.findViewById(R.id.year_profile);
        TextView branch_text_view = view.findViewById(R.id.branch_profile);
        TextView college_text_view = view.findViewById(R.id.college_name_text_view_profile);
        user_image_view = view.findViewById(R.id.user_image_profile);
        CardView asked_question_card_view = view.findViewById(R.id.asked_questions_card_view);
        CardView edit_profile_card_view = view.findViewById(R.id.edit_profile_card_view);
        CardView logout_card_view = view.findViewById(R.id.logout_card_view);
        ImageView upload_image = view.findViewById(R.id.upload_image);
        uploadImageViewModel = new ViewModelProvider(ProfileFragment.this).get(UploadImageViewModel.class);
        pr = new prefUtils(getContext());
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        });

        logout_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        edit_profile_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });

        asked_question_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserQuestionsActivity.class);
                startActivity(i);
            }
        });

        year_text_view.setText(prefUtils.getYEAR());
        branch_text_view.setText(prefUtils.getBRANCH());
        user_name_text_view.setText(prefUtils.getNAME());
        college_text_view.setText(prefUtils.getCOLLEGE());
        if (prefUtils.getIMAGE() != null) {
            String imgUrl = retrofitInstance.getURL() + prefUtils.getIMAGE();
            Picasso.with(getContext()).load(imgUrl).into(user_image_view);
        }
        return view;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        pr.logoutUser();
                        getActivity().finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            pickFromGallery();
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    private void pickFromGallery(){

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if (resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);
            Log.d(path, "onActivityResult: IMAGE PATH");
            cursor.close();
            uploadToServer(path);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {


        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadToServer(String filePath) {
        int set = uploadImageViewModel.uploadImage(filePath);
        if(set == 100){
            user_image_view.setImageURI(selectedImage);
        }
    }

}
