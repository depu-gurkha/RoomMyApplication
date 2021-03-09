package com.part.roommyapplication.NestedRecycler;

public class Lecture {
    private String lectureTitle;
    private String lectureUrl;

    public Lecture(String lectureTitle, String lectureUrl) {
        this.lectureTitle = lectureTitle;
        this.lectureUrl = lectureUrl;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public void setLectureTitle(String lectureTitle) {
        this.lectureTitle = lectureTitle;
    }

    public String getLectureUrl() {
        return lectureUrl;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }
}
