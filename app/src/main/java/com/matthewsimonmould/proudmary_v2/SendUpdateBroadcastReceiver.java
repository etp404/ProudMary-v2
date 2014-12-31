package com.matthewsimonmould.proudmary_v2;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendUpdateBroadcastReceiver extends BroadcastReceiver {

    public SendUpdateBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, SendNotificationService.class);
        context.startService(serviceIntent);
    }
}
