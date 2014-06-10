package com.gdgandroidwearshell.app.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.gdgandroidwearshell.app.services.ScheduleAlarmsWakefulIntentService;

public class ScheduleAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, ScheduleAlarmsWakefulIntentService.class);

        startWakefulService(context, service);
    }

}
