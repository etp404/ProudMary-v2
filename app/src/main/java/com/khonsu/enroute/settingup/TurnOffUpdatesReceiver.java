package com.khonsu.enroute.settingup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.events.EventBus;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settings.UpdaterSettings;

public final class TurnOffUpdatesReceiver extends BroadcastReceiver {

	public final static String UPDATES_TURNED_OFF = "updatesTurnedOff";

	@Override
	public void onReceive(Context context, Intent intent) {
		UpdateScheduler updateScheduler = new UpdateScheduler(context);
		updateScheduler.cancelUpdate();

		UpdaterSettings updaterSettings = new UpdaterSettings(context.getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));
		updaterSettings.setUpdatesActive(false);

		EventBus.getInstance().announce(UPDATES_TURNED_OFF);
	}
}
