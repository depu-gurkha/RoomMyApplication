package com.part.roommyapplication.Room.Table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblSubject")

public class Subject {
//    @PrimaryKey(autoGenerate=true)
//    private int id;
    @PrimaryKey
    @ColumnInfo(name = "SubjectId")
    private int subjectId;

    @ColumnInfo(name = "CourseTitle")
    String title;
    @ColumnInfo(name = "Description")
    private  String desc;

    public Subject(int subjectId,  String title, String desc) {
        this.subjectId = subjectId;
        this.title = title;
        this.desc = desc;
    }

    public int getSubjectId() {
        return subjectId;
    }

//    public int getId() {
//        return id;
//    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
}
