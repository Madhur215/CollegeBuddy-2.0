package com.example.collegebuddyversion2.Database;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.collegebuddyversion2.DAO.QuestionsDAO;
import com.example.collegebuddyversion2.Entity.QuestionsEntity;

@androidx.room.Database(entities = {QuestionsEntity.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract QuestionsDAO questionsDAO();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, Database.class, "collegebuddy_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
