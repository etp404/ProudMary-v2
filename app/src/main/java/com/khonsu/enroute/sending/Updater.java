package com.khonsu.enroute.sending;

import android.location.Location;

import com.khonsu.enroute.Estimate;
import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.events.EventBus;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;
import com.khonsu.enroute.UrlAccessor;
import com.khonsu.enroute.sending.messaging.MessageGenerator;
import com.khonsu.enroute.sending.messaging.SMSSender;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Updater {
	private static long RETRY_IF_FAIL_DELAY = TimeUnit.MINUTES.toMillis(5);
	public static final String UPDATE_SENT = "UPDATE_SENT";
	public static final String UPDATE_ERROR = "ERROR_SENDING_UPDATE";
	public static final String SENDING_COMPLETE = "SENDING_COMPLETE";

	private final UpdaterSettings updaterSettings;
	private final UpdateScheduler updateScheduler;

	public Updater(UpdaterSettings updaterSettings,
				   UpdateScheduler updateScheduler) {
		this.updaterSettings = updaterSettings;
		this.updateScheduler = updateScheduler;
	}

	protected void sendUpdate(Location lastLocation) {
		if (lastLocation == null) {
			retrySendingLater();
		}
		GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(new UrlAccessor());
		try {

			Estimate estimate = googleMapsDurationGetter.getJourneyEstimate(
				String.valueOf(lastLocation.getLatitude()),
				String.valueOf(lastLocation.getLongitude()),
				updaterSettings.getDestination(),
				updaterSettings.getTransportMode());

			MessageGenerator messageGenerator = new MessageGenerator(updaterSettings);
			String message = messageGenerator.generateMessage(
					String.valueOf(lastLocation.getLatitude()),
					String.valueOf(lastLocation.getLongitude()),
					estimate);


			if (updaterSettings.isUpdatesActive()) {
				SMSSender.sendSMS(updaterSettings.getRecipient().getNumber(), message);

				EventBus.getInstance().announce(UPDATE_SENT);

				long triggerAtMillis = new Date().getTime() + updaterSettings.getUpdatePeriodInMillis();
				scheduleNextUpdate(triggerAtMillis);
			}

		} catch (UnableToGetEstimatedJourneyTimeException e) {
			e.printStackTrace();
			if (updaterSettings.isUpdatesActive()) {
				retrySendingLater();
				EventBus.getInstance().announce(UPDATE_ERROR);
			}
		}
		EventBus.getInstance().announce(SENDING_COMPLETE);

	}

	private void scheduleNextUpdate(long triggerAtMillis) {
		updateScheduler.scheduleNextUpdate(triggerAtMillis);
		updaterSettings.setTimeForNextUpdateInMillis(triggerAtMillis);
	}

	private void retrySendingLater() {
		long triggerAtMillis = new Date().getTime() + RETRY_IF_FAIL_DELAY;
		scheduleNextUpdate(triggerAtMillis);
	}
}
