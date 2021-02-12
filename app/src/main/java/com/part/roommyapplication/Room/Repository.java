package com.part.roommyapplication.Room;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.part.roommyapplication.Room.Table.Chapter;
import com.part.roommyapplication.Room.Table.Content;
import com.part.roommyapplication.Room.Table.Quiz;
import com.part.roommyapplication.Room.Table.Subject;

import java.util.List;

public class Repository {
    MyDAO myDAO;
    LiveData<List<Subject>> allSubject;
    LiveData<List<Chapter>> allChapter;
    LiveData<List<Content>> allContent;
    LiveData<List<Quiz>> allQuiz;
    public static Activity activity;

    public Repository(Application application, Activity activity) {
        SubjectDatabase database = SubjectDatabase.getInstance(application);
        myDAO = database.myDAO();
        allSubject = myDAO.getAllSubject();
        allChapter = myDAO.getAllChapter();
        allContent = myDAO.getAllContent();
        allQuiz = myDAO.getAllQuiz();
        this.activity = activity;
    }

    //Inserting into the four tables

    public void insertSubject(Subject subject) {
        new InsertSubjectAsyncTask(myDAO).execute(subject);
    }

    public void insertChapter(Chapter chapter) {
        new InsertChapterAsyncTask(myDAO).execute(chapter);
    }

    public void insertContent(Content content) {
        new InsertContentAsyncTask(myDAO).execute(content);
    }



    public void insertQuiz(Quiz quiz) {
        new InsertQuizAsyncTask(myDAO).execute(quiz);
    }

//    public void deleteAllChapter() {
//        new DeleteAllChapterAsyncTask(myDAO).execute();
//    }

    //Methods to dislplay the values of the database
    public LiveData<List<Chapter>> getAllChapter(){
        return allChapter;
    }
    public LiveData<List<Subject>> getAllSubject(){
        return allSubject;
    }
    public LiveData<List<Content>> getAllContent(){
        return allContent;
    }
    public LiveData<List<Quiz>> getAllQuiz(){
        return allQuiz;
    }

    //Displaying from sqlite
    public void displayDetail(){
        new DisplayDetailAsycTask(myDAO).execute();
    }


    public static class InsertSubjectAsyncTask extends AsyncTask<Subject, Void, Void> {
        private MyDAO myDAO;

        private InsertSubjectAsyncTask(MyDAO myDAO) {
            this.myDAO = myDAO;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            myDAO.insertSubject(subjects);
            return null;
        }
    }

    public static class InsertChapterAsyncTask extends AsyncTask<Chapter, Void, Void> {
        private MyDAO myDAO;

        private InsertChapterAsyncTask(MyDAO myDAO) {
            this.myDAO = myDAO;
        }

        @Override
        protected Void doInBackground(Chapter... chapters) {
            myDAO.insertChapter(chapters);
            return null;
        }
    }

    public static class InsertContentAsyncTask extends AsyncTask<Content, Void, Void> {
        private MyDAO myDAO;

        private InsertContentAsyncTask(MyDAO myDAO) {
            this.myDAO = myDAO;
        }

        @Override
        protected Void doInBackground(Content... contents) {
            myDAO.insertContent(contents);
            return null;
        }
    }

    public static class InsertQuizAsyncTask extends AsyncTask<Quiz, Void, Void> {
        private MyDAO myDAO;

        private InsertQuizAsyncTask(MyDAO myDAO) {
            this.myDAO = myDAO;
        }

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            myDAO.insertQuiz(quizzes);
            return null;
        }
    }

    public static class DisplayDetailAsycTask extends AsyncTask<Void,Void,Void>{
               private MyDAO myDAO;
             DisplayDetailAsycTask(MyDAO myDAO){
                 this.myDAO=myDAO;
             }
        @Override
        protected Void doInBackground(Void... voids) {

           LiveData<List<Chapter>> c= myDAO.getAllChapter();
           Log.d("MyChapterSql",c.getValue().toString());

            return null;
        }
    }

    //Deleting all Chapters
//    private static class DeleteAllChapterAsyncTask extends AsyncTask<Void, Void, Void> {
//        private MyDAO myDAO;
//
//        private DeleteAllChapterAsyncTask(MyDAO myDAO) {
//            this.myDAO = myDAO;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            myDAO.deleteAllSubject();
//            return null;
//        }
//    }
}
