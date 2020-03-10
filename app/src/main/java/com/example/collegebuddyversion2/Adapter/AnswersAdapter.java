package com.example.collegebuddyversion2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Models.Answers;
import com.example.collegebuddyversion2.R;

import java.util.ArrayList;
import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.answerHolder> {

    private List<Answers> answersList;
    public AnswersAdapter(List<Answers> data){
        answersList = data;
    }


    @NonNull
    @Override
    public AnswersAdapter.answerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_layout,
                parent, false);
        return new answerHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.answerHolder holder, int position) {
        Answers ans = answersList.get(position);

        holder.setAnswer_text_view(ans.getAnswer());
        holder.setAnsweredByName(ans.getAnswered_by_name());
        holder.setUpvotes_text_view(ans.getUpvotes());

    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }


    public class answerHolder extends RecyclerView.ViewHolder {

        TextView answered_by_name_text_view;
        TextView date_text_view;
        TextView answer_text_view;
        TextView upvotes_text_view;

        public answerHolder(@NonNull View itemView) {
            super(itemView);

            answered_by_name_text_view = itemView.findViewById(R.id.answered_by_user_name_answer_layout);
            date_text_view = itemView.findViewById(R.id.date_answers_layout);
            answer_text_view = itemView.findViewById(R.id.answer_text_view_answers_layout);
            upvotes_text_view = itemView.findViewById(R.id.number_of_likes_answers_layout);

            upvotes_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        void setAnsweredByName(String username){
            answered_by_name_text_view.setText(username);
        }

        void setUpvotes_text_view(String upvotes){
            date_text_view.setText(upvotes);
        }

        void setAnswer_text_view(String ans){
            answer_text_view.setText(ans);
        }

        public void setDate_text_view(TextView date_text_view) {
            this.date_text_view = date_text_view;
        }
    }



}