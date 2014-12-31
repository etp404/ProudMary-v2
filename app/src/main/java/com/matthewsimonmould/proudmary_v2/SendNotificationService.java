package com.matthewsimonmould.proudmary_v2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SendNotificationService extends Service {

    private final NotificationManagerWrapper notificationManagerWrapper;

    public SendNotificationService(NotificationManagerWrapper notificationManagerWrapper) {
        this.notificationManagerWrapper = notificationManagerWrapper;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManagerWrapper.provideNotification("");
        return super.onStartCommand(intent, flags, startId);
    }
}
