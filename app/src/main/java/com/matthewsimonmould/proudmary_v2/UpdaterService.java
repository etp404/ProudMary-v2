package com.matthewsimonmould.proudmary_v2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Date;

public class UpdaterService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String message = new Date().toString(); //TODO: Create right text
        new Notifier(getApplicationContext()).notify(message);
        return 0;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
