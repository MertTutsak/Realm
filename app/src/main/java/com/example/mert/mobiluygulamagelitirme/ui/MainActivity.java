package com.example.mert.mobiluygulamagelitirme.ui;

import android.app.Activity;
import android.os.Bundle;

import com.example.mert.mobiluygulamagelitirme.R;
import com.example.mert.mobiluygulamagelitirme.data.db.RealmHelper;
import com.example.mert.mobiluygulamagelitirme.data.model.Lesson;

import java.util.ArrayList;

public class MainActivity extends Activity {

    protected ArrayList<Lesson> lessons;

    protected RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realmHelper = new RealmHelper(MainActivity.this);
    }

    public Lesson getLesson(int position) {
        if (realmHelper.getLessons().size() > position) {
            return realmHelper.getLessons().get(position);
        }
        return null;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
}
