package com.example.collegebuddyversion2.Repository;

import android.app.Application;
import android.widget.Toast;

import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadRepository {

    private JsonApiHolder jsonApiHolder;
    private Application application;
    private int check = 0;

    public ImageUploadRepository(Application application){
        jsonApiHolder = retrofitInstance.getRetrofitInstance(application).create(JsonApiHolder.class);
        this.application = application;
    }

    public int uploadImage(String filePath){

        File file = new File(filePath);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call<ResponseBody> call = jsonApiHolder.uploadImage(part , name , prefUtils.getToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(application, "Image Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    check = 100;
                }
                else{
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(application, "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
        return check;
    }


}
