package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegebuddyversion2.Adapter.SubjectPdfAdapter;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.SubjectDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubjectDetailActivity extends AppCompatActivity {

    public static final String SUBJECT_KEY = "subject_key";
    private String pdf_key;
    private int p;
    private int mrequestCode = 10;
    private WebView pdf_web_view;
    DownloadManager downloadManager;
    private RecyclerView pdfRecyclerView;
    private SubjectPdfAdapter subjectPdfAdapter;
    private List<SubjectPdfList> pdfList = new ArrayList<>();
    private JsonApiHolder jsonApiHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);
        Intent i = getIntent();
        pdf_key = i.getStringExtra(SUBJECT_KEY);
        Toolbar tb = findViewById(R.id.toolbar_pdf_activity);
        jsonApiHolder = retrofitInstance.getRetrofitInstance(SubjectDetailActivity.this)
                .create(JsonApiHolder.class);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pdf_web_view = findViewById(R.id.pdf_web_view);
        ImageView back_button = findViewById(R.id.pdf_list_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pdfRecyclerView = findViewById(R.id.subject_pdf_recycler_view);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfRecyclerView.setHasFixedSize(true);

        SubjectDetailViewModel subjectDetailViewModel = new ViewModelProvider(this)
                .get(SubjectDetailViewModel.class);
        subjectDetailViewModel.getPdfs(pdf_key).observe(this, new Observer<List<SubjectPdfList>>() {
            @Override
            public void onChanged(List<SubjectPdfList> subjectPdfLists) {
                pdfList = subjectPdfLists;
                subjectPdfAdapter = new SubjectPdfAdapter(subjectPdfLists);
                pdfRecyclerView.setAdapter(subjectPdfAdapter);
            }
        });

        subjectPdfAdapter.setOnPdfClickListener(new SubjectPdfAdapter.OnPdfClickListener() {
            @Override
            public void onPdfClick(int position) {
                SubjectPdfList clickedPdf = pdfList.get(position);
                String p_key = clickedPdf.getPdf_key();
                p = Integer.parseInt(p_key);
                String url = retrofitInstance.getURL() + "/api/PDFController/ViewPDF/" + p + "?token="
                        + prefUtils.getToken();
                String finalUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
                pdf_web_view.setVisibility(View.VISIBLE);
                pdf_web_view.getSettings().setBuiltInZoomControls(true);
                pdf_web_view.loadUrl(finalUrl);
                pdf_web_view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void downloadPdf(int position) {
                SubjectPdfList clickedPdf = pdfList.get(position);
                String p_key = clickedPdf.getPdf_key();
                int p = Integer.parseInt(p_key);
                download(p);
            }

            @Override
            public void addToLibrary(int position) {
                SubjectPdfList clickedPdf = pdfList.get(position);
                String p_key = clickedPdf.getPdf_key();
                int p = Integer.parseInt(p_key);
                addPDF(p);
            }
        });

    }

    private void addPDF(int p) {
        Call<String> call = jsonApiHolder.addToLibrary(p , prefUtils.getToken());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String message = response.body();
                    Toast.makeText(SubjectDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SubjectDetailActivity.this, "An Error Occurred!" +
                            "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SubjectDetailActivity.this, "No response from the server!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void download(int p_key) {

        if(ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this , "granted" , Toast.LENGTH_SHORT).show();
            String mimeType = "application/pdf";
            downloadManager = (DownloadManager)this.getSystemService(Context.DOWNLOAD_SERVICE);
            String url = retrofitInstance.getURL() + "/api/PDFController/GetPDF/" + p_key + "?token="
                    + prefUtils.getToken();
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType(mimeType);
            downloadManager.enqueue(request);
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this ,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("Storage permission is required for download")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SubjectDetailActivity.this,
                                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, mrequestCode);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , mrequestCode);


            }
        }
    }
}
