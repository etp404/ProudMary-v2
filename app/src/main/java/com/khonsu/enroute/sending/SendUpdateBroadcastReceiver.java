package com.khonsu.enroute.sending;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.khonsu.enroute.events.EventBus;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settings.UpdaterSettings;

import java.util.concurrent.Executor;

public class SendUpdateBroadcastReceiver extends BroadcastReceiver {

	public static String UPDATE_SENDING_STARTED = "UPDATE_SENDING_STARTED";

	@Override
	public void onReceive(Context context, Intent intent) {

		EventBus.getInstance().announce(SendUpdateBroadcastReceiver.UPDATE_SENDING_STARTED);

		final Updater updater = new Updater(new UpdaterSettings(context.getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0)),
				new UpdateScheduler(context));
		LocationGetter locationGetter = new LocationGetter();
		locationGetter.getLocation(
				context, new LocationGetter.LocationCallback() {
					@Override
					public void success(final Location location) {
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								updater.sendUpdate(location);
							}
						});
						thread.start();
					}
				});
	}
}
