package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

import java.util.concurrent.TimeUnit;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

    private Context context;

    public StartSwitchListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (isChecked) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, TimeUnit.SECONDS.toMillis(10), PendingIntent.getBroadcast(context, 0, intent, 0));
        }
        else {
            alarmManager.cancel(PendingIntent.getBroadcast(context, 0, intent, 0));
        }
    }
}
