package com.part.roommyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.part.roommyapplication.MyQuiz.PlayActivity;
import com.part.roommyapplication.NestedRecycler.LectureChapterActivity;

import com.part.roommyapplication.Room.MyViewModel;
import com.part.roommyapplication.Room.Repository;
import com.part.roommyapplication.Room.Table.Chapter;
import com.part.roommyapplication.Room.Table.Content;
import com.part.roommyapplication.Room.Table.NestedList;
import com.part.roommyapplication.Room.Table.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyViewModel myViewModel;
    Repository repository;
    Button btn,btnNext,btnQuiz;
    ArrayList<Chapter> chap_list;
    ArrayList<Content> content_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btnDisplay);
        btnNext=findViewById(R.id.btnNext);
        btnQuiz=findViewById(R.id.btnQuiz);
        myViewModel = new MyViewModel(getApplication(), this);
        myViewModel.getVolleyDetails();
        repository = new Repository(getApplication(), this);
        chap_list=new ArrayList<>();
        content_list = new ArrayList<>();
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, LectureChapterActivity.class);
                intent.putExtra("chapterList",(Serializable)chap_list);
                intent.putExtra("ContentList",(Serializable)content_list);
                Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<List<Subject>> chapterList = myViewModel.getAllSubjects();
                Log.d("done", chapterList.toString());
                repository.getAllChapter().observe(MainActivity.this, new Observer<List<Chapter>>() {
                    @Override
                    public void onChanged(List<Chapter> chapters) {
                        Log.d("Chapter", String.valueOf(chapters.size()));
                        for (int i = 0; i < chapters.size(); i++) {
                            Chapter c = chapters.get(i);
                            Toast.makeText(MainActivity.this, "Chapter Added", Toast.LENGTH_SHORT).show();
                            repository.getAllContent().observe(MainActivity.this, new Observer<List<Content>>() {
                                @Override
                                public void onChanged(List<Content> contents) {

                                    Log.d("Content", String.valueOf(contents.size()));

                                    for (int j = 0; j < contents.size(); j++) {
                                        Content ct = contents.get(j);
                                        if(c.getChapterNo()==ct.getChapterNo());
                                        {
                                            content_list.add(ct);
                                        }
                                    }
                                }
                            });
                            chap_list.add(c);
                        }
                    }
                });
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                ArrayList<NestedList> nestedList = generateList(chap_list,content_list);
                Log.d("Nestedlist", String.valueOf(nestedList.size()));
                for (int i = 0; i < nestedList.size(); i++) {
                    Log.d("chapDesc", nestedList.get(i).getChapterDesc());
                    Log.d("chapTitle", nestedList.get(i).getChapterTitle());
                    Log.d("chapDesc", String.valueOf(nestedList.get(i).getChapterNo()));
                    Log.d("contentDesc", nestedList.get(i).getContentDesc());
                    Log.d("contentTttle", nestedList.get(i).getContentTitle());
                    Log.d("type", nestedList.get(i).getType());
                    Log.d("file", nestedList.get(i).getFile());
                    Log.d("id", String.valueOf(nestedList.get(i).getId()));
                }
                //Generate the combineList

//                repository.getAllSubject().observe(MainActivity.this, new Observer<List<Subject>>() {
//                    @Override
//                    public void onChanged(List<Subject> subjects) {
//                        Log.d("Subject", String.valueOf(subjects.size()));
//                        for (int i = 0; i < subjects.size(); i++) {
//                            Subject s = subjects.get(i);
//                            Log.d("SubDesc", s.getDesc());
//                            Log.d("Subtit", s.getTitle());
//                            Log.d("SubSubid", String.valueOf(s.getSubjectId()));
//
//
//
//                        }
//                    }
//                });

            }
        });

    }



    public ArrayList<NestedList> generateList(ArrayList<Chapter> chap_list,ArrayList<Content> content_list) {
        ArrayList<NestedList> combineList = new ArrayList();
        for (int i = 0; i < chap_list.size(); i++) {
            String chapDesc = chap_list.get(i).getDesc();
            String chapTitle = chap_list.get(i).getTitle();
            int chapterNo = chap_list.get(i).getChapterNo();

            for (int j = 0; j < content_list.size(); j++) {
                if(chapterNo==content_list.get(j).getChapterNo()) {
                    int contentId = content_list.get(j).getContentID();
                    String contentDesc = content_list.get(j).getDesc();
                    String contentTitle = content_list.get(j).getTitle();
                    String type = content_list.get(j).getType();
                    int id = content_list.get(j).getId();
                    String file = content_list.get(j).getFile();
                    NestedList nestedList = new NestedList(chapterNo, chapDesc, chapTitle, id, contentId, type, contentTitle, contentDesc, file);
                    combineList.add(nestedList);
                }

            }

        }

        return combineList;
    }

}