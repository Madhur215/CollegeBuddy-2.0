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
import com.example.collegebuddyversion2.Activities.ViewAnswersActivity;
import com.example.collegebuddyversion2.Adapter.QuestionsAdapter;
import com.example.collegebuddyversion2.Entity.QuestionsEntity;
import com.example.collegebuddyversion2.Interface.JsonApiHolder;
import com.example.collegebuddyversion2.Models.Questions;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.QuestionsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {

    public final static String QUESTION_ID ="qid";
    public final static String QUESTION = "question";
    public final static String ASKED_BY_NAME = "name";
    public final static String IMAGE_URI = "image";

    private List<Questions>  questionsList = new ArrayList<>();
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
                new Observer<List<Questions>>() {
            @Override
            public void onChanged(final List<Questions> questionsList) {
                questionsAdapter = new QuestionsAdapter(questionsList, getContext());
                questionsRecyclerView.setAdapter(questionsAdapter);
                questionsAdapter.setOnQuestionClickListener(new QuestionsAdapter.OnQuestionClickListener() {
                    @Override
                    public void onQuestionClick(int position) {
                        Questions clickedQuestion = questionsList.get(position);
                        Intent i = new Intent(getContext(), ViewAnswersActivity.class);
                        i.putExtra(QUESTION_ID, clickedQuestion.getQid());
                        i.putExtra(QUESTION, clickedQuestion.getQuestion());
                        i.putExtra(ASKED_BY_NAME, clickedQuestion.getName());
                        i.putExtra(IMAGE_URI , clickedQuestion.getImage());
                        startActivity(i);
                    }
                });
            }
        });
        return view;
    }

}
