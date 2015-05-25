package com.khonsu.enroute.sending.scheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.events.EventBus;
import com.khonsu.enroute.sending.SendUpdateBroadcastReceiver;

public class UpdateScheduler {
	public static final String NEXT_UPDATE_SCHEDULED = "NEXT_UPDATE_SCHEDULED";
	private Context context;

	public UpdateScheduler(Context context) {
		this.context = context;
	}
	public void scheduleNextUpdate(long triggerAtMillis) {
		EventBus.getInstance().announce(NEXT_UPDATE_SCHEDULED, triggerAtMillis);

		PendingIntent pendingIntent = createPendingIntent();
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

		alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
	}

	public void cancelUpdate() {
		PendingIntent pendingIntent = createPendingIntent();
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}

	private PendingIntent createPendingIntent() {
		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
}
