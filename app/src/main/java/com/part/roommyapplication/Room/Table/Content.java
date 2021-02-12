package com.part.roommyapplication.Room.Table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tblContent"
        ,foreignKeys = @ForeignKey(entity = Chapter.class,
        parentColumns = "chapterID",childColumns = "ChapterID",

        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),indices = {@Index(value = "contentID",unique = true)}
)
public class Content implements Serializable {
    @PrimaryKey
    private int id;

    private int contentID;
    @ColumnInfo(name = "ChapterID")
    private int chapterNo;
    @ColumnInfo
    private String type;
    private String title;
    @ColumnInfo(name = "Description")
    private String desc;
    @ColumnInfo
    private String file;

    public Content(int id, int contentID, int chapterNo, String type, String title, String desc, String file) {
        this.id = id;
        this.contentID = contentID;
        this.chapterNo = chapterNo;
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public int getContentID() {
        return contentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }


    public String getFile() {
        return file;
    }
}
