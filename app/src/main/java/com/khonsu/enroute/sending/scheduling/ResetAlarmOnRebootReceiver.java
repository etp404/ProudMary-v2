package com.khonsu.enroute.sending.scheduling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.settings.UpdaterSettings;

public class ResetAlarmOnRebootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		UpdaterSettings updaterSettings = new UpdaterSettings(context.getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));
		if (updaterSettings.isUpdatesActive()) {
			UpdateScheduler updateScheduler = new UpdateScheduler(context);
			updateScheduler.scheduleNextUpdate(updaterSettings.getTimeForNextUpdateInMillis());
		}
	}
}
