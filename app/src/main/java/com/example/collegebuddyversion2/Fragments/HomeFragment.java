package com.example.collegebuddyversion2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.example.collegebuddyversion2.ViewModels.LibraryViewModel;
import com.example.collegebuddyversion2.ViewModels.SubjectsViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Subjects> subjectsList = new ArrayList<>();
    private GridView subjectsGridView;
    public final static String SUBJECT_KEY ="skey";
    private RecyclerView notesRecyclerView;
    private LibraryAdapter libraryAdapter;


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
        i.putExtra(SUBJECT_KEY, s.getSubject_key());
        startActivity(i);

    }

}

