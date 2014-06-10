package com.gdgandroidwearshell.app.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.*;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gdgandroidwearshell.app.R;
import com.gdgandroidwearshell.app.model.Mom;
import com.gdgandroidwearshell.app.receivers.MakeNetworkRequestReceiver;
import com.gdgandroidwearshell.app.receivers.ProcessActionReceiver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Date;

public class MakeNetworkRequestWakefulIntentService extends IntentService {

    public static final String EXTRA_IDENTIFIER_CALL = "EXTRA_IDENTIFIER_CALL";
    public static final String EXTRA_IDENTIFIER_EMAIL = "EXTRA_IDENTIFIER_EMAIL";
    public static final int NOTIFICATION_ID = 133713;

    public MakeNetworkRequestWakefulIntentService() {
        super("MakeNetworkRequestWakefulIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Mom mom = getMomFromServer();

        makeNotification(mom);

        MakeNetworkRequestReceiver.completeWakefulIntent(intent);
    }

    private Mom getMomFromServer() {
        String momString = getJsonFromServer("https://www.dropbox.com/meta_dl/eyJzdWJfcGF0aCI6ICIiLCAidGVzdF9saW5rIjogZmFsc2UsICJzZXJ2ZXIiOiAiZGwuZHJvcGJveHVzZXJjb250ZW50LmNvbSIsICJpdGVtX2lkIjogbnVsbCwgImlzX2RpciI6IGZhbHNlLCAidGtleSI6ICJxdGIyY2k4eDFmbzVqanEifQ/AANUUXmOb07sduUfP0vu9It2qnI8VnijAztsQXwuYEZ1Mg?dl=1");

        Gson gson = new GsonBuilder().create();

        Mom mom = gson.fromJson(momString, Mom.class);

        return mom;
    }

    private void makeNotification(Mom mom) {

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText("Lots of Text, This Is the BigStyle.bigText field, will show lots of text once user pulls it down, oh yeah!");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(mom.getName())
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher))
                .setContentText("Content Text")
                .setContentInfo("Content Info")
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(new Date().getTime())
                .addAction(R.drawable.ic_action_call, "Call", getPendingIntentForActionCall(mom.getPhoneNumber()))
                .addAction(R.drawable.ic_action_email, "Email", getPendingIntentForActionEmail(mom.getEmailAddress()))
                .setStyle(bigStyle);

        //send to Android notificaiton system
        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);

        //send to Android Wear
        Notification wearableNotification = new WearableNotifications.Builder(builder)
                        .setHintHideIcon(false)
                        .build();

        NotificationManagerCompat notificationCompatManager = NotificationManagerCompat.from(this);
        notificationCompatManager.notify(NOTIFICATION_ID, wearableNotification);
    }

    private PendingIntent getPendingIntentForActionCall(String phone) {
        Intent intent = new Intent(getApplicationContext(), ProcessActionReceiver.class);

        intent.putExtra(EXTRA_IDENTIFIER_CALL, phone);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        pendingIntent.cancel();

        return pendingIntent;
    }

    private PendingIntent getPendingIntentForActionEmail(String email) {
        Intent intent = new Intent(getApplicationContext(), ProcessActionReceiver.class);

        intent.putExtra(EXTRA_IDENTIFIER_EMAIL, email);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        pendingIntent.cancel();

        return pendingIntent;
    }

    private String getJsonFromServer(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e("MakeNetworkRequestWakefulIntentService", e.getMessage(), e);
        }

        return "";
    }
}
