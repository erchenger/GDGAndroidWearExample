package com.gdgandroidwearshell.app;

import android.app.Application;

import com.gdgandroidwearshell.app.scheduler.AlarmScheduler;

public class GDGAndroidWearShell extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AlarmScheduler scheduler = new AlarmScheduler();
        scheduler.scheduleAlarms(getApplicationContext());
    }
}
