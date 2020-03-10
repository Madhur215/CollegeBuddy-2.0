package com.example.collegebuddyversion2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Activities.AskQuestionActivity;
import com.example.collegebuddyversion2.Adapter.QuestionsAdapter;
import com.example.collegebuddyversion2.Entity.QuestionsEntity;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.QuestionsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {

    private List<QuestionsEntity>  questionsEntityList = new ArrayList<>();
    private QuestionsViewModel questionsViewModel;
    private QuestionsAdapter questionsAdapter;
    private RecyclerView questionsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);
        FloatingActionButton ask_fab = view.findViewById(R.id.ask_question_floating_button);
        ask_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AskQuestionActivity.class);
                startActivity(intent);
            }
        });
        questionsRecyclerView = view.findViewById(R.id.questions_recycler_view);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionsRecyclerView.setHasFixedSize(true);
        questionsViewModel = ViewModelProviders.of(QuestionFragment.this).get(QuestionsViewModel.class);
        questionsViewModel.getQuestionsList().observe(getViewLifecycleOwner(),
                new Observer<List<QuestionsEntity>>() {
            @Override
            public void onChanged(List<QuestionsEntity> questionsEntityList) {
                questionsAdapter = new QuestionsAdapter(questionsEntityList, getContext());
                questionsRecyclerView.setAdapter(questionsAdapter);
            }
        });
        return view;
    }

}
