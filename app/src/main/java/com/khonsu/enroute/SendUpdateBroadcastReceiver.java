package com.khonsu.enroute;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendUpdateBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, UpdaterService.class);
        context.startService(serviceIntent);
    }
}
