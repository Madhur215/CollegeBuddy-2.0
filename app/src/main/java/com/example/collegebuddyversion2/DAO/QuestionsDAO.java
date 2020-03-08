package com.example.collegebuddyversion2.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.collegebuddyversion2.Entity.QuestionsEntity;

import java.util.List;

@Dao
public interface QuestionsDAO {

    @Insert
    void insert(List<QuestionsEntity> questions);

    @Query("SELECT * FROM questions_table")
    LiveData<List<QuestionsEntity>> getAllQuestions();

}
