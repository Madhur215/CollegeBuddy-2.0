package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.collegebuddyversion2.Adapter.QuestionsAdapter;
import com.example.collegebuddyversion2.Models.Questions;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.ViewModels.UserQuestionViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserQuestionsActivity extends AppCompatActivity {

//    private ArrayList<Questions> questionsList;
    private UserQuestionViewModel userQuestionViewModel;
    private QuestionsAdapter userQuestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);
        Toolbar user_question_toolbar = findViewById(R.id.toolbar_user_question_activity);
        setSupportActionBar(user_question_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView back_button = findViewById(R.id.user_question_back_button);
        RecyclerView userQuestionRecyclerView = findViewById(R.id.user_questions_recycler_view);
        userQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userQuestionRecyclerView.setHasFixedSize(true);

        userQuestionAdapter = new QuestionsAdapter(this);
        userQuestionRecyclerView.setAdapter(userQuestionAdapter);

        userQuestionAdapter.setOnQuestionClickListener(new QuestionsAdapter.OnQuestionClickListener() {
            @Override
            public void onQuestionClick(Questions questions) {
                Intent i = new Intent(UserQuestionsActivity.this, ViewAnswersActivity.class);
                i.putExtra(ViewAnswersActivity.QUESTION_ID, questions.getQid());
                i.putExtra(ViewAnswersActivity.QUESTION, questions.getQuestion());
                i.putExtra(ViewAnswersActivity.ASKED_BY_NAME, questions.getName());
                i.putExtra(ViewAnswersActivity.IMAGE_URI , questions.getImage());
                startActivity(i);
            }
        });

        userQuestionViewModel = new ViewModelProvider(this).get(UserQuestionViewModel.class);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getUserQuestions();

    }

    private void getUserQuestions() {
        userQuestionViewModel.getUserQuestionList().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {
                userQuestionAdapter.setQuestions(questions);
            }
        });


    }
}
