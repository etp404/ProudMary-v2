package com.khonsu.enroute.usernotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.NextUpdateFormatter;
import com.khonsu.enroute.R;
import com.khonsu.enroute.settingup.TurnOffUpdatesReceiver;
import com.khonsu.enroute.settingup.activities.MainActivity;

public final class Notifier {

	private static final int DEFAULT_NOTIFICATION_ID = 0;
	private static final int ERROR_NOTIFICATION_ID = 1;

	private static final String SENDING_UPDATE_NOTIFICATION = "Sending update";
	private static final String UPDATE_SENT = "Update sent";
	private static final String ERROR_WITH_PREVIOUS_UPDATE = "Error with previous update.";

	private final Context context;
	private final NotificationManager notificationManager;

	public Notifier(Context context) {
		this.context = context;
		this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}


	public void updateSendingStarted() {
		sendNotification(SENDING_UPDATE_NOTIFICATION);
	}

	public void updateSent() {
		sendNotification(UPDATE_SENT);
	}

	public void nextUpdateScheduled(Long timeInMillis) {
		sendCancellableNotification(NextUpdateFormatter.format(timeInMillis));
	}

	public void errorWithPreviousUpdate() {
		sendErrorNotification(ERROR_WITH_PREVIOUS_UPDATE);
	}


	private void sendCancellableNotification(String message) {
		Notification.Builder builder = new Notification.Builder(context)
				.setContentTitle(message)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
				.addAction(R.drawable.ic_action_cancel, "Stop", PendingIntent.getBroadcast(context, 0, new Intent(context, TurnOffUpdatesReceiver.class), 0));

		Notification notification = builder.build();
		notification.flags =
				Notification.DEFAULT_LIGHTS |
						Notification.FLAG_NO_CLEAR |
						Notification.FLAG_ONGOING_EVENT;
		notificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
	}

	private void sendNotification(String message) {
		Notification.Builder builder = new Notification.Builder(context)
				.setContentTitle(message)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));

		Notification notification = builder.build();
		notification.flags =
				Notification.DEFAULT_LIGHTS |
						Notification.FLAG_NO_CLEAR |
						Notification.FLAG_ONGOING_EVENT;
		notificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);	}

	private void sendErrorNotification(String message) {
		Notification.Builder builder = new Notification.Builder(context)
				.setContentTitle(message)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));

		Notification notification = builder.build();
		notification.flags = Notification.DEFAULT_LIGHTS;
		notificationManager.notify(ERROR_NOTIFICATION_ID, notification);
	}

	public void clear() {
		notificationManager.cancel(DEFAULT_NOTIFICATION_ID);
		notificationManager.cancel(ERROR_NOTIFICATION_ID);
	}
}
