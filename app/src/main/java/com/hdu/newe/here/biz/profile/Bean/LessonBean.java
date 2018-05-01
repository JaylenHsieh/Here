package com.hdu.newe.here.biz.profile.bean;

/**
 * Created by Jaylen Hsieh on 2018/04/12.
 */
public class LessonBean {
    private String lesson;
    private String state;

    public LessonBean(String lesson,String state){
        this.lesson = lesson;
        this.state = state;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
