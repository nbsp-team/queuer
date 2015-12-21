package com.nbsp.queuer;

import android.app.Application;

import com.nbsp.queuer.db.DB;


/**
 * Класс, описывающий поля и методы типа App.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DB.createInstance(this);
    }
}