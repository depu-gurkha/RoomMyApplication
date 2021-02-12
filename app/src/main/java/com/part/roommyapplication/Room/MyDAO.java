package com.part.roommyapplication.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.part.roommyapplication.Room.Table.Chapter;
import com.part.roommyapplication.Room.Table.Content;
import com.part.roommyapplication.Room.Table.Quiz;
import com.part.roommyapplication.Room.Table.Subject;

import java.util.List;

@Dao
public interface  MyDAO {
        @Insert
        void insertSubject(Subject[] subject);
        @Insert
        void insertChapter(Chapter[] chapter);
        @Insert
        void insertContent(Content[] content);
        @Insert
        void insertQuiz(Quiz[] quiz);

        //External Database
        //From Web integration to sync SQLite
        @Query("Select * from tblSubject")
        LiveData<List<Subject>> getAllSubject();
        @Query("Select * from tblContent")
        LiveData<List<Content>> getAllContent();
        @Query("Select * from tblChapter")
        LiveData<List<Chapter>> getAllChapter();
        @Query("Select * from tblQuiz")
        LiveData<List<Quiz>> getAllQuiz();
       // From Web integration  to sync SQLite
        //Internal Database
        //From SQLiteJSON
//
//        @Query("Delete From tblSubject")
//        void deleteAllSubject();
//        @Query("Delete From tblContent")
//        void deleteAllContent();
//        @Query("Delete From tblChapter")
//        void deleteAllChapter();
//        @Query("Delete From tblQuiz")
//        void deleteAllQuiz();


}
