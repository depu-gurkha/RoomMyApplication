package com.part.roommyapplication.Room.Table;

public class NestedList {
    private int ChapterNo;
    private String chapterDesc;
    private String chapterTitle;
    private int id;
    private int contentID;
    private String type;
    private String contentTitle;
    private String contentDesc;
    private String file;

    public NestedList(int chapterNo, String chapterDesc, String chapterTitle, int id, int contentID, String type, String contentTitle, String contentDesc, String file) {
        ChapterNo = chapterNo;
        this.chapterDesc = chapterDesc;
        this.chapterTitle = chapterTitle;
        this.id = id;
        this.contentID = contentID;
        this.type = type;
        this.contentTitle = contentTitle;
        this.contentDesc = contentDesc;
        this.file = file;
    }

    public int getChapterNo() {
        return ChapterNo;
    }

    public String getChapterDesc() {
        return chapterDesc;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public int getId() {
        return id;
    }

    public int getContentID() {
        return contentID;
    }

    public String getType() {
        return type;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public String getFile() {
        return file;
    }
}
