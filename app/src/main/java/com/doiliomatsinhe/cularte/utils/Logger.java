package com.doiliomatsinhe.cularte.utils;

import android.app.Application;

import timber.log.Timber;

public class Logger extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
