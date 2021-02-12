package com.part.roommyapplication.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.part.roommyapplication.Room.Table.Chapter;
import com.part.roommyapplication.Room.Table.Content;
import com.part.roommyapplication.Room.Table.Quiz;
import com.part.roommyapplication.Room.Table.Subject;


@Database(entities = {Subject.class, Chapter.class, Content.class, Quiz.class},version = 1)
public abstract class SubjectDatabase extends RoomDatabase {
    public static SubjectDatabase instance;
    public abstract MyDAO myDAO();

    public static synchronized SubjectDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    SubjectDatabase.class,"MyDatabase").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
