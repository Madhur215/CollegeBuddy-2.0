package com.example.collegebuddyversion2.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "questions_table")
public class QuestionsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("Question")
    private String question;
    @SerializedName("QID")
    private String question_id;
    @SerializedName("ID")
    private String asked_by_name;
    @SerializedName("Datetime")
    private String asked_on_date;
    @SerializedName("Image")
    private String image;

}
