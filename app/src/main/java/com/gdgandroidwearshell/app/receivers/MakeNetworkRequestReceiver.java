package com.gdgandroidwearshell.app.receivers;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.gdgandroidwearshell.app.services.MakeNetworkRequestWakefulIntentService;

public class MakeNetworkRequestReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, MakeNetworkRequestWakefulIntentService.class);

        startWakefulService(context, service);
    }

}