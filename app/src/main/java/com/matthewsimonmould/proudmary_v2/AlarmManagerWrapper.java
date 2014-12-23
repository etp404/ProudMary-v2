package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;

public class AlarmManagerWrapper {
    private final AlarmManager alarmManager;

    public AlarmManagerWrapper(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    public void setRepeating(int type, long triggerAtMillis, long intervalMillis, PendingIntent operation) {
        alarmManager.setRepeating(type, triggerAtMillis, intervalMillis, operation);
    }
}
