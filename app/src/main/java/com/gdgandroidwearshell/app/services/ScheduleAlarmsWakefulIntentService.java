package com.gdgandroidwearshell.app.services;

import android.app.IntentService;
import android.content.Intent;

import com.gdgandroidwearshell.app.receivers.ScheduleAlarmReceiver;
import com.gdgandroidwearshell.app.scheduler.AlarmScheduler;

public class ScheduleAlarmsWakefulIntentService extends IntentService {

    public ScheduleAlarmsWakefulIntentService() {
        super("ScheduleAlarmsWakefulIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AlarmScheduler scheduler = new AlarmScheduler();

        scheduler.scheduleAlarms(getApplicationContext());

        ScheduleAlarmReceiver.completeWakefulIntent(intent);
    }

}
