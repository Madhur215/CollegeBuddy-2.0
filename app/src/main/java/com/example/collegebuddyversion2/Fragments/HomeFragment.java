package com.example.collegebuddyversion2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Activities.SubjectDetailActivity;
import com.example.collegebuddyversion2.Adapter.LibraryAdapter;
import com.example.collegebuddyversion2.Adapter.SubjectsAdapter;
import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.Models.Subjects;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.LibraryViewModel;
import com.example.collegebuddyversion2.ViewModels.SubjectsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Subjects> subjectsList = new ArrayList<>();
    private GridView subjectsGridView;
    private RecyclerView notesRecyclerView;
    private LibraryAdapter libraryAdapter;
    private WebView pdf_web_view_home;
    private List<SubjectPdfList> pdfLists = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        subjectsGridView = view.findViewById(R.id.subjects_grid_view);
        TextView year_text_view = view.findViewById(R.id.year_text_view_home);
        TextView branch_text_view = view.findViewById(R.id.branch_text_view_home);
        branch_text_view.setText(prefUtils.getBRANCH());
        year_text_view.setText(prefUtils.getYEAR());
        ImageView user_image = view.findViewById(R.id.user_image_home);
        if (prefUtils.getIMAGE() != null) {
            String imgUrl = retrofitInstance.getURL() + prefUtils.getIMAGE();
            Picasso.with(getContext()).load(imgUrl).into(user_image);
        }
        pdf_web_view_home = view.findViewById(R.id.pdf_web_view_home);
        notesRecyclerView = view.findViewById(R.id.library_recycler_view);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notesRecyclerView.setHasFixedSize(true);
        libraryAdapter = new LibraryAdapter();
        notesRecyclerView.setAdapter(libraryAdapter);

        LibraryViewModel libraryViewModel = new ViewModelProvider(HomeFragment.this)
                .get(LibraryViewModel.class);
        libraryViewModel.getLibrary().observe(getViewLifecycleOwner(), new Observer<List<SubjectPdfList>>() {
            @Override
            public void onChanged(List<SubjectPdfList> subjectPdfLists) {
                libraryAdapter.setNotes(subjectPdfLists);
                pdfLists = subjectPdfLists;
            }
        });

        libraryAdapter.setOnNotesClickListener(new LibraryAdapter.OnNotesClickListener() {
            @Override
            public void onNotesClick(int position) {
                SubjectPdfList clickedPdf = pdfLists.get(position);
                String p_key = clickedPdf.getPdf_key();
                int p = Integer.parseInt(p_key);

                String url = retrofitInstance.getURL() + "/api/PDFController/ViewPDF/"
                        + p + "?token=" + prefUtils.getToken();
                String finalUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
                pdf_web_view_home.setVisibility(View.VISIBLE);
                pdf_web_view_home.getSettings().setBuiltInZoomControls(true);

                pdf_web_view_home.loadUrl(finalUrl);
                pdf_web_view_home.setVisibility(View.INVISIBLE);
            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout_home);
        AppBarLayout appBarLayout = view.findViewById(R.id.app_bar_layout_home);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(prefUtils.getYEAR() + " Year " + prefUtils.getBRANCH());
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });


        SubjectsViewModel subjectsViewModel = new ViewModelProvider(HomeFragment.this)
                .get(SubjectsViewModel.class);
        subjectsViewModel.getSubjects().observe(getViewLifecycleOwner(), new Observer<List<Subjects>>() {
            @Override
            public void onChanged(List<Subjects> subjects) {
                SubjectsAdapter subjectsAdapter = new SubjectsAdapter(getContext(), subjects);
                subjectsList = subjects;
                subjectsGridView.setAdapter(subjectsAdapter);
            }
        });

        subjectsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectDetails(position);
            }
        });
        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    private void subjectDetails(int position) {
        Subjects s = subjectsList.get(position);
        Intent i = new Intent(getContext() , SubjectDetailActivity.class);
        i.putExtra(SubjectDetailActivity.SUBJECT_KEY, s.getSubject_key());
        startActivity(i);

    }

    //TODO DELETE PDF FROM LIBRARY

}

