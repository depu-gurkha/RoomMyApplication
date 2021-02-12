package com.part.roommyapplication.Room;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.part.roommyapplication.MainActivity;
import com.part.roommyapplication.Room.Table.Chapter;
import com.part.roommyapplication.Room.Table.Content;
import com.part.roommyapplication.Room.Table.Quiz;
import com.part.roommyapplication.Room.Table.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyViewModel extends AndroidViewModel implements ViewModelStoreOwner {
    private Repository repository;
    private LiveData<List<Subject>> allSubjects;
    private LiveData<List<Content>> allContents;
    private LiveData<List<Chapter>> allChapters;
    private LiveData<List<Quiz>> allQuizess;

    public MyViewModel(@NonNull Application application, Activity activity) {
        super(application);
        repository = new Repository(application, activity);
        allChapters=repository.getAllChapter();
        allSubjects=repository.getAllSubject();
        allContents=repository.getAllContent();
        allQuizess=repository.getAllQuiz();
    }

    public void getVolleyDetails() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        //Toast.makeText(getApplication(),path, Toast.LENGTH_LONG).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://classroom.rkmshillong.org/ClassRoom/getContent?courseID=1034", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Toast.makeText(getApplication(), "Inside Volley", Toast.LENGTH_SHORT).show();

                                JSONObject obj = response.getJSONObject(i);
                                int subjectID = obj.getInt("subjectID");
                                String title = obj.getString("title");
                                String description = obj.getString("description");
                                Subject subject = new Subject(subjectID, title, description);
                                Toast.makeText(getApplication(), "chapter", Toast.LENGTH_SHORT).show();
                                insertSubject(subject);
                                JSONArray chapterArray = obj.getJSONArray("chapter");
                                for (int j = 0; j < chapterArray.length(); j++) {
                                    JSONObject chapterObject = chapterArray.getJSONObject(j);
                                    int chapterId = chapterObject.getInt("chapterID");
                                    int subjectId = chapterObject.getInt("subjectID");
                                    int items = chapterObject.getInt("items");
                                    String desc = chapterObject.getString("description");
                                    String chapterTitle = chapterObject.getString("title");
                                    Chapter chapter = new Chapter(chapterId, desc, chapterTitle, items, subjectId);
                                    insertChapter(chapter);
                                    JSONArray contentArray = chapterObject.getJSONArray("content");
                                    for (int k = 0; k < contentArray.length(); k++) {
                                        Log.d("checking", contentArray.toString());
                                        JSONObject contentObject = contentArray.getJSONObject(k);
                                        int id = contentObject.getInt("contentCode");
                                        int contentId = contentObject.getInt("contentID");
                                        int contentChapterId = contentObject.getInt("chapterID");
                                        String type = contentObject.getString("type");
                                        String contentTitle = contentObject.getString("title");
                                        String contentDesc = contentObject.getString("description");
                                        String file = contentObject.getString("file");
                                        Content content = new Content(id, contentId, contentChapterId, type, contentTitle, contentDesc, file);
                                        insertContent(content);
                                        if(type.equals("quiz")) {

                                            JSONArray quizArray = contentObject.getJSONArray("questions");
                                            for(int l=0;l<quizArray.length();l++){
                                                JSONObject quizObject=quizArray.getJSONObject(l);
                                                int qId=quizObject.getInt("questionID");
                                                int qContentID=quizObject.getInt("contentID");
                                                String question= quizObject.getString("question");
                                                String option1=quizObject.getJSONObject("answers").getString("a");
                                                String option2=quizObject.getJSONObject("answers").getString("b");
                                                String option3=quizObject.getJSONObject("answers").getString("c");
                                                String option4=quizObject.getJSONObject("answers").getString("d");
                                                String correctAns=quizObject.getString("correctAnswer");
                                                int ans=0;
                                                switch(correctAns){
                                                    case "a":
                                                        ans=1;
                                                        break;
                                                    case "b":
                                                        ans=2;
                                                        break;
                                                    case "c":
                                                        ans=3;
                                                        break;
                                                    case "d":
                                                        ans=4;
                                                        break;
                                                }
                                                Quiz quiz=new Quiz(qId,qContentID,question,option1,option2,option3,option4,ans);
                                                Log.d("Quiz",quiz.toString());
                                                insertQuiz(quiz);
                                                Toast.makeText(getApplication(), "Quiz Inserted", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "Error Loading Please Load Again", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    public void insertSubject(final Subject subject) {
        Toast.makeText(getApplication(), "Inserted Chapter", Toast.LENGTH_SHORT).show();
        repository.insertSubject(subject);
    }

    public void insertChapter(final Chapter chapter) {
        Toast.makeText(getApplication(), "Inserted Chapter", Toast.LENGTH_SHORT).show();
        repository.insertChapter(chapter);
    }

    public void insertContent(final Content content) {
        Toast.makeText(getApplication(), "Inserted Chapter", Toast.LENGTH_SHORT).show();
        repository.insertContent(content);
    }

    public void insertQuiz(final Quiz quiz) {
        Toast.makeText(getApplication(), "Inserted Chapter", Toast.LENGTH_SHORT).show();
        repository.insertQuiz(quiz);
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return allSubjects;
    }

    public LiveData<List<Content>> getAllContents() {
        return allContents;
    }

    public LiveData<List<Chapter>> getAllChapters() {
        return allChapters;
    }

    public LiveData<List<Quiz>> getAllQuiz() {
        return allQuizess;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return null;
    }
}
