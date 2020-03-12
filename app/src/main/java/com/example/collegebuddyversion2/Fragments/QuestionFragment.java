package com.example.collegebuddyversion2.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.collegebuddyversion2.Utils.prefUtils;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.QuestionsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {

    public final static String QUESTION_ID ="qid";
    public final static String QUESTION = "question";
    public final static String ASKED_BY_NAME = "name";
    public final static String IMAGE_URI = "image";

//    private LiveData<List<Questions>>  questionsList = new LiveData<>();
//    private QuestionsAdapter questionsAdapter;
    private RecyclerView questionsRecyclerView;
    private QuestionsAdapter questionsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);
        FloatingActionButton ask_fab = view.findViewById(R.id.ask_question_floating_button);
        TextView college_name = view.findViewById(R.id.college_name_question_fragment);
        college_name.setText(prefUtils.getCOLLEGE());
        ask_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AskQuestionActivity.class);
                startActivity(intent);
//                questionsAdapter.notifyDataSetChanged();
            }
        });
        questionsRecyclerView = view.findViewById(R.id.questions_recycler_view);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionsRecyclerView.setHasFixedSize(true);

        questionsAdapter = new QuestionsAdapter(getContext());
        questionsRecyclerView.setAdapter(questionsAdapter);



        QuestionsViewModel questionsViewModel= new ViewModelProvider(QuestionFragment.this)
                .get(QuestionsViewModel.class);
        questionsViewModel.getQuestionsList().observe(getViewLifecycleOwner(),
                new Observer<List<Questions>>() {
            @Override
            public void onChanged(final List<Questions> questionsList) {
//                questionsAdapter = new QuestionsAdapter(questionsList, getContext());
//                questionsRecyclerView.setAdapter(questionsAdapter);
//                questionsAdapter.setOnQuestionClickListener(new QuestionsAdapter.OnQuestionClickListener() {
//                    @Override
//                    public void onQuestionClick(int position) {
//                        Questions clickedQuestion = questionsList.get(position);
//                        Intent i = new Intent(getContext(), ViewAnswersActivity.class);
//                        i.putExtra(QUESTION_ID, clickedQuestion.getQid());
//                        i.putExtra(QUESTION, clickedQuestion.getQuestion());
//                        i.putExtra(ASKED_BY_NAME, clickedQuestion.getName());
//                        i.putExtra(IMAGE_URI , clickedQuestion.getImage());
//                        startActivity(i);
//                    }
//                });
                questionsAdapter.setQuestions(questionsList);
            }
        });

        questionsAdapter.setOnQuestionClickListener(new QuestionsAdapter.OnQuestionClickListener() {
            @Override
            public void onQuestionClick(Questions questions) {
                Intent i = new Intent(getContext(), ViewAnswersActivity.class);
                i.putExtra(QUESTION_ID, questions.getQid());
                i.putExtra(QUESTION, questions.getQuestion());
                i.putExtra(ASKED_BY_NAME, questions.getName());
                i.putExtra(IMAGE_URI , questions.getImage());
                startActivity(i);
            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout_questions);
        AppBarLayout appBarLayout = view.findViewById(R.id.app_bar_layout_questions);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Discover");
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
        return view;
    }

}
