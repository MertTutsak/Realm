package com.example.mert.mobiluygulamagelitirme.data.model;

import java.util.ArrayList;

import io.realm.RealmObject;

public class Lesson extends RealmObject {

    private String lessonId;

    private String name;

    private int day;

    private int hour;

    private int during;

    public Lesson() {

    }

    public Lesson(String lessonId) {
        this.lessonId = lessonId;
    }

    public Lesson(String lessonId, String name) {
        this.lessonId = lessonId;
        this.name = name;
    }

    public String getId() {
        return lessonId;
    }

    public void setId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDuring() {
        return during;
    }

    public void setDuring(int during) {
        this.during = during;
    }
}
