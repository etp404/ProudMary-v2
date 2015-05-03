package com.khonsu.enroute.usernotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.NextUpdateFormatter;
import com.khonsu.enroute.R;
import com.khonsu.enroute.settingup.activities.MainActivity;

public class Notifier {
	private static final int NOTIFICATION_ID = 0;
	private static Notifier instance;

	private final NotificationManager notificationManager;
	private Context context;
	private String statusInfo = "Running";
	private String nextUpdateInfo;

	private Notifier(Context context) {
        this.context = context;
		this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public static Notifier getInstance(Context context) {
		if (instance == null) {
			instance = new Notifier(context);
		}
		return instance;
	}

    private void provideNotification() {
		Notification.Builder notificationBuilder = new Notification.Builder(context)
				.setContentTitle(statusInfo)
				.setContentText(nextUpdateInfo)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));

        Notification notification = notificationBuilder.build();

		notification.flags =
				Notification.DEFAULT_LIGHTS |
				Notification.FLAG_NO_CLEAR |
				Notification.FLAG_ONGOING_EVENT;
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	public void notifyUpdateSent() {
		statusInfo = context.getResources().getString(R.string.update_sent_notification);
		provideNotification();
	}

	public void notifyLocationUnavailable() {
		statusInfo = context.getResources().getString(R.string.last_update_failed);
		provideNotification();
	}

	public void notifyUnableToGetEta() {
		statusInfo = context.getResources().getString(R.string.last_update_failed);
		provideNotification();
	}

	public void notifyNextUpdate(long triggerAtMillis) {
		nextUpdateInfo = NextUpdateFormatter.format(triggerAtMillis);
		provideNotification();
	}

	public void clearNotification() {
		notificationManager.cancel(NOTIFICATION_ID);
	}
}
