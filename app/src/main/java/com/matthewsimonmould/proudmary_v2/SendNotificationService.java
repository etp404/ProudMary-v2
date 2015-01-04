package com.matthewsimonmould.proudmary_v2;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SendNotificationService extends Service {

    private final NotificationManagerWrapper notificationManagerWrapper;
    private final NotificationBuilder notificationBuilder;

    public SendNotificationService(NotificationManagerWrapper notificationManagerWrapper, NotificationBuilder notificationBuilder) {
        this.notificationBuilder = notificationBuilder;
        this.notificationManagerWrapper = notificationManagerWrapper;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = notificationBuilder.build(null, null, null, null);
        notificationManagerWrapper.provideNotification(notification);
        return super.onStartCommand(intent, flags, startId);
    }
}
