package com.khonsu.ontheway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.khonsu.ontheway.settings.UpdaterSettings;

public class ResetAlarmOnRebootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		UpdaterSettings updaterSettings = new UpdaterSettings(context.getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));
		if (updaterSettings.isUpdatesActive()) {
			UpdateScheduler.scheduleNextUpdate(context, updaterSettings.getTimeForNextUpdateInMillis(), updaterSettings);
		}
	}
}
