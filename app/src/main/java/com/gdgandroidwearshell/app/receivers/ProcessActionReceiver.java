package com.gdgandroidwearshell.app.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.gdgandroidwearshell.app.services.MakeNetworkRequestWakefulIntentService;
import com.gdgandroidwearshell.app.services.ProcessActionIntentService;

public class ProcessActionReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, ProcessActionIntentService.class);

        if (intent.hasExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_CALL)) {
            service.putExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_CALL, intent.getStringExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_CALL));
        }

        if (intent.hasExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_EMAIL)) {
            service.putExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_EMAIL, intent.getStringExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_EMAIL));
        }

        startWakefulService(context, service);
    }

}