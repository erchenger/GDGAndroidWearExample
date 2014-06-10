package com.gdgandroidwearshell.app.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.preview.support.v4.app.NotificationManagerCompat;

import com.gdgandroidwearshell.app.receivers.ProcessActionReceiver;

public class ProcessActionIntentService extends IntentService {

    public ProcessActionIntentService() {
        super("ProcessActionIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        makeCallIfPhoneExists(intent);
        
        sendEmailIfEmailExistes(intent);
        
        //cancel Android notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.cancel(MakeNetworkRequestWakefulIntentService.NOTIFICATION_ID);

        //Cancel Android Wear Notification
        NotificationManagerCompat notificationCompatManager = NotificationManagerCompat.from(this);
        notificationCompatManager.cancel(MakeNetworkRequestWakefulIntentService.NOTIFICATION_ID);

        ProcessActionReceiver.completeWakefulIntent(intent);
    }

    private void makeCallIfPhoneExists(Intent intent) {
        if (!intent.hasExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_CALL)) {
            return;
        }

        String uri = "tel:" + intent.getStringExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_CALL).trim() ;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse(uri));

        startActivity(callIntent);
    }

    private void sendEmailIfEmailExistes(Intent intent) {
        if (!intent.hasExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_EMAIL)) {
            return;
        }

        String email = intent.getStringExtra(MakeNetworkRequestWakefulIntentService.EXTRA_IDENTIFIER_EMAIL);

        Intent send = new Intent(Intent.ACTION_SENDTO);
        send.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String uriText = "mailto:" + Uri.encode(email);
        Uri uri = Uri.parse(uriText);
        send.setData(uri);

        startActivity(Intent.createChooser(send, "Email"));
    }
}
