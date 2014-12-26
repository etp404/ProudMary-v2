package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

    private AlarmManagerWrapper alarmManagerWrapper;
    private Context context;

    public StartSwitchListener(Context context, AlarmManagerWrapper alarmManagerWrapper) {
        this.context = context;
        this.alarmManagerWrapper = alarmManagerWrapper;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Intent intent = new Intent();
        if (isChecked) {
            alarmManagerWrapper.setRepeating(AlarmManager.RTC_WAKEUP, 0, 10000, PendingIntent.getBroadcast(context, 0, intent, 0));
        }
        else {
            alarmManagerWrapper.cancel(PendingIntent.getBroadcast(context, 0, intent, 0));
        }
    }
}
