package com.khonsu.enroute;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.settings.UpdaterSettings;

public class UpdateScheduler {
	public static String SCHEDULE_CHANGE = "schedule_change";
	public static void scheduleNextUpdate(Context context, long triggerAtMillis, UpdaterSettings updaterSettings) {
		PendingIntent pendingIntent = getPendingIntent(context);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

		alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

		updaterSettings.setTimeForNextUpdateInMillis(triggerAtMillis);

		notifyScheduleChange(context);
	}

	public static void cancelUpdate(Context context) {
		PendingIntent pendingIntent = getPendingIntent(context);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
		notifyScheduleChange(context);
	}

	private static void notifyScheduleChange(Context context) {
		context.sendBroadcast(new Intent(SCHEDULE_CHANGE));
	}

	private static PendingIntent getPendingIntent(Context context) {
		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
}
