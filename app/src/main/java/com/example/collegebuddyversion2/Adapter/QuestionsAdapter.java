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
import com.example.collegebuddyversion2.Models.Questions;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.questionHolder> {

    private List<Questions>questionsEntityList;
    private Context context;
    private retrofitInstance rf = new retrofitInstance();
    private OnQuestionClickListener mListener;

    public QuestionsAdapter(List<Questions> questionsEntityList, Context context){
        this.questionsEntityList = questionsEntityList;
        this.context = context;

    }

    public interface OnQuestionClickListener {
        void onQuestionClick(int position);
    }

    public void setOnQuestionClickListener(OnQuestionClickListener listener){
        mListener = listener;
    }


    @NonNull
    @Override
    public QuestionsAdapter.questionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_layout,
                parent, false);
        return new questionHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.questionHolder holder, int position) {
        Questions ques = questionsEntityList.get(position);

        holder.question.setText(ques.getQuestion());
        holder.asked_by_name.setText(ques.getName());
        holder.asked_on_date.setText(ques.getDate());
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


        public questionHolder(@NonNull View itemView, final OnQuestionClickListener listener) {
            super(itemView);
            question = itemView.findViewById(R.id.asked_question_text_view);
            asked_by_name = itemView.findViewById(R.id.asked_by_name_text_view);
            asked_on_date = itemView.findViewById(R.id.asked_on_date_question_layout);
            asked_by_image = itemView.findViewById(R.id.asked_by_user_image_question_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onQuestionClick(position);
                        }
                    }
                }
            });

        }

        void setAsked_by_image(String image){
            if(image != null) {
                String imgUrl = rf.getURL() + image;
                Picasso.with(context).load(imgUrl).into(asked_by_image);
            }
        }
    }
}
