package com.part.roommyapplication.NestedRecycler;

import java.util.List;

public class ChapterDetail {
    private String chapterTitle;
    private String totalLecture;
    private List<Lecture> lectureList;

    public ChapterDetail(String itemTitle, String totalLecture, List<Lecture> subItemList) {
        this.chapterTitle = itemTitle;
        this.totalLecture = totalLecture;
        this.lectureList = subItemList;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getTotalLecture() {
        return totalLecture;
    }

    public void setTotalLecture(String totalLecture) {
        this.totalLecture = totalLecture;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }
}
