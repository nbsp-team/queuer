package com.nbsp.queuer;

import android.app.Application;
import android.os.Bundle;

import com.nbsp.queuer.db.DB;

import eu.inloop.easygcm.EasyGcm;
import eu.inloop.easygcm.GcmListener;


/**
 * Класс, описывающий поля и методы типа App.
 */
public class App extends Application implements GcmListener {

    @Override
    public void onCreate() {
        super.onCreate();

        DB.createInstance(this);

//        if (AccountUtils.getInstance(this).isAuthorized()) {
//            initGcm();
//        }
    }

    public void initGcm() {
        EasyGcm.init(this);
    }

    @Override
    public void onMessage(String s, Bundle bundle) {
    }

    @Override
    public void sendRegistrationIdToBackend(String s) {
    }
}
