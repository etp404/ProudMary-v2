package com.khonsu.enroute;

import android.app.Application;

import com.khonsu.enroute.events.EventBus;
import com.khonsu.enroute.sending.SendUpdateBroadcastReceiver;
import com.khonsu.enroute.sending.Updater;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settingup.TurnOffUpdatesReceiver;
import com.khonsu.enroute.usernotifications.Notifier;

public final class ProudMaryApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		final Notifier notifier = new Notifier(getApplicationContext());
		EventBus.getInstance().register(SendUpdateBroadcastReceiver.UPDATE_SENDING_STARTED, new EventBus.Consumer() {
			@Override
			public void invoke(Object payload) {
				notifier.updateSendingStarted();
			}
		});

		EventBus.getInstance().register(Updater.UPDATE_SENT, new EventBus.Consumer() {
			@Override
			public void invoke(Object payload) {
				notifier.updateSent();
			}
		});

		EventBus.getInstance().register(UpdateScheduler.NEXT_UPDATE_SCHEDULED, new EventBus.Consumer<Long>() {
			@Override
			public void invoke(Long timeInMillis) {
				notifier.nextUpdateScheduled(timeInMillis);
			}
		});

		EventBus.getInstance().register(TurnOffUpdatesReceiver.UPDATES_TURNED_OFF, new EventBus.Consumer() {
			@Override
			public void invoke(Object payload) {
				notifier.clear();
			}
		});
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		EventBus.getInstance().clear();
	}
}
