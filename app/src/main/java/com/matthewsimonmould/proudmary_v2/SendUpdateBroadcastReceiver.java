package com.matthewsimonmould.proudmary_v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendUpdateBroadcastReceiver extends BroadcastReceiver {
    private final NotificationManagerWrapper notificationManagerWrapper;
    public SendUpdateBroadcastReceiver(NotificationManagerWrapper notificationManagerWrapper) {
        this.notificationManagerWrapper = notificationManagerWrapper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManagerWrapper.provideNotification("Something happened");
    }
}
