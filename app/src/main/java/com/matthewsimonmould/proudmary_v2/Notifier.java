package com.matthewsimonmould.proudmary_v2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

public class Notifier {
    private Context context;

    public Notifier(Context context) {
        this.context = context;
    }

    public void notify(String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Resources resources = context.getResources();
        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setContentTitle(resources.getString(R.string.app_name))
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.pm_logo)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));

        Notification notification = notificationBuilder.build();
        notificationManager.notify(0, notification);
    }
}
