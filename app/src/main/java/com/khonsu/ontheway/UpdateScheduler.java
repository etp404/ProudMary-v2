package com.khonsu.ontheway;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.khonsu.ontheway.settings.UpdaterSettings;

public class UpdateScheduler {
	public static void scheduleNextUpdate(Context context, long triggerAtMillis, UpdaterSettings updaterSettings) {
		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

		alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

		updaterSettings.setTimeForNextUpdateInMillis(triggerAtMillis);
	}
}
