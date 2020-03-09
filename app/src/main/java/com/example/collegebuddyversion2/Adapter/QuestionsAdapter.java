package com.example.collegebuddyversion2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Entity.QuestionsEntity;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.questionHolder> {

    private List<QuestionsEntity>questionsEntityList;
    private Context context;
    private retrofitInstance rf = new retrofitInstance();

    public QuestionsAdapter(List<QuestionsEntity> questionsEntityList, Context context){
        this.questionsEntityList = questionsEntityList;
        this.context = context;

    }


    @NonNull
    @Override
    public QuestionsAdapter.questionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_layout,
                parent, false);
        return new questionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.questionHolder holder, int position) {
        QuestionsEntity ques = questionsEntityList.get(position);

        holder.question.setText(ques.getQuestion());
        holder.asked_by_name.setText(ques.getAsked_by_name());
        holder.asked_on_date.setText(ques.getAsked_on_date());
        holder.setAsked_by_image(ques.getImage());
    }

    @Override
    public int getItemCount() {
        return questionsEntityList.size();
    }

    class questionHolder extends RecyclerView.ViewHolder{

        TextView question;
        TextView asked_on_date;
        TextView asked_by_name;
        ImageView asked_by_image;


        public questionHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.asked_question_text_view);
            asked_by_name = itemView.findViewById(R.id.asked_by_name_text_view);
            asked_on_date = itemView.findViewById(R.id.asked_on_date_question_layout);
            asked_by_image = itemView.findViewById(R.id.asked_by_user_image_question_layout);

        }

        void setAsked_by_image(String image){
            if(image != null) {
                String imgUrl = rf.getURL() + image;
                Picasso.with(context).load(imgUrl).into(asked_by_image);
            }
        }
    }
}
