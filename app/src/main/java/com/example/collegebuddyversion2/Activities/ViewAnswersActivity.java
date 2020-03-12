package com.example.collegebuddyversion2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegebuddyversion2.Adapter.AnswersAdapter;
import com.example.collegebuddyversion2.Fragments.QuestionFragment;
import com.example.collegebuddyversion2.Models.Answers;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.example.collegebuddyversion2.ViewModels.AnswersViewModel;
import com.example.collegebuddyversion2.ViewModels.PostAnswerViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewAnswersActivity extends AppCompatActivity {

    public static final String QUESTION_ID = "qid";
    public static final String QUESTION = "question";
    public static final String ASKED_BY_NAME = "name";
    public static final String IMAGE_URI = "image";

    public static String question_id;
    String question;
    String asked_by_name;
    Button post_answer_button;
    private String answer_anonymously;
    EditText answer_edit_text;
    TextView question_text_view;
    TextView asked_by_name_text_view;
    ImageView asked_by_image;
    private AnswersAdapter answersAdapter;
    private RecyclerView answersRecyclerView;
    private PostAnswerViewModel postAnswerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answers);

        Intent i = getIntent();
        question_id = i.getStringExtra(QUESTION_ID);
        question = i.getStringExtra(QUESTION);
        asked_by_name = i.getStringExtra(ASKED_BY_NAME);
        answersRecyclerView = findViewById(R.id.answers_recycler_view);
        answersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answersRecyclerView.setHasFixedSize(true);
        final Toolbar answer_toolbar = findViewById(R.id.toolbar_answer_activity);
        answer_edit_text = findViewById(R.id.write_answer_edit_text);
        question_text_view = findViewById(R.id.question_text_view_write_answer);
        asked_by_name_text_view = findViewById(R.id.asked_by_name_write_answer);
        asked_by_image = findViewById(R.id.asked_by_user_image_write_answer);
        question_text_view.setText(question);
        asked_by_name_text_view.setText(asked_by_name);
        setImage(i.getStringExtra(IMAGE_URI));
        post_answer_button = findViewById(R.id.post_answer_button);

        setSupportActionBar(answer_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView back_image_view = findViewById(R.id.answer_activity_back_image);
        back_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        postAnswerViewModel = ViewModelProviders.of(this).get(PostAnswerViewModel.class);
        AnswersViewModel answersViewModel = ViewModelProviders.of(this).get(AnswersViewModel.class);
        answersViewModel.getAnswersList().observe(this, new Observer<List<Answers>>() {
            @Override
            public void onChanged(List<Answers> answers) {
                answersAdapter = new AnswersAdapter(answers);
                answersRecyclerView.setAdapter(answersAdapter);

            }
        });
        post_answer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAnswer()) {
                    closeKeyboard();
                    String answer = answer_edit_text.getText().toString().trim();
                    postAnswerViewModel.AddAnswer(answer);
                }
            }
        });
    }

    private void setImage(String image_uri) {
        if(image_uri != null) {
            String imgUrl = retrofitInstance.getURL() + image_uri;
            Picasso.with(this).load(imgUrl).into(asked_by_image);
        }
    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

    private boolean checkAnswer(){
        String ans = answer_edit_text.getText().toString().trim();
        if(ans.isEmpty()){
            Toast.makeText(this, "Invalid Answer!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void answerAnonymously(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            answer_anonymously = "true";
        }
    }
}
