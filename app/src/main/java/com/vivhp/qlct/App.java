package com.vivhp.qlct;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by vivhp on 10/22/2016.
 * config Application
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
