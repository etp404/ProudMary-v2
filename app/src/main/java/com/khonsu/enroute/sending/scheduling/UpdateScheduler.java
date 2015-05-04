package com.khonsu.enroute.sending.scheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.sending.SendUpdateBroadcastReceiver;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.usernotifications.Notifier;

public class UpdateScheduler {
	private Context context;

	public UpdateScheduler(Context context) {
		this.context = context;
	}
	public void scheduleNextUpdate(long triggerAtMillis) {
		PendingIntent pendingIntent = createPendingIntent(context);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

		alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
	}

	void cancelUpdate(Context context) {
		PendingIntent pendingIntent = createPendingIntent(context);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}

	private PendingIntent createPendingIntent(Context context) {
		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
}
