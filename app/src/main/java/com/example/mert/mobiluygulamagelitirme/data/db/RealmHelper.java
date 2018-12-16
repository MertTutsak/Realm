package com.example.mert.mobiluygulamagelitirme.data.db;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.mert.mobiluygulamagelitirme.data.model.Lesson;

import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javax.crypto.NoSuchPaddingException;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {

    //Activity
    private Activity activity;

    //RealmHelper
    private io.realm.Realm realm;

    public RealmHelper(Activity activity) {

        this.realm = io.realm.Realm.getDefaultInstance();
    }

    public boolean saveData(RealmObject realmObject) {
        this.realm.beginTransaction();
        this.realm.insert(realmObject);
        this.realm.commitTransaction();
        return true;
    }

    public boolean updateData(RealmObject realmObject) {
        /* ------- */
        return true;
    }

    public RealmResults query(RealmQuery query) {
        return query.findAll();
    }

    public RealmResults queryAsync(RealmQuery query) {
        return query.findAllAsync();
    }

    public boolean isHave(RealmQuery query) {
        if (query.findAll().size() < 1) {
            Log.d(this.getClass().getSimpleName(), "isHave : false");
            return false;
        }

        Log.d(this.getClass().getSimpleName(), "isHave : true");
        return true;
    }

    public boolean isHaveByUserName(String lessonId) {
        RealmResults<Lesson> users = getRealm().where(Lesson.class).contains("lessonId", lessonId).findAll();

        if (users.size() < 1) {
            return false;
        } else {
            return true;
        }
    }

    public io.realm.Realm getRealm() {
        return this.realm;
    }

    public ArrayList<Lesson> getLessons() {
        RealmResults<Lesson> results = realm.where(Lesson.class).findAll();

        ArrayList<Lesson> lessonArrayList = new ArrayList<>();

        for (Lesson lesson : results) {
            Log.i(this.getClass().getSimpleName(), "Get Lesson Name => " + lesson.getName() + " Day => " + lesson.getDay() + " Hour => " + lesson.getHour() + " During => " + lesson.getDuring());
            lessonArrayList.add(lesson);
        }

        return lessonArrayList;
    }

    public boolean deleteLesson(final String lessonId, final com.example.mert.mobiluygulamagelitirme.data.db.Realm.Delete listener) {
        final RealmResults<Lesson> results = realm.where(Lesson.class).equalTo("lessonId", lessonId).findAll();

        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();

        if (results.size() > 0) {
            listener.OnDeleteFailed();
        } else {
            listener.OnDeleteSuccess();
        }

        return true;
    }

    public void updateLesson(final Lesson lesson, final com.example.mert.mobiluygulamagelitirme.data.db.Realm.Edit listener) {
        RealmResults<Lesson> results = realm.where(Lesson.class).equalTo("lessonId", lesson.getId()).findAll();
        if (results.size() > 0) {
            realm.beginTransaction();
            for (Lesson object : results) {
                object.setName(lesson.getName());
                object.setDay(lesson.getDay());
                object.setHour(lesson.getHour());
                object.setDuring(lesson.getDuring());
            }
            realm.commitTransaction();
            listener.OnEditSuccess();
        } else {
            listener.OnEditFailed();
        }
    }

    public void addLesson(Lesson lesson, final com.example.mert.mobiluygulamagelitirme.data.db.Realm.Add listener) {
        Log.i(this.getClass().getSimpleName(), "Add Lesson Name => " + lesson.getName() + " Day => " + lesson.getDay() + " Hour => " + lesson.getHour() + " During => " + lesson.getDuring());

        realm.beginTransaction();
        Lesson lessonObject = realm.createObject(Lesson.class); // Create a new object
        lessonObject.setId(lesson.getId());
        lessonObject.setName(lesson.getName());
        lessonObject.setDay(lesson.getDay());
        lessonObject.setHour(lesson.getHour());
        lessonObject.setDuring(lesson.getDuring());
        realm.commitTransaction();

        final RealmResults<Lesson> results = realm.where(Lesson.class).equalTo("lessonId", lesson.getId()).findAll();

        if (results.size() > 0) {
            listener.OnAddSuccess();
        } else {
            listener.OnAddFailed();
        }
    }

}
