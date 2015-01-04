package com.matthewsimonmould.proudmary_v2;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

public class NotificationBuilder {
    public static Notification build(Context context, PendingIntent launchNotification, String contentTitle, String contentText) {
        return new Notification.Builder(context)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.pm_logo)
                .setContentIntent(launchNotification)
                .build();
    }
}
