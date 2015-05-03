package com.khonsu.enroute.sending;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.sending.UpdaterService;

public class SendUpdateBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, UpdaterService.class);
        context.startService(serviceIntent);
    }
}
