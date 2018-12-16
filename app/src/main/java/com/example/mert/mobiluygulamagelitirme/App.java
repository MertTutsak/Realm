package com.example.mert.mobiluygulamagelitirme;

import android.app.Application;
import android.os.Environment;

import io.realm.Realm;

public class App extends Application {

    //Root Path
    public static String rootPath;

    @Override
    public void onCreate() {
        super.onCreate();

        //Root Path
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        //RealmHelper
        Realm.init(this);
    }
}
